import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextArea;

public class Console extends JTextArea implements Runnable{
	private DataLink data_link;
	
	public Console(DataLink data_link) {
		//this.inputstream = inputstream;
		this.data_link = data_link;
		setEditable(false);
		setColumns(40);
		setRows(20);
		setBackground(new Color(0x333333));
		setForeground(new Color(0xffffff));
		setText("Console");
		
		new Thread(this).start();
	}


	public void run() {
		while (true) {
			if(this.getText().equals(data_link.getConsole()) == false) {
				setText(data_link.getConsole());
			}
				
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
