import java.awt.Component;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.fazecast.jSerialComm.SerialPort;

class MyComboBoxModel extends AbstractListModel implements ComboBoxModel, Event {
	SerialPort selection = null;
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
		selection = (SerialPort) anItem; 
	} 

	public Object getSelectedItem() {
		return selection; 
	}

	public void update() {
		if(port_scanner.getPorts().length > 0) {
			selection = port_scanner.getPorts()[0];
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
			SerialPort item = (SerialPort) value;

			setText(item.getDescriptivePortName());
			setIcon(null);

		} else {
			setText("No avaible ports");
			setIcon(null);
		}

		return this;
	}
}


public class PortSelector extends JComboBox implements Event{
	MyComboBoxModel mcbm = null;
	ItemRenderer ir = null;
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

	}

	public void checkAblility() {
		if(getItemCount() > 0) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}

	public SerialPort getSelectedSerialPort() {
		return (SerialPort) getSelectedItem(); 
	}

	public void update() {	
		this.updateUI();
		checkAblility();
	}

}
