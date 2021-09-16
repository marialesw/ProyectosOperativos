package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;

	private JPanel panelActual;

	private JPanelInserts panelInserts;

	public MainWindow(ActionListener actionListener) {
		Image icon = new ImageIcon(getClass().getResource("/img/Logo.png")).getImage();
        setIconImage(icon);
		setTitle("SIMULACION DE PROCESOS");
		CentrarJFrame();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents(actionListener);
		setVisible(true);
	}

	private void initComponents(ActionListener actionListener) {
		panelInserts = new JPanelInserts(actionListener);
		add(panelInserts, BorderLayout.CENTER);
	}

	public void changePanel(JPanel panel) {
		getContentPane().remove(panelActual);
		panelActual = panel;
		add(panelActual, BorderLayout.CENTER);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	public String getId() {
		return panelInserts.getId();
	}

	public int getTime() {
		return panelInserts.getTime();
	}
	public void clean() {
		panelInserts.clean();
	}
	public boolean getLocked() {
		return panelInserts.getLocked();
	}
	public void activeButtons() {
		panelInserts.activeButtons();
	}

	public static void showWarningMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	private void CentrarJFrame(){
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize(width/2, height/2);		

		setLocationRelativeTo(null);		
		setVisible(true);
	}
}
