


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

import com.fazecast.jSerialComm.SerialPort;



public class Window extends JFrame implements KeyListener {
	private JPanel contentPane;

	JComboBox port_selector;

	SerialPort selected_serial_port;

	JButton connect_button;
	JButton dfu;
	JButton restart;
	JButton command;

	JTextArea console;

	JSpinner spinner;

	InputStream in;
	OutputStream out;

	SerialPort all_ports [] = SerialPort.getCommPorts();

	InputStreamScannerTask input_stream_scanner_task; 

	SerialPortScanner serial_port_scanner;

	public Window() {
		super("Selfie STM32 Debuger");
		setPreferredSize(new Dimension(800, 600));


		this.setFocusable(true);
		addKeyListener(this);

		setLocationByPlatform(true);
		setBounds(100, 100, 800, 415);

		contentPane = new JPanel();
		contentPane.setSize(new Dimension(800, 600));
		contentPane.setPreferredSize(new Dimension(800, 600));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		console = new JTextArea();
		console.setEditable(false);
		console.setColumns(40);
		console.setRows(20);
		console.addKeyListener(this);

		port_selector = new JComboBox(all_ports);

		port_selector.addKeyListener(this);


		if(port_selector.getItemCount() != 0){
			port_selector.setRenderer(new ItemRenderer());
		} else {
			port_selector.removeAll();
			port_selector.addItem("No avaible ports");
			//port_selector.disable();
		}

		connect_button = new JButton("connect");
		connect_button.addKeyListener(this);

		connect_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selected_serial_port == null)
					tryOpen();
				else 
					closePort();

			}
		});

		dfu = new JButton("DFU");
		dfu.setEnabled(false);
		dfu.addKeyListener(this);

		dfu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selected_serial_port != null)
					try {
						out.write('R');
						closePort();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});

		restart = new JButton("Restart");
		restart.setEnabled(false);
		restart.addKeyListener(this);

		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selected_serial_port != null)
					try {
						out.write('r');
						closePort();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});

		command = new JButton("Command");
		command.setEnabled(false);
		command.addKeyListener(this);

		command.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selected_serial_port != null) {
					try {
						byte data [] = new byte [3];
						data[0] = (byte) 0xff;
						data[1] = ((Integer) spinner.getValue()).byteValue();
						data[2] = (byte) 0xfe;
						out.write(data);
					} catch (IOException e1) {
						//TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});


		spinner = new JSpinner(new SpinnerNumberModel(50,0,255,1));
		JSpinner.DefaultEditor editor =
				(JSpinner.DefaultEditor)spinner.getEditor();
		JFormattedTextField tf = editor.getTextField();
		tf.setFormatterFactory(new MyFormatterFactory());
		
		spinner.addKeyListener(this);




		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(port_selector, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(console, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(connect_button, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
								.addComponent(dfu, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
								.addComponent(restart, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
										.addComponent(spinner, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(command, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap())
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(port_selector, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(console, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(connect_button)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(dfu)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(restart)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(command))
						.addGap(161))
				);
		contentPane.setLayout(gl_contentPane);

		add(connect_button);
		add(port_selector);
		add(console);

		//pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		input_stream_scanner_task = new InputStreamScannerTask();
		serial_port_scanner = new SerialPortScanner();

		input_stream_scanner_task.setConsole(console);

		//serial_port_scanner.setInputSteram(port_selector);

		Timer timer1 = new Timer(true);
		timer1.scheduleAtFixedRate(input_stream_scanner_task, 0, 10);

		Timer timer2 = new Timer(true);
		//timer2.scheduleAtFixedRate(serial_port_scanner, 0, 1000);










	}

	void tryOpen(){
		selected_serial_port = (SerialPort) port_selector.getSelectedItem();
		if(!selected_serial_port.isOpen()) {
			selected_serial_port.openPort();
			selected_serial_port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
			if(selected_serial_port.isOpen()) {
				connect_button.setText("disconnect");
				in = selected_serial_port.getInputStream();
				out = selected_serial_port.getOutputStream();
				input_stream_scanner_task.setInputSteram(in);
				port_selector.disable();
				dfu.setEnabled(true);
				restart.setEnabled(true);
				command.setEnabled(true);
				this.repaint();
			}
		}
	}

	void closePort() {
		input_stream_scanner_task.setInputSteram(null);
		selected_serial_port.closePort();
		if(!selected_serial_port.isOpen()) {
			connect_button.setText("connect");
			selected_serial_port = null;
			port_selector.enable();
			dfu.setEnabled(false);
			restart.setEnabled(false);
			command.setEnabled(false);
			this.repaint();
		} else {
			input_stream_scanner_task.setInputSteram(in);
		}
	}



	@Override
	public void keyPressed(KeyEvent evt) {
		if(selected_serial_port != null && selected_serial_port.isOpen()) {
			try {
				out.write(evt.getKeyChar());
				console.setText("");
				//out.write('s');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//	System.out.println(evt.getKeyChar());

	}

	@Override
	public void keyReleased(KeyEvent evt) {
		//char c = evt.getKeyChar();

		//System.out.println(c);
	}

	@Override
	public void keyTyped(KeyEvent evt) {

	}
}

class ItemRenderer extends BasicComboBoxRenderer {
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);

		SerialPort item = (SerialPort) value;

		if(value != null) {
			if (index == -1) {
				setText(item.getDescriptivePortName());
				setIcon(null);
			} else {
				setText(item.getDescriptivePortName());
				setIcon(null);
			}
		}


		return this;
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
	@Override
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
