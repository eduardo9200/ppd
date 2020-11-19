package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ClientConfigFile {

private static final String CONFIG_FILE = "client_config.txt";
	
	private static final String PROP_SERVER = "server";
	
	private static final String PROP_PORT = "port";
	
	private static final String DEFAULT_SERVER = "localhost";
	
	private static final String DEFAULT_PORT = "1909";
	
	private static Properties props;
	
	static {
		props = new Properties();
		
		File file = new File(CONFIG_FILE);
		
		if(file.exists()) {
			FileInputStream in = null;
			
			try { 
				in = new FileInputStream(file);
				props.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(in != null) {
					try {
						in.close();
					} catch(IOException e) {
						
					}
				}
			}
		} else {
			setServer(DEFAULT_SERVER);
			setPort(DEFAULT_PORT);
		}
	}
	
	private ClientConfigFile() throws IOException {}
	
	public static void setServer(String server) {
		props.setProperty(PROP_SERVER, server);
	}
	
	public static void setPort(String port) {
		props.setProperty(PROP_PORT, port);
	}
	
	public static String getServer() {
		return props.getProperty(PROP_SERVER);
	}
	
	public static String getPort() {
		return props.getProperty(PROP_PORT);
	}
	
	public static void save() throws IOException {
		FileOutputStream out = null;
		
		try {
			out = new FileOutputStream(CONFIG_FILE);
			props.store(out, null);
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
}
