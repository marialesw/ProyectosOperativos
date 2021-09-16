package controllers;

import models.MyQueue;
import models.Node;
import models.Process;
import models.SimpleList;
import utilities.Utillidades;
import views.Constants;
import views.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utilities.Utillidades;
import javax.swing.JProgressBar;


public class MainController extends Thread implements ActionListener {

	private static final int TIME_EXECUTION = 5;
	private SimpleList<Process> processList;
	private MyQueue<Process> readyQueue;
	private MyQueue<Process> executionQueue; 
	private MyQueue<Process> orderExecutionQueue; 
	private MyQueue<Process> lockedQueue;
	private MainWindow mainWindow;
	private Report reports; 
	private JProgressBar progreso;



	public MainController() {
		processList = new SimpleList<>();
		readyQueue = new MyQueue<>();
		executionQueue = new MyQueue<>();
		lockedQueue = new MyQueue<>();
		orderExecutionQueue = new MyQueue<>();
		mainWindow = new MainWindow(this);
		reports = new Report();

		//		new Comparator<Node<Process>>() {
		//
		//			@Override
		//			public int compare(Node<Process> o1, Node<Process> o2) {
		//				return o1.getInfo().getTimeProcess() - o2.getInfo().getTimeProcess();
		//			}
		//		}
		//createList();
		//printList();

		//startProgram();
		//printQueue(executionQueue);	
	}

	private void createList() {
		Process process1 = new Process("Proceso 1", 25, true); 
		Process process2 = new Process("Proceso 2", 15, false); 
		Process process3 = new Process("Proceso 3", 20, true); 
		Process process4 = new Process("Proceso 4", 10, true); 
		processList.addLast(process1);
		processList.addLast(process2);
		processList.addLast(process3);	
		processList.addLast(process4);		
	}

	/**
	 * este metodo llena la cola de listos con los procesos iniciales
	 */
	private void createReadyQueue() {
		Node<Process> aux = processList.getHead();
		while(aux!= null) {
			readyQueue.put(aux.getInfo());
			aux = aux.getNext();
		}	
	}

	private void printList() {
		Node<Process> aux = processList.getHead();
		while(aux!= null) {
			System.out.println(aux.getInfo().getNameProcess());
			aux = aux.getNext();
		}
	}

	private void printQueue(MyQueue<Process> queue) {
		MyQueue<Process> myQueue1 = new MyQueue<>();
		while (!queue.isEmpty()) {
			Process string = queue.get();
			System.out.println("" + string);
			myQueue1.put(string);
		}
	}

	private void startProgram() {
		while(!readyQueue.isEmpty()) {
			Process actualProcess = readyQueue.get();
			int timeProcess = actualProcess.getTimeProcess();
			if (timeProcess >= 0 ) {
				if (timeProcess > TIME_EXECUTION) {
					actualProcess.setTimeProcess(timeProcess - TIME_EXECUTION);
					if (actualProcess.isLocked()) {
						lockedQueue.put(actualProcess);
					}
					readyQueue.put(actualProcess);
				}else {
					orderExecutionQueue.put(actualProcess);
				}
				executionQueue.put(actualProcess);
			}else {
					orderExecutionQueue.put(actualProcess);
			}
		}
	}

	private boolean isInList(String name) {
		Node<Process> aux = processList.getHead();
		while(aux!= null) {
			System.out.println(aux.getInfo().getNameProcess() + "---" + name);
			if (aux.getInfo().getNameProcess().equals("Proceso " + name)) {
				return true;
			}
			aux = aux.getNext();
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case Constants.ADD_PROCESS:
			if (mainWindow.getId() != null && mainWindow.getTime() != -1) {
				if (!isInList(mainWindow.getId())) {
					processList.add(new Process(mainWindow.getId(), mainWindow.getTime(), mainWindow.getLocked()));
					mainWindow.clean();
					printList();
				}else {
					Utillidades.showMessageError("El identificador de proceso que ingresó ya se encuentra en la lista", "Error");
				}
			}
			break;
		case Constants.INIT_SIMULATION:
			if (!processList.isEmpty()) {
				System.out.println("no esta vacia");
				createReadyQueue();
				startProgram();
				mainWindow.activeButtons();
			}else {
				Utillidades.showMessageError("No existen procesos", "error");
			}
			break;
		case Constants.INITIAL_PROCESS:
			createReadyQueue();
			reports.generateReport(readyQueue,"Procesos iniciales", 1, null);
			break;
		case Constants.EXECUTIONS_REPORT:
			reports.generateReport(executionQueue,"Ejecucion", 2, null);
			break;
		case Constants.CRASH_REPORT:
			reports.generateReport(lockedQueue,"Bloqueados", 1, null);
			break;
		case Constants.ORDER_OF_COMPLETION:
			reports.generateReport(orderExecutionQueue, "Orden de finalizacion", 1, null);
			break;
		default:
			break;
		}
	}
	
	private void showCharge() {
		this.start();
		
	}
	@Override
	public void run() {
		Utillidades.showMessageWarning("Inicio", "fbhgf");
		for (int i = 0; i < 100; i++) {
			progreso.setValue(i);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Utillidades.showMessageWarning("fin del mensaje", "fbhgf");
		}
		super.run();
	}
}
