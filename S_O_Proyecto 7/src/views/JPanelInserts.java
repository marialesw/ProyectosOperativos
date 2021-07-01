package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import datastructures.MyQueue;
import utilities.Utillidades;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import models.Locked;
import models.Process;

public class JPanelInserts extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblName;
	private JLabel lblTime;
	private JLabel lbPriority;
	private JTextField tfNameProcess;
	private JTextField tfTime;
	private JButton jbuttonAdd;
	private JButton jbuttonUpdate;
	private JButton buttProcces;
	private JButton buttExecution;
	private JButton buttLocked;
	private JButton buttOrderComp;
	private JLabel lblIntro;
	private JLabel lblTitle;
	private JList<String> listProcess;
	private JScrollPane scrollLista;
	private JComboBox jcomPartition;
	private JButton jButtonNewComunication;
	private JPanelTable panelTable;
	private JButton jbuttonDelete;
	private Font fuenteGeneral;
	private DefaultListModel<String> modelo;
	private JButton jButtonDeleteComunication;
	private JLabel lblNamePart;
	private JLabel lblSizePart;
	private JTextField tfNamePart;
	private JTextField tfSizePart;
	private JLabel lblSizeProcess;
	private JTextField tfSizeProcess;
	private JButton jbuttonAddProcess;
	private JButton jbuttonDeleteProcess;
	private JButton jbuttonUpdateProcess;
	private JLabel lblTamño;
	private JTextField tfTamaño;


	public JPanelInserts(ActionListener actionListener) {
		setLayout(new GridLayout(1,2));
		setBackground(Color.decode(Constants.COLOR_FONDO));
		initComponents(actionListener);
		setVisible(true);
	}

	private void initComponents(ActionListener actionListener) {	
		panelTable = new JPanelTable(actionListener);

		Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		this.setBorder(empty);
		JPanel panelEast = new JPanel(new GridLayout(4, 1));

		JPanel jPanelIntro = new JPanel(new GridLayout(2,1));
		jPanelIntro.setBackground(Color.decode(Constants.COLOR_TITLE));
		Font fuente = new Font(Constants.FONT_TITLES, Font.BOLD, 18);
		lblTitle = new JLabel(Constants.TITLE);
		lblTitle.setBackground(null);
		lblTitle.setBorder(null);
		lblTitle.setForeground(Color.black);
		lblTitle.setFont(fuente);
		jPanelIntro.add(lblTitle);

		fuenteGeneral = new Font(Constants.FONT_FORMULARIOS, Font.PLAIN, 18);

		lblIntro = new JLabel(Constants.DESCRIPTION);
		lblIntro.setBackground(null);
		lblIntro.setBorder(null);
		lblIntro.setForeground(Color.black);
		lblIntro.setFont(fuenteGeneral);
		jPanelIntro.add(lblIntro);

		createPanelProcceso();

		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		configureButtons(actionListener);

		JPanel panelIngress = new JPanel(new GridLayout(1,6));
		panelIngress.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		panelIngress.setBorder(new MatteBorder(10,10,10,10,Color.decode(Constants.COLOR_FORMULARIO)));

		lblNamePart = new JLabel("Nombre (partición):", SwingConstants.CENTER);
		lblNamePart.setFont(fuenteGeneral);
		tfNamePart = new JTextField("");
		tfNamePart.setBorder(BorderFactory.createLoweredBevelBorder());

		lblSizePart = new JLabel("Tamaño (partición): " , SwingConstants.CENTER);
		lblSizePart.setFont(fuenteGeneral);

		tfSizePart = new JTextField();
		tfSizePart.setBorder(BorderFactory.createLoweredBevelBorder());

		panelIngress.add(lblNamePart);
		panelIngress.add(tfNamePart);
		panelIngress.add(lblSizePart);
		panelIngress.add(tfSizePart);

		JPanel panelComunication = new JPanel(new GridLayout(1,2));
		panelComunication.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		
		Border bordePanelDat = new TitledBorder(new EtchedBorder(), "Datos del proceso");
		panelComunication.setBorder(bordePanelDat);
		
		JPanel panelTamaño = new JPanel(new GridLayout(1,2));
		panelTamaño.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		
		Border bordePanelTamaño = new TitledBorder(new EtchedBorder(), "Datos de Memoria");
		panelTamaño.setBorder(bordePanelTamaño);

		JPanel jPanelDatosdeProceso = new JPanel(new GridLayout(3, 2));
		jPanelDatosdeProceso.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		lblName = new JLabel("Nombre (proceso):", SwingConstants.CENTER);
		lblName.setFont(fuenteGeneral);

		lblTime = new JLabel("Tiempo (proceso): " , SwingConstants.CENTER);
		lblTime.setFont(fuenteGeneral);

		lblSizeProcess = new JLabel("Tamaño (proceso): " , SwingConstants.CENTER);
		lblSizeProcess.setFont(fuenteGeneral);
		
		JPanel jPanelDatosMemoria = new JPanel(new GridLayout(1, 2));
		jPanelDatosMemoria.setBackground(Color.decode(Constants.COLOR_FORMULARIO));

		lblTamño = new JLabel("Tamaño de memoria: " , SwingConstants.CENTER);
		lblTamño.setFont(fuenteGeneral);
		
		tfTamaño = new JTextField("");
		tfTamaño.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		tfTamaño.setBorder(null);
		
		jPanelDatosMemoria.add(lblTamño);
		jPanelDatosMemoria.add(tfTamaño);
		
		jPanelDatosdeProceso.add(lblName);
		jPanelDatosdeProceso.add(tfNameProcess);
		jPanelDatosdeProceso.add(lblTime);
		jPanelDatosdeProceso.add(tfTime);
		jPanelDatosdeProceso.add(lblSizeProcess);
		jPanelDatosdeProceso.add(tfSizeProcess);
		panelTamaño.add(jPanelDatosMemoria);
		panelComunication.add(jPanelDatosdeProceso);		


		panelButtons.add(jbuttonAddProcess);
//		panelButtons.add(jbuttonDeleteProcess);
//		panelButtons.add(jbuttonUpdateProcess);

		panelEast.add(jPanelIntro);
		panelEast.add(panelTamaño);
		panelEast.add(panelComunication);
		panelEast.add(panelButtons);

		add(panelEast);
		add(panelTable);
		//add(lblTitleReports);
	}

	private void createPanelProcceso() {
		tfNameProcess = new JTextField();
		tfNameProcess.setBorder(BorderFactory.createLoweredBevelBorder());
		tfTime = new JTextField();
		tfTime.setBorder(BorderFactory.createLoweredBevelBorder());
		tfSizeProcess = new JTextField();
		tfSizeProcess.setBorder(BorderFactory.createLoweredBevelBorder());
	}

	private void configureButtons(ActionListener actionListener) {
		fuenteGeneral = new Font(Constants.FONT_FORMULARIOS, Font.ROMAN_BASELINE, 18);
		jbuttonAdd = new JButton(Constants.ADD_PARTITION);
		jbuttonAdd.setFont(fuenteGeneral);
		jbuttonAdd.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jbuttonAdd.setForeground(Color.white);
		jbuttonAdd.setActionCommand(Constants.ADD_PARTITION);
		jbuttonAdd.addActionListener(actionListener);
		
		jbuttonDelete = new JButton(Constants.DELETE_PARTICION);
		jbuttonDelete.setFont(fuenteGeneral);
		jbuttonDelete.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jbuttonDelete.setForeground(Color.white);
		jbuttonDelete.setActionCommand(Constants.DELETE_PARTICION);
		jbuttonDelete.addActionListener(actionListener);

		jbuttonAddProcess = new JButton(Constants.BUTTON_ADD_PROCESS);
		jbuttonAddProcess.setFont(fuenteGeneral);
		jbuttonAddProcess.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jbuttonAddProcess.setForeground(Color.white);
		jbuttonAddProcess.setActionCommand(Constants.BUTTON_ADD_PROCESS);
		jbuttonAddProcess.addActionListener(actionListener);

//		jbuttonDeleteProcess = new JButton(Constants.BUTTON_DELETE_PROCESS);
//		editarBoton(jbuttonDeleteProcess, actionListener, Constants.BUTTON_DELETE_PROCESS);
//
//		jbuttonUpdateProcess = new JButton(Constants.BUTTON_EDITAR_PROCESS);
//		editarBoton(jbuttonUpdateProcess, actionListener, Constants.BUTTON_EDITAR_PROCESS);
	}

	private void editarBoton(JButton button, ActionListener actionListener, String command) {
		button.setFont(fuenteGeneral);
		button.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		button.setForeground(Color.white);
		button.setActionCommand(command);
		button.addActionListener(actionListener);
	}

	public void clean() {
		tfNameProcess.setText("");
		tfTime.setText("");
		tfSizeProcess.setText("");
	}

	public String getName() {
		if (tfNameProcess.getText() != null && !tfNameProcess.getText().isEmpty()) {
			return tfNameProcess.getText();
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
	
	public int getSizeProcess() {
		if (tfSizeProcess.getText() != null && !tfSizeProcess.getText().isEmpty()) {
			if (Utillidades.isNumeric(tfSizeProcess.getText())) {
				return Integer.parseInt(tfSizeProcess.getText());
			}else {
				Utillidades.showMessageWarning("el tamaño del proceso es inválido", "Advertencia");
			}
		} else {
			Utillidades.showMessageWarning("Debe ingresar el tamaño del proceso", "Advertencia");
		}
		return -1;
	}

	public void activeButtons() {
		buttExecution.setEnabled(true);
		buttLocked.setEnabled(true);
		buttOrderComp.setEnabled(true);
		buttProcces.setEnabled(true);
	}

	public String getPartition() {
		String select = jcomPartition.getSelectedItem().toString();
		return select;
	}

	public void addElement(String[] fila) {
		panelTable.addElemn(fila);
	}
	public void deleteRows() {
		panelTable.deleteRows();
	}

	public void addItemList(String element) {
		modelo.addElement(element);
	}

	public int getSelection(){
		return listProcess.getSelectedIndex();
	}
	
	public void deleteRow(int row) {
		panelTable.deleteRow(row);
	}

	public int geSelectRowTable() {
		return panelTable.rowsSelect();
	}

	public String getNamePartition() {
		if (tfNamePart.getText() != null && !tfNamePart.getText().isEmpty()) {
			return tfNamePart.getText();
		} else {
			Utillidades.showMessageWarning("Debe ingresar el nombre de la particion", "Advertencia");
		}
		return null;
	}
	public int getSizePartition() {
		if (tfSizePart.getText() != null && !tfSizePart.getText().isEmpty()) {
			if (Utillidades.isNumeric(tfSizePart.getText())) {
				return Integer.parseInt(tfSizePart.getText());
			}else {
				Utillidades.showMessageWarning("el tamaño de la partición es inválido", "Advertencia");
			}
		} else {
			Utillidades.showMessageWarning("Debe ingresar el tamaño de la partición", "Advertencia");
		}
		return -1;
	}
	
	public int getSizeMemory() {
		if (tfTamaño.getText() != null && !tfTamaño.getText().isEmpty()) {
			if (Utillidades.isNumeric(tfTamaño.getText())) {
				return Integer.parseInt(tfTamaño.getText());
			}else {
				Utillidades.showMessageWarning("el tamaño de memoria no es correcto", "Advertencia");
			}
		} else {
			Utillidades.showMessageWarning("Debe ingresar el tamaño de memoria", "Advertencia");
		}
		return -1;
	}
	
	public void reinitCampsParticion(){
		tfNamePart.setText("");
		tfSizePart.setText("");
	}
	public int getRowSelected() {
		return panelTable.rowsSelect();
	}
	public void deleteActual() {
		panelTable.deleteActual();
	}
}