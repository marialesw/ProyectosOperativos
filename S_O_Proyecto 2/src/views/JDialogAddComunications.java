package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import models.Process;
import models.MyQueue;


public class JDialogAddComunications extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox jComboComunication;

	public JDialogAddComunications(MyQueue<Process> procesos, ActionListener actionListener){
		setPreferredSize(new Dimension(500,150));
		setLayout( new BorderLayout() );
		setTitle( "Producto" );
		initComponents(procesos, actionListener);
		pack( );
		centrar( );
	}

	private void initComponents(MyQueue<Process> procesos, ActionListener actionListener) {
		JPanel panelComunications = new JPanel(new GridLayout(2,1));
		panelComunications.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		JLabel jLabelTitle = new JLabel("Seleccione los procesos con los que desea comunicarse");
		panelComunications.add(jLabelTitle);
		JPanel panelConts = new JPanel(new GridLayout(1,2));
		jComboComunication = new JComboBox<>();
		fillCombobox(procesos);
		jComboComunication.setMaximumSize(new Dimension(50,50));
		jComboComunication.setAlignmentX(SwingConstants.CENTER);
		
		JButton buttonAdd = new JButton("Agregar");
		buttonAdd.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		buttonAdd.setBorder(null);
		buttonAdd.setActionCommand(Constants.ADD_NEW_COMUNICATION);
		buttonAdd.addActionListener(actionListener);
		panelConts.add(jComboComunication);
		panelConts.add(buttonAdd);
		panelComunications.add(panelConts);
		this.add(panelComunications, BorderLayout.CENTER);
	}

	private void fillCombobox(MyQueue<Process> procesos) {
		while (!procesos.isEmpty()) {
			jComboComunication.addItem(procesos.get().getNameProcess());
		}
	}
	
	public String getComunication() {
		return jComboComunication.getSelectedItem().toString();
	}
	
	private void centrar( ){
		Dimension screen = Toolkit.getDefaultToolkit( ).getScreenSize( );
		int xEsquina = ( screen.width - getWidth( ) ) / 2;
		int yEsquina = ( screen.height - getHeight( ) ) / 2;
		setLocation( xEsquina, yEsquina );
	}
}
