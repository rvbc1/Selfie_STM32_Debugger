import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import com.fazecast.jSerialComm.SerialPort;

public class DataLink implements Runnable{
	InputStream in;
	OutputStream out;
	private String last_action = "Console";
	private String response = "";
	private boolean is_open = false;
	SerialPort serial_port;
	private CommunicationPort communication_port = null;
	private InputStream console_stream = null;

	public DataLink() {
		String string = "This is a String.\nWe are going to convert it to InputStream.\n" +
				"Greetings from JavaCodeGeeks!";

		//use ByteArrayInputStream to get the bytes of the String and convert them to InputStream.
		console_stream = new ByteArrayInputStream(string.getBytes());

		new Thread(this).start();
	}

	public void setCommunicationPort(CommunicationPort communication_port) {
		this.communication_port = communication_port;
		System.out.println("Active DataLink on: \"" + this.communication_port.getName() + "\"");
	}

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
		return communication_port.isOpen();
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
			if(communication_port.trySend(b)) {
				System.out.println("Data send successful");
			} else {
				System.out.println("Problem with send");
			}
		} else {
			System.out.println("Data not send - port closed");
		}
	}

	void sendChar(char ch) {
		char [] char_array = new char [1];
		char_array[0] = ch;
		trySend(new String(char_array).getBytes());
		last_action = "Pressed \'" + ch + "\'";
		response = "";
	}

	void sendDFU() {
		sendChar('R');
		last_action = "Jump to DFU";
	}

	void sendReset() {
		sendChar('r');
		last_action = "Reset MCU";
	}

	public String getConsole() {
		String return_string = last_action + "\n";
		return_string = return_string.concat(response);
		return return_string;
	}

	private String convert(InputStream inputStream, Charset charset) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}

	public void run() {
		while (true) {
			if((communication_port != null) && (communication_port.getInputStream() != null)) {
				try {
				//	String test = "";
					while(communication_port.getInputStream().available() > 0) {
						response = response + ((char)communication_port.getInputStream().read()); // sholud be better code
						//System.out.print((char)in.read());
						//console.setText(console.getText() + (char)in.read());
						//	String theString = IOUtils.toString(inputStream, encoding); 
						//System.out.println(convert(communication_port.getInputStream(), Charset.forName("UTF-8")));
					}
					//System.out.print(test);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
