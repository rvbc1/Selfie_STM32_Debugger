import java.awt.Color;

import javax.swing.JTextArea;

public class Console extends JTextArea {
	public Console() {
		setEditable(false);
		setColumns(40);
		setRows(20);
		setBackground(new Color(0x333333));
		setForeground(new Color(0xffffff));
		setText("Console");
	//	addKeyListener(this);
	}
}
