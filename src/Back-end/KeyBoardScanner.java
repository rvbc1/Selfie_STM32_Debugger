import java.awt.event.KeyListener;
import java.util.Scanner;

import com.sun.glass.events.KeyEvent;

public class KeyBoardScanner implements KeyListener{
	private DataLink data_link = null;

	public KeyBoardScanner(DataLink data_link) {
		this.data_link = data_link;
	}

	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {
		System.out.println("Key pressed: " + e.getKeyChar());
		data_link.sendChar(e.getKeyChar());
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
