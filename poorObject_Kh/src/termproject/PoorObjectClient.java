package termproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class PoorObjectClient {
	public void initiate() {
		System.out.println("[클라이언트]서비스명과 문자열을 입력하세요");
	}
	public String getInput() {
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		scanner.close();
		return input;
	}	
	public static void main(String[] args) {
		PoorObjectClient po = new PoorObjectClient();
		
		Socket socket = null;
		try {
			socket = new Socket();
			System.out.println("[클라이언트]연결 요청");
			socket.connect(new InetSocketAddress("localhost", 5001));
			System.out.println("[클라이언트]연결 성공");
			
			byte[] bytes = null;
			String message = null;
			
			OutputStream os = socket.getOutputStream();
			
			po.initiate();
			message = po.getInput();
			bytes = message.getBytes("UTF-8");
			os.write(bytes);
			os.flush();
			System.out.println("[클라이언트] 서버로 데이터 보내기 성공");
			
			InputStream is = socket.getInputStream();
			bytes = new byte[100];
			int readByteCount = is.read(bytes);
			message = new String(bytes,0,readByteCount,"UTF-8");
			System.out.println("[클라이언트] 서버에서 데이터 받기 성공");
			System.out.println(message);
			
			os.close();
			is.close();
			
		}catch(Exception e) {}
		
		if(!socket.isClosed()) {
			try {
				socket.close();				
			}catch(IOException e1) {}
		}
	}

}
