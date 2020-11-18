package br.edu.ifce.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.edu.ifce.client.ChatClientIF;

public interface ChatServerIF extends Remote {

	public void registerChatClient(ChatClientIF chatClient) throws RemoteException;
	
	public void broadcastMessage(String message) throws RemoteException;
}
