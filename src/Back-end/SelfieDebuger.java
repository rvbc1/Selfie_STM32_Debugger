import java.awt.EventQueue;

public class SelfieDebuger {

	public static void main(String[] args) {
		KeyBoardScanner keyborad_scanner = new KeyBoardScanner();
		
		
		Runnable port_scanner_runner = new PortScanner();
		Thread port_scanner_thread = new Thread(port_scanner_runner);
		port_scanner_thread.start();
		
		
		Runnable window_runner = new Window(keyborad_scanner);
		//Thread threads = new Thread(runner);
	}
}
