import java.io.InputStream;
import java.io.OutputStream;

public interface CommunicationPort {
	public boolean tryOpen();
	public boolean tryClose();
	public boolean isOpen();
	public InputStream getInputStream();
	public OutputStream getOutputStream();
	public boolean trySend(byte bytes []);
	public String getName();
	public boolean equals(Object o);
}
