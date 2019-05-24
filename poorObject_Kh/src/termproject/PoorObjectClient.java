package termproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class PoorObjectClient {
	public void initiate() {
		System.out.println("[Ŭ���̾�Ʈ]���񽺸�� ���ڿ��� �Է��ϼ���");
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
			System.out.println("[Ŭ���̾�Ʈ]���� ��û");
			socket.connect(new InetSocketAddress("localhost", 5001));
			System.out.println("[Ŭ���̾�Ʈ]���� ����");
			
			byte[] bytes = null;
			String message = null;
			
			OutputStream os = socket.getOutputStream();
			
			po.initiate();
			message = po.getInput();
			bytes = message.getBytes("UTF-8");
			os.write(bytes);
			os.flush();
			System.out.println("[Ŭ���̾�Ʈ] ������ ������ ������ ����");
			
			InputStream is = socket.getInputStream();
			bytes = new byte[100];
			int readByteCount = is.read(bytes);
			message = new String(bytes,0,readByteCount,"UTF-8");
			System.out.println("[Ŭ���̾�Ʈ] �������� ������ �ޱ� ����");
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
