package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

import com.itextpdf.text.DocumentException;

import models.Manager;
import models.MyProcess;
import views.Screen;

public class Control extends MouseAdapter implements ActionListener{
	private Screen screen;
	private Manager manager;
	
	public Control() {
//		screen =  new Screen(this, this);
		try {
			manager = new Manager();
		} catch (FileNotFoundException e) {
//			screen.showErrorMessage(e.getMessage());
		} catch (DocumentException e) {
//			screen.showErrorMessage(e.getMessage());
		}
		testData2(); // datos ingresados
//		screen.updateProcesses(manager.getProcesses());
//		validateEnableBtns();
	}

	private void testData() {
		try {
//			manager.addProcess(new MyProcess("p18", 6, 18));
//			manager.addProcess(new MyProcess("p40", 3, 40));
//			manager.addProcess(new MyProcess("p4", 8, 4));
//			manager.addProcess(new MyProcess("p15", 7, 15));
//			manager.addProcess(new MyProcess("p50", 4, 50));
//			manager.simulate(64);
			
			manager.addProcess(new MyProcess("p11", 5, 11));
			manager.addProcess(new MyProcess("p15", 7, 15));
			manager.addProcess(new MyProcess("p18", 8, 18));
			manager.addProcess(new MyProcess("p6", 3, 6));
			manager.addProcess(new MyProcess("p9", 4, 9));
			manager.addProcess(new MyProcess("p20", 2, 20));
			manager.addProcess(new MyProcess("p13", 3, 13));
			manager.simulate(45);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testData2() {
		try {
//			manager.addProcess(new MyProcess("p18", 6, 18));
//			manager.addProcess(new MyProcess("p40", 3, 40));
//			manager.addProcess(new MyProcess("p4", 8, 4));
//			manager.addProcess(new MyProcess("p15", 7, 15));
//			manager.addProcess(new MyProcess("p50", 4, 50));
//			manager.simulate(64);
			
			manager.addProcess(new MyProcess("p25", 3, 25));
			manager.addProcess(new MyProcess("p15", 2, 15));
			manager.addProcess(new MyProcess("p10", 1, 10));
			manager.addProcess(new MyProcess("p20", 4, 20));
			manager.addProcess(new MyProcess("p30", 5, 30));
			manager.simulate(60);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void validateEnableBtns() {
		if(manager.getProcesses().size()>0){
			screen.enableBtns(true);
		}else{
			screen.enableBtns(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Actions.valueOf(e.getActionCommand())) {
		case EXIT_ON_CLOSE:
			screen.dispose();
			break;
		case ACCEPT_ADD_PROCESS:
			try {
				manager.addProcess(screen.getProcess());
				screen.updateProcesses(manager.getProcesses());
				screen.closeAddProcessDialog();
				validateEnableBtns();
			} catch (Exception e2) {
				screen.cleanFieldsAddProcessDialog();
				screen.showErrorMessage(e2.getMessage());
			}
			break;
		case ACCEPT_EDIT_PROCESS:
			try {
				manager.updateProcess(screen.getProcessToUpdate());
				screen.updateProcesses(manager.getProcesses());
				screen.closeEditProcessDialog();
			} catch (Exception e1) {
				screen.showErrorMessage(e1.getMessage());
			}
			break;
		case CANCEL_ADD_PROCESS:
			screen.closeAddProcessDialog();
			break;
		case CLEAN_ALL:
			manager.clean();
			screen.updateProcesses(manager.getProcesses());
			break;
		case CLOSE_EDIT_PROCESS_DIALOG:
			screen.closeEditProcessDialog();
			break;
		case CLOSE_ERROR_DIALOG:
			screen.closeErrorMessage();
			break;
		case CLOSE_PROCESS_DIALOG:
			screen.closeAddProcessDialog();
			break;
		case OPEN_REPORT:
			try {
				manager.openPdf();
			} catch (Exception e1) {
				screen.showErrorMessage(e1.getMessage());
			}
			break;
		case SHOW_ADD_PROCESS:
			screen.showAddProcessDialog();
			break;
		case SIMULATE:
			try {
				manager.saveDataIn(screen.getMemorySize());
				manager.simulate(screen.getMemorySize());   
				manager.writeSimulationDataAndCloseDocument();
			} catch (FileNotFoundException e3) {
				screen.showErrorMessage(e3.getMessage());
			} catch (DocumentException e3) {
				screen.showErrorMessage(e3.getMessage());
			} catch (Exception e1) {
				screen.showErrorMessage(e1.getMessage());
			}
			break;
		case CLOSE_ADD_PROCESS_DIALOG:
			screen.closeAddProcessDialog();
			break;
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent().getName().startsWith("editPro")){
			screen.showWindowToUpdateProcess(manager.searchProcess(new MyProcess(e.getComponent().getName().substring(7), 0, 0)));
		}else if(e.getComponent().getName().startsWith("deletePro")){
			manager.deleteProcess(manager.searchProcess(new MyProcess(e.getComponent().getName().substring(9), 0, 0)));
			screen.updateProcesses(manager.getProcesses());
			screen.showMessage("El proceso ha sido eliminado");
			validateEnableBtns();
		}
	}
}
