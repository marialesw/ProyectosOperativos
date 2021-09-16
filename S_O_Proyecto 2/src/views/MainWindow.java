package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.MyQueue;
import models.Process;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;

	private JPanel panelActual;

	private JPanelInserts panelInserts;
	private JMenuBar jMenuBar;
	private JMenu jMenu1;
	private JMenuItem jMenuItem1, jMenuItem2;

	private JDialogAddComunications jdialogComunication;
	private ActionListener actionListener;

	public MainWindow(ActionListener actionListener) {
		this.actionListener = actionListener;
		Image icon = new ImageIcon(getClass().getResource("/img/Logo.png")).getImage();
        setIconImage(icon);
		setTitle("SIMULACION DE PROCESOS");
		CentrarJFrame();
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}

	private void initComponents() {
		jMenuBar = new JMenuBar();
		jMenu1 = new JMenu(Constants.OPTIONS);
		jMenuItem1 = new JMenuItem(Constants.CHANGE_DIR);
		jMenuItem2 = new JMenuItem(Constants.EXIT);
		jMenu1.add(jMenuItem1);
		jMenu1.add(jMenuItem2);
		jMenuBar.add(jMenu1);
		setJMenuBar(jMenuBar);
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
	public void createDialog(MyQueue<Process>  myQueue) {
		jdialogComunication = new JDialogAddComunications(myQueue, actionListener);
		jdialogComunication.setVisible(true);
	}

	public String getName() {
		return panelInserts.getName();
	}

	public int getTime() {
		return panelInserts.getTime();
	}
	public void clean() {
		panelInserts.clean();
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
	
	public void addElement(String[] fila) {
		panelInserts.addElement(fila);
	}
	public void deleteRows() {
		panelInserts.deleteRows();
	}
	public boolean getLocked() {
		return panelInserts.getLocked();
	}
	public boolean isLay() {
		return panelInserts.isLay();
	}
	public boolean isDestroy() {
		return panelInserts.isDestroy();
	}
	public int getTimeDestroy() {
		return panelInserts.getTimeDestroy();
	}
	public int getPriority() {
		return panelInserts.getPriority();
	}
	public void setEditableTime(boolean b) {
		panelInserts.setEditableTime(b);
	}
	public String getComunicationProcess() {
		return jdialogComunication.getComunication();
	}
	public void addItemList(String element) {
		panelInserts.addItemList(element);
	}
	public int getSelectionList() {
		return panelInserts.getSelection();
	}
	public MyQueue<String> getListInformation(){
		return panelInserts.getListInformacion();
	}
	public void removeProcessComunication() {
		panelInserts.removeProcessComunication();
	}
	public boolean isInListInformacion(String item) {
		return panelInserts.isInListInformacion(item);
	}
	public void deleteRow(int row) {
		panelInserts.deleteRow(row);
	}
	public int geSelectRowTable() {
		return panelInserts.geSelectRowTable();
	}
}
