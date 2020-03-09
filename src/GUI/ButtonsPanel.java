import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel{
	JButton dfu;
	JButton restart;
	JButton command;

	private DataLink data_link = null;
	public ButtonsPanel(DataLink data_link) {
		this.data_link = data_link;

		setLayout(new GridLayout(3,1));
		dfu = new JButton("DFU");
		//dfu.setEnabled(false);
		//		dfu.addKeyListener(this);

		dfu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data_link.sendDFU();
			}
		});

		restart = new JButton("Restart");
		//restart.setEnabled(false);
		//		restart.addKeyListener(this);
		//
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data_link.sendReset();
			}
		});

		command = new JButton("Command");
		//command.setEnabled(false);
		//		command.addKeyListener(this);
		//
		//		command.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				if(selected_serial_port != null) {
		//					try {
		//						byte data [] = new byte [3];
		//						data[0] = (byte) 0xff;
		//						data[1] = ((Integer) spinner.getValue()).byteValue();
		//						data[2] = (byte) 0xfe;
		//						out.write(data);
		//					} catch (IOException e1) {
		//						//TODO Auto-generated catch block
		//						e1.printStackTrace();
		//					}
		//				}
		//			}
		//		});
		add(dfu);
		add(restart);
		add(command);
	}
}
