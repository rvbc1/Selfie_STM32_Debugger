import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ConnectButton extends JButton implements Runnable{
	PortSelector port_selector;
	DataLink data_link;
	private boolean connected = false;
	
	public ConnectButton(PortSelector port_selector, DataLink data_link) {
		setText("connect");
		this.port_selector = port_selector;
		this.data_link = data_link;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(connected) {
					port_selector.getSelectedSerialPort().tryClose();
				} else {
					port_selector.getSelectedSerialPort().tryOpen();
				}

			}
		});

		//		Runnable button_runner = this;
		//		Thread button_thread = new Thread(button_runner);
		//		button_thread.start();
		new Thread(this).start();
	}
	
	private void update() {
		if(connected) {
			setText("disconnect");
		} else {
			setText("connect");
		}
	}

	public void run() {
		while(true) {
			if(port_selector.getSelectedSerialPort().isOpen() != connected) {
				connected = port_selector.getSelectedSerialPort().isOpen();
				update();
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

