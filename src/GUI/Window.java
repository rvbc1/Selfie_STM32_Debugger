import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Keymap;

import com.fazecast.jSerialComm.SerialPort;

public class Window extends JFrame implements Runnable {
	private JPanel contentPane;

	PortSelector port_selector;

	SerialPort selected_serial_port;

	JButton connect_button;

	Console console;

	JSpinner spinner;

	DataLink data_link;

	//SerialPort all_ports [] = SerialPort.getCommPorts();

	InputStreamScannerTask input_stream_scanner_task; 

	SerialPortScanner serial_port_scanner;
	
	public void run() {
	//	new Window();
	}
	
	private void addComponents(PortScanner port_scanner) {
		contentPane = new JPanel();
//		contentPane.setSize(new Dimension(800, 600));
//		contentPane.setPreferredSize(new Dimension(800, 600));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		//KeyBoardScanner kbs = new KeyBoardScanner();
		console = new Console();
		
		JPanel testpanel = new JPanel();
		testpanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		port_selector = new PortSelector(port_scanner);
		data_link = new DataLink();
		connect_button = new ConnectButton(port_selector, data_link);
		setResizable(false);
		


		
		
		contentPane.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridwidth  = 3;
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(testpanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx = 3;
		c.weightx = 1;
		c.gridwidth  = 3;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,5,5);
		testpanel.add(port_selector, c);
	//	testpanel.add(port_selector);

		c.fill = GridBagConstraints.HORIZONTAL;
	//	c.weightx = 1;
		c.gridwidth  = 1;
		c.gridx = 3;
		c.gridy = 0;
		
		JLabel lab = new JLabel("Status", SwingConstants.CENTER);
		testpanel.add(lab, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx = 1;
		c.gridwidth  = 1;
		c.gridx = 4;
		c.gridy = 0;

		
		testpanel.add(connect_button, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth  = 2;
		c.gridheight  = 2;
		c.gridx = 0;
		c.gridy = 1;
		
	//	contentPane.add(connect_button);
	//	contentPane.add(port_selector);
		contentPane.add(console, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth  = 1;
		c.gridheight  = 2;
		c.gridx = 2;
		c.gridy = 1;
		contentPane.add(new ButtonsPanel(data_link), c);
//		add(connect_button);
//		add(port_selector);
//		add(console);
		
//        Keymap firstNameMap = console.getKeymap();
//        KeyStroke altF1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1,InputEvent.SHIFT_MASK);
//        firstNameMap.addActionForKeyStroke(altF1,System.out.print("dup"));

		//pack();
	}
	
	private void addKeyListnerToComonents(KeyListener key_listner) {
		//this.addKeyListener(key_listner);
		console.addKeyListener(key_listner);
	}

	private void init(PortScanner port_scanner) {
		//this.super("Selfie STM32 Debuger");
		setTitle("Selfie STM32 Debuger");
		setPreferredSize(new Dimension(800, 600));
		this.setFocusable(true);
		setLocationByPlatform(true);
		setBounds(100, 100, 800, 600);
		
		addComponents(port_scanner);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Window(KeyListener key_listner, PortScanner port_scanner) {
		init(port_scanner);
		addKeyListnerToComonents(key_listner);
	
	}

}


class InputStreamScannerTask extends TimerTask {
	InputStream in;
	JTextArea console;
	@Override
	public void run() {
		try {
			if(in != null) {
				while(in.available() > 0) {
					//System.out.print((char)in.read());
					console.setText(console.getText() + (char)in.read());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void setInputSteram(InputStream in) {
		this.in = in;
	}

	void setConsole(JTextArea console) {
		this.console = console;
	}

}


class SerialPortScanner extends TimerTask {
	JTextField all_ports;

	public void run() {
		//		if(all_ports.getItemCount() > 0) {
		//			SerialPort sp = (SerialPort)all_ports.getSelectedItem();
		//			sp.getSystemPortName();
		//		}
		//		all_ports.removeAllItems();
		//		SerialPort new_array [] = SerialPort.getCommPorts();
		//		for(int i = 0 ;i < new_array.length; i++) {
		//			all_ports.addItem(new_array[i]);
		//		}

	}



	void setInputSteram(JTextField all_ports) {
		this.all_ports = all_ports;
	}

}

class MyFormatterFactory extends
DefaultFormatterFactory {
	public AbstractFormatter getDefaultFormatter() {
		return new HexFormatter();
	}
}

class HexFormatter extends DefaultFormatter {
	public Object stringToValue(String text) throws ParseException {
		try {
			return Integer.valueOf(text.substring(2), 16);
		} catch (NumberFormatException nfe) {
			throw new ParseException(text,0);
		}
	}

	public String valueToString(Object value) throws ParseException {
		return "0x" + Integer.toHexString(
				((Integer)value).intValue()).toUpperCase();
	}
}
