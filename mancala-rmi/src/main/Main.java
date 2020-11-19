package main;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import client.ChatClient;
import server.ChatServer;

public class Main {

	public static void main(String[] args) {
		
		int resposta = JOptionPane.showConfirmDialog(null, "Você está entrando como servidor?", "Cliente ou Servidor", JOptionPane.YES_NO_OPTION);
		
		if(resposta == 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ChatServer frame = new ChatServer();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});	
		
		} else if(resposta == 1) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ChatClient frame = new ChatClient();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
	}
}
