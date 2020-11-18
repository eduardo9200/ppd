package server;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exception.ChatException;
import util.UIUtils;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatServer extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	
	private ServerHandler handler;
	
	private JPanel contentPane;
	private JTextField textFieldPort;
	private JTextField textFieldHost;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public ChatServer() {
		super("Servidor de chat");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldPort = new JTextField();
		textFieldPort.setBounds(50, 54, 197, 32);
		contentPane.add(textFieldPort);
		textFieldPort.setColumns(10);
		
		lblHost = new JLabel("Host");
		lblHost.setBounds(10, 20, 35, 14);
		contentPane.add(lblHost);
		
		lblPort = new JLabel("Port");
		lblPort.setLabelFor(textFieldPort);
		lblPort.setBounds(10, 63, 29, 14);
		contentPane.add(lblPort);
		
		textFieldHost = new JTextField();
		lblHost.setLabelFor(textFieldHost);
		textFieldHost.setBounds(50, 11, 197, 32);
		contentPane.add(textFieldHost);
		textFieldHost.setColumns(10);
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.setBounds(50, 135, 89, 23);
		contentPane.add(btnIniciar);
		
		btnParar = new JButton("Parar");
		btnParar.setBounds(158, 135, 89, 23);
		contentPane.add(btnParar);
		
		lblInfo = new JLabel("");
		lblInfo.setBounds(50, 97, 197, 23);
		contentPane.add(lblInfo);
		
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIniciarActionPerformed(e);
			}
		});
		
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPararActionPerformed(e);
			}
		});
		
		setResizable(false);
		addWindowListener(this);
		setSize(297, 222);
		
		try {
			handler = new ServerHandler();
		} catch(Exception e) {
			UIUtils.displayException(this, e);
		}
		
	}
	
	private void btnIniciarActionPerformed(ActionEvent e) {
		btnIniciar.setEnabled(false);
		btnParar.setEnabled(true);
		
		int port;
		try {
			String porta = this.textFieldPort.getText();
			handler.setServerPort(porta);
			port = handler.startServer(porta);
			lblInfo.setText("Servidor iniciado na porta: " + port);
		} catch (ChatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void btnPararActionPerformed(ActionEvent e) {
		btnIniciar.setEnabled(true);
		btnParar.setEnabled(false);
		
		try {
			handler.stopServer();
		} catch (ChatException ex) {
			UIUtils.displayException(this, ex);
			ex.printStackTrace();
		}
		lblInfo.setText("O servidor parou");
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			if(handler != null) {
				handler.stopServer();
			}
		} catch (ChatException ex) {
			ex.printStackTrace();
		}		
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
	
	private JButton btnIniciar;
	private JButton btnParar;
	private JLabel 	lblHost;
	private JLabel 	lblPort;
	private JLabel	lblInfo;
}
