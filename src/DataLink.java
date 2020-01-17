import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fazecast.jSerialComm.SerialPort;

public class DataLink {
	InputStream in;
	OutputStream out;
	private boolean is_open = false;
	SerialPort serial_port;
	boolean tryOpen(SerialPort selected_serial_port){
		if(!selected_serial_port.isOpen()) {
			selected_serial_port.openPort();
			selected_serial_port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
			if(selected_serial_port.isOpen()) {
//				connect_button.setText("disconnect");
				in = selected_serial_port.getInputStream();
				out = selected_serial_port.getOutputStream();
//				input_stream_scanner_task.setInputSteram(in);
//				port_selector.disable();
//				dfu.setEnabled(true);
//				restart.setEnabled(true);
//				command.setEnabled(true);
				//this.repaint();
				serial_port = selected_serial_port;
				is_open = true;
				return true;
			}
		}
		return false;
	}
	
	boolean isOpen() {
		return is_open;
	}
	boolean closePort() {
	//	input_stream_scanner_task.setInputSteram(null);
		serial_port.closePort();
		if(!serial_port.isOpen()) {
		//	connect_button.setText("connect");
		//	selected_serial_port = null;
		//	port_selector.enable();
		//	dfu.setEnabled(false);
		//	restart.setEnabled(false);
		//	command.setEnabled(false);
		//	this.repaint();
			is_open = false;
			return true;
	//	} else {
		//	input_stream_scanner_task.setInputSteram(in);
		}
		return false;
	}
	void trySend(byte b []){
		if(isOpen()) {
			try {
				out.write(b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
