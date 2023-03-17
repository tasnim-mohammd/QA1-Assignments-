package edu.najah.cap;

import edu.najah.cap.ex.EditorSaveException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.logging.Logger;


public class Editor extends JFrame implements ActionListener, DocumentListener {

	private static final Logger logger = Logger.getLogger(Editor.class.getName());

	protected JEditorPane textPanel;
	private final JMenuBar menu;
	private boolean changeStatus = false;



	private File file;

	private final String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};


	public Editor() {

		setTitle("Simple Editor");
		textPanel = new JEditorPane();

		add(new JScrollPane(textPanel), "Center");
		textPanel.getDocument().addDocumentListener(this);

		menu = new JMenuBar();
		setJMenuBar(menu);
		buildMenu();

		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void buildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {
      JMenu jFileMenu;

		jFileMenu = new JMenu("File");
		jFileMenu.setMnemonic('F');
		menu.add(jFileMenu);
		JMenuItem n = new JMenuItem(actions[2]);
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		jFileMenu.add(n);
		JMenuItem open = new JMenuItem(actions[0]);
		jFileMenu.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem(actions[1]);
		jFileMenu.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveAs = new JMenuItem(actions[5]);
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		jFileMenu.add(saveAs);
		saveAs.addActionListener(this);
		JMenuItem quit = new JMenuItem(actions[4]);
		jFileMenu.add(quit);
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

	}

	private void buildEditMenu() {
		JMenu edit = new JMenu(actions[3]);
		menu.add(edit);
		edit.setMnemonic('E');
		JMenuItem cut = new JMenuItem("Cut");
		cut.addActionListener(this);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cut.setMnemonic('T');
		edit.add(cut);
		JMenuItem copy = new JMenuItem("Copy");
		copy.addActionListener(this);
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		edit.add(copy);
		JMenuItem paste= new JMenuItem("Paste");
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(paste);
		paste.addActionListener(this);

		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic('F');
		find.addActionListener(this);
		edit.add(find);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		JMenuItem selectAll = new JMenuItem("Select All");
		selectAll.setMnemonic('A');
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		selectAll.addActionListener(this);
		edit.add(selectAll);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
			case "Exit" -> handleExitAction();
			case "Load" -> handleLoadFileAction();
			case "Save" -> handleSaveFileAction();
			case "New" -> handleNewFileAction();
			case "Save As" -> handleSaveAsAction();
			case "Select All" -> handleSelectAllAction();
			case "Copy" -> handleCopyAction();
			case "Cut" -> handleCutAction();
			case "Paste" -> handlePasteAction();
			case "Find" -> handleFindAction();
			default -> logger.info("The entered action is not in the list");
		}
	}

	private void handleExitAction() {
		System.exit(0);
	}

	private void handleLoadFileAction() {
		loadFile();
	}

	private void handleSaveFileAction() {
		int ans = 0;
		if (changeStatus) {
			ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		if (ans != 1) {
			if (file == null) {
				saveAs(actions[1]);
			} else {
				String text =textPanel.getText();
				try (FileWriter writer = new FileWriter(file)) {
					if (!file.canWrite()) {
						throw new EditorSaveException("Cannot write file!");
					}
					writer.write(text);
					changeStatus = false;
				} catch (IOException ex) {
					ex.printStackTrace();
				}


			}
		}
	}



	private void handleNewFileAction() {
		if (changeStatus) {
			handleSaveFileAction();
		}
		file = null;
		textPanel.setText("");
		changeStatus = false;
		setTitle("Editor");
	}

	private void handleSaveAsAction() {
		saveAs(actions[5]);
	}

	private void handleSelectAllAction() {
		textPanel.selectAll();
	}

	private void handleCopyAction() {
		textPanel.copy();
	}

	private void handleCutAction() {
		textPanel.cut();
	}

	private void handlePasteAction() {
		textPanel.paste();
	}

	private void handleFindAction() {
		FindDialog find = new FindDialog(this, true);
		find.showDialog();
	}



	private void saveFile() {
		if (file == null) {
			saveAs(actions[1]);
			return;
		}
		String text = textPanel.getText();
		logger.info(text);
		try (PrintWriter writer = new PrintWriter(file)) {
			if (!file.canWrite())
				throw new EditorSaveException("Cannot write file!");
			writer.write(text);
			changeStatus = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadFile() {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setMultiSelectionEnabled(false);
		try {
			int result = dialog.showOpenDialog(this);

			if (result != 0) {
				return;
			}

			if (changeStatus) {
				int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (ans == 1) {
					return;
				}
				saveFile();
			}

			file = dialog.getSelectedFile();

			textPanel.setText(readFile(file));
			changeStatus = false;
			setTitle("Editor - " + file.getName());

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private String readFile(File file) throws IOException {
		StringBuilder rs = new StringBuilder();
		try (FileReader fr = new FileReader(file);
			 BufferedReader reader = new BufferedReader(fr)) {
			String line;
			while ((line = reader.readLine()) != null) {
				rs.append(line).append("\n");
			}
		}
		return rs.toString();
	}


	private void saveAs(String dialogTitle) {
		dialogTitle = dialogTitle.toUpperCase();
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != 0) {
			return;
		}
		file = dialog.getSelectedFile();
		try (PrintWriter writer = new PrintWriter(file)) {
			writer.write(textPanel.getText());
			changeStatus = false;
			setTitle("Editor - " + file.getName());
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		changeStatus = true;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changeStatus = true;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changeStatus = true;
	}

}
