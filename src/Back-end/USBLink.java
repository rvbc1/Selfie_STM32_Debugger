

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;



public class USBLink {
	

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				new Window();
//			}
//		});
		//		System.out.println("Program started");
		//		CommPortIdentifier serialPortId;
		//		Enumeration enumComm;
		//
		//		enumComm = CommPortIdentifier.getPortIdentifiers();
		//		while (enumComm.hasMoreElements()) {
		//			serialPortId = (CommPortIdentifier) enumComm.nextElement();
		//			if (serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
		//				System.out.println(serialPortId.getName());
		//			}
		//		}
		//
		//		System.out.println("Finished successfully");

//		SerialPort all_ports[] = SerialPort.getCommPorts();
//		for(int i = 0; i < all_ports.length; i++){
//			System.out.println(all_ports[i].getDescriptivePortName());
//		}
//		
//		SerialPort comPort = SerialPort.getCommPorts()[0];
//		
//		comPort.openPort();
//		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
//		InputStream in = comPort.getInputStream();
//		OutputStream out = comPort.getOutputStream();
//
//		Scanner sc = new Scanner(System.in);
//
//		try
//		{
//			while(true) {
//				if (sc.hasNextLine()) {	
//					out.write(sc.nextShort());
//				}
//				if(in.available() > 0) {
//					System.out.print((char)in.read());
//				}
//			}
//		} catch (Exception e) { e.printStackTrace(); }
//		comPort.closePort();
//	}

}
