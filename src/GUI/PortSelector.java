import java.awt.Component;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.fazecast.jSerialComm.SerialPort;

class MyComboBoxModel extends AbstractListModel implements ComboBoxModel, Event {
	//CommunicationPort selection = null;
	CommunicationPort selection = null;
	private PortScanner port_scanner = null;

	MyComboBoxModel(PortScanner port_scanner){
		this.port_scanner = port_scanner;
		//port_scanner.addEvent(this);
		update();
	}

	public Object getElementAt(int index) {
		return port_scanner.getPorts()[index];
	}

	public int getSize() {
		return port_scanner.getPorts().length;
	}

	public void setSelectedItem(Object anItem) {
		selection = (CommunicationPort) anItem; 
	} 

	public Object getSelectedItem() {
		return selection; 
	}
	
	private boolean isStillSlection() {
		for(int i = 0; i < port_scanner.getPorts().length; i++) {
			if(port_scanner.getPorts()[i].equals(selection))
				return true;
		}
		return false;
	}

	public void update() {
		if(port_scanner.getPorts().length > 0) {
			if((selection == null) || (isStillSlection() == false)) {
				selection = port_scanner.getPorts()[0];
			}
		} else {
			selection = null;
		}
	}


}

class ItemRenderer extends BasicComboBoxRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);
		if(value != null) {
			CommunicationPort item = (CommunicationPort) value;

			setText(item.getName());
			setIcon(null);

		} else {
			setText("No avaible ports");
			setIcon(null);
		}

		return this;
	}
}


public class PortSelector extends JComboBox implements Event, Runnable{
	private MyComboBoxModel mcbm = null;
	private ItemRenderer ir = null;
	private boolean connected = false;
	
	public PortSelector(PortScanner port_scanner){
		mcbm = new MyComboBoxModel(port_scanner);
		ir = new ItemRenderer();
		port_scanner.addEvent(mcbm);
		port_scanner.addEvent(this);

		setModel(mcbm);
		setRenderer(ir);

		//	Runnable runner = new PortScanner(mcbm, this);
		//	Thread threads = new Thread(runner);
		//	threads.start();

		update();
		
		new Thread(this).start();
	}

	public void checkAblility() {
		if(getItemCount() > 0) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}

	public CommunicationPort getSelectedSerialPort() {
		return (CommunicationPort) getSelectedItem(); 
	}

	public void update() {	
		this.updateUI();
		checkAblility();
	}

	
	public void run() {
		while(true) {
			if(connected != getSelectedSerialPort().isOpen()) {
				connected = getSelectedSerialPort().isOpen();
				if(getSelectedSerialPort().isOpen()) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
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
