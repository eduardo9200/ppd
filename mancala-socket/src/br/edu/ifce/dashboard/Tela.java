package br.edu.ifce.dashboard;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ifce.exceptions.JogadorInvalidoException;
import lombok.Getter;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Tela extends JFrame {

	private static final long serialVersionUID = 3284490234670385257L;
	
	private JPanel contentPane;
	
	private List<Integer> casasJogador_1;
	private List<Integer> casasJogador_2;
	
	private GameGeneralRules game;
	private Jogador eu;
	private Jogador adversario;
	
	static ServerSocket serverSocket;
	static Socket socket;
	static DataInputStream dataInputStream;
	static DataOutputStream dataOutputStream;

//	private PrintWriter writer;
	//private BufferedReader reader; 

	/**
	 * Create the frame.
	 */
	public Tela() {
		this.initComponents();
		//this.preencherListaJogadores();
		this.game = this.instanciaJogo();
		this.atualizaTabuleiro(this.game);
		
		this.estabeleceConexao(true);
		//this.estabeleceJogadores();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setSize(800, 600);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Campo de texto para o jogador escrever uma mensagem
		textField_mensagem = new JTextField();
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
		btnDesfazerJogada.setToolTipText("Bug N\u00E3o resolvido");
		btnDesfazerJogada.setEnabled(false);
		btnDesfazerJogada.setBounds(20, 257, 89, 35);
		contentPane.add(btnDesfazerJogada);
		
		//Refazer Jogada
		btnRefazerJogada = new JButton("Refazer");
		btnRefazerJogada.setToolTipText("Bug n\u00E3o resolvido");
		btnRefazerJogada.setEnabled(false);
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(549, 91, 225, 330);
		contentPane.add(scrollPane);
		
		//Textarea do chat
		textAreaChat = new JTextArea();
		scrollPane.setViewportView(textAreaChat);
		textAreaChat.setEditable(false);
		textAreaChat.setLineWrap(true);
		textAreaChat.setWrapStyleWord(true);
		
		btnInformacoes = new JButton("Inform\u00E7\u00F5es");
		btnInformacoes.setBounds(20, 11, 108, 23);
		contentPane.add(btnInformacoes);
		
		btnInformacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnInformacoesActionPerformed(e);
			}
		});
		
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
		
		textField_mensagem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				textField_mensagemKeyReleased(e);
			}
		});
	}
	
	/*private void iniciarEscritor() {
		textField_mensagem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
					textAreaChat.append(textField_mensagem.getText() + "\n");
					
					String msgTerminal = textField_mensagem.getText();
						
					if(msgTerminal == null || msgTerminal.isEmpty()) {
						return;
					}
					
					Object jogadorSelecionadoNaListaChat = list.getSelectedValue();
					
					if(jogadorSelecionadoNaListaChat != null) {
						writer.println(Commands.DEFAULT_MESSAGE_COMMAND + jogadorSelecionadoNaListaChat);
						writer.println(msgTerminal);
						textField_mensagem.setText("");
						
					} else {
						if(msgTerminal.equalsIgnoreCase(Commands.COMAND_EXIT)) {
							System.exit(0);
						}
						
						JOptionPane.showMessageDialog(Tela.this, "Selecione um usuário");
						return;
					}
				}
			}
		});
	}*/
	
	/*private void preencherListaJogadores() {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		
		String[] listaJogadores = new String[] {Jogador.AMARELO.getDescricao(), Jogador.AZUL.getDescricao()};
		list.setModel(modelo);
		
		for(String usuario : listaJogadores) {
			modelo.addElement(usuario);
		}
	}*/
	
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
		
		//System.out.println("Jogador da Vez: " + game.getJogadorDaVez().getDescricao());
		
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
	
	private void estabeleceConexao(boolean isServidor) {
		
		if(isServidor) {
			
			
		} else { //isClient
			
		}
		
		return;
	}
	
	private void estabeleceJogadores(boolean isServidor) {
		if(isServidor) {
			this.eu = Jogador.AMARELO;
			this.adversario = Jogador.AZUL;
			game.mudaJogadorDaVez(Jogador.AMARELO);
			game.mudaJogadorDaVez(Jogador.AZUL);
			
		} else {
			this.eu = Jogador.AZUL;
			this.adversario = Jogador.AMARELO;
			game.mudaJogadorDaVez(Jogador.AZUL);
			game.mudaJogadorDaVez(Jogador.AMARELO);
		}
		
		
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
	
	public void moverSementes(Integer casaEscolhidaNoTabuleiro, Jogador jogador) throws NullPointerException {
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
					this.labelInfo.setText(mensagem);
					
				} else if(vencedor == adversario) {
					String mensagem = "Seu adversário venceu.";
					int messageType = JOptionPane.INFORMATION_MESSAGE;
					this.mostraMensagem(mensagem, messageType);
					this.labelInfo.setText(mensagem);
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
		enviaMensagem();
	}
	
	private void textField_mensagemKeyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			enviaMensagem();
		}
	}
	
	private void btnInformacoesActionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this,
				  "1. O primeiro a iniciar a partida será através de um consenso entre os jogadores, via chat;\n"
				+ "2. Ao clicar em uma casa, as sementes são distribuídas automaticamente;\n"
				+ "3. Os direitos de captura de sementes do adversário e de uma nova jogada são verificadas e informadas automaticamente aos jogadores."
				+ "4. Os arquivos do projeto se encontram em: https://github.com/eduardo9200/ppd/tree/master/mancala-socket"
				+ ""
				+ ""
				+ "");
	}
	
	private void enviaMensagem() {
		try {
			String msgSaida = "";
			msgSaida = textField_mensagem.getText().trim();
			
			if(msgSaida != null && !msgSaida.isEmpty()) {
				dataOutputStream.writeUTF(msgSaida);
				textAreaChat.append("me: " + msgSaida + "\n");	
			}
			
			textField_mensagem.setText("");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void btnDesfazerJogadaActionPerformed(ActionEvent e) {
		this.game.desfazerJogada();
		this.atualizaTabuleiro(game);
		
		Long idJogadorEu = this.eu.getId();
		String msgOutput = Commands.COMMAND_UNDO + "/" + idJogadorEu;
		this.enviaMensagemComando(msgOutput);
	}
	
	private void btnRefazerJogadaActionPerformed(ActionEvent e) {
		this.game.refazerJogada();
		this.atualizaTabuleiro(game);
		
		Long idJogadorEu = this.eu.getId();
		String msgOutput = Commands.COMMAND_REDO + "/" + idJogadorEu;
		this.enviaMensagemComando(msgOutput);
	}
	
	private void btnReiniciarActionPerformed(ActionEvent e) {
		int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente reiniciar o jogo?", "Confirmação", JOptionPane.WARNING_MESSAGE);
		if(opcao == 0) {
			this.game.reiniciarPartida();
			this.atualizaTabuleiro(game);

			String msgOutput = Commands.COMMAND_RESET_GAME;
			this.enviaMensagemComando(msgOutput);
		}
	}
	
	private void btnDesistirActionPerformed(ActionEvent e) {
		int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente desistir do jogo?", "Confirmação", JOptionPane.WARNING_MESSAGE);
		if(opcao == 0) {
			Jogador vencedor = this.game.desistir(eu);
			this.labelInfo.setText("Você desistiu! =( Seu adversário venceu.");
			
			String msgOutput = Commands.COMMAND_GIVE_UP + "/" + vencedor.getId();
			this.enviaMensagemComando(msgOutput);
		}
	}
	
	private void btnSairActionPerformed(ActionEvent e) {
		int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Confirmação", JOptionPane.WARNING_MESSAGE);
		if(opcao == 0) {
			//String msgOutput = Commands.COMMAND_EXIT;
			//this.enviaMensagemComando(msgOutput);
			System.exit(0);
		}
	}
	
	private void enviaMensagemComando(String mensagem) {
		try {
			dataOutputStream.writeUTF(mensagem);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void btnCasaA1ActionPerformed(ActionEvent e) {
		int idCasa = 1;
		Long idJogador = Jogador.AMARELO.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 1;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
			
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA2ActionPerformed(ActionEvent e) {
		int idCasa = 2;
		Long idJogador = Jogador.AMARELO.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 2;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA3ActionPerformed(ActionEvent e) {
		int idCasa = 3;
		Long idJogador = Jogador.AMARELO.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 3;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA4ActionPerformed(ActionEvent e) {
		int idCasa = 4;
		Long idJogador = Jogador.AMARELO.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 4;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA5ActionPerformed(ActionEvent e) {
		int idCasa = 5;
		Long idJogador = Jogador.AMARELO.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 5;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaA6ActionPerformed(ActionEvent e) {
		int idCasa = 6;
		Long idJogador = Jogador.AMARELO.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AMARELO)) {
			game.mudaJogadorDaVez(Jogador.AMARELO);
			int casaNoTabuleiro = 6;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador azul!");
		}
	}
	
	private void btnCasaB1ActionPerformed(ActionEvent e) {
		int idCasa = 1;
		Long idJogador = Jogador.AZUL.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 1;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB2ActionPerformed(ActionEvent e) {
		int idCasa = 2;
		Long idJogador = Jogador.AZUL.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 2;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB3ActionPerformed(ActionEvent e) {
		int idCasa = 3;
		Long idJogador = Jogador.AZUL.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 3;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB4ActionPerformed(ActionEvent e) {
		int idCasa = 4;
		Long idJogador = Jogador.AZUL.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 4;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB5ActionPerformed(ActionEvent e) {
		int idCasa = 5;
		Long idJogador = Jogador.AZUL.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 5;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	private void btnCasaB6ActionPerformed(ActionEvent e) {
		int idCasa = 6;
		Long idJogador = Jogador.AZUL.getId();
		String msgSaida = Commands.COMMAND_MOVE_SEEDS + "/" + idCasa + "/" + idJogador;
		
		if(validaJogada(Jogador.AZUL)) {
			game.mudaJogadorDaVez(Jogador.AZUL);
			int casaNoTabuleiro = 6;
			this.moverSementes(casaNoTabuleiro, eu);
			this.enviaMensagemComando(msgSaida);
		} else {
			JOptionPane.showMessageDialog(this, "É a vez do jogador amarelo");
		}
	}
	
	/*public void iniciarChat() {
		try {
			final Socket clientSocket = new Socket(Commands.DEFAULT_LOCAL_SERVER, 9999);
			
			Boolean autoFlush = true;
			writer = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
			
			InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
			reader = new BufferedReader(inputStreamReader);
			
			try {
				
				while(true) {
					String msg = reader.readLine();
					
					if(msg == null || msg.isEmpty()) {
						continue;
					}
					
					textAreaChat.append("Resposta do servidor: " + msg);
				}
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Não foi possível ler a mensagem do servidor.");
				e.printStackTrace();
			}
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Endereço inválido");
			e.printStackTrace();
		
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao iniciar");
			e.printStackTrace();
		}
	}*/
	
	public void gerenciaClienteServidor(Boolean isServidor, Integer porta, String host) {
		
		this.estabeleceJogadores(isServidor);
		
		if(isServidor) {
			
			String mensagemEntrada = "";
			
			try {
				serverSocket = new ServerSocket(porta);
				socket = serverSocket.accept();
				
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());			
				
				while(true) {
					mensagemEntrada = dataInputStream.readUTF();
					
					if(mensagemEntrada.startsWith(Commands.DEFAULT_COMMAND)) {
						executaComando(mensagemEntrada);
						
					} else {
						textAreaChat.append("Client: " + mensagemEntrada + "\n");	
					}
				}
				
			} catch(IOException e) {
				JOptionPane.showMessageDialog(this, "Falha! O servidor foi finalizado ou a porta informada pode já estar em uso.", "Falha", JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
				System.exit(0);
			}
			
		} else { //isClient
			try {
				socket = new Socket(host, porta);
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
				String msgEntrada = "";
				
				while(true) {
					msgEntrada = dataInputStream.readUTF();
					
					if(msgEntrada.startsWith(Commands.DEFAULT_COMMAND)) {
						executaComando(msgEntrada);
						
					} else {
						textAreaChat.append("Server: " + msgEntrada + "\n");	
					}
				}
						
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void executaComando(String comando) {
		//System.out.println("executando");
		//System.out.println(comando);
		
		String[] parametros = comando.split("/");
		String mainCommand = parametros[0];
		
		if(mainCommand.equals(Commands.COMMAND_MOVE_SEEDS)) {
			executaMover(parametros[1], parametros[2]);
		
		} else if(mainCommand.equals(Commands.COMMAND_GIVE_UP)) {
			executaDesistirJogo(parametros[1]);
			
		} else if(mainCommand.equals(Commands.COMMAND_UNDO)) {
			executaDesfazerJogada();
		
		} else if(mainCommand.equals(Commands.COMMAND_REDO)) {
			executaRefazerJogada();
		
		} else if(mainCommand.equals(Commands.COMMAND_RESET_GAME)) {
			executaReiniciarPartida();
		
		} else if(mainCommand.equals(Commands.COMMAND_EXIT)) {
			this.labelInfo.setText("Seu adversário saiu do jogo.");
		}
		
	}
	
	private void executaReiniciarPartida() {
		this.game.reiniciarPartida();
		this.atualizaTabuleiro(game);
		this.labelInfo.setText("Partida reiniciada.");
	}
	
	private void executaDesistirJogo(String idJogadorVencedor) {
		if(Integer.parseInt(idJogadorVencedor) == eu.getId()) {
			this.labelInfo.setText("Você venceu! Seu adversário desistiu.");
		}
	}
	
	private void executaMover(String idCasaEscolhida, String idJogador) {
		//System.out.println("movendo");
		
		Integer casaEscolhida = Integer.parseInt(idCasaEscolhida);
		Jogador jogador;
		
		try {
			jogador = Jogador.getById(Long.parseLong(idJogador));
			this.game.mudaJogadorDaVez(jogador == Jogador.AMARELO ? Jogador.AMARELO : Jogador.AZUL);
			this.moverSementes(casaEscolhida, jogador);
			this.atualizaTabuleiro(game);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JogadorInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void executaDesfazerJogada() {
		game.desfazerJogada();
		game.mudaJogadorDaVez(eu == Jogador.AMARELO ? Jogador.AMARELO : Jogador.AZUL);
		this.atualizaTabuleiro(game);
	}
	
	private void executaRefazerJogada() {
		game.desfazerJogada();
		game.mudaJogadorDaVez(eu == Jogador.AMARELO ? Jogador.AMARELO : Jogador.AZUL);
		this.atualizaTabuleiro(game);
	}
	
	public static void main(String[] args) {
		
		Boolean isServidor = false;
		Integer porta = null;
		String host = null;
		
		int valor = JOptionPane.showConfirmDialog(null, "Você está entrando como servidor?", "Servidor ou Cliente", JOptionPane.YES_NO_OPTION);
		
		if(valor == 0) { //Pressionou 'Yes'
			isServidor = true;
			
			String input = null;
			while (input == null || input.isEmpty()) {
				input = JOptionPane.showInputDialog(null, "Informe a porta do servidor", "Porta do Servidor", JOptionPane.QUESTION_MESSAGE);	
			}
			
			porta = Integer.parseInt(input);
			
		} else if(valor == 1) { //Pressionou 'No'
			isServidor = false;
			String inputHost = null;
			String inputPort = null;
			
			JOptionPane.showMessageDialog(null, "Atenção! Você está entrando como cliente.");
			
			while(inputHost == null || inputHost.isEmpty()) {
				inputHost = JOptionPane.showInputDialog(null, "Informe o host", "Porta do Servidor", JOptionPane.QUESTION_MESSAGE);
			}
			
			while(inputPort == null || inputPort.isEmpty()) {
				inputPort = JOptionPane.showInputDialog(null, "Informe a porta do servidor", "Porta do Servidor", JOptionPane.QUESTION_MESSAGE);
			}
			
			porta = Integer.parseInt(inputPort);
		}
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {*/
				try {
					Tela frame = new Tela();
					frame.setTitle("Mancala - Sockets");
					frame.setSize(800, 600);
					frame.setVisible(true);
					
					frame.gerenciaClienteServidor(isServidor, porta, host);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			/*}
		});*/
	}
	
	
	/* Actions Performed - END */
	
	/**
	 * Declaração das variáveis utilizadas na tela (botões, labels etc).
	 * */
	@Getter
	private static JTextArea textAreaChat;
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
	private JScrollPane scrollPane;
	private JButton btnInformacoes;
}
