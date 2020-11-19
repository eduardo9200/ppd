package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dashboard.CasaUltimaSemente;
import dashboard.Commands;
import dashboard.GameGeneralRules;
import dashboard.Jogador;
import exception.ChatException;
import exception.JogadorInvalidoException;
import lombok.Getter;
import util.UIUtils;

public class ChatClient extends JFrame implements WindowListener {

private static final long serialVersionUID = 3284490234670385257L;
	
	private JPanel contentPane;
	
	private List<Integer> casasJogador_1;
	private List<Integer> casasJogador_2;
	
	private GameGeneralRules game;
	private Jogador eu;
	private Jogador adversario;
	
	private ServerInvoker serverInvoker;
	
	private String host;
	private int porta;
	private String nome;
	
	private boolean jogadorEstabelecido = false;

	/**
	 * Create the frame.
	 */
	public ChatClient() {
		this.initComponents();
		//this.preencherListaJogadores();
		this.game = this.instanciaJogo();
		this.atualizaTabuleiro(this.game);
		
		//this.estabeleceJogadores();
		setConnected(false);
		chamaTelaConfiguracoes();
	}
	
	private void setConnected(boolean connected) {
		if(!connected) {
			//habilita/desabilita botões e labels
		} else {
			
		}
	}
	
	private void chamaTelaConfiguracoes() {
		nome = JOptionPane.showInputDialog("Seu nome");
		host = JOptionPane.showInputDialog("Informe o Host");
		
		String port = JOptionPane.showInputDialog("Informe a porta");
		porta = Integer.parseInt(port);
		
		conectarAoServidor();
	}
	
	private void conectarAoServidor() {
		try {
			this.serverInvoker = new ServerInvoker(this.host, this.porta, this.nome, this);
			serverInvoker.connect();
			setConnected(true);
			
		} catch(ChatException e) {
			e.printStackTrace();
		}
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
		
		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setBounds(253, 11, 102, 23);
		contentPane.add(btnDesconectar);
		
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDesconectarActionPerformed(e);
			}
		});
		
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
		System.out.println(ultimaCasa.getIdTabuleiro());
		if(ultimaCasa.getIdTabuleiro() == null) {
			String mensagem = "A casa escolhida está vazia. Escolha outra casa.";
			int messageType = JOptionPane.WARNING_MESSAGE;
			this.mostraMensagem(mensagem, messageType);
			game.mudaJogadorDaVez(jogadorDaVez == Jogador.AMARELO ? Jogador.AZUL : Jogador.AMARELO); //Garante que a vez não será passada ao outro jogador, caso a casa selecionada esteja vazia
			//throw new NullPointerException("Casa selecionada vazia.");
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
	
	private void btnDesconectarActionPerformed(ActionEvent e) {
		try {
			this.serverInvoker.disconnect();
		} catch (ChatException e1) {
			e1.printStackTrace();
		}
		this.setConnected(false);
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
	
	private void enviaMensagemComando(String mensagem) {
		try {
			serverInvoker.sendMessage(mensagem);
		} catch (ChatException e) {
			e.printStackTrace();
		}
	}
	
	private void enviaMensagem() {
		String msgSaida = "";
		msgSaida = textField_mensagem.getText().trim();
		
		if(msgSaida != null && !msgSaida.isEmpty()) {
			try {
				serverInvoker.sendMessage(msgSaida);
			} catch (ChatException e) {
				e.printStackTrace();
			}
		}
		
		textField_mensagem.setText("");
	}
	
	public void showMessageFromServer(String message) {
		System.out.println(message);
		if(message.contains(Commands.DEFAULT_COMMAND)) {
			executaComando(message);
			
		} else {
			if(!this.jogadorEstabelecido) {
				if(message.split("/")[1].equals("1")) {
					this.estabeleceJogadores(true);
					this.jogadorEstabelecido = true;
				}
					
				else if(message.split("/")[1].equals("2")) {
					this.estabeleceJogadores(false);
					this.jogadorEstabelecido = true;
				}	
			}
			textAreaChat.append(message + "\n");
		}
	}
	
	public void handleServerShutdown() {
		UIUtils.displayAlert(this, "Servidor fora do ar", "O servidor do chat saiu do ar");
		setConnected(false);
	}
	
	private void executaComando(String comando) {
		//System.out.println("executando");
		//System.out.println(comando);
		
		String[] parametros = comando.split("/");
		String mainCommand = parametros[0];
		
		if(mainCommand.contains(Commands.COMMAND_MOVE_SEEDS)) {
			executaMover(parametros[1], parametros[2]);
		
		} else if(mainCommand.contains(Commands.COMMAND_GIVE_UP)) {
			executaDesistirJogo(parametros[1]);
			
		} else if(mainCommand.contains(Commands.COMMAND_UNDO)) {
			executaDesfazerJogada();
		
		} else if(mainCommand.contains(Commands.COMMAND_REDO)) {
			executaRefazerJogada();
		
		} else if(mainCommand.contains(Commands.COMMAND_RESET_GAME)) {
			executaReiniciarPartida();
		
		} else if(mainCommand.contains(Commands.COMMAND_EXIT)) {
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

	@Override
	public void windowClosing(WindowEvent event) {
		try {
			if(serverInvoker != null) {
				serverInvoker.disconnect();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
