package br.edu.ifce.dashboard;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ifce.exceptions.JogadorInvalidoException;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.JLabel;

public class Tela extends JFrame {

	private static final long serialVersionUID = 3284490234670385257L;
	
	private JPanel contentPane;
	
	private List<Integer> casasJogador_1;
	private List<Integer> casasJogador_2;
	
	private GameGeneralRules game;
	private Jogador eu;
	private Jogador adversario;

	/**
	 * Create the frame.
	 */
	public Tela() {
		this.initComponents();
		this.game = this.instanciaJogo();
		this.atualizaTabuleiro(this.game);
		
		this.estabeleceConexao();
		this.estabeleceJogadores();
	}
	
	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setSize(800, 600);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Textarea do chat
		textAreaChat = new JTextArea();
		textAreaChat.setBounds(549, 91, 225, 331);
		textAreaChat.setLineWrap(true);
		textAreaChat.setWrapStyleWord(true);
		contentPane.add(textAreaChat);
		
		//Campo de texto para o jogador escrever uma mensagem
		textField_mensagem = new JTextField();
		textField_mensagem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("pressionou enter");
					//Envia mensagem
				}
			}
		});
		textField_mensagem.setBounds(549, 433, 225, 35);
		contentPane.add(textField_mensagem);
		textField_mensagem.setColumns(10);
		
		//Botão para enviar mensagem
		btnEnviarMsg = new JButton("Enviar");
		btnEnviarMsg.setBounds(696, 479, 78, 23);
		contentPane.add(btnEnviarMsg);
		
		/*-------------------------------------------------------*/
		/* Botões de ações do jogador */
		
		//Desfazer Jogada
		btnDesfazerJogada = new JButton("Desfazer");
		btnDesfazerJogada.setBounds(20, 257, 89, 35);
		contentPane.add(btnDesfazerJogada);
		
		//Refazer Jogada
		btnRefazerJogada = new JButton("Refazer");
		btnRefazerJogada.setBounds(121, 257, 89, 35);
		contentPane.add(btnRefazerJogada);
		
		//Reiniciar
		btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.setBounds(487, 11, 89, 23);
		contentPane.add(btnReiniciar);
		
		//Desistir
		btnDesistir = new JButton("Desistir");
		btnDesistir.setBounds(586, 11, 89, 23);
		contentPane.add(btnDesistir);
		
		//Sair
		btnSair = new JButton("Sair");
		btnSair.setBounds(685, 11, 89, 23);
		contentPane.add(btnSair);
		
		/*-------------------------------------------------------*/
		/* Botões do tabuleiro */
		//Kallah Jogador 1 (A)
		btnKallahA = new JButton("0");
		btnKallahA.setBackground(new Color(255, 215, 0));
		btnKallahA.setEnabled(false);
		btnKallahA.setBounds(458, 91, 70, 112);
		contentPane.add(btnKallahA);
		
		btnCasaA1 = new JButton("0");
		btnCasaA1.setBackground(new Color(255, 215, 0));
		btnCasaA1.setBounds(100, 162, 50, 41);
		contentPane.add(btnCasaA1);
		
		btnCasaA2 = new JButton("0");
		btnCasaA2.setBackground(new Color(255, 215, 0));
		btnCasaA2.setBounds(160, 162, 50, 41);
		contentPane.add(btnCasaA2);
		
		btnCasaA3 = new JButton("0");
		btnCasaA3.setBackground(new Color(255, 215, 0));
		btnCasaA3.setBounds(218, 162, 50, 41);
		contentPane.add(btnCasaA3);
		
		btnCasaA4 = new JButton("0");
		btnCasaA4.setBackground(new Color(255, 215, 0));
		btnCasaA4.setBounds(278, 162, 50, 41);
		contentPane.add(btnCasaA4);
		
		btnCasaA5 = new JButton("0");
		btnCasaA5.setBackground(new Color(255, 215, 0));
		btnCasaA5.setBounds(338, 162, 50, 41);
		contentPane.add(btnCasaA5);
		
		btnCasaA6 = new JButton("0");
		btnCasaA6.setBackground(new Color(255, 215, 0));
		btnCasaA6.setBounds(398, 162, 50, 41);
		contentPane.add(btnCasaA6);
		
		//Kallah Jogador 2 (B)
		btnKallahB = new JButton("0");
		btnKallahB.setForeground(new Color(255, 255, 255));
		btnKallahB.setBackground(new Color(0, 0, 205));
		btnKallahB.setEnabled(false);
		btnKallahB.setBounds(20, 91, 70, 112);
		contentPane.add(btnKallahB);
		
		btnCasaB1 = new JButton("0");
		btnCasaB1.setForeground(Color.WHITE);
		btnCasaB1.setBackground(new Color(0, 0, 205));
		btnCasaB1.setBounds(398, 91, 50, 41);
		contentPane.add(btnCasaB1);
		
		btnCasaB2 = new JButton("0");
		btnCasaB2.setForeground(Color.WHITE);
		btnCasaB2.setBackground(new Color(0, 0, 205));
		btnCasaB2.setBounds(338, 91, 50, 41);
		contentPane.add(btnCasaB2);
		
		btnCasaB3 = new JButton("0");
		btnCasaB3.setForeground(Color.WHITE);
		btnCasaB3.setBackground(new Color(0, 0, 205));
		btnCasaB3.setBounds(278, 91, 50, 41);
		contentPane.add(btnCasaB3);
		
		btnCasaB4 = new JButton("0");
		btnCasaB4.setForeground(Color.WHITE);
		btnCasaB4.setBackground(new Color(0, 0, 205));
		btnCasaB4.setBounds(218, 91, 50, 41);
		contentPane.add(btnCasaB4);
		
		btnCasaB5 = new JButton("0");
		btnCasaB5.setForeground(Color.WHITE);
		btnCasaB5.setBackground(new Color(0, 0, 205));
		btnCasaB5.setBounds(160, 91, 50, 41);
		contentPane.add(btnCasaB5);
		
		btnCasaB6 = new JButton("0");
		btnCasaB6.setForeground(Color.WHITE);
		btnCasaB6.setBackground(new Color(0, 0, 205));
		btnCasaB6.setBounds(100, 91, 50, 41);
		contentPane.add(btnCasaB6);
		
		labelInfo = new JLabel("");
		labelInfo.setForeground(new Color(75, 0, 130));
		labelInfo.setBackground(new Color(255, 255, 255));
		labelInfo.setBounds(220, 267, 250, 25);
		contentPane.add(labelInfo);
		
		btnEnviarMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEnviarMsgActionPerformed(e);
			}
		});
		
		btnDesfazerJogada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDesfazerJogadaActionPerformed(e);
			}
		});
		
		btnRefazerJogada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRefazerJogadaActionPerformed(e);
			}
		});
		
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReiniciarActionPerformed(e);
			}
		});
		
		btnDesistir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDesistirActionPerformed(e);
			}
		});
		
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSairActionPerformed(e);
			}
		});
		
		btnCasaA1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaA1ActionPerformed(e);
			}
		});
		
		btnCasaA2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaA2ActionPerformed(e);
			}
		});
		
		btnCasaA3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaA3ActionPerformed(e);
			}
		});
		
		btnCasaA4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaA4ActionPerformed(e);
			}
		});
		
		btnCasaA5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaA5ActionPerformed(e);
			}
		});
		
		btnCasaA6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaA6ActionPerformed(e);
			}
		});
		
		btnCasaB1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaB1ActionPerformed(e);
			}
		});
		
		btnCasaB2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaB2ActionPerformed(e);
			}
		});
		
		btnCasaB3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaB3ActionPerformed(e);
			}
		});
		
		btnCasaB4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaB4ActionPerformed(e);
			}
		});
		
		btnCasaB5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaB5ActionPerformed(e);
			}
		});
		
		btnCasaB6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCasaB6ActionPerformed(e);
			}
		});
	}
	
	private GameGeneralRules instanciaJogo() {
		GameGeneralRules game = new GameGeneralRules();
		game.criaTabuleiro();
		return game;
	}
	
	private void desabilitaBotoesAdversarios(Jogador eu) {
		if(eu == Jogador.AMARELO) {
			this.btnCasaB1.setEnabled(false);
			this.btnCasaB2.setEnabled(false);
			this.btnCasaB3.setEnabled(false);
			this.btnCasaB4.setEnabled(false);
			this.btnCasaB5.setEnabled(false);
			this.btnCasaB6.setEnabled(false);
		
		} else if(eu == Jogador.AZUL) {
			this.btnCasaA1.setEnabled(false);
			this.btnCasaA2.setEnabled(false);
			this.btnCasaA3.setEnabled(false);
			this.btnCasaA4.setEnabled(false);
			this.btnCasaA5.setEnabled(false);
			this.btnCasaA6.setEnabled(false);
		}
	}
	
	private void atualizaTabuleiro(GameGeneralRules game) {
		this.casasJogador_1 = game.getTabuleiroJogador_1();
		this.casasJogador_2 = game.getTabuleiroJogador_2();
		
		System.out.println("Jogador da Vez: " + game.getJogadorDaVez().getDescricao());
		
		this.btnCasaA1.setText(this.casasJogador_1.get(0).toString());
		this.btnCasaA2.setText(this.casasJogador_1.get(1).toString());
		this.btnCasaA3.setText(this.casasJogador_1.get(2).toString());
		this.btnCasaA4.setText(this.casasJogador_1.get(3).toString());
		this.btnCasaA5.setText(this.casasJogador_1.get(4).toString());
		this.btnCasaA6.setText(this.casasJogador_1.get(5).toString());
		this.btnKallahA.setText(this.casasJogador_1.get(6).toString());
		
		this.btnCasaB1.setText(this.casasJogador_2.get(0).toString());
		this.btnCasaB2.setText(this.casasJogador_2.get(1).toString());
		this.btnCasaB3.setText(this.casasJogador_2.get(2).toString());
		this.btnCasaB4.setText(this.casasJogador_2.get(3).toString());
		this.btnCasaB5.setText(this.casasJogador_2.get(4).toString());
		this.btnCasaB6.setText(this.casasJogador_2.get(5).toString());
		this.btnKallahB.setText(this.casasJogador_2.get(6).toString());
	}
	
	private Boolean validaJogada(Jogador jogadorQueQuerJogar) {
		return game.getJogadorDaVez() == jogadorQueQuerJogar;
	}
	
	private void estabeleceConexao() {
		return;
	}
	
	private void estabeleceJogadores() {
		this.eu = Jogador.AMARELO;
		this.adversario = Jogador.AZUL;
		game.mudaJogadorDaVez(Jogador.AMARELO);
		game.mudaJogadorDaVez(Jogador.AZUL);
		this.desabilitaBotoesAdversarios(eu);
	}
	
	private void validaUltimaCasa(CasaUltimaSemente ultimaCasa, Jogador jogadorDaVez) throws NullPointerException {
		if(ultimaCasa.getIdTabuleiro() == null) {
			String mensagem = "A casa escolhida está vazia. Escolha outra casa.";
			int messageType = JOptionPane.WARNING_MESSAGE;
			this.mostraMensagem(mensagem, messageType);
			game.mudaJogadorDaVez(jogadorDaVez == Jogador.AMARELO ? Jogador.AZUL : Jogador.AMARELO); //Garante que a vez não será passada ao outro jogador, caso a casa selecionada esteja vazia
			throw new NullPointerException("Casa selecionada vazia.");
		}
	}
	
	private void moverSementes(Integer casaEscolhidaNoTabuleiro, Jogador jogador) throws NullPointerException {
		try {
			int posicaoCasaNaLista = casaEscolhidaNoTabuleiro - 1;
			CasaUltimaSemente ultimaCasa = this.game.moveSementes(posicaoCasaNaLista, jogador);
			this.atualizaTabuleiro(game);
			
			validaUltimaCasa(ultimaCasa, jogador);
			
			if(game.temDireitoACapturaDeSementes(ultimaCasa, eu)) {
				String mensagem = "A última semente caiu em uma casa sua vazia. Você capturou as sementes adversárias da casa correspondente.";
				int messageType = JOptionPane.INFORMATION_MESSAGE;
				this.mostraMensagem(mensagem, messageType);
				this.game.capturaSementesAdversarias(ultimaCasa.getCasa(), eu);
				
			} else if(game.temDireitoACapturaDeSementes(ultimaCasa, adversario)) {
				String mensagem = "A última semente do seu adversário caiu em uma casa dele vazia. Ele capturou suas sementes da casa correspondente.";
				int messageType = JOptionPane.WARNING_MESSAGE;
				this.mostraMensagem(mensagem, messageType);
				this.game.capturaSementesAdversarias(ultimaCasa.getCasa(), adversario);
			}
			
			if(game.temDireitoANovaJogada(ultimaCasa, eu)) {
				game.mudaJogadorDaVez(eu == Jogador.AMARELO ? Jogador.AZUL : Jogador.AMARELO);
				String mensagem = "A última semente caiu em sua Kallah. Você tem direito a uma nova jogada.";
				int messageType = JOptionPane.INFORMATION_MESSAGE;
				this.mostraMensagem(mensagem, messageType);
				
			} else if(game.temDireitoANovaJogada(ultimaCasa, adversario)) {
				game.mudaJogadorDaVez(adversario == Jogador.AMARELO ? Jogador.AZUL : Jogador.AMARELO);
				String mensagem = "A última semente do adversário caiu na Kallah dele. Ele tem direito a uma nova jogada.";
				int messageType =  JOptionPane.WARNING_MESSAGE;
				this.mostraMensagem(mensagem, messageType);
			}
			
			this.atualizaTabuleiro(game);
			
			if(game.isNecessarioFinalizarJogo()) {
				Jogador vencedor = game.finalizaJogo();
				
				if(vencedor == eu) {
					String mensagem = "Parabéns! Você venceu.";
					int messageType = JOptionPane.INFORMATION_MESSAGE;
					this.mostraMensagem(mensagem, messageType);
					
				} else if(vencedor == adversario) {
					String mensagem = "Seu adversário venceu.";
					int messageType = JOptionPane.INFORMATION_MESSAGE;
					this.mostraMensagem(mensagem, messageType);
				}
				
				this.atualizaTabuleiro(game);
			}
			
		} catch(JogadorInvalidoException ex) {
			ex.printStackTrace();
		}
	}
	
	private void mostraMensagem(String message, int messageType) {
		JOptionPane.showMessageDialog(this, message, "Atenção", messageType);
	}
	
	/* Actions Performed - START */
	private void btnEnviarMsgActionPerformed(ActionEvent e) {
		System.out.println("Pressionou botão Enviar");
	}
	
	private void btnDesfazerJogadaActionPerformed(ActionEvent e) {
		this.game.desfazerJogada();
		this.atualizaTabuleiro(game);
	}
	
	private void btnRefazerJogadaActionPerformed(ActionEvent e) {
		this.game.refazerJogada();
		this.atualizaTabuleiro(game);
	}
	
	private void btnReiniciarActionPerformed(ActionEvent e) {
		int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente reiniciar o jogo?", "Confirmação", JOptionPane.WARNING_MESSAGE);
		if(opcao == 0) {
			this.game.reiniciarPartida();
			this.atualizaTabuleiro(game);
			//this.labelInfo.setText("Você desistiu! =( Seu adversário venceu.");
			//Enviar para o outro jogador mensagem que ele ganhou
		}
	}
	
	private void btnDesistirActionPerformed(ActionEvent e) {
		int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente desistir do jogo?", "Confirmação", JOptionPane.WARNING_MESSAGE);
		if(opcao == 0) {
			Jogador vencedor = this.game.desistir(eu);
			this.labelInfo.setText("Você desistiu! =( Seu adversário venceu.");
			//Enviar para o outro jogador mensagem que ele ganhou
		}
	}
	
	private void btnSairActionPerformed(ActionEvent e) {
		int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Confirmação", JOptionPane.WARNING_MESSAGE);
		if(opcao == 0)
			System.exit(0);
	}
	
	private void btnCasaA1ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 1;
			this.moverSementes(casaNoTabuleiro, eu);	
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA2ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 2;
			this.moverSementes(casaNoTabuleiro, eu);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA3ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 3;
			this.moverSementes(casaNoTabuleiro, eu);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA4ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 4;
			this.moverSementes(casaNoTabuleiro, eu);	
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA5ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 5;
			this.moverSementes(casaNoTabuleiro, eu);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA6ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 6;
			this.moverSementes(casaNoTabuleiro, eu);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaB1ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 1;
			this.moverSementes(casaNoTabuleiro, adversario);	
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB2ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 2;
			this.moverSementes(casaNoTabuleiro, adversario);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB3ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 3;
			this.moverSementes(casaNoTabuleiro, adversario);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB4ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 4;
			this.moverSementes(casaNoTabuleiro, adversario);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB5ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 5;
			this.moverSementes(casaNoTabuleiro, adversario);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB6ActionPerformed(ActionEvent e) {
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 6;
			this.moverSementes(casaNoTabuleiro, adversario);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	/* Actions Performed - END */
	
	/**
	 * Declaração das variáveis utilizadas na tela (botões, labels etc).
	 * */
	private JTextArea textAreaChat;
	private JTextField textField_mensagem;
	private JButton btnEnviarMsg;
	private JButton btnDesfazerJogada;
	private JButton btnRefazerJogada;
	private JButton btnReiniciar;
	private JButton btnDesistir;
	private JButton btnSair;
	private JButton btnKallahA;
	private JButton btnCasaA1;
	private JButton btnCasaA2;
	private JButton btnCasaA3;
	private JButton btnCasaA4;
	private JButton btnCasaA5;
	private JButton btnCasaA6;
	private JButton btnKallahB;
	private JButton btnCasaB1;
	private JButton btnCasaB2;
	private JButton btnCasaB3;
	private JButton btnCasaB4;
	private JButton btnCasaB5;
	private JButton btnCasaB6;
	private JLabel labelInfo;

}
