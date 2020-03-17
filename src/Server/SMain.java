package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SMain {

	public static void main(String[] args) throws IOException {
		ServerSocket serverS = null;
		Socket withClient = null;

		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.119", 9999));

		ArrayList<Socket> cList = new ArrayList<>();
		SCenter sc = new SCenter();
		while (true) {
			System.out.println("서버 대기중");
			withClient = serverS.accept();
			cList.add(withClient);
			System.out.println(cList);
			System.out.println(withClient.getInetAddress() + "클라이언트 접속함");
			ServerChat s =  new ServerChat(withClient,sc);
			sc.addServerChat(s);
			s.start();
		}
	}
}
