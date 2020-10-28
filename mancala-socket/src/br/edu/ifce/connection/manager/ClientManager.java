package br.edu.ifce.connection.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import br.edu.ifce.dashboard.Commands;
import lombok.Getter;

public class ClientManager extends Thread {

	private Socket clientSocket;
	private BufferedReader reader;
	
	@Getter
	private String nomeCliente;
	
	@Getter
	private PrintWriter writer;
	
	private static final Map<String, ClientManager> clientes = new HashMap<String, ClientManager>();
	
	public ClientManager(Socket socket) {
		this.clientSocket = socket;
		this.start();
	}
	
	@Override
	public void run() {
		try {
			InputStreamReader inputStreamAdapter = new InputStreamReader(this.clientSocket.getInputStream());
			reader = new BufferedReader(inputStreamAdapter);
			Boolean autoFlush = true;
			writer = new PrintWriter(this.clientSocket.getOutputStream(), autoFlush);
			
			writer.println("Seu nome: ");
			String msg = reader.readLine();
			this.nomeCliente = msg.toLowerCase().replaceAll(",", "");
			writer.println("Olá, " + this.nomeCliente);
			
			clientes.put(this.nomeCliente, this);
			
			while(true) {
				msg = reader.readLine();
				
				if(msg.equalsIgnoreCase("cmd:exit")) {
					this.clientSocket.close();
					
				} else if(msg.toLowerCase().startsWith("msg:")) {
					String nomeDestinatario = msg.substring(4, msg.length()); 
					ClientManager destinatario = clientes.get(nomeDestinatario);
					
					if(destinatario == null) {
						writer.println("O cliente informado não existe.");
					} else {
						writer.println("digite uma mensagem para " + destinatario.getNomeCliente());
						destinatario.getWriter().println(this.nomeCliente + " disse: " + reader.readLine());
					}
					
				} else if(msg.equalsIgnoreCase(Commands.COMMAND_LIST_PLAYERS)) {
					StringBuffer stringBuffer = new StringBuffer();
					
					for(String c : clientes.keySet()) {
						stringBuffer.append(c);
						stringBuffer.append(",");
					}
					
					stringBuffer.delete(stringBuffer.length()-1, stringBuffer.length());
					writer.println(stringBuffer.toString());
					
					
				} else {
					writer.println(this.nomeCliente + "; MSG = " + msg);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
