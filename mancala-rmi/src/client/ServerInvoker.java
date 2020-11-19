package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ClientCallback;
import common.ClientInfo;
import common.ServerOperations;
import exception.ChatException;
import exception.DuplicateNameException;
import lombok.Setter;

public class ServerInvoker {
	
	private String lookupURL;
	private ServerOperations serverOperations;
	private ClientInfo clientInfo;
	
	@Setter
	boolean connected = false;
	
	public ServerInvoker(String server, int port, String name, ChatClient frame) throws ChatException {
		try {
			this.lookupURL = "rmi://" + server + ":" + port + "/" + ServerOperations.SERVER_OBJ_NAME;
			
			ClientCallbackImpl callbackImpl = new ClientCallbackImpl(frame, this);
			
			ClientCallback callback = (ClientCallback) UnicastRemoteObject.toStub(callbackImpl);
			
			clientInfo = new ClientInfo(name, callback);
			
		} catch(RemoteException e) {
			throw new ChatException("Erro ao criar o objeto de callback", e);
		}
	}
	
	public void connect() throws ChatException {
		try {
			serverOperations = (ServerOperations) Naming.lookup(lookupURL);
			serverOperations.connect(clientInfo);
			connected = true;
			
		} catch (DuplicateNameException e) {
			throw e;
		} catch(Exception e) {
			throw new ChatException("Erro ao se conectar ao servidor", e);
		}
	}
	
	public void disconnect() throws ChatException {
		try {
			if(connected) {
				serverOperations.disconnect(clientInfo);
				connected = false;
			}
		} catch(RemoteException e) {
			throw new ChatException("Erro ao se desconectar do servidor", e);
		}
	}
	
	public void sendMessage(String message) throws ChatException {
		try {
			serverOperations.sendMessage(clientInfo, message);
		} catch(RemoteException e) {
			throw new ChatException("Erro ao se desconectar do servidor", e);
		}
	}
	
}
