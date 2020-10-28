package br.edu.ifce.connection.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import br.edu.ifce.dashboard.Commands;

public class SocketClient extends Thread {
	public static void main(String[] args) {
		try {
			final Socket clientSocket = new Socket(Commands.DEFAULT_LOCAL_SERVER, 9999);
			
			new Thread() {
				
				@Override
				public void run() {
					try {
						InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
						BufferedReader reader = new BufferedReader(inputStreamReader);
						
						while(true) {
							String msg = reader.readLine();
							System.out.println("Resposta do servidor: " + msg);
						}
						
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Não foi possível ler a mensagem do servidor.");
						e.printStackTrace();
					}
				}
			}.start();
			
			Boolean autoFlush = true;
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
			BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));
			String msgTerminal = "";
			
			while(true) {
				msgTerminal = leitorTerminal.readLine();
				
				if(msgTerminal == null || msgTerminal.length() == 0) {
					continue;
				}
				
				writer.println(msgTerminal);
				
				if(msgTerminal.equalsIgnoreCase(Commands.COMMAND_EXIT)) {
					System.exit(0);
				}
			}
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Endereço inválido");
			e.printStackTrace();
		
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao iniciar");
			e.printStackTrace();
		}
	}
}
