package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import datastructures.MyQueue;
import models.Particion;
import models.Process;
import utilities.Utillidades;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;

	private JPanel panelActual;
	private JPanelInserts panelInserts;
	private JMenuBar jMenuBar;
	private JMenu jMenu1;
	private JMenuItem jMenuItem1, jMenuItem2;
	private ActionListener actionListener;

	private Font fuenteGeneral;

	public MainWindow(ActionListener actionListener) {
		this.actionListener = actionListener;
		setUndecorated(true);//
		setTitle("SIMULACION DE PROCESOS");
		CentrarJFrame();
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}

	private void initComponents() {
		jMenuBar = new JMenuBar();
		fuenteGeneral = new Font(Constants.FONT_FORMULARIOS, Font.ROMAN_BASELINE, 18);
		
		JButton button = new JButton("salir");
		button.setFont(fuenteGeneral);
		button.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		button.setForeground(Color.white);
		button.setActionCommand(Constants.EXIT);
		button.addActionListener(actionListener);
		
		jMenu1 = new JMenu(Constants.OPTIONS);
		jMenuItem1 = new JMenuItem(Constants.CHANGE_DIR);
		jMenuItem2 = new JMenuItem(Constants.EXIT);
		jMenu1.add(jMenuItem1);
		jMenu1.add(jMenuItem2);
		jMenuBar.add(jMenu1);
		jMenuBar.add(button);
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
	
	public String getPartition() {
		return panelInserts.getPartition();
	}

	public void openPDF() {
		try {
			java.lang.Process p = Runtime.getRuntime().exec ("rundll32 SHELL32.DLL,"
					+ "ShellExec_RunDLL " + "D:\\asus\\Desktop\\ESCRITORIO\\PROGRAMACION III V\\S_O_Proyecto 6\\files\\informe.pdf");
		System.out.println("sE ABREEE");
		} catch (Exception e) {
		Utillidades.showMessageError("No se puede abrir el archivo", "ERROR");
		}
	}
	
	public int getRowSelected() {
		return panelInserts.getRowSelected();
	}
	
	public void addItemList(String element) {
		panelInserts.addItemList(element);
	}
	public int getSelectionList() {
		return panelInserts.getSelection();
	}

	public void deleteRow(int row) {
		panelInserts.deleteRow(row);
	}
	public int geSelectRowTable() {
		return panelInserts.geSelectRowTable();
	}
//	public void deletef(SimplyCircleList<Particion> listadeParticiones){
//		return jdialogComunication.fillCombobox(listadeParticiones);
//	}
	
	public String getNamePartition() {
		return panelInserts.getNamePartition();
	}
	public int getSizePartition() {
		return panelInserts.getSizePartition();
	}
	public int getSizeProcess() {
		return panelInserts.getSizeProcess();
	}
	public void reinitCampsParticion(){
		panelInserts.reinitCampsParticion();
	}
	
	public void generateFileChooser() {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Usuario\\eclipse-workspace\\ProyectoJJ\\files");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int resultado = chooser.showOpenDialog(this);
		File archivo = chooser.getSelectedFile();
//		if ((archivo == null)  archivo.getName().equals("")) ) {
//			Utillidades.showMessageError("seleccione el archivo", "error");
//		}else {
//			
//		}
	}
}
