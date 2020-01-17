import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ConnectButton extends JButton{
	PortSelector port_selector;
	DataLink data_link;
	public ConnectButton(PortSelector port_selector, DataLink data_link) {
		setText("connect");
		this.port_selector = port_selector;
		this.data_link = data_link;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(data_link.isOpen()) {
					data_link.closePort();
					setText("connect");
					port_selector.setEnabled(true);
				} else {
					if(data_link.tryOpen(port_selector.getSelectedSerialPort())) {
						setText("disconnect");
						port_selector.setEnabled(false);
					}
				}
			}
		});
	}
}

