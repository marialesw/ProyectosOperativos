package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JOptionPane;

import models.MyQueue;
import models.Node;
import models.Order;
import models.Process;
import utilities.Utillidades;
import views.Constants;
import views.MainWindow;

public class MainController implements ActionListener {

	private static final int TIME_EXECUTION = 5;
	private MyQueue<Process> initialQueue; // esta cola es de los que estan listos para ejecucion
	private MyQueue<Process>  readyQueue;
	private MyQueue<Process>  executionQueue;
	private MyQueue<Process>  destroyedQueue;
	private MyQueue<Process>  lockedQueue;
	private MyQueue<Process>  layQueue;
	private MyQueue<Process>  auxReadyQueue;
	private MyQueue<Process>  finishQueue;
	private MyQueue<String>  comunicationQueue;
	private MainWindow jfMainWindow;
	private Report report;

	public MainController() {
		jfMainWindow = new MainWindow(this);
		readyQueue = new MyQueue<>();
		auxReadyQueue = new MyQueue<Process>();
		initQueues();
		report = new Report();
		ArrayList<String> aux1 = new ArrayList<>();
		aux1.add("p3");
		aux1.add("p2");
		ArrayList<String> aux2 = new ArrayList<>();
		aux2.add("p7");
		aux2.add("p1");
		addProcess(new Process("p1", 20, false, true, false, -1, 2,aux1 ));
		addProcess(new Process("p2", 30, true, false, true, 14, 5, aux2));
		addProcess(new Process("p3", 10, false, true, false, -1, 1, aux2));
		addProcess(new Process("p4", 15, true, false, true, 10,1, aux1));
		addProcess(new Process("p5", 25, false, true, false, -1,3, aux1));
		addProcess(new Process("p6", 20, true, false, false, -1, 1, aux1));
		addProcess(new Process("p7", 20, true, true, false, -1,3, aux1));
		addProcess(new Process("p8", 19, true, false, false, -1,5, aux1));

		Order<Process> order = new Order<Process>(new Comparator<Process>(){
			@Override
			public int compare(Process o1, Process o2) {
				return o1.getPriorityProcess()-o2.getPriorityProcess();
			}
		});

		//order.sortQueue(initialQueue);
		//System.out.println(executionQueue.getLast().getInfo().getNameProcess());
		//printQueues();
	}

	private void initQueues(){
		initialQueue = new MyQueue<>();
		executionQueue = new MyQueue<>();
		destroyedQueue = new MyQueue<>();
		lockedQueue = new MyQueue<>();
		layQueue = new MyQueue<>();
		finishQueue = new MyQueue<>();
		comunicationQueue = new MyQueue<>();
	}

	//inicio de ejecucion
	private void init() {
		saveQueue();
		while(!readyQueue.isEmpty()) {
			Process actualProcess = readyQueue.get();
			int timeProcess = actualProcess.getTimeProcess();
			Process processAux = new Process(actualProcess.getNameProcess(),actualProcess.getTimeProcess(),
					actualProcess.isLocked(), actualProcess.isLayOff(), actualProcess.isDestroyed(), actualProcess.getTimeDestroy(),
					actualProcess.getPriorityProcess(), actualProcess.getComunicationsList());
			if (timeProcess > 0) {
				auxReadyQueue.put(processAux);
				if (timeProcess > TIME_EXECUTION) {
					startComunications(actualProcess);
					actualProcess.setTimeProcess(timeProcess - TIME_EXECUTION);
					executionQueue.put(processAux);
					if (!validateStates(actualProcess, timeProcess- TIME_EXECUTION)) { // si no es destruido, entonces se agrega a la cola de listos 
						readyQueue.put(actualProcess);
					}
				}else {
					actualProcess.setTimeProcess(timeProcess - timeProcess);						
					finishQueue.put(actualProcess);
				}
			}else {	
				auxReadyQueue.put(processAux);
				finishQueue.put(actualProcess);
			}
		}
	}

	/**
	 * guarda el contenido de una cola en la otra sin afectarla
	 * @param saveQueue donde se guarda
	 * @param queueToSave cola que voy a guardar
	 */
	private void saveQueue() {
		MyQueue<Process> aux = new MyQueue<>();
		while (!initialQueue.isEmpty()) {
			Process newProcess = initialQueue.get();
			readyQueue.put(newProcess);
			aux.put(newProcess);
		}
		initialQueue = aux;		
	}

	private void startComunications(Process actualProcess) {
		ArrayList<String> comunications = actualProcess.getComunicationsList();
		for (int i = 0; i < comunications.size(); i++) {
			if(isInList(comunications.get(i), readyQueue,1)) {
				comunicationQueue.put(" - " + actualProcess.getNameProcess() + " se comunicó con: " + comunications.get(i));
			}
		}
	}

	/*
	 * siendo n el valor 1: ready, 2: initial
	 */
	private boolean isInList(String string, MyQueue<Process> queue, int n) {
		boolean isInReady = false;
		MyQueue<Process> aux = new MyQueue<>();
		while (!queue.isEmpty()) {
			Process process = queue.get();
			if (process.getNameProcess().equals(string)) {
				isInReady = true;
			}
			aux.put(process);
		}
		if (n == 1) {
			readyQueue = aux;
		}else if(n == 2) {
			initialQueue = aux;
		}
		return isInReady;
	}

	private boolean validateStates(Process actualProcess, int timeProcess) {
		if(actualProcess.getTimeDestroy() >= timeProcess) {
			destroyedQueue.put(actualProcess);
			return true;
		}else if (actualProcess.isLocked()) {
			lockedQueue.put(actualProcess);
		}
		if(actualProcess.isLayOff()) {
			layQueue.put(actualProcess);
		}
		return false;
	}

	private void deleteProcess(int priority) {
		MyQueue<Process> aux = new MyQueue<>();
		while (!initialQueue.isEmpty()) {
			System.out.println("no esta vacia");
			Process actualProcess = initialQueue.get();
			if (actualProcess.getPriorityProcess() != priority) {
				aux.put(actualProcess);
			}else {
				addRest(aux, 2);
				break;
			}
		}
	}

	private void updateProcess(int priority, int timeProcess, boolean locked, boolean layOff, boolean isDestroyed, 
			int timeDestroy, int priorityProcess, ArrayList<String> comunicationsList) {

		MyQueue<Process> aux = new MyQueue<>();
		while (!initialQueue.isEmpty()) {
			System.out.println("no esta vacia");
			Process actualProcess = initialQueue.get();
			if (actualProcess.getPriorityProcess() != priority) {
				aux.put(actualProcess);
			}else {
				actualProcess.setTimeProcess(timeProcess);
				actualProcess.setLocked(locked);
				actualProcess.setLayOff(layOff);
				actualProcess.setDestroyed(isDestroyed);
				actualProcess.setTimeDestroy(timeDestroy);
				actualProcess.setPriorityProcess(priorityProcess);
				actualProcess.setComunicationsList(comunicationsList);

				addRest(aux, 2);
				break;
			}
		}
	}

	private void addProcess(Process newProcess) {
		int initialPriorityProcess = newProcess.getPriorityProcess();
		System.out.println("Esta vacía " + initialQueue.isEmpty());
		if (initialQueue.isEmpty()) {
			newProcess.setPriorityProcess(1);
			initialQueue.put(newProcess);
		}else if(initialQueue.getLast().getInfo().getPriorityProcess() < initialPriorityProcess){
			newProcess.setPriorityProcess(initialQueue.getLast().getInfo().getPriorityProcess() + 1);
			initialQueue.put(newProcess);
		}else {
			MyQueue<Process> auxQueue = new MyQueue<Process>();
			while(!initialQueue.isEmpty()) {
				Process actualProcess = initialQueue.get();
				int actualPriority = actualProcess.getPriorityProcess();
				if (actualPriority == initialPriorityProcess) {
					auxQueue.put(newProcess);
					actualProcess.setPriorityProcess(actualPriority + 1);
					auxQueue.put(actualProcess);
					addRest(auxQueue, 1); //agrega los demás elementos de la cola original a la auxiliar
					break;
				}else {
					auxQueue.put(actualProcess);
				}
			}
		}
		jfMainWindow.deleteRows();
		updateTable();
	}

	private void updateTable() {
		MyQueue<Process> aux = new MyQueue<>();
		while (!initialQueue.isEmpty()) {
			Process newProcess = initialQueue.get();
			String[] fila = {newProcess.getNameProcess(), "" + newProcess.getTimeProcess(), "" + newProcess.getPriorityProcess(), getComunicationsToTable(newProcess)};
			jfMainWindow.addElement(fila);
			aux.put(newProcess);
		}
		initialQueue = aux;
	}

	private String getComunicationsToTable(Process newProcess) {
		String res = "";
		ArrayList<String> aux = newProcess.getComunicationsList();
		for (int i = 0; i < aux.size(); i++) {
			res += " - " + aux.get(i);
		}
		return res;
	}

	private void addRest(MyQueue<Process> auxQueue, int option) {
		while (!initialQueue.isEmpty()) {
			Process actualProcess = initialQueue.get();
			if (option == 1) {
				System.out.println("Opcion 1");
				actualProcess.setPriorityProcess(actualProcess.getPriorityProcess() + 1);
			}else if (option == 2) {
				System.out.println("Opcion 2");
				actualProcess.setPriorityProcess(actualProcess.getPriorityProcess() - 1);
			}
			auxQueue.put(actualProcess);
		}
		initialQueue = auxQueue;
	}

	private void printQueues(String string, MyQueue<Process> myQueue, int n) {
		MyQueue<Process> aux = new MyQueue<>();
		while (!myQueue.isEmpty()) {
			Process process = myQueue.get();
			aux.put(process);
			System.out.println(" - " + string + " " + process.toString());
		}
		if (n == 1) {
			readyQueue = aux;
		}else if(n == 2) {
			initialQueue = aux;
		}else if(n == 3) {
			executionQueue = aux;
		}
	}
	private void printQueuesComunication(String string, MyQueue<String> myQueue) {
		MyQueue<String> aux = new MyQueue<>();
		while (!myQueue.isEmpty()) {
			String process = myQueue.get();
			aux.put(process);
			System.out.println(" - " + string + " " + process.toString());
		}
		myQueue = aux;
	}

	public MyQueue<Process> getQueue() {
		MyQueue<Process> aux = new MyQueue<>();
		MyQueue<Process> retur = new MyQueue<>();
		while (!initialQueue.isEmpty()) {
			Process process = initialQueue.get();
			aux.put(process);
			retur.put(process);
		}
		initialQueue = aux;
		return retur;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case Constants.ADD_PROCESS:
			addProcessFromFrame();
			break;
		case Constants.DESTROY: 
			jfMainWindow.setEditableTime(true);
			break;
		case Constants.DES:
			jfMainWindow.setEditableTime(false);
			break;
		case Constants.BUTTON_ADD:
			jfMainWindow.createDialog(getQueue());
			break;
		case Constants.ADD_NEW_COMUNICATION:
			if (!jfMainWindow.isInListInformacion(jfMainWindow.getComunicationProcess())) {
				jfMainWindow.addItemList(jfMainWindow.getComunicationProcess());
			}else {
				Utillidades.showMessageError("Este proceso ya se encuentra en la lista de comunicaciones", "Error");
			}
			break;
		case Constants.REMOVE_ALL:
			initialQueue = new MyQueue<>();
			jfMainWindow.deleteRows();
			break;
		case Constants.UPDATE_PROCESS:
			if (jfMainWindow.geSelectRowTable() != -1) {
//				addProcessFromFrame();
//				jfMainWindow.deleteRow(jfMainWindow.geSelectRowTable());
			}else {
				JOptionPane.showMessageDialog(null,
						"Seleccione el proceso que quiere editar");	
			}
			break;
		case Constants.DELETE_PROCESS:
			if (jfMainWindow.geSelectRowTable() != -1) {
				deleteProcess(jfMainWindow.geSelectRowTable() + 1);
				jfMainWindow.deleteRow(jfMainWindow.geSelectRowTable());
			}else {
				JOptionPane.showMessageDialog(null,
						"Seleccione el proceso que quiere eliminar");	
			}
			break;
		case Constants.CREATE_REPORT:
			init();
			report.generateReport(initialQueue, auxReadyQueue,executionQueue, destroyedQueue, lockedQueue, layQueue ,comunicationQueue,
					finishQueue,"LISTOS");
			break;
		case Constants.BUTTON_REST:
			//printQueues(" Inicial ", initialQueue,2); // cola inicial
			//printQueues(" BLoqueados ", lockedQueue); // cola de bloqueados
			//printQueues(" Suspendidos ", layQueue); // cola de suspendidos 
			//printQueues("Finalizado", finishQueue);
			//printQueues("ejecucion ", executionQueue,3);
			//printQueuesComunication("comunicacion ", comunicationQueue);
			//printQueues("listos ", readyQueue); // no funciona porq|ue la cola de listos en este momento ya está vacía-.
			if ( jfMainWindow.getSelectionList() != -1) {
				System.out.println(jfMainWindow.getListInformation().get());
				jfMainWindow.removeProcessComunication();
			}else {
				JOptionPane.showMessageDialog(null,
						"Seleccione el proceso a borrar");	
			}
			break;
		default:
			break;
		}

	}

	private void addProcessFromFrame() {
		if (!isInList(jfMainWindow.getName(), initialQueue,2)) {
			if (jfMainWindow.getName() != null && jfMainWindow.getTime() != -1) {
				if ((jfMainWindow.isDestroy() && jfMainWindow.getTimeDestroy() != -1) || !jfMainWindow.isDestroy()) {
					if (jfMainWindow.getTimeDestroy() <= jfMainWindow.getTime()) {
						ArrayList<String> procesList = new ArrayList<String>();
						procesList = createArray(jfMainWindow.getListInformation());
						addProcess(new Process(jfMainWindow.getName(), jfMainWindow.getTime(), jfMainWindow.getLocked(), 
								jfMainWindow.isLay(), jfMainWindow.isDestroy(), jfMainWindow.getTimeDestroy(), jfMainWindow.getPriority(), 
								procesList));
					}else {
						Utillidades.showMessageError("El tiempo de destrucción no puede ser mayor al tiempo del proceso", "Error");
					}
				}else {
					Utillidades.showMessageError("Debe ingresar el tiempo en el que se destruye el proceso", "Error");
				}
			}
		}else {
			Utillidades.showMessageError("El nombre de proceso ya se encuentra en la lista", "Error");
		}
	}

	private ArrayList<String> createArray(MyQueue<String> listInformation) {
		ArrayList<String> aux = new ArrayList<>();
		while (!listInformation.isEmpty()) {
			aux.add(listInformation.get());
		}
		return aux;
	}
}
