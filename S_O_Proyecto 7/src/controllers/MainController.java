package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import datastructures.MyDoubleList;
import datastructures.MyNode;
import datastructures.MyQueue;
import models.Compactacion;
import models.Particion;
import models.Process;
import models.SOMemory;
import utilities.Utillidades;
import views.Constants;
import views.MainWindow;

public class MainController implements ActionListener {

	private static final int TIME_EXECUTION = 1;
	private static final int POS_INIT = 0;
	private MyDoubleList<Particion> listPartInitials;
	private MyDoubleList<Particion> listAuxPart;
	private MyQueue<Particion> queueReadyPartList;	
	private MyQueue<Particion> queueAuxReadyList;
	private MyQueue<Particion> queueExecution;
	private MyQueue<Process> queueProcessFinished;
	private MyQueue<Particion> queuePartFinished;
	private MyQueue<Process> queueProcessReady;
	private MyQueue<Compactacion> queueCompactations;
	private MyQueue<Process> queueInitialProcess;

	private SOMemory memory;
	private int varPart = 1; // es el numero que controla los nombres de particiones
	private int numComp = 1; // es el numero que controla los nombres de particiones
	private MainWindow mainWindow;

	public MainController() {
		memory = new SOMemory();
		queueInitialProcess = new MyQueue<>();
		queueCompactations = new MyQueue<>();
		listPartInitials = new MyDoubleList<>();
		queueReadyPartList = new MyQueue<>();
		queueAuxReadyList = new MyQueue<>();
		queueExecution = new MyQueue<>();
		queuePartFinished = new MyQueue<>();
		listAuxPart = new MyDoubleList<>();
		queueProcessFinished = new MyQueue<>();
		queueProcessReady = new MyQueue<>();
		mainWindow = new MainWindow(this);
		
//		memory.setSize(80);
//		inicializarDatos3();
//		initSimulation();
//		createReports();
	}

	private void initSimulation() {
		MyQueue<Particion> partInMemory = memory.getParticiones();
		System.out.println("Esta vacio " + partInMemory.isEmpty());
		while(!partInMemory.isEmpty() && memory.hayOcupadas()) {
			Particion actualParticion = partInMemory.get();
			int timeProcess = actualParticion.getTimeProcess();
			System.out.println("Actual " + actualParticion);
			Particion particionAux = new Particion(actualParticion.getNameParticion(),
					new Process(actualParticion.getNameProcess(),actualParticion.getTimeProcess(),
							actualParticion.getSizeProcess()));
			if (timeProcess > 0) {
				queueExecution.put(particionAux);
				queueAuxReadyList.put(particionAux);
				if (timeProcess > TIME_EXECUTION) {
					actualParticion.executarProcess();
					actualParticion.setTimeProcess(timeProcess - TIME_EXECUTION);
					partInMemory.put(actualParticion);
				}else {
					actualParticion.executarProcess();
					actualParticion.setTimeProcess(timeProcess - timeProcess);	
					listAuxPart.replace(actualParticion);
					partInMemory.put(actualParticion);
				}
			}else {	
				queueAuxReadyList.put(particionAux);
				actualParticion.setGap(true);
				endProcessAndParticions(actualParticion,1);
				endProcessAndParticions(actualParticion,2);
				listAuxPart.replace(actualParticion);
				partInMemory.put(actualParticion);
			}
			if (memory.isLast(actualParticion)) {
				if (memory.existGap( 1)) {
					System.out.println("Vamoo a compactar");
					memory.setPosicionInicial(POS_INIT);
					verifyCompactacion();
				}else if (memory.existGap(2)) {
					System.out.println("no se puede compactar");
					verifyIngress();
				}
				partInMemory = memory.getParticiones();
			}
		}
		if (memory.gapTotal()) {
			System.out.println("Compactacion final");
			memory.setPosicionInicial(POS_INIT);
			verifyCompactacion();
			memory.getParticiones();
		}
	}

	private void endProcessAndParticions(Particion actualParticion, int n) {
		if (n == 1) {
			if (!isFinish(actualParticion, 2)) {
				queuePartFinished.put(actualParticion);
			}	
		}else {
			if (actualParticion.getProcess() != null) {
				if (!isFinish(actualParticion, 1)) {
					queueProcessFinished.put(actualParticion.getProcess());
				}
			}
		}
	}

	private void verifyCompactacion() {
		MyQueue<Particion> partiEnUso = new MyQueue<>();
		MyQueue<Particion> partiVacia = new MyQueue<>();
		MyQueue<Particion> partMemory = memory.getParticiones();
		boolean isChange = false;
		while (!partMemory.isEmpty()) {
			Particion p = partMemory.get();
			if (!p.isGap()) {
				if (isChange) {
					endProcessAndParticions(p, 1);
					String namePart = "Part" + varPart;
					Particion particionAct = new Particion(namePart, p.getProcess());
					listPartInitials.addElementToTail(particionAct);
					varPart++;
					partiEnUso.put(particionAct);
				}else {
					partiEnUso.put(p);
				}				
			}else {
				isChange = true;
				partiVacia.put(p);
			}
		}
		Particion vacia = getPart(partiVacia);
		memory.fillParticiones(partiEnUso, vacia);
		queueCompactations.put(new Compactacion("Comp " + numComp, memory.getParticiones(), partiVacia));
		numComp++;
	}

	private void verifyIngress() {
		boolean isIngres = false;
		MyQueue<Process> aux = new MyQueue<>();
		while (!queueProcessReady.isEmpty()) {
			System.out.println("NO ESTA VACIA");
			Process processActual =  queueProcessReady.get();
			if (memory.hayEspacio(processActual.getTamaño())) {
				System.out.println("habia " + memory.getSizeLibre());
				String namePart = "Part" + varPart;
				Particion particion = new Particion(namePart, processActual);
				listPartInitials.addElementToTail(particion);
				if (!isIngres) {
					endProcessAndParticions(memory.getParticionLast(), 1);
					memory.removeLast();	
				}
				memory.addParticion(particion);
				varPart++;
				isIngres = true;
				System.out.println("Ingrese " + particion.getSizeParticion());
			}else {
				if (isIngres) {
					System.out.println("sobro " + memory.getSizeLibre());
					nuevaParticion();
				}
				aux.put(processActual);
				while (!queueProcessReady.isEmpty()) {
					aux.put(queueProcessReady.get());
				}
			}
		}
		while (!aux.isEmpty()) {
			queueProcessReady.put(aux.get());
			System.err.println("GUARDANDO " );
		}
		if (isIngres) {
			if (queueProcessReady.isEmpty() && memory.getSizeLibre() > 0) {
				nuevaParticion();
			}
		}
	}

	private void nuevaParticion() {
		String namePart = "Part" + varPart;
		Particion particion = new Particion(namePart, memory.getSizeLibre());
		listPartInitials.addElementToTail(particion);
		memory.addParticion(particion);
		varPart++;
	}

	private Particion getPart(MyQueue<Particion> partiVacia) {
		int sizeVacia = 0;
		MyQueue<Particion> particions = new MyQueue<>();
		while (!partiVacia.isEmpty()) {
			Particion particion = partiVacia.get();
			System.out.println(" Vacia " + particion.toString());
			sizeVacia += particion.getSizeParticion();
			particions.put(particion);
		}
		String namePart = "Part" + varPart;
		Particion particionAct = new Particion(namePart, sizeVacia);
		listPartInitials.addElementToTail(particionAct);
		varPart++;
		while (!particions.isEmpty()) {
			partiVacia.put(particions.get());
		}
		return particionAct;
	}

	private boolean isFinish(Particion partInit, int i) {
		if (i == 1) {
			MyQueue<Process> aux = getCopyFinished();
			while (!aux.isEmpty()) {
				if (aux.get().getNameProcess().equals(partInit.getNameProcess())) {
					return true;
				}
			}
		}else {
			MyQueue<Particion> aux = getCopyFinishedPart();
			while (!aux.isEmpty()) {
				if (aux.get().getNameParticion().equals(partInit.getNameParticion())) {
					return true;
				}
			}
		}
		return false;
	}
	private MyQueue<Process> getCopyFinished() {
		MyQueue<Process> aux = new MyQueue<>();
		MyQueue<Process> resp = new MyQueue<>();
		while (!queueProcessFinished.isEmpty()) {
			Process process = queueProcessFinished.get();
			aux.put(process);
			resp.put(process);
		}
		while (!aux.isEmpty()) {
			queueProcessFinished.put(aux.get());
		}
		return resp;
	}
	private MyQueue<Particion> getCopyFinishedPart() {
		MyQueue<Particion> aux = new MyQueue<>();
		MyQueue<Particion> resp = new MyQueue<>();
		while (!queuePartFinished.isEmpty()) {
			Particion particion = queuePartFinished.get();
			aux.put(particion);
			resp.put(particion);
		}
		while (!aux.isEmpty()) {
			queuePartFinished.put(aux.get());
		}
		return resp;
	}

	private void crearParticiones() {
		MyQueue<Process> aux = new MyQueue<>();
		while (!queueProcessReady.isEmpty()) {
			Process processActual =  queueProcessReady.get();
			if (memory.hayEspacio(processActual.getTamaño())) {
				String namePart = "Part" + varPart;
				Particion particion = new Particion(namePart, processActual);
				listPartInitials.addElementToTail(particion);
				memory.addParticion(particion);
				varPart++;
			}else {
				if (memory.getSizeLibre() > 0) {
					String namePart = "Part" + varPart;
					Particion particion = new Particion(namePart, memory.getSizeLibre());
					listPartInitials.addElementToTail(particion);
					memory.addParticion(particion);
					varPart++;
				}
				aux.put(processActual);
				while (!queueProcessReady.isEmpty()) {
					aux.put(queueProcessReady.get());
				}
			}
		}
		queueProcessReady = aux;
	}

	private void createProceso(Process process) {
		queueInitialProcess.put(process);
		queueProcessReady.put(process);
	}

	/**
	 * Datos prueba anterior ing
	 */
	private void inicializarDatos1() {
		createProceso(new Process("P11", 5, 11));
		createProceso(new Process("P15", 7, 15));
		createProceso(new Process("P18", 8, 18));
		createProceso(new Process("P6", 3, 6));
		createProceso(new Process("P9", 4, 9));
		createProceso(new Process("P20", 2, 20));
		createProceso(new Process("P13", 3, 13));
		crearParticiones();
	}

	/**
	 * Datos prueba Maria
	 */
	private void inicializarDatos3() {
		createProceso(new Process("P1", 7, 10));
		createProceso(new Process("P2", 2, 30));
		createProceso(new Process("P3", 4, 50));
		createProceso(new Process("P4", 5, 18));
		createProceso(new Process("P5", 8, 25));
		createProceso(new Process("P6", 4, 8));
		createProceso(new Process("P7", 7, 15));
		crearParticiones();
	}
	/**
	 * Datos prueba Maria 2
	 */

	private void inicializarDatos4() {
		createProceso(new Process("P15", 7, 10));
		createProceso(new Process("P13", 2, 30));
		createProceso(new Process("P4", 4, 50));
		createProceso(new Process("P12", 5, 18));
		createProceso(new Process("P18", 8, 25));
		createProceso(new Process("P5", 4, 8));
		createProceso(new Process("P8", 7, 15));
		crearParticiones();
	}
	
	private void inicializarDatos2() {
		createProceso(new Process("P20", 5, 20));
		createProceso(new Process("P15", 4, 15));
		createProceso(new Process("P2", 9, 2));
		createProceso(new Process("P10", 4, 10));
		createProceso(new Process("P9", 3, 9));
		crearParticiones();
	}

	private void createReports() {
		Reports reports = new Reports();
		reports.generateReport(listPartInitials, queueInitialProcess, queueProcessFinished, queueCompactations, queuePartFinished, queueAuxReadyList, "prueba2");
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
				aux2.put(p);
			}
		}
		queueProcessReady = aux2;
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
			while (!queueProcessReady.isEmpty()) {
				Process newProcess = queueProcessReady.get();
				destino.put(newProcess);
				aux.put(newProcess);
			}
			queueProcessReady = aux;
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
			if (mainWindow.getRowSelected() != -1) {
				Process process = getProcess(mainWindow.getRowSelected() + 1);
				borrarProceso(process);
				mainWindow.deleteActual();
			}else {
				Utillidades.showMessageWarning("Seleccione un proceso para eliminar", "Eliminar");
			}
			break;
		case Constants.CREATE_REPORT: 
			if (createMemory()) {
				crearParticiones();
				initSimulation();
				createReports();
				mainWindow.openPDF();
			}
			break;
		default:
			break;
		}
	}

	private boolean createMemory() {
		int size = mainWindow.getSizeMemory();
		if (size != -1) {
			memory.setSize(size);
			return true;
		}
		return false;
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
