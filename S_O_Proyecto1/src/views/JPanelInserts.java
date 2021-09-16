package views;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import utilities.Utillidades;
import java.awt.Font;
import models.Locked;

public class JPanelInserts extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblId;
	private JLabel lblTime;
	private JRadioButton radYes;
	private JLabel lblAdd;
	private JTextField tfIdentificador;
	private JTextField tfTime;
	private JButton jbuttonAdd;
	private JButton jbuttonInitS;
	private JRadioButton radNot;
	private javax.swing.ButtonGroup ngGenero;
	private JButton buttProcces;
	private JButton buttExecution;
	private JButton buttLocked;
	private JButton buttOrderComp;
	private JTextArea lblIntro;
	private JLabel lblTitleReports;
	

	public JPanelInserts(ActionListener actionListener) {
		setLayout(new GridLayout(2,2));
		setBackground(Color.decode("#3F6260"));
		initComponents(actionListener);
		setVisible(true);
	}

	private void initComponents(ActionListener actionListener) {	
		  Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		  this.setBorder(empty);
		Font fuente = new Font("Times New Roman", 1, 14);
		lblId = new JLabel("Id:");
		lblTime = new JLabel("Tiempo (min):");
		lblIntro = new JTextArea(Constants.DESCRIPTION);
		lblIntro.setBackground(null);
		lblIntro.setForeground(Color.black);
		lblIntro.setFont(fuente);
		fuente = new Font("Times New Roman", 1, 18);
		lblTitleReports = new JLabel("                Reportes");
		lblTitleReports.setFont(fuente);
		lblTitleReports.setForeground(Color.white);
		radYes = new JRadioButton(Locked.SI.name());
		radYes.setBackground(null);
		radNot = new JRadioButton(Locked.NO.name());
		radNot.setBackground(null);
		lblAdd = new JLabel("Bloqueo:");
		// Fields
		tfIdentificador = new JTextField();
		tfTime = new JTextField();
		jbuttonAdd = new JButton(Constants.ADD_PROCESS);
		jbuttonAdd.setBackground(Color.decode("#002E2C"));
		jbuttonAdd.setForeground(Color.white);
		jbuttonAdd.setActionCommand(Constants.ADD_PROCESS);
		jbuttonAdd.addActionListener(actionListener);
		
		jbuttonInitS = new JButton(Constants.INIT_SIMULATION);
		jbuttonInitS.setBackground(Color.decode("#002E2C"));
		jbuttonInitS.setForeground(Color.white);
		jbuttonInitS.setActionCommand(Constants.INIT_SIMULATION);
		jbuttonInitS.addActionListener(actionListener);
		
		JPanel panelDat = new JPanel(new GridLayout(6, 2));
		panelDat.setBackground(Color.decode("#607D7B"));
		panelDat.add(lblId);
		panelDat.add(tfIdentificador);
		panelDat.add(lblTime);
		panelDat.add(tfTime);
		panelDat.add(lblAdd);

		JPanel panelLocked = new JPanel(new GridLayout(1,2));
		panelLocked.setBackground(Color.decode("#607D7B"));
		ngGenero= new ButtonGroup();
		ngGenero.add(radYes);
		ngGenero.add(radNot);

		panelLocked.add(radYes);
		panelLocked.add(radNot);
		panelDat.add(panelLocked);

		panelDat.add(jbuttonAdd);
		panelDat.add(jbuttonInitS);

		add(lblIntro);
		add(lblTitleReports);
		add(panelDat);
		JPanel panelReports = new JPanel(new GridLayout(4,1));
		panelReports.setBackground(Color.decode("#607D7B"));
		
		buttProcces = new JButton(Constants.INITIAL_PROCESS);
		buttProcces.setBackground(null);
		buttProcces.setForeground(Color.black);
		buttProcces.setEnabled(false);
		buttProcces.setActionCommand(Constants.INITIAL_PROCESS);
		buttProcces.addActionListener(actionListener);
		
		buttExecution = new JButton(Constants.EXECUTIONS_REPORT);
		buttExecution.setBackground(null);
		buttExecution.setForeground(Color.black);
		buttExecution.setEnabled(false);
		buttExecution.setActionCommand(Constants.EXECUTIONS_REPORT);
		buttExecution.addActionListener(actionListener);


		buttLocked = new JButton(Constants.CRASH_REPORT);
		buttLocked.setBackground(null);
		buttLocked.setForeground(Color.black);
		buttLocked.setEnabled(false);
		buttLocked.setActionCommand(Constants.CRASH_REPORT);
		buttLocked.addActionListener(actionListener);

		buttOrderComp = new JButton(Constants.ORDER_OF_COMPLETION);
		buttOrderComp.setBackground(null);
		buttOrderComp.setForeground(Color.black);
		buttOrderComp.setEnabled(false);
		buttOrderComp.setActionCommand(Constants.ORDER_OF_COMPLETION);
		buttOrderComp.addActionListener(actionListener);
		
		panelReports.add(buttProcces);
		panelReports.add(buttExecution);
		panelReports.add(buttLocked);
		panelReports.add(buttOrderComp);
		add(panelReports);
	}
	
	public void clean() {
		tfIdentificador.setText("");
		tfTime.setText("");
	}
	
	public String getId() {
		if (tfIdentificador.getText() != null && !tfIdentificador.getText().isEmpty()) {
			return tfIdentificador.getText();
		} else {
			Utillidades.showMessageWarning("Debe ingresar el identificador del proceso", "Advertencia");
		}
		return null;
	}
	
	public int getTime() {
		if (tfTime.getText() != null && !tfTime.getText().isEmpty()) {
			if (Utillidades.isNumeric(tfTime.getText())) {
				return Integer.parseInt(tfTime.getText());
			}else {
				Utillidades.showMessageWarning("la cantidad de tiempo es inválida", "Advertencia");
			}
		} else {
			Utillidades.showMessageWarning("Debe ingresar el tiempo para el proceso", "Advertencia");
		}
		return -1;
	}
	
	public void activeButtons() {
		buttExecution.setEnabled(true);
		buttLocked.setEnabled(true);
		buttOrderComp.setEnabled(true);
		buttProcces.setEnabled(true);

	}
	
	public boolean getLocked(){
		if(!radYes.isSelected() && !radNot.isSelected()) {
			Utillidades.showMessageWarning("Debe seleccionar si el proceso es bloqueado o no", "Advertencia");
		}else {
			return radYes.isSelected() ? true : false;
		}
		return false;

	}

}
