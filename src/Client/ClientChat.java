package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
	Socket withServer = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String id = null;
	private Scanner input = new Scanner(System.in);

	ClientChat(Socket c) throws IOException {
		this.withServer = c;
		streamSet();
		send();
		receive();
	}

	private void receive() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("receive start~~");
					while (true) {
						reMsg = withServer.getInputStream();
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();
						System.out.println("[" + id + "]" + msg );
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void send() throws IOException {
		new Thread(new Runnable() {
		
			@Override
			public void run() {
				try {
					System.out.println("send start~");
					Scanner in = new Scanner(System.in);
					while (true) {
						String reMsg = in.nextLine();
						sendMsg = withServer.getOutputStream();
						sendMsg.write(reMsg.getBytes());
					}
				}
				catch (IOException e) {
				e.printStackTrace();
				}
			}
		}).start();
	}	
	
	private void streamSet() throws IOException {
		System.out.println("ID를 입력하세요");
		id = input.nextLine();
		
		sendMsg = withServer.getOutputStream();
		sendMsg.write(id.getBytes());
		
		reMsg = withServer.getInputStream();
		byte[] reBuffer = new byte[100];
		reMsg.read(reBuffer);
		String msg = new String(reBuffer);
		msg = msg.trim();
		System.out.println(msg);

	}

}
