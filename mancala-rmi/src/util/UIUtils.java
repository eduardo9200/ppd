package util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JOptionPane;

public class UIUtils {

	public static void centerWindow(Window window) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		window.setLocation(screenSize.width / 2 - window.getWidth() / 2, screenSize.height / 2 - window.getHeight() / 2);
	}
	
	public static void displayException(Component parent, Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(parent, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void displayAlert(Component parent, String title, String message) {
		if(title == null) {
			JOptionPane.showMessageDialog(parent, message);
			return;
		} else {
			JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE); 	
		}
	}
}
