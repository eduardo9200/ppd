package br.edu.ifce.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ChatServerDriver {
	
	public static void main(String[] args) {
		try {
			Naming.rebind("RMIChatServer", new ChatServer());
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
