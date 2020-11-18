package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import common.ClientCallback;
import common.ClientInfo;
import common.ServerOperations;
import exception.ChatException;
import lombok.Getter;
import lombok.Setter;

public class ServerHandler {

	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	private static final String CONFIG_FILE = "server_config.txt";
	private static final String PROP_PORT = "port";
	private static final int DEFAULT_PORT = 1909;
	
	private ServerOperationsImpl serverOperations;
	private Registry registry;
	private Properties props;
	private boolean started;
	
	@Getter @Setter
	private int port;
	
	public ServerHandler() throws ChatException, IOException {
		this.props = new Properties();
		File file = new File(CONFIG_FILE);
		
		if(file.exists()) {
			FileInputStream input = null;
			try {
				input = new FileInputStream(file);
				props.load(input);
			} finally {
				if(input != null) {
					input.close();
				}
			}
			
		} else {
			props.setProperty(PROP_PORT, String.valueOf(DEFAULT_PORT));
		}
		
		try {
			registry = LocateRegistry.createRegistry(getServerPort(null));
		} catch(RemoteException e) {
			throw new ChatException("Erro ao carregar o Registry", e);
		}
	}
	
	public int startServer(String inputPort) throws ChatException {
		try {
			logger.info("O servidor foi iniciado");
			
			serverOperations = new ServerOperationsImpl();
			
			registry.bind(ServerOperations.SERVER_OBJ_NAME, serverOperations);
			
			started = true;
			
			return getServerPort(inputPort);
			
		} catch (Exception e) {
			throw new ChatException("Erro ao iniciar o servidor", e);
		}
	}
	
	public void stopServer() throws ChatException {
		try {
			if(!started) {
				return;
			}
			
			logger.info("O servidor foi parado");
			
			registry.unbind(ServerOperations.SERVER_OBJ_NAME);
			
			Set<ClientInfo> clients = serverOperations.getClients();
			
			for(ClientInfo clientInfo : clients) {
				final ClientCallback callback = clientInfo.getCallback();
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							callback.onServerShutdown();
						} catch(RemoteException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
			
			clients.clear();
			started = false;
			
		} catch(Exception e) {
			throw new ChatException("Erro ao parar servidor", e);
		}
	}
	
	public void setServerPort(String port) throws ChatException, NumberFormatException {
		this.props = new Properties();
		this.props.setProperty(PROP_PORT, String.valueOf(port));
		
		try {
			registry = LocateRegistry.createRegistry(getServerPort(port));
		} catch(RemoteException e) {
			throw new ChatException("Erro ao carregar o Registry", e);
		}
	}
	
	private int getServerPort(String inputPort) throws ChatException, NumberFormatException {
		String portStr = props.getProperty(PROP_PORT);
		
		if(portStr == null) {
			throw new ChatException("Porta do servidor não definida");
		}
		
		try {
			int port = Integer.parseInt(portStr);
			
			if(port < 1 || port > 65635) {
				throw new ChatException("A porta não está em um intervalo válido");
			}
			
			return port;
			
		} catch (NumberFormatException e) {
			throw new ChatException("A porta não é um número válido");
		}
	}
}
