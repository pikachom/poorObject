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
				System.out.println("[서버]연결 기다림");
				Socket socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[서버]연결 수락함 // IP : " + isa.getHostName());
				
				byte[] bytes = null;
				String message = null;
				
				InputStream is = socket.getInputStream();
				bytes = new byte[100];
				int readByteCount = is.read(bytes);
				message = new String(bytes, 0, readByteCount, "UTF-8");
				System.out.println("[서버] 클라이언트에서 데이터 받기 성공 // 받은 메시지 : " + message);
				message = PoorObjectServer.runSvc(message);
				if(message == null) {
					System.out.println("[서버] 서비스 실행 실패");
					is.close();
					socket.close();
				}
				OutputStream os = socket.getOutputStream();
				
				bytes = message.getBytes("UTF-8");
				os.write(bytes);
				os.flush();
				System.out.println("[서버] 클라이언트로 데이터 보내기 성공");
				
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
