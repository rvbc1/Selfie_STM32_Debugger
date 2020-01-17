import java.awt.Component;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.fazecast.jSerialComm.SerialPort;

class MyComboBoxModel extends AbstractListModel implements ComboBoxModel {
	SerialPort all_ports [] = SerialPort.getCommPorts();
	SerialPort selection = null;

	MyComboBoxModel(){
		update();
	}

	public Object getElementAt(int index) {
		return all_ports[index];
	}

	public int getSize() {
		return all_ports.length;
	}

	public void setSelectedItem(Object anItem) {
		selection = (SerialPort) anItem; 
	} 

	public Object getSelectedItem() {
		return selection; 
	}
	

	
	private void update() {
		SerialPort all_ports [] = SerialPort.getCommPorts();
		if(all_ports.length > 0) {
			selection = all_ports[0];
		} else {
			selection = null;
		}
	}


}

class ItemRenderer extends BasicComboBoxRenderer {
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


public class PortSelector extends JComboBox{
	public PortSelector(){
		setModel(new MyComboBoxModel());
		setRenderer(new ItemRenderer());
		
		
		if(getItemCount() > 0) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}
	
	public SerialPort getSelectedSerialPort() {
		return (SerialPort) getSelectedItem(); 
	}
	
}
