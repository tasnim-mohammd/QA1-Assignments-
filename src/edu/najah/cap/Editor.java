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

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, DocumentListener {

	private static final Logger logger = Logger.getLogger(Editor.class.getName());

	protected JEditorPane textPanel;
	private JMenuBar menu;
	private boolean changeStatus = false;



	protected File file;

	private String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};

	protected JMenu jmfile;

	public Editor() {
		//Editor the name of our application
		super("Editor");
		 textPanel = new JEditorPane();
		// center means middle of container.
		add(new JScrollPane(textPanel), "Center");
		textPanel.getDocument().addDocumentListener(this);

			menu = new JMenuBar();
		setJMenuBar(menu);
		buildMenu();
		//The size of window
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void buildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {

		jmfile = new JMenu("File");
		jmfile.setMnemonic('F');
		menu.add(jmfile);
		JMenuItem n = new JMenuItem(actions[2]);
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		jmfile.add(n);
		JMenuItem open = new JMenuItem(actions[0]);
		jmfile.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem(actions[1]);
		jmfile.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveas = new JMenuItem(actions[5]);
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		jmfile.add(saveas);
		saveas.addActionListener(this);
		JMenuItem quit = new JMenuItem(actions[4]);
		jmfile.add(quit);
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
		JMenuItem sall = new JMenuItem("Select All");
		sall.setMnemonic('A');
		sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		sall.addActionListener(this);
		edit.add(sall);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
			case "Exit":
				handleExitAction();
				break;
			case "Load":
				handleLoadFileAction();
				break;
			case "Save":
				handleSaveFileAction();
				break;
			case "New":
				handleNewFileAction();
				break;
			case "Save As":
				handleSaveAsAction();
				break;
			case "Select All":
				handleSelectAllAction();
				break;
			case "Copy":
				handleCopyAction();
				break;
			case "Cut":
				handleCutAction();
				break;
			case "Paste":
				handlePasteAction();
				break;
			case "Find":
				handleFindAction();
				break;
			default:
                                logger.info("The entered action is not in the list");
		
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
			ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
		}
		if (ans != 1) {
			if (file == null) {
				saveAs(actions[1]);
			} else {
				String text =textPanel.getText();
				try {
					FileWriter writer = new FileWriter(file);
					if (!file.canWrite()) {
						throw new EditorSaveException("Cannot write file!");
					}
					writer.write(text);
					writer.close();
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
		try (PrintWriter writer = new PrintWriter(file);) {
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
				int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
				if (ans == 1) {
					return;
				}
				saveFile();
			}

			file = dialog.getSelectedFile();

			StringBuilder rs = new StringBuilder(readFile(file));

			textPanel.setText(rs.toString());
			changeStatus = false;
			setTitle("Editor - " + file.getName());

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Error", 0);
		}
	}
	private String readFile(File file) throws IOException {
		StringBuilder rs = new StringBuilder();
		try (FileReader fr = new FileReader(file);
			 BufferedReader reader = new BufferedReader(fr);) {
			String line;
			while ((line = reader.readLine()) != null) {
				rs.append(line + "\n");
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
