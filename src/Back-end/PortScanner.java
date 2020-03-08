import com.fazecast.jSerialComm.SerialPort;

public class PortScanner implements Runnable{
	private SerialPort all_ports [] = null;
	private PortSelector port_selector = null;
	private MyComboBoxModel mcbm = null;
	
	public PortScanner() {
		all_ports = SerialPort.getCommPorts();
		System.out.println("Founded ports: " + all_ports.length);
		for(int i = 0; i < all_ports.length; i++) {
			System.out.println(all_ports[i].getDescriptivePortName());
		}
	}
	
	public void run() {
		while(true) {
			checkPorts();
		}
	}
	
	private void checkPorts() {
		SerialPort present_ports [] = SerialPort.getCommPorts();
		if(all_ports.length != present_ports.length) {
			searchDiffrences(present_ports);
		}
	}
	
	private void searchDiffrences(SerialPort new_ports []) {
		int ports_changed = -1;

		if(new_ports.length > all_ports.length) {
			// ports added
			ports_changed = new_ports.length - all_ports.length;

			main_loop : for(int i = 0; i < new_ports.length; i++) {
				for(int j = 0; j < all_ports.length; j++) {
					if(new_ports[i].getDescriptivePortName().equals(all_ports[j].getDescriptivePortName())){
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
					if(all_ports[i].getDescriptivePortName().equals(new_ports[j].getDescriptivePortName())) {
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
	}
}
