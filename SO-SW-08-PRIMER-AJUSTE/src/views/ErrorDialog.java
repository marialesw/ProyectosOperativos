package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Actions;

public class ErrorDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("Georgia", Font.PLAIN, 20);
	
	public ErrorDialog(String errorMessage, ActionListener actionListener) {
		setSize(500, 200);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);
		setUndecorated(true);
		init(errorMessage, actionListener);
		setVisible(true);
	}

	private void init(String errorMessage, ActionListener actionListener) {
		JPanel titlePnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePnl.setBackground(Color.decode("#b81414"));
		JLabel lblTitle = new JLabel("Mensaje de error");
		lblTitle.setFont(FONT);
		lblTitle.setForeground(Color.WHITE);
		titlePnl.add(lblTitle);
		add(titlePnl, BorderLayout.PAGE_START);
		
		JPanel contentPnl = new JPanel();
		contentPnl.setLayout(new BorderLayout());
		contentPnl.setBorder(BorderFactory.createLineBorder(Color.decode("#b81414")));
		JPanel pnlMessage = new JPanel();
		pnlMessage.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0));
		JTextArea message = new JTextArea(errorMessage);
		message.setBackground(Color.decode("#eeeeee"));
		message.setEditable(false);
		message.setFont(FONT);
		pnlMessage.add(message);
		contentPnl.add(pnlMessage, BorderLayout.CENTER);
		
		JPanel pnlBtn = new JPanel();
		JButton btnClose = new JButton("OK");
		btnClose.setBackground(Color.decode("#b81414"));
		btnClose.setForeground(Color.WHITE);
		btnClose.setFont(FONT);
		btnClose.setActionCommand(Actions.CLOSE_ERROR_DIALOG.name());
		btnClose.addActionListener(actionListener);
		pnlBtn.add(btnClose);
		contentPnl.add(pnlBtn, BorderLayout.SOUTH);
		
		add(contentPnl);
	}
	
}
