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
import utilities.Utillidades;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import models.Locked;
import models.MyQueue;
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
	private JPanel jPanelListComunications;
	private JComboBox jcomPriority;
	private JButton jButtonNewComunication;
	private JPanelTable panelTable;
	private JPanelOptions jPanelOptions;
	private JButton jbuttonDelete;
	private Font fuenteGeneral;
	private DefaultListModel<String> modelo;
	private JButton jButtonDeleteComunication;


	public JPanelInserts(ActionListener actionListener) {
		setLayout(new GridLayout(1,2));
		setBackground(Color.decode(Constants.COLOR_FONDO));
		initComponents(actionListener);
		setVisible(true);
	}

	private void initComponents(ActionListener actionListener) {	
		panelTable = new JPanelTable(actionListener);
		jPanelOptions = new JPanelOptions(actionListener);

		Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		this.setBorder(empty);
		JPanel panelEast = new JPanel(new GridLayout(4, 1));

		JPanel jPanelIntro = new JPanel(new GridLayout(2,1));
		jPanelIntro.setBackground(Color.decode(Constants.COLOR_TITLE));
		Font fuente = new Font(Constants.FONT_TITLES, Font.BOLD, 16);
		lblTitle = new JLabel(Constants.TITLE);
		lblTitle.setBackground(null);
		lblTitle.setBorder(null);
		lblTitle.setForeground(Color.black);
		lblTitle.setFont(fuente);
		jPanelIntro.add(lblTitle);

		fuenteGeneral = new Font(Constants.FONT_FORMULARIOS, Font.PLAIN, 14);

		lblIntro = new JLabel(Constants.DESCRIPTION);
		lblIntro.setBackground(null);
		lblIntro.setBorder(null);
		lblIntro.setForeground(Color.black);
		lblIntro.setFont(fuenteGeneral);
		jPanelIntro.add(lblIntro);

		lblName = new JLabel("Nombre:", SwingConstants.CENTER);
		lblName.setFont(fuenteGeneral);

		lblTime = new JLabel("Tiempo (min):" , SwingConstants.CENTER);
		lblTime.setFont(fuenteGeneral);


		JPanel jPanelPriority = createPanelPriority();

		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		configureButtons(actionListener);

		JPanel panelDat = new JPanel(new GridLayout(2, 1));
		JPanel panelIngress = new JPanel(new GridLayout(1,6));
		panelIngress.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		panelIngress.setBorder(new MatteBorder(10,10,10,10,Color.decode(Constants.COLOR_FORMULARIO)));

		Border bordePanelDat = new TitledBorder(new EtchedBorder(), "Datos del proceso");
		panelDat.setBorder(bordePanelDat);

		JPanel panelComunication = new JPanel(new GridLayout(1,2));
		panelComunication.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		createJlist();
		panelDat.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		panelIngress.add(lblName);
		panelIngress.add(tfNameProcess);
		panelIngress.add(lblTime);
		panelIngress.add(tfTime);

		JPanel jPanelButon = new JPanel(new FlowLayout(2,1,1));
		jPanelButon.setBackground(Color.decode(Constants.COLOR_FORMULARIO));

		jButtonNewComunication = new JButton(Constants.BUTTON_ADD);
		jButtonNewComunication.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jButtonNewComunication.setForeground(Color.white);
		jButtonNewComunication.setActionCommand(Constants.BUTTON_ADD);
		jButtonNewComunication.addActionListener(actionListener);
		jPanelButon.add(jButtonNewComunication);

		jButtonDeleteComunication = new JButton(Constants.BUTTON_REST);
		jButtonDeleteComunication.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jButtonDeleteComunication.setForeground(Color.white);
		jButtonDeleteComunication.setActionCommand(Constants.BUTTON_REST);
		jButtonDeleteComunication.addActionListener(actionListener);
		jPanelButon.add(jButtonDeleteComunication);


		panelComunication.add(jPanelPriority);
		panelComunication.add(jPanelButon);
		panelComunication.add(jPanelListComunications);

		panelDat.add(panelIngress);
		panelDat.add(panelComunication);

		panelButtons.add(jbuttonAdd);
		panelButtons.add(jbuttonUpdate);
		panelButtons.add(jbuttonDelete);

		panelEast.add(jPanelIntro);
		panelEast.add(panelDat);
		panelEast.add(jPanelOptions);
		panelEast.add(panelButtons);

		add(panelEast);
		add(panelTable);
		//add(lblTitleReports);
	}

	private JPanel createPanelPriority() {
		JPanel jPanelPriority = new JPanel(new GridBagLayout());
		jPanelPriority.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		lbPriority = new JLabel(Constants.PRIORITY, SwingConstants.CENTER);
		lbPriority.setFont(fuenteGeneral);

		jcomPriority = new JComboBox<Integer>();
		jcomPriority.setForeground(Color.white);
		jcomPriority.setBorder(BorderFactory.createLineBorder(Color.decode(Constants.COLOR_BUTTONS), 2));
		jcomPriority.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		fillCombobox();
		// Fields
		jPanelPriority.add(lbPriority, constraints);
		constraints.gridx = 4;
		constraints.gridy = 1;
		jPanelPriority.add(jcomPriority, constraints);
		tfNameProcess = new JTextField();
		tfNameProcess.setBorder(BorderFactory.createLoweredBevelBorder());
		tfTime = new JTextField();
		tfTime.setBorder(BorderFactory.createLoweredBevelBorder());
		return jPanelPriority;
	}

	private void configureButtons(ActionListener actionListener) {
		fuenteGeneral = new Font(Constants.FONT_FORMULARIOS, Font.ROMAN_BASELINE, 15);
		jbuttonAdd = new JButton(Constants.ADD_PROCESS);
		jbuttonAdd.setFont(fuenteGeneral);
		jbuttonAdd.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jbuttonAdd.setForeground(Color.white);
		jbuttonAdd.setActionCommand(Constants.ADD_PROCESS);
		jbuttonAdd.addActionListener(actionListener);

		jbuttonUpdate = new JButton(Constants.UPDATE_PROCESS);
		jbuttonUpdate.setFont(fuenteGeneral);
		jbuttonUpdate.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jbuttonUpdate.setForeground(Color.white);
		jbuttonUpdate.setActionCommand(Constants.INIT_SIMULATION);
		jbuttonUpdate.addActionListener(actionListener);

		jbuttonDelete = new JButton(Constants.DELETE_PROCESS);
		jbuttonDelete.setFont(fuenteGeneral);
		jbuttonDelete.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jbuttonDelete.setForeground(Color.white);
		jbuttonDelete.setActionCommand(Constants.DELETE_PROCESS);
		jbuttonDelete.addActionListener(actionListener);
	}

	private void createJlist() {
		jPanelListComunications = new JPanel(new GridLayout(1,1));
		Border borderListComunications = new TitledBorder(new EtchedBorder(), Constants.COMUNICATIONS_TITLE);
		jPanelListComunications.setBorder(borderListComunications);
		listProcess = new JList<String>();
		modelo = new DefaultListModel<String>();
		listProcess.setModel(modelo);
		scrollLista = new JScrollPane();
		scrollLista.setBounds(20, 120, 220, 80);
		scrollLista.setViewportView(listProcess);
		listProcess.setLayoutOrientation(JList.VERTICAL);
		jPanelListComunications.add(scrollLista);
	}

	public void clean() {
		tfNameProcess.setText("");
		tfTime.setText("");
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

	public void activeButtons() {
		buttExecution.setEnabled(true);
		buttLocked.setEnabled(true);
		buttOrderComp.setEnabled(true);
		buttProcces.setEnabled(true);
	}

	private void fillCombobox(){
		for (int i = 1; i < 21; i++) {
			jcomPriority.addItem("" + i);
		}
	}

	public int getPriority() {
		int select = Integer.valueOf(""+jcomPriority.getSelectedItem());
		return select;
	}

	public void addElement(String[] fila) {
		panelTable.addElemn(fila);
	}
	public void deleteRows() {
		panelTable.deleteRows();
	}
	public boolean getLocked() {
		return jPanelOptions.getLocked();
	}
	public boolean isLay() {
		return jPanelOptions.isLay();
	}
	public boolean isDestroy() {
		return jPanelOptions.isDestroy();
	}
	public int getTimeDestroy() {
		return jPanelOptions.getTime();
	}
	public void setEditableTime(boolean b) {
		jPanelOptions.setEditableTime(b);
	}
	public void addItemList(String element) {
		modelo.addElement(element);
	}

	public int getSelection(){
		return listProcess.getSelectedIndex();
	}
	public MyQueue<String> getListInformacion() {
		MyQueue<String> queueAux = new MyQueue<>();
		for (int j = 0; j < modelo.size(); j++) {
			queueAux.put(" " + modelo.get(j));
		}
		return queueAux;
	}
	public void removeProcessComunication() {
		System.out.println(listProcess.getSelectedIndex());
		modelo.remove(listProcess.getSelectedIndex());
		repaint();
	}
	public boolean isInListInformacion(String item) {
		for (int j = 0; j < modelo.size(); j++) {
			if (modelo.get(j).equals(item)) {
				return true;
			}
		}
		return false;
	}
	public void deleteRow(int row) {
		panelTable.deleteRow(row);
	}
	
	public int geSelectRowTable() {
		return panelTable.rowsSelect();
	}
}