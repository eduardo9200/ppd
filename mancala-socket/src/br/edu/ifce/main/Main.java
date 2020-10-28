package br.edu.ifce.main;

import java.awt.EventQueue;

import br.edu.ifce.dashboard.Tela;

@Deprecated //O método main agora está na classe Tela.java;
public class Main {

public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setTitle("Mancala - Sockets");
					frame.setSize(800, 600);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
