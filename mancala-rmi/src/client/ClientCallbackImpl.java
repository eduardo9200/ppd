package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ClientCallback;

public class ClientCallbackImpl extends UnicastRemoteObject implements ClientCallback {
	
	private static final long serialVersionUID = 1L;
	
	private ChatClient frame;
	private ServerInvoker serverInvoker;
	
	protected ClientCallbackImpl(ChatClient frame, ServerInvoker serverInvoker) throws RemoteException {
		this.frame = frame;
		this.serverInvoker = serverInvoker;
	}

	@Override
	public void onIncomingMessage(String message) throws RemoteException {
		frame.showMessageFromServer(message);
	}

	@Override
	public void onServerShutdown() throws RemoteException {
		frame.handleServerShutdown();
		serverInvoker.setConnected(false);
	}

}
