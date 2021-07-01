package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.Actions;
import models.MyProcess;

public class Screen extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static final String WINDOW_TITLE = "Simulador";
	private static final String BUTTON_COLOR = "#c1d82f";
//	private static final String BACKGROUND_COLOR = "#ffffff";
	private static final Font FONT = new Font("Georgia", Font.PLAIN, 20);
	
	private ProcessesPnlList processesPnl;
	private ActionListener actionListener;
	private MouseListener mouseListener;
	private AddProcessDialog addProcessDialog;
	private EditProcess editProcess;
	private ErrorDialog errorDialog;
	private JButton btnClean;
	private JButton simulateBtn;
	private JButton reportsBtn;
	private JTextField txtMemoryIn;
	
	public Screen(ActionListener actionListener, MouseListener mouseListener) {
		setTitle(WINDOW_TITLE);
		this.setUndecorated(true);
		setSize(1360, 760);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		this.actionListener = actionListener;
		this.mouseListener = mouseListener;
		init();
		setVisible(true);
	}

	private void init() {
		/*
		 * Opción de cerrar 
		 */
		createCloseOperation();
		createContainer();
		createBottomBtns();
	}
	
	private void createContainer() {
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 20, 20), new LineBorder(Color.BLACK)));
		JPanel headerRightPnl = new JPanel();
		headerRightPnl.setLayout(new GridLayout(2, 1));
		JPanel memorySizePnl = new JPanel();
//		memorySizePnl.setBorder(new LineBorder(Color.BLACK));
		JLabel lblMemoryIn = new JLabel("Tamaño de la memoria");
		lblMemoryIn.setFont(FONT);
		memorySizePnl.add(lblMemoryIn);
		txtMemoryIn = new JTextField(8);
		memorySizePnl.add(txtMemoryIn);
		headerRightPnl.add(memorySizePnl);
		
		JPanel titleQueue = new JPanel();
		titleQueue.setBorder(new LineBorder(Color.BLACK));
		titleQueue.setBackground(Color.GRAY);
		JLabel lblProcesses = new JLabel("Cola de procesos          ");
		lblProcesses.setForeground(Color.WHITE);
		lblProcesses.setFont(FONT);
		titleQueue.add(lblProcesses);
		
		titleQueue.add(createButton("Agregar", Actions.SHOW_ADD_PROCESS.name()));
		
		headerRightPnl.add(titleQueue);
		container.add(headerRightPnl, BorderLayout.NORTH);
		
		processesPnl = new ProcessesPnlList(mouseListener);
		JScrollPane sp = new JScrollPane(processesPnl);
		container.add(sp, BorderLayout.CENTER);
		
		add(container, BorderLayout.CENTER);
		
	}
	
	private void createBottomBtns() {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel btnsPnl = new JPanel();
//		btnsPnl.setBackground(Color.decode("#52565e"));
		btnsPnl.setLayout(gbl);
		
		simulateBtn = createButton("   Simular   ", Actions.SIMULATE.name());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 20, 40, 20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		simulateBtn.setEnabled(false);
		btnsPnl.add(simulateBtn, gbc);
		
		btnClean = createButton("   Limpiar   ", Actions.CLEAN_ALL.name());
		gbc.gridy = 1;
		btnClean.setEnabled(false);
		btnsPnl.add(btnClean, gbc);
		
		reportsBtn = createButton("   Abrir reportes   ", Actions.OPEN_REPORT.name());
		gbc.gridy = 2;
		reportsBtn.setEnabled(false);
		btnsPnl.add(reportsBtn, gbc);
		
		add(btnsPnl, BorderLayout.LINE_START);
	}
	
	private JButton createButton(String title, String actionCommand){
		JButton btn = new JButton(title);
		btn.setBackground(Color.decode(BUTTON_COLOR));
		btn.setFont(FONT);
		btn.setActionCommand(actionCommand);
		btn.addActionListener(actionListener);
		return btn;
	}
	
	private void createCloseOperation() {
		JPanel closePnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		closePnl.setBackground(Color.BLACK);
		JLabel lblTittle = new JLabel("Simulación de compactación del almacenamiento en un sistema operativo con particiones variables                                                                     ");
		lblTittle.setFont(FONT);
		lblTittle.setForeground(Color.WHITE);
		closePnl.add(lblTittle);
		
		JButton btnClose = new JButton("Cerrar");
		btnClose.setBackground(Color.decode("#b81414"));
		btnClose.setFont(FONT);
		btnClose.setForeground(Color.WHITE);
		btnClose.setActionCommand(Actions.EXIT_ON_CLOSE.name());
		btnClose.addActionListener(actionListener);
		
		closePnl.add(btnClose);
		add(closePnl, BorderLayout.PAGE_START);
	}

	public void updateProcesses(Queue<MyProcess> processes) {
		processesPnl.updateProcesses(processes);
	}

	public void showErrorMessage(String errorMessage) {
		errorDialog = new ErrorDialog(errorMessage, actionListener);
	}

	public void enableBtns(boolean enable) {
		btnClean.setEnabled(enable);
		simulateBtn.setEnabled(enable);
		reportsBtn.setEnabled(enable);
	}

	public MyProcess getProcess() throws Exception{
		return addProcessDialog.getProcess();
	}

	public void closeAddProcessDialog(){
		addProcessDialog.dispose();
	}

	public void showWindowToUpdateProcess(MyProcess searchProcess) {
		editProcess = new EditProcess(searchProcess, actionListener);
	}
	
	public MyProcess getProcessToUpdate() throws Exception{
		return editProcess.getProcessToUpdate();
	}

	public void closeEditProcessDialog(){
		editProcess.dispose();
	}

	public void closeErrorMessage() {
		errorDialog.dispose();
	}

	public void showAddProcessDialog() {
		addProcessDialog = new AddProcessDialog(actionListener);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void cleanFieldsAddProcessDialog() {
		addProcessDialog.cleanFields();
	}

	public int getMemorySize() throws Exception {
		if(txtMemoryIn.getText().isEmpty()){
			throw new Exception("Por favor ingrese el tamaño de la memoria");
		}
		return Integer.parseInt(txtMemoryIn.getText());
	}
}
