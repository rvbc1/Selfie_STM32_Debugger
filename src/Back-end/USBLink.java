import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fazecast.jSerialComm.SerialPort;



public class USBLink implements CommunicationPort{
	private SerialPort serial_port = null;
	
	private void init() {
		
	}
	
	public USBLink() {
		init();
	}
	
	public USBLink(SerialPort serial_port) {
		this.serial_port = serial_port;
		init();
	}
	
	public void changePort(SerialPort serial_port) {
		this.serial_port = serial_port;
	}
	
	public boolean tryOpen() {
		if(serial_port.isOpen()) {
			System.out.println("Port \"" + serial_port.getDescriptivePortName() + "\" is already open!");
		} else {
			if(serial_port.openPort()) {
				System.out.println("Port \"" + serial_port.getDescriptivePortName() + "\" opened");
				return true;
			} else {
				System.out.println("Problem with opening port \"" + serial_port.getDescriptivePortName() + "\"");
			}
		}
		return false;
	}
	
	public boolean tryClose() {
		if(serial_port.isOpen()) {
			if(serial_port.closePort()) {
				System.out.println("Port \"" + serial_port.getDescriptivePortName() + "\" closed");
				return true;
			} else {
				System.out.println("Problem with closing port \"" + serial_port.getDescriptivePortName() + "\"");
			}
		} else {
			System.out.println("Port \"" + serial_port.getDescriptivePortName() + "\" is already closed!");
		}
		return false;	
	}
	
	public boolean isOpen() {
		return serial_port.isOpen();
	}

	public InputStream getInputStream() {
		return serial_port.getInputStream();
	}

	public OutputStream getOutputStream() {
		return serial_port.getOutputStream();
	}
	
	public boolean trySend(byte bytes []) {
		if(isOpen()) {
			try {
				getOutputStream().write(bytes);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public String getName() {
		return serial_port.getDescriptivePortName();
	}
	
    public boolean equals(Object o) { 
    	  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof USBLink)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        USBLink usb_o = (USBLink) o; 
          
        // Compare the data members and return accordingly  
	    return this.getName().equals(usb_o.getName());
    } 
}
