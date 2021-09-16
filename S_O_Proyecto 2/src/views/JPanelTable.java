package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	
	public JPanelTable(ActionListener actionListener) {
		initComponentes(actionListener);
	}
	
	public void initComponentes(ActionListener actionListener) {
		
		Border bordePanelDat = new TitledBorder(new EtchedBorder(), "Tabla de procesos");
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.decode("#3F6260"));
		this.setBorder(bordePanelDat);

		dTM = new DefaultTableModel();
		titulos();
		Font Letra = new Font("Franklin Ghotic Demi", Font.ITALIC, 14);
		jTable = new JTable(dTM);
		jTable.setRowHeight(30);

		//jTable.setIntercellSpacing(new Dimension(100,50));
		//error que no muestra los elementos de la tabla
		jTable.setModel(dTM);
		jTable.getTableHeader().setReorderingAllowed(false);
		jTable.getTableHeader().setBackground(Color.lightGray);
		jTable.getTableHeader().setForeground(Color.black);
		
		jTable.getTableHeader().setFont(Letra);
		jTable.setBackground(Color.white);
		jTable.setForeground(Color.black);
		jTable.setBorder(null);
		
		jSP = new JScrollPane(jTable);
		jSP.setForeground(Color.BLACK);
		jSP.setBorder(null);
		jSP.setAlignmentX(Component.CENTER_ALIGNMENT);

		add(jSP, BorderLayout.CENTER);
		
		JPanel jPanelReports = new JPanel();
		JButton button = new JButton(Constants.CREATE_REPORT);
		fuenteGeneral = new Font(Constants.FONT_FORMULARIOS, Font.PLAIN, 15);
		button.setFont(fuenteGeneral);
		button.setForeground(Color.white);
		button.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		button.setActionCommand(Constants.CREATE_REPORT);
		button.addActionListener(actionListener);
		
		JButton buttonRemove = new JButton(Constants.REMOVE_ALL);
		buttonRemove.setFont(fuenteGeneral);
		buttonRemove.setForeground(Color.white);
		buttonRemove.setBackground(Color.decode(Constants.COLOR_BUTTONS));
		buttonRemove.setActionCommand(Constants.REMOVE_ALL);
		buttonRemove.addActionListener(actionListener);
		
		jPanelReports.add(button);
		jPanelReports.add(buttonRemove);
		add(jPanelReports, BorderLayout.SOUTH);

		}
	
	public void titulos() {
		String[] Panel = { "Nombre", "Tiempo", "Prioridad", "Comunicaciones" };
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
	public void deleteRows() {
		dTM.setRowCount(0);
	}
}
