package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import datastructures.MyDoubleList;
import datastructures.MyNode;
import datastructures.MyQueue;
import models.Condensacion;
import models.Particion;
import models.Process;
import utilities.Utillidades;
import views.Constants;
import views.MainWindow;

public class MainController implements ActionListener {

	private static final int TIME_EXECUTION = 5;
	private MyDoubleList<Particion> listPartInitials;
	private MyDoubleList<Particion> listAuxPart;
	private MyQueue<Particion> queueReadyPartList;	
	private MyQueue<Particion> queueAuxReadyList;
	private MyQueue<Particion> queueExecution;
	private MyQueue<Process> queueProcessFinished;
	private MyQueue<Particion> queuePartFinished;
	private MyQueue<Condensacion> queueCondensation;
	private MyQueue<Process> queueProcessInitial;

	private int varPart = 1; // es el numero que controla los nombres de particiones
	private int numCondensation = 1; // es el numero que controla los nombres de particiones
	private MainWindow mainWindow;

	public MainController() {
		listPartInitials = new MyDoubleList<>();
		queueReadyPartList = new MyQueue<>();
		queueAuxReadyList = new MyQueue<>();
		queueExecution = new MyQueue<>();
		queuePartFinished = new MyQueue<>();
		listAuxPart = new MyDoubleList<>();
		queueCondensation = new MyQueue<>();
		queueProcessFinished = new MyQueue<>();
		queueProcessInitial = new MyQueue<>();
		mainWindow = new MainWindow(this);
		//				inicializarDatos1();
		//				init();
		//				System.out.println("Condensaciones ");
		//				printCondensation("", queueCondensation);
		//				createReports();
	}

	//inicio de ejecucion
	private void init() {
		saveQueue();
		while(!queueReadyPartList.isEmpty()) {
			Particion actualParticion = queueReadyPartList.get();
			int timeProcess = actualParticion.getTimeProcess();
			Particion particionAux = new Particion(actualParticion.getNameParticion(),
					new Process(actualParticion.getNameProcess(),actualParticion.getTimeProcess(),
							actualParticion.getSizeProcess()));
			if (timeProcess > 0) {
				queueExecution.put(particionAux);
				queueAuxReadyList.put(particionAux);
				if (timeProcess > TIME_EXECUTION) {
					actualParticion.executarProcess();
					actualParticion.setTimeProcess(timeProcess - TIME_EXECUTION);
					queueReadyPartList.put(actualParticion);
				}else {
					actualParticion.executarProcess();
					actualParticion.setTimeProcess(timeProcess - timeProcess);	
					actualParticion.setGap(true);
					queuePartFinished.put(actualParticion);
					queueProcessFinished.put(actualParticion.getProcess());
					listAuxPart.replace(actualParticion);
					verifyCondensation(actualParticion);
				}
			}else {	
				queueAuxReadyList.put(particionAux);
				actualParticion.setGap(true);
				queuePartFinished.put(actualParticion);
				queueProcessFinished.put(actualParticion.getProcess());
				listAuxPart.replace(actualParticion);
				verifyCondensation(actualParticion);
			}
		}
	}

	private void verifyCondensation(Particion actualParticion) {
		int num = 1;
		MyNode<Particion> aux = listAuxPart.getHead();
		MyDoubleList<Particion> aux2 = new MyDoubleList<>();
		while (aux != null) {
			if (aux.getInfo() == actualParticion) {
				MyNode<Particion> previous = getPrevious(aux);
				MyNode<Particion> next = getNext(aux);
				if (previous != null && next != null) {
					if (previous.getInfo().isGap() && next.getInfo().isGap()) {
						Condensacion condensacion = new Condensacion("Con" + numCondensation, previous.getInfo(), aux.getInfo());
						numCondensation++;
						condensacion.agregarTerceraParticion(next.getInfo());
						queueCondensation.put(condensacion);  
						aux2.deleteTail();
						aux2.addElementToTail(condensacion.getParticion(varPart));
						listPartInitials.addElementToTail(condensacion.getParticion(varPart));
						queuePartFinished.put(condensacion.getParticion(varPart));
						aux = aux.getNext().getNext();
						varPart++;
					}else if (previous.getInfo().isGap()) {
						aux = PreviousGap(aux, aux2, previous);
						num++;
						varPart++;
					}else if (next.getInfo().isGap()) {
						aux = nextGap(aux, aux2, next);
						varPart++;
					}
				}else if (previous != null ) { 
					if (previous.getInfo().isGap()) {
						aux = PreviousGap(aux, aux2, previous);
						varPart++;
					}
				}else if (next != null ) { 
					if (next.getInfo().isGap()) {
						aux = nextGap(aux, aux2, next);
						varPart++;
					}
				}
				if (aux != null) {
					aux2.addElementToTail(aux.getInfo());
				}
			}else {
				aux2.addElementToTail(aux.getInfo());
			}
			if (aux != null) {
				aux = aux.getNext();
			}
		}
		listAuxPart = aux2;
		System.out.println("Actual");
		listAuxPart.printList();
	}

	private MyNode<Particion> nextGap(MyNode<Particion> aux, MyDoubleList<Particion> aux2,
			MyNode<Particion> next) {
		Condensacion condensacion = new Condensacion("Con" + numCondensation, aux.getInfo(), next.getInfo());
		numCondensation++;
		queueCondensation.put(condensacion);   

		//	listAuxPart.replace(condensacion.getParticion(varPart), aux, next, null);
		aux2.addElementToTail(condensacion.getParticion(varPart));
		listPartInitials.addElementToTail(condensacion.getParticion(varPart));
		queuePartFinished.put(condensacion.getParticion(varPart));
		aux = aux.getNext().getNext();
		return aux;
	}

	private MyNode<Particion> PreviousGap(MyNode<Particion> aux, MyDoubleList<Particion> aux2,
			MyNode<Particion> previous) {
		Condensacion condensacion = new Condensacion("Con" + numCondensation, previous.getInfo(), aux.getInfo());
		numCondensation++;
		queueCondensation.put(condensacion);  
		//listAuxPart.replace(condensacion.getParticion(varPart), previous, aux, null);
		aux2.deleteTail();
		aux2.addElementToTail(condensacion.getParticion(varPart));
		listPartInitials.addElementToTail(condensacion.getParticion(varPart));
		queuePartFinished.put(condensacion.getParticion(varPart));
		if (aux.getNext() != null) {
			aux = aux.getNext();
		}else {
			aux = null;
		}
		return aux;
	}

	private MyNode<Particion> getPrevious(MyNode<Particion> myNode) {
		MyNode<Particion> resp = null;
		MyNode<Particion> iterator = listAuxPart.getHead();
		while (iterator != null) {
			if (iterator == myNode) {
				if (iterator.getPrevious() != null) {
					resp = iterator.getPrevious();
				}
			}
			iterator = iterator.getNext();
		}
		return resp;
	}

	private MyNode<Particion> getNext(MyNode<Particion> myNode) {
		MyNode<Particion> resp = null;
		MyNode<Particion> iterator = listAuxPart.getHead();
		while (iterator != null) {
			if (iterator == myNode) {
				if (iterator.getNext() != null) {
					resp = iterator.getNext();
				}
			}
			iterator = iterator.getNext();
		}
		return resp;
	}

	/**
	 * guarda el contenido de una cola en la otra sin afectarla
	 * 
	 */
	private void saveQueue() {
		MyQueue<Process> aux = new MyQueue<>();
		MyNode<Particion> part = listPartInitials.getHead();
		while (part != null) {
			Particion newPartition = part.getInfo();
			queueReadyPartList.put(newPartition);
			listAuxPart.addElementToTail(newPartition);
			part = part.getNext();
		}
	}

	private void printQueues(String string, MyQueue<Particion> myQueue, int n) {
		MyQueue<Particion> aux = new MyQueue<>();
		System.out.println(myQueue.isEmpty());
		while (!myQueue.isEmpty()) {
			Particion particion = myQueue.get();
			aux.put(particion);
			System.out.println(" - " + string + " " + particion.toString());
		}
		if (n == 1) {
			queueAuxReadyList = aux;
		}else if(n == 2) {
			queueExecution = aux;
		}else if(n == 3) {
			queuePartFinished = aux;
		}
	}

	private void printCondensation(String string, MyQueue<Condensacion> myQueue) {
		MyQueue<Condensacion> aux = new MyQueue<>();
		System.out.println(myQueue.isEmpty());
		while (!myQueue.isEmpty()) {
			Condensacion condensation = myQueue.get();
			aux.put(condensation);
			System.out.println(" - " + string + " " + condensation.toString());
		}
		queueCondensation = aux;
	}

	private void inicializarDatos1() {
		createProceso(new Process("P1", 15, 15));
		createProceso(new Process("P2", 20, 10));
		createProceso(new Process("P3", 30, 25));
		createProceso(new Process("P4", 6, 50));
		createProceso(new Process("P5", 1, 10));
		createProceso(new Process("P6", 18, 100));
		createProceso(new Process("P7", 29, 180));
	}

	private void fillQueueProcess() {

	}

	private void inicializarDatos3() {
		createProceso(new Process("P1", 15, 542));
		createProceso(new Process("P2", 28, 69));
		createProceso(new Process("P3", 13, 510));
		createProceso(new Process("P4", 5, 14));
		createProceso(new Process("P5", 11, 2));
		createProceso(new Process("P6", 19, 782));
	}

	private void inicializarDatos2() {
		createProceso(new Process("P1", 5, 10));
		createProceso(new Process("P2", 4, 15));
		createProceso(new Process("P3", 6, 20));
		createProceso(new Process("P4", 3, 14));
		createProceso(new Process("P5", 2, 18));
	}

	private void createReports() {
		Reports reports = new Reports();
		reports.generateReport(listPartInitials, queueProcessInitial, queueProcessFinished, queueCondensation, queuePartFinished, queueAuxReadyList, "informe");
	}

	private void createProceso(Process process) {
		String namePart = "Part" + varPart;
		Particion particion = new Particion(namePart, process);
		listPartInitials.addElementToTail(particion);
		queueProcessInitial.put(process);
		varPart++;
	}

	private void borrarProceso(Process process) {
		MyNode<Particion> iterator = listPartInitials.getHead();
		MyDoubleList<Particion> auxList = new MyDoubleList<>();
		while (iterator != null) {
			if (!iterator.getInfo().getProcess().equals(process)) {
				auxList.addElementToTail(iterator.getInfo());
			}
			iterator = iterator.getNext();
		}
		listPartInitials = auxList;
		MyQueue<Process> aux = new MyQueue<>();
		MyQueue<Process> aux2 = new MyQueue<>();
		MyQueue<Process> actu = saveQueue(aux, 1);
		while (!actu.isEmpty()) {
			Process p = actu.get();
			if (!p.getNameProcess().equals(process.getNameProcess())) {
				System.out.println("fodefdsf " + process.getNameProcess() );
				aux2.put(p);
			}
		}
		queueProcessInitial = aux2;
	}

	private boolean isInList(String name) {
		final MyQueue<Process> aux = new MyQueue<>();
		MyQueue<Process> actu = saveQueue(aux, 1);
		while (!actu.isEmpty()) {
			Process process = actu.get();
			if (process.getNameProcess().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private MyQueue<Process> saveQueue(MyQueue<Process> destino, int exec) {
		final MyQueue<Process> aux = new MyQueue<>();
		if (exec == 1) {
			while (!queueProcessInitial.isEmpty()) {
				Process newProcess = queueProcessInitial.get();
				destino.put(newProcess);
				aux.put(newProcess);
			}
			queueProcessInitial = aux;
		}
		return destino;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action){
		case Constants.BUTTON_ADD_PROCESS: 
			crearProceso();
			break;
		case Constants.BUTTON_EDITAR_PROCESS: 
			eidtarProceso();
			break;
		case Constants.DELETE_PROCESS: 
			System.out.println("348 " + mainWindow.getRowSelected());

			if (mainWindow.getRowSelected() != -1) {
				Process process = getProcess(mainWindow.getRowSelected() + 1);
				borrarProceso(process);
			}else {
				Utillidades.showMessageWarning("Seleccione un proceso para eliminar", "Eliminar");
			}
			break;
		case Constants.CREATE_REPORT: 
			init();
			createReports();
			mainWindow.openPDF();
			break;
		default:
			break;
		}
	}

	private Process getProcess(int rowSelected) {
		MyQueue<Process> aux = new MyQueue<>();
		MyQueue<Process> actu = saveQueue(aux, 1);
		int n = 0;
		Process process = null;
		while (!actu.isEmpty()) {
			process = actu.get();
			n ++;
			if (n == rowSelected) {
				return process;
			}else {
				aux.put(process);
			}
		}
		System.out.println("LINEA " + rowSelected + " proceso " + process.getNameProcess());
		return process;
	}

	private void eidtarProceso() {
		// TODO Auto-generated method stub

	}

	private void crearProceso() {
		//verificar que no se repita

		String name = mainWindow.getName();
		int time = mainWindow.getTime();
		int size = mainWindow.getSizeProcess();
		if (name != null && time != -1
				&& size != -1) {
			if (!isInList(name)) {
				createProceso(new Process(name,
						time, size));
				String[] fila = { name, "" + time, 
						"" + size};
				this.mainWindow.addElement(fila);
			}else {
				Utillidades.showMessageError("El proceso ingresado ya existe", "error");					
			}
		}
		mainWindow.clean();
	}


}
