package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import common.ClientCallback;
import common.ClientInfo;
import common.ServerOperations;
import exception.DuplicateNameException;
import lombok.Getter;

public class ServerOperationsImpl extends UnicastRemoteObject implements ServerOperations {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ServerOperationsImpl.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	@Getter
	private Set<ClientInfo> clients = new HashSet<ClientInfo>();
	
	/** Construtor  */
	public ServerOperationsImpl() throws RemoteException {}

	@Override
	public synchronized void connect(ClientInfo clientInfo) throws RemoteException, DuplicateNameException {
		boolean added = clients.add(clientInfo);
		
		if(!added) {
			throw new DuplicateNameException("O nome " + clientInfo.getName() + " já existe no chat.");
		}
		
		String message = clientInfo.getName() + " entrou no chat/" + clients.size();
		String formattedMessage = formatMessageFromServerToClients(message);
		
		broadcastMessage(formattedMessage);
	}
	
	@Override
	public synchronized void disconnect(ClientInfo clientInfo) throws RemoteException {
		clients.remove(clientInfo);
		
		String message = clientInfo.getName() + " saiu do chat";
		String formattedMessage = formatMessageFromServerToClients(message);
		
		broadcastMessage(formattedMessage);
	}
	
	@Override
	public synchronized void sendMessage(ClientInfo clientInfo, String message) throws RemoteException {
		String formattedMessage = formatMessageFromClientToClients(message, clientInfo.getName());
		broadcastMessage(formattedMessage);
	}
	
	private String formatMessageFromServerToClients(String message) {
		String formattedMessage = String.format("{%s} %s", sdf.format(new Date()), message);
		return formattedMessage;
	}
	
	private String formatMessageFromClientToClients(String message, String clientName) {
		String formattedMessage = String.format("(%s) [%s] %s", sdf.format(new Date()), clientName, message);
		return formattedMessage;
	}
	
	private void broadcastMessage(final String message) {
		logger.info(message);
		
		for(ClientInfo client : clients) {
			final ClientCallback callback = client.getCallback();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						callback.onIncomingMessage(message);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
