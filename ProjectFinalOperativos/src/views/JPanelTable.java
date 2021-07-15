package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class JPanelTable extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel dTM;
	private JTable jTable;
	private JScrollPane jSP;
	private Font fuenteGeneral;
	private JLabel jlabelIndications;
	private JLabel jlabelPart;
	private JButton jbuttonUpdate;
	private JButton jbuttonDelete;
	
	public JPanelTable(ActionListener actionListener) {
		initComponentes(actionListener);
	}
	
	public void initComponentes(ActionListener actionListener) {
		fuenteGeneral = new Font(Constants.FONT_FORMULARIOS, Font.ROMAN_BASELINE, 18);
		
		JPanel jPanelPartitions = new JPanel(new GridBagLayout());
		jPanelPartitions.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		
		jlabelPart = new JLabel(Constants.PARTITION);
		jlabelPart.setFont(fuenteGeneral);
		jlabelIndications = new JLabel("Por favor seleccione la partición que desea verificar");
		jlabelIndications.setFont(fuenteGeneral);
		
		jPanelPartitions.add(jlabelPart);
	
		Border bordePanelDat = new TitledBorder(new EtchedBorder(), "Tabla de procesos");
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.decode("#3F6260"));

		dTM = new DefaultTableModel();
		titulos();
		jTable = new JTable(dTM);
		jTable.setRowHeight(30);

		//jTable.setIntercellSpacing(new Dimension(100,50));
		//error que no muestra los elementos de la tabla
		jTable.setModel(dTM);
		jTable.getTableHeader().setReorderingAllowed(false);
		jTable.getTableHeader().setBackground(Color.lightGray);
		jTable.getTableHeader().setForeground(Color.black);
		
		jTable.getTableHeader().setFont(fuenteGeneral);
		jTable.setBackground(Color.white);
		jTable.setForeground(Color.black);
		jTable.setBorder(null);
		
		jSP = new JScrollPane(jTable);
		jSP.setForeground(Color.BLACK);
		jSP.setBorder(null);
		jSP.setAlignmentX(Component.CENTER_ALIGNMENT);
		jSP.setBorder(bordePanelDat);

		add(jSP, BorderLayout.CENTER);
		
		JPanel jPanelReports = new JPanel();
		JButton button = new JButton(Constants.CREATE_REPORT);
		button.setFont(fuenteGeneral);
		button.setForeground(Color.white);
		button.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		button.setActionCommand(Constants.CREATE_REPORT);
		button.addActionListener(actionListener);
		buttons(actionListener, jPanelReports, button);

		}

	private void buttons(ActionListener actionListener, JPanel jPanelReports, JButton button) {
	
		jbuttonUpdate = new JButton(Constants.UPDATE_PROCESS);
		jbuttonUpdate.setFont(fuenteGeneral);
		jbuttonUpdate.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jbuttonUpdate.setForeground(Color.white);
		jbuttonUpdate.setActionCommand(Constants.UPDATE_PROCESS);
		jbuttonUpdate.addActionListener(actionListener);
		
		jbuttonDelete = new JButton(Constants.DELETE_PROCESS);
		jbuttonDelete.setFont(fuenteGeneral);
		jbuttonDelete.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		jbuttonDelete.setForeground(Color.white);
		jbuttonDelete.setActionCommand(Constants.DELETE_PROCESS);
		jbuttonDelete.addActionListener(actionListener);
		
		jPanelReports.add(button);
		jPanelReports.add(jbuttonUpdate);
		jPanelReports.add(jbuttonDelete);
		add(jPanelReports, BorderLayout.SOUTH);
	}

	public void titulos() {
		String[] Panel = { "Proceso", "Tiempo", "Tamaño"};
		dTM.setColumnIdentifiers(Panel);
	}
	
	public void addElemn(String[] fila) {
		dTM.addRow(fila);
	}
	
	public void addElemnPosicion(String dato, int row, int column) {
		jTable.setValueAt(dato, row, column);
	}
	
	public String selectElemnt(int auxFila, int auxCol) {
		return (String) dTM.getValueAt(auxFila, auxCol);
	}
	
	public void deleteRow(int rowSelect) {
			dTM.removeRow(rowSelect); //Sale IndexBoundsException
	}
		
	public int totalQuantyRows() {
		return jTable.getSelectedRowCount();
	}
	
	public int rowsSelect() {
		return jTable.getSelectedRow();
	}
	
	public String[] getDatesTable(){
		return null;
		
	}
	public void deleteActual() {
		dTM.removeRow(jTable.getSelectedRow()); //Sale IndexBoundsException
	}
	public void deleteRows() {
		dTM.setRowCount(0);
	}
}
