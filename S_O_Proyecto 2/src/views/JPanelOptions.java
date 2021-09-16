package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import utilities.Utillidades;

public class JPanelOptions extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ButtonGroup ngLocked;
	private JRadioButton radLockedYes;
	private JRadioButton radLockedNot;
	private JLabel jLabelYes;
	private JLabel jLabelNot;
	private JLabel jLabelLocked;
	private JLabel jLabelLay;
	private JLabel jLabelDestroy;
	private JRadioButton radLayYes;
	private JRadioButton radLayNot;
	private ButtonGroup ngLay;
	private JRadioButton radDestroyYes;
	private JRadioButton radDestroyNot;
	private ButtonGroup ngDestroy;
	private Font fuenteGeneral;
	private JTextField jtxTime;

	public JPanelOptions(ActionListener actionListener) {
		this.setLayout(new GridLayout(1,2));
		this.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		initComponents(actionListener);
	}

	private void initComponents(ActionListener actionListener) {
		fuenteGeneral = new Font(Constants.FONT_FORMULARIOS, Font.PLAIN, 14);

		JPanel panelTime = new JPanel(new GridLayout(3, 1));
		panelTime.setBackground(Color.decode(Constants.COLOR_FORMULARIO));

		JLabel jlabelTime = new JLabel("Tiempo");
		jlabelTime.setFont(fuenteGeneral);

		jlabelTime.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		jtxTime = new JTextField();
		jtxTime.setEditable(false);
		jtxTime.setBorder(BorderFactory.createLoweredBevelBorder());

		JPanel jpanel = new JPanel(new GridLayout(1,2));
		jpanel.setBackground(Color.decode(Constants.COLOR_FORMULARIO));

		jpanel.add(jlabelTime);
		jpanel.add(jtxTime);
		Border borderPanel = new TitledBorder(new EtchedBorder(), "Tiempo de destrucción");
		jpanel.setBorder(borderPanel);
		
		JPanel vacio = new JPanel();
		vacio.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		JPanel vacio1 = new JPanel();
		vacio1.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		JPanel vacio2 = new JPanel();
		vacio2.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		panelTime.add(vacio1);
		panelTime.add(vacio2);
		panelTime.add(jpanel);
		
		
		
		JPanel panelRadioButtons = new JPanel(new GridLayout(4, 2));
		panelRadioButtons.setBackground(Color.decode(Constants.COLOR_FORMULARIO));

		radLockedYes = new JRadioButton();
		radLockedYes.setBackground(null);
		radLockedNot = new JRadioButton();
		radLockedNot.setBackground(null);
		
		radLayYes = new JRadioButton();
		radLayYes.setBackground(null);
		radLayNot = new JRadioButton();
		radLayNot.setBackground(null);

		radDestroyYes = new JRadioButton();
		radDestroyYes.setBackground(null);
		radDestroyYes.setActionCommand(Constants.DESTROY);
		radDestroyYes.addActionListener(actionListener);
		radDestroyNot = new JRadioButton();
		radDestroyNot.setBackground(null);
		radDestroyNot.setActionCommand(Constants.DES);
		radDestroyNot.addActionListener(actionListener);
		
		JPanel jpanelTitles = new JPanel(new GridLayout(1,2));
		jpanelTitles.setBackground(Color.decode(Constants.COLOR_FORMULARIO));

		jLabelYes = new JLabel("SI");
		jLabelYes.setFont(fuenteGeneral);

		jLabelNot = new JLabel("NO");
		jLabelNot.setFont(fuenteGeneral);

		jpanelTitles.add(jLabelYes);
		jpanelTitles.add(jLabelNot);

		jLabelLocked = new JLabel("BLOQUEO");
		jLabelLocked.setFont(fuenteGeneral);

		jLabelLay = new JLabel("SUSPENSION");
		jLabelLay.setFont(fuenteGeneral);

		jLabelDestroy = new JLabel("DESTRUIR");
		jLabelDestroy.setFont(fuenteGeneral);


		ngLocked= new ButtonGroup();
		ngLocked.add(radLockedYes);
		ngLocked.add(radLockedNot);
		
		ngLay = new ButtonGroup();
		ngLay.add(radLayYes);
		ngLay.add(radLayNot);
		
		ngDestroy = new ButtonGroup();
		ngDestroy.add(radDestroyYes);
		ngDestroy.add(radDestroyNot);
		
		JPanel jpanelLocked = new JPanel(new GridLayout(1,2));
		jpanelLocked.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		jpanelLocked.add(radLockedYes);
		jpanelLocked.add(radLockedNot);
		
		JPanel jpanellay = new JPanel(new GridLayout(1,2));
		jpanellay.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		jpanellay.add(radLayYes);
		jpanellay.add(radLayNot);
		
		JPanel jpanelDestroy = new JPanel(new GridLayout(1,2));
		jpanelDestroy.setBackground(Color.decode(Constants.COLOR_FORMULARIO));
		jpanelDestroy.add(radDestroyYes);
		jpanelDestroy.add(radDestroyNot);
		
		panelRadioButtons.add(vacio);
		panelRadioButtons.add(jpanelTitles);
		panelRadioButtons.add(jLabelLocked);
		panelRadioButtons.add(jpanelLocked);
		panelRadioButtons.add(jLabelLay);
		panelRadioButtons.add(jpanellay);
		panelRadioButtons.add(jLabelDestroy);
		panelRadioButtons.add(jpanelDestroy);

		Border borderRadioB = new TitledBorder(new EtchedBorder(), "Selección");
		panelRadioButtons.setBorder(borderRadioB);

		this.add(panelTime);		
		this.add(panelRadioButtons);		

	}
	
	public boolean getLocked(){
		if(!radLockedYes.isSelected() && !radLockedNot.isSelected()) {
			Utillidades.showMessageWarning("Debe seleccionar si el proceso es bloqueado o no", "Advertencia");
		}else {
			return radLockedYes.isSelected() ? true : false;
		}
		return false;

	}
	
	public boolean isLay(){
		if(!radLayYes.isSelected() && !radLayNot.isSelected()) {
			Utillidades.showMessageWarning("Debe seleccionar si el proceso es bloqueado o no", "Advertencia");
		}else {
			return radLayYes.isSelected() ? true : false;
		}
		return false;

	}
	
	public boolean isDestroy(){
		if(!radDestroyYes.isSelected() && !radDestroyNot.isSelected()) {
			Utillidades.showMessageWarning("Debe seleccionar si el proceso es bloqueado o no", "Advertencia");
		}else {
			return radDestroyYes.isSelected() ? true : false;
		}
		return false;

	}
	
	public void setEditableTime(boolean b) {
		jtxTime.setEditable(b);
		if (!b) {
			jtxTime.setText("");
		}
	}
	
	public int getTime() {
		if (jtxTime.getText() != null && !jtxTime.getText().isEmpty()) {
			if (Utillidades.isNumeric(jtxTime.getText())) {
				return Integer.parseInt(jtxTime.getText());
			}else {
				Utillidades.showMessageWarning("la cantidad de tiempo de destrucción es inválida", "Advertencia");
			}
		}
		return -1;
	}
}