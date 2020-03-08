import java.awt.event.KeyListener;
import java.util.Scanner;

import com.sun.glass.events.KeyEvent;

public class KeyBoardScanner implements KeyListener{


	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {
		System.out.println(e.getKeyChar());
		
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
