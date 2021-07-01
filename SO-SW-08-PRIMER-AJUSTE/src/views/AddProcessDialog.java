package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Actions;
import models.MyProcess;

public class AddProcessDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("Georgia", Font.PLAIN, 20);
	private static final String NAME = "Nombre :";
	private static final String SIZE = "Tamaño :";
	private static final String TIME = "Tiempo :";
	private static final String ERROR_EMPTY = "Por favor ingrese todos los campos";
	private static final String ERROR_NEGATIVE_TIME = "Ingrese todos los numeros positivos";
	private static final String BUTTON_COLOR = "#c1d82f";
	
	private JTextField txtName;
	private JTextField txtTime;
	private JTextField txtSize;
	
	public AddProcessDialog(ActionListener actionListener) {
		setSize(500, 280);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);
		setUndecorated(true);
		init(actionListener);
		setVisible(true);
	}

	private void init(ActionListener actionListener) {
		createCloseOperation(actionListener);
		createContentPnl(actionListener);
	}
	
	private void createContentPnl(ActionListener actionListener) {
		JPanel contentPnl = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		contentPnl.setLayout(gbl);
		contentPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel lblName = new JLabel(NAME, JLabel.LEFT);
		lblName.setFont(FONT);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(13,15,0,15); 
		contentPnl.add(lblName, gbc);

		txtName = new JTextField(15);
		txtName.setFont(FONT);
		gbc.gridx = 1;
		gbc.gridy = 0;
		contentPnl.add(txtName, gbc);

		JLabel lblTime = new JLabel(TIME, JLabel.LEFT);
		lblTime.setFont(FONT);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		contentPnl.add(lblTime, gbc);

		txtTime = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		txtTime.setFont(FONT);
		contentPnl.add(txtTime, gbc);
		
		JLabel lblSize = new JLabel(SIZE, JLabel.LEFT);
		lblSize.setFont(FONT);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		contentPnl.add(lblSize, gbc);
		
		txtSize = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 2;
		txtSize.setFont(FONT);
		contentPnl.add(txtSize, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		contentPnl.add(initButtonsPnl(actionListener), gbc);
		
		add(contentPnl, BorderLayout.CENTER);
	}

	private JPanel initButtonsPnl(ActionListener actionListener) {
		JPanel buttonsPnl = new JPanel();
		JButton btnAdd = new JButton("Agregar");
		btnAdd.setBackground(Color.decode(BUTTON_COLOR));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setFont(FONT);
		btnAdd.setActionCommand(Actions.ACCEPT_ADD_PROCESS.name());
		btnAdd.addActionListener(actionListener);
		buttonsPnl.add(btnAdd);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBackground(Color.decode(BUTTON_COLOR));
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setFont(FONT);
		btnCancel.setActionCommand(Actions.CANCEL_ADD_PROCESS.name());
		btnCancel.addActionListener(actionListener);
		buttonsPnl.add(btnCancel);
		return buttonsPnl;
	}
	
	private void createCloseOperation(ActionListener actionListener) {
		JPanel closePnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lblTitle = new JLabel("Agregar proceso                                           ");
		lblTitle.setFont(FONT);
		lblTitle.setForeground(Color.WHITE);
		closePnl.add(lblTitle);
		closePnl.setBackground(Color.BLACK);
		JButton btnClose = new JButton("Cerrar");
		btnClose.setFont(FONT);
		btnClose.setBackground(Color.decode("#b81414"));
		btnClose.setForeground(Color.WHITE);
		btnClose.setActionCommand(Actions.CLOSE_ADD_PROCESS_DIALOG.name());
		btnClose.addActionListener(actionListener);
		
		closePnl.add(btnClose);
		add(closePnl, BorderLayout.PAGE_START);
	}

	public MyProcess getProcess() throws Exception {
		MyProcess process;
		if(txtName.getText().isEmpty() || txtTime.getText().isEmpty() || txtSize.getText().isEmpty()) {
			throw new Exception(ERROR_EMPTY);
		}else if(Integer.parseInt(txtTime.getText())<0){
			throw new Exception(ERROR_NEGATIVE_TIME);
		}else {
			process = new MyProcess(txtName.getText(), Integer.parseInt(txtTime.getText()), Integer.parseInt(txtSize.getText()));
		}
		return process;
	}

	public void cleanFields() {
		txtName.setText("");
		txtSize.setText("");
		txtTime.setText("");
	}
}
