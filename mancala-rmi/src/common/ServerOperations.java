package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exception.DuplicateNameException;

public interface ServerOperations extends Remote {

	public static final String SERVER_OBJ_NAME = "chatServer";
	
	public void connect(ClientInfo clientInfo) throws RemoteException, DuplicateNameException;
	public void disconnect(ClientInfo clientInfo) throws RemoteException;
	
	public void sendMessage(ClientInfo clientInfo, String message) throws RemoteException;
}
