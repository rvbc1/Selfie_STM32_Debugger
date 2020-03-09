
public class SelfieDebuger {

	public static void main(String[] args) {
		KeyBoardScanner keyborad_scanner = new KeyBoardScanner();
		
		PortScanner port_scanner = new PortScanner();
		Runnable port_scanner_runner = port_scanner;
		Thread port_scanner_thread = new Thread(port_scanner_runner);
		port_scanner_thread.start();
		
		
		@SuppressWarnings("unused")
		Runnable window_runner = new Window(keyborad_scanner, port_scanner);
		//Thread threads = new Thread(runner);
		
	}
}
