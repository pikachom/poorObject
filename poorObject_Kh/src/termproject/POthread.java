package termproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class POthread extends Thread{
	int threadID;	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", 5001));
			while(true) {
				System.out.println("[����]���� ��ٸ�");
				Socket socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[����]���� ������ // IP : " + isa.getHostName());
				
				byte[] bytes = null;
				String message = null;
				
				InputStream is = socket.getInputStream();
				bytes = new byte[100];
				int readByteCount = is.read(bytes);
				message = new String(bytes, 0, readByteCount, "UTF-8");
				System.out.println("[����] Ŭ���̾�Ʈ���� ������ �ޱ� ���� // ���� �޽��� : " + message);
				message = PoorObjectServer.runSvc(message);
				if(message == null) {
					System.out.println("[����] ���� ���� ����");
					is.close();
					socket.close();
				}
				OutputStream os = socket.getOutputStream();
				
				bytes = message.getBytes("UTF-8");
				os.write(bytes);
				os.flush();
				System.out.println("[����] Ŭ���̾�Ʈ�� ������ ������ ����");
				
				is.close();
				os.close();
				socket.close();
				
			}
			
		}catch(Exception e) {}
		
		if(!serverSocket.isClosed()) {
			try {
				serverSocket.close();
			}catch (IOException e1) {}
		}

	}

}
