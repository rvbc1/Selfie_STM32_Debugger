
public class SelfieDebuger {

	public static void main(String[] args) {		
		PortScanner port_scanner = new PortScanner();
		Runnable port_scanner_runner = port_scanner;
		Thread port_scanner_thread = new Thread(port_scanner_runner);
		port_scanner_thread.start();
		
		DataLink data_link = new DataLink();
		
		KeyBoardScanner keyborad_scanner = new KeyBoardScanner(data_link);
		
		Runnable window_runner = new Window(keyborad_scanner, port_scanner, data_link);
		Thread window_thread = new Thread(window_runner);
		window_thread.start();
	}
}
