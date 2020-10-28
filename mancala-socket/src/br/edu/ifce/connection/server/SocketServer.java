package br.edu.ifce.connection.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import br.edu.ifce.connection.manager.ClientManager;

public class SocketServer {
	public static void main(String[] args) {
		
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(9999);
			
			System.out.println("Servidor iniciado com sucesso.");
			
			while(true) {
				Socket socket = server.accept();
				new ClientManager(socket);
			}
			
		} catch (IOException e) {
			try {
				if(server != null) {
					server.close();	
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "A porta está em uso. Tente outra porta.");
			e.printStackTrace();
		}
	}
}
