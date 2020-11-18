package br.edu.ifce.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.client.ChatClientIF;

public class ChatServer extends UnicastRemoteObject implements ChatServerIF {

	private static final long serialVersionUID = 1L;

	private List<ChatClientIF> chatClients;
	
	protected ChatServer() throws RemoteException {
		//super();
		chatClients = new ArrayList<ChatClientIF>();
	}

	@Override
	public synchronized void registerChatClient(ChatClientIF chatClient) throws RemoteException {
		this.chatClients.add(chatClient);
	}

	@Override
	public synchronized void broadcastMessage(String message) throws RemoteException {
		int i = 0;
		while(i<chatClients.size()) {
			chatClients.get(i).retrieveMessage(message);
			i++;
		}
		
	}

}
