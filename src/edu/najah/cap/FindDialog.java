package edu.najah.cap;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindDialog extends JDialog implements ActionListener, KeyListener {
	private final Editor editor;
	JLabel label;
	private JTextField textField;
	private JCheckBox caseSensitive;
	JButton find;
	JButton close;
	private boolean finishedFinding = true;
	private transient Matcher matcher;

	public FindDialog(Editor parent, boolean modal) {
		super(parent, modal);
		editor = parent;
		getContentPane().addKeyListener(this);
		getContentPane().setFocusable(true);
		initComponents();
		setTitle("Find");
		setLocationRelativeTo(parent);
		pack();
	}

	public void showDialog() {
		setVisible(true);
	}

	private void initComponents() {
		setLayout(new GridLayout(3, 1));
		JPanel panel1 = new JPanel();
		label = new JLabel("Find : ");
		label.setDisplayedMnemonic('F');
		panel1.add(label);
		textField = new JTextField(15);
		panel1.add(textField);
		label.setLabelFor(textField);
		add(panel1);
		JPanel panel2 = new JPanel();
		caseSensitive = new JCheckBox("Case sensitive");
		panel2.add(caseSensitive);
		add(panel2);
		JPanel panel3 = new JPanel();
		find = new JButton("Find");
		close = new JButton("Close");
		find.addActionListener(this);
		close.addActionListener(this);
		panel3.add(find);
		panel3.add(close);
		add(panel3);
		textField.addKeyListener(this);
		find.addKeyListener(this);
		close.addKeyListener(this);
		caseSensitive.addKeyListener(this);
	}

	    private void find(String pattern) {
        if (pattern.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Search string cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matcher.find()) {
            if (!finishedFinding) {
                JOptionPane.showMessageDialog(this, "No more matches found", "Info", JOptionPane.INFORMATION_MESSAGE);
                finishedFinding = true;
            }
            return;
        }

        int start = matcher.start();
        int end = matcher.end();
        editor.textPanel.select(start, end);
        editor.textPanel.requestFocusInWindow();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Find")) {
			String input = textField.getText();
			StringBuilder pattern = new StringBuilder();
			if (!caseSensitive.isSelected()) {
				pattern.append("(?i)");
			}
			pattern.append(input);
			matcher = Pattern.compile(pattern.toString()).matcher(editor.textPanel.getText());
			finishedFinding = false;
			find(pattern.toString());
		} else if (cmd.equals("Close")) {
			closeDialog();
		}
	}

	private void closeDialog() {
		setVisible(false);
		dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		throw new UnsupportedOperationException("keyTyped not supported");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			closeDialog();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		throw new UnsupportedOperationException("keyReleased not supported");
	}
}

