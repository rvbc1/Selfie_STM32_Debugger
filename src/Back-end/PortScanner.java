import java.util.Vector;

import com.fazecast.jSerialComm.SerialPort;

public class PortScanner implements Runnable{
	private SerialPort all_ports [] = null;
	private Vector <Event> events = new Vector<Event>();
	
	public PortScanner() {
		all_ports = SerialPort.getCommPorts();
		System.out.println("Available ports: " + all_ports.length);
		for(int i = 0; i < all_ports.length; i++) {
			System.out.println("\t" + all_ports[i].getDescriptivePortName());
		}
	}
	
	public USBLink [] getPorts() {
	//	System.out.print(all_ports.length);
		USBLink [] all_usb_ports = new USBLink[all_ports.length];
	//	System.out.print(all_usb_ports.length);
		for(int i = 0; i < all_usb_ports.length; i++) {
			all_usb_ports[i] = new USBLink(all_ports[i]);
			//all_usb_ports[i].getName();
			//all_ports[i].getDescriptivePortName();
		}
		return all_usb_ports;
	//	return all_ports;
	}
	
	public void run() {
		while(true) {
			checkPorts();
			
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void checkPorts() {
		SerialPort present_ports [] = SerialPort.getCommPorts();
		if(all_ports.length != present_ports.length) {
			searchDiffrences(present_ports);
		}
	}
	
	private boolean comparePorts(SerialPort serial_port_1, SerialPort serial_port_2) {
		if(serial_port_1.getDescriptivePortName().equals(serial_port_2.getDescriptivePortName())) {
			return true;
		}
		return false;
	}
	
	private void searchDiffrences(SerialPort new_ports []) {
		int ports_changed = -1;

		if(new_ports.length > all_ports.length) {
			// ports added
			ports_changed = new_ports.length - all_ports.length;

			main_loop : for(int i = 0; i < new_ports.length; i++) {
				for(int j = 0; j < all_ports.length; j++) {
					if(comparePorts(new_ports[i], all_ports[j])){
						continue main_loop;
					}
				}
				System.out.println("Added " + new_ports[i].getDescriptivePortName());
				ports_changed--;
			}
			
		} else {
			// ports removed
			ports_changed = all_ports.length - new_ports.length;
			main_loop : for(int i = 0; i < all_ports.length; i++) {
				for(int j = 0; j < new_ports.length; j++) {
					if(comparePorts(all_ports[i], new_ports[j])) {
							continue main_loop;
					}
				}
				System.out.println("Removed " + all_ports[i].getDescriptivePortName());
				ports_changed--;
			}

		}
		if(ports_changed != 0) {
			System.out.println("problem with new ports: " + ports_changed);
		}
		all_ports = new_ports;
		updateEvents();
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	private void updateEvents() {
		for(int i = 0; i < events.size(); i++) {
			events.get(i).update();
		}
	}
}
