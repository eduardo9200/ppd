package br.edu.ifce.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import br.edu.ifce.server.ChatServerIF;

public class ChatClientDriver {

	public static void main(String[] args) {
		String url = "rmi://localhost/RMIChatServer";
		
		try {
			ChatServerIF chatServer = (ChatServerIF) Naming.lookup(url);
			new Thread(new ChatClient("Natty", chatServer)).start();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
