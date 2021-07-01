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

public class EditProcess extends JDialog{

	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("Georgia", Font.PLAIN, 20);
	private static final String NAME = "Nombre :";
	private static final String SIZE = "Tamaño :";
	private static final String TIME = "Tiempo :";
	private static final String ERROR_EMPTY = "Ingrese todos los campos";
	private static final String ERROR_NEGATIVE_TIME = "Ingrese todos los numeros positivos";
	private static final String BUTTON_COLOR = "#c1d82f";
	
	private JLabel txtName;
	private JTextField txtTime;
	private JTextField txtSize;

	public EditProcess(MyProcess process, ActionListener actionListener) {
		setSize(500, 280);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);
		setUndecorated(true);
		init(process, actionListener);
		setVisible(true);
	}
	
	private void init(MyProcess process, ActionListener actionListener) {
		createCloseOperation(actionListener);
		createContentPnl(process, actionListener);
	}
	
	private void createContentPnl(MyProcess process, ActionListener actionListener) {
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

		txtName = new JLabel();
		txtName.setText(process.getName());
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
		txtTime.setText("" + process.getTime());
		txtTime.setFont(FONT);
		gbc.gridx = 1;
		gbc.gridy = 1;
		contentPnl.add(txtTime, gbc);
		
		JLabel lblSize = new JLabel(SIZE, JLabel.LEFT);
		lblSize.setFont(FONT);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		contentPnl.add(lblSize, gbc);
		
		txtSize = new JTextField(15);
		txtSize.setText("" + process.getSize());
		txtSize.setFont(FONT);
		gbc.gridx = 1;
		gbc.gridy = 2;
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
		JButton btnAdd = new JButton("Actualizar");
		btnAdd.setBackground(Color.decode(BUTTON_COLOR));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setFont(FONT);
		btnAdd.setActionCommand(Actions.ACCEPT_EDIT_PROCESS.name());
		btnAdd.addActionListener(actionListener);
		buttonsPnl.add(btnAdd);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBackground(Color.decode(BUTTON_COLOR));
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setFont(FONT);
		btnCancel.setActionCommand(Actions.CLOSE_EDIT_PROCESS_DIALOG.name());
		btnCancel.addActionListener(actionListener);
		buttonsPnl.add(btnCancel);
		return buttonsPnl;
	}
	
	private void createCloseOperation(ActionListener actionListener) {
		JPanel closePnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lblTitle = new JLabel("Editar proceso                         ");
		lblTitle.setFont(FONT);
		lblTitle.setForeground(Color.WHITE);
		closePnl.add(lblTitle);
		closePnl.setBackground(Color.BLACK);
		JButton btnClose = new JButton("Cerrar");
		btnClose.setFont(FONT);
		btnClose.setBackground(Color.decode("#b81414"));
		btnClose.setForeground(Color.WHITE);
		btnClose.setActionCommand(Actions.CLOSE_EDIT_PROCESS_DIALOG.name());
		btnClose.addActionListener(actionListener);
		
		closePnl.add(btnClose);
		add(closePnl, BorderLayout.PAGE_START);
	}

	public MyProcess getProcessToUpdate() throws Exception {
		MyProcess process;
		if(txtTime.getText().isEmpty() || txtSize.getText().isEmpty()) {
			throw new Exception(ERROR_EMPTY);
		}else if(Integer.parseInt(txtTime.getText())<0){
			throw new Exception(ERROR_NEGATIVE_TIME);
		}else {
			process = new MyProcess(txtName.getText(), Integer.parseInt(txtTime.getText()), Integer.parseInt(txtSize.getText()));
		}
		return process;
	}

}
