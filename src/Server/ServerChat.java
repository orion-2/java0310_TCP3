package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat extends Thread {
	private Socket withClient = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String id = null;
	private SCenter sc = null;
	@Override
	public void run() {
		streamSet();
		receive();
//		send();

	}

	public ServerChat(Socket c, SCenter s ) throws IOException {
		this.withClient = c;
		this.sc = s;
//		streamSet();
	}

	private void streamSet() {

		try {
			reMsg = withClient.getInputStream();
			byte[] reBuffer = new byte[100];
			reMsg.read(reBuffer);
			id = new String(reBuffer);
			id = id.trim();
			InetAddress c_ip = withClient.getInetAddress();
			String ip = c_ip.getHostAddress();

			System.out.println(id + "님 로그인 (" + ip + ")"); // 아이디 receive

			String reMsg = "정상접속 하였습니다.";
			sendMsg = withClient.getOutputStream();
			sendMsg.write(reMsg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void receive() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("receive start~~");
					while (true) {
						reMsg = withClient.getInputStream();
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();
						sc.reMsg(msg,id);
//						System.out.println("[" + id + "]" + msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void send(String reMsg) {
		try {
			sendMsg = withClient.getOutputStream();
			sendMsg.write(reMsg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
