package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import models.MyProcess;

public class ProcessesPnlList extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final Font FONT_INFO = new Font("Georgia", 1, 40);
	private static final Font FONT = new Font("Georgia", Font.PLAIN, 18);
	private MouseListener mouseListener;
	
	public ProcessesPnlList(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
		this.setVisible(true);
	}
	public void updateProcesses(Queue<MyProcess> processes) {
		removeAll();
		if(processes.isEmpty()) {
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 25, 20, 25);
			JLabel lblInfoIn = new JLabel(" Aún no hay procesos creados  ", SwingConstants.CENTER);
			lblInfoIn.setFont(new Font("Georgia", Font.PLAIN, 20));
			add(lblInfoIn);
		}else {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			showProcesses(processes);
		}
		revalidate();
		repaint();
	}

	private void showProcesses(Queue<MyProcess> processes) {
		Queue<MyProcess> temp = new LinkedList<MyProcess>();
		 while (!processes.isEmpty()) {
		    	MyProcess p = processes.remove();
				JPanel processPanel = createProcessPnl(p);
//				processPnls.add(processPanel);
				add(processPanel);
		        temp.add(p);
		    }
		    while (!temp.isEmpty()) {
		    	MyProcess p = temp.remove();
		        processes.add(p);
		    }
	}
	
	private JPanel createProcessPnl(MyProcess process){
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(gbl);
		cardPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.decode("#879720"))));
//		cardPanel.setMaximumSize(new Dimension(1040, 120));
		cardPanel.setMinimumSize(new Dimension(1040, 120));
		
		gbc.insets = new Insets(0, 50, 0, 20);
		
		JLabel lblName = new JLabel("Nombre");
		lblName.setFont(FONT);
		gbc.gridx = 0;
		gbc.gridy = 0;
		cardPanel.add(lblName, gbc);
		
		JLabel name = new JLabel(process.getName(),SwingConstants.CENTER);
		name.setFont(FONT_INFO);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		cardPanel.add(name, gbc);
		
		JLabel lblTime= new JLabel("Tiempo");
		lblTime.setFont(FONT);
		gbc.gridx = 1;
		gbc.gridy = 0;
		cardPanel.add(lblTime, gbc);
		
		
		JLabel time = new JLabel();
		DecimalFormat formato = new DecimalFormat("###,###,###");
		time.setText(formato.format(process.getTime()));
		time.setFont(FONT_INFO);
		gbc.gridx = 1;
		gbc.gridy = 1;
		cardPanel.add(time, gbc);
		
		JLabel lblSize = new JLabel("Tamaño");
		lblSize.setFont(FONT);
		gbc.gridx = 2;
		gbc.gridy = 0;
		cardPanel.add(lblSize, gbc);
		
		JLabel size = new JLabel(""+process.getSize());
		String valorFormateado = formato.format(process.getSize());
		size.setText(valorFormateado);
		
		size.setFont(FONT_INFO);
		gbc.gridy = 1;
		cardPanel.add(size, gbc);
		
		JButton editPartitionBtn = new JButton("Editar");
		editPartitionBtn.setName("editPro" + process.getName());
		editPartitionBtn.addMouseListener(mouseListener);
		editPartitionBtn.setFont(FONT);
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 120, 0, 20);
		gbc.gridheight = 2;
		cardPanel.add(editPartitionBtn, gbc);
		
		JButton deletePartitionBtn = new JButton("Eliminar");
		deletePartitionBtn.setName("deletePro" + process.getName());
		deletePartitionBtn.addMouseListener(mouseListener);
		deletePartitionBtn.setFont(FONT);
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridheight = 2;
		cardPanel.add(deletePartitionBtn, gbc);
		return cardPanel;
	}
	
	public void clean(){
		removeAll();
		setLayout(new GridLayout(1, 1));
		add(new JLabel("     Ingrese los procesos     ", SwingConstants.CENTER));
		revalidate();
		repaint();
	}
}
