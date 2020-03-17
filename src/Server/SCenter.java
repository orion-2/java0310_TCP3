package Server;

import java.util.ArrayList;

public class SCenter {
	private ArrayList<ServerChat> sList = new ArrayList<>();

	public void addServerChat(ServerChat s) {
		this.sList.add(s);
	}

	public void reMsg(String msg, String id) {
		sendAll("[" + id + "]" + msg);
	}

	private void sendAll(String msg) {
		for(int i = 0; i < sList.size(); i++) {
			sList.get(i).send(msg);
		}
	}

}
