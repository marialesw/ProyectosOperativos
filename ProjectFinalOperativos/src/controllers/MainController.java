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

	private static final int TIME_EXECUTION = 1;
	private static final int POS_INIT = 0;
	private MyDoubleList<Particion> listPartInitials;
	private MyQueue<Particion> queueReadyPartList;	
	private MyQueue<Particion> queueAuxReadyList;
	private MyQueue<Particion> queueExecution;
	private MyQueue<Process> queueProcessFinished;
	private MyQueue<Particion> queuePartFinished;
	private MyQueue<Process> queueProcessReady;
	private MyQueue<Condensacion> queueCondensaciones;
	private MyQueue<Process> queueInitialProcess;
	private MyQueue<Particion> queueREADY;
	private Particion lastParticion;
	private int sizeLibre;
	private int sizeMemory;
	private int varPart = 1; // es el numero que controla los nombres de particiones
	private int numCondensation = 1; // es el numero que controla los nombres de particiones
	private MainWindow mainWindow;
	private MyDoubleList listParticiones;
	private boolean isFin;

	public MainController() {
		isFin = false;
		queueREADY = new MyQueue<>();
		queueInitialProcess = new MyQueue<>();
		queueCondensaciones = new MyQueue<>();
		listPartInitials = new MyDoubleList<>();
		queueReadyPartList = new MyQueue<>();
		queueAuxReadyList = new MyQueue<>();
		queueExecution = new MyQueue<>();
		queuePartFinished = new MyQueue<>();
		queueProcessFinished = new MyQueue<>();
		queueProcessReady = new MyQueue<>();
		listParticiones = new MyDoubleList<>();	
		//mainWindow = new MainWindow(this);
		sizeMemory = 60;
		sizeLibre = sizeMemory;
		inicializarDatos4();
		initSimulation();
	
		//createReports();
	}

	private void initSimulation() {
		int cintril = 0;
		//memory.hayOcupadas()
		saveQueue();
		while(!queueREADY.isEmpty()) {
			Particion actualParticion = queueREADY.get();
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
					queueREADY.put(actualParticion);
				}else {
					actualParticion.executarProcess();
					actualParticion.setTimeProcess(timeProcess - timeProcess);	
					actualParticion.setGap(true);
					listParticiones.replace(actualParticion);
					queueREADY.put(actualParticion);
				}
			}else {	
				queueAuxReadyList.put(particionAux);
				actualParticion.setGap(true);
				endProcessAndParticions(actualParticion,1);
				endProcessAndParticions(actualParticion,2);
				listParticiones.replace(actualParticion);
				
			}
			if (actualParticion == lastParticion) {
				System.out.println("LLEGUE A LA ULTIMA");
				System.err.println("ANTES DE VERIFICAR INGRESO ");
				printListAct();
				verifyIngress();
				//verifyIngress(actualParticion);
				System.err.println("DESP DE VERIFICAR INGRESO ");
				printListAct();
				verifyCondensation1(actualParticion);
				System.err.println("DESP DE CONDEN ");
				printListAct();
			}
			cintril++;
			if (cintril == 20) {
				break;
			}
		}
	}

	private void verifyIngress() {
		System.out.println("VERIFICANDO SI ENTRA ALGO");
		MyQueue<Particion> aux = new MyQueue<>();
		MyQueue<Process> auxPendientes = new MyQueue<>();
		while (!queueREADY.isEmpty()) { // RECORREMOS LISTA DE LISTOS
			Particion particion = queueREADY.get(); // TOMAMOS DICHA PARTICION
			System.out.println("Analizando particion " + particion.getNameParticion());
			if (particion.isGap()) {
				System.out.println("Esta vacia");
				System.out.println(queueProcessReady.isEmpty());
				while (!queueProcessReady.isEmpty()) {
					Process processList =  queueProcessReady.get();
					if (processList.getTama?o() < particion.getSizeParticion()) {
						System.out.println("PUEDE INGRESAR "+ processList);
						nuevaParticion();
					}else {
						auxPendientes.put(processList);
						System.out.println("no PUEDE INGRESAR "+ processList);
					}
				}
			}else {
				System.out.println(" No Esta vacia");
				aux.put(particion);

			}
			queueProcessReady = auxPendientes;
			System.err.println(" -: " + particion.toString());
		}
		while (!aux.isEmpty()) {
			queueREADY.put(aux.get());
		}
	}

	private void printListAct() {
		MyQueue<Particion> aux = new MyQueue<>();
		while (!queueREADY.isEmpty()) {
			Particion particion = queueREADY.get();
			System.err.println(" - " + particion.toString());
			aux.put(particion);
		}
		while (!aux.isEmpty()) {
			queueREADY.put(aux.get());
		}
	}
	
	private void verifyCondensation1(Particion actualParticion) {
		System.out.println("VERIFICACION DE CONDENSACION");
		MyQueue<Particion> ultimas = new MyQueue<>();
		MyQueue<Particion> primeras = new MyQueue<>();
		while (!queueREADY.isEmpty()) {
			Particion particion = queueREADY.get();
			while (isFin) {
				
			}
		}
		MyQueue<Particion> particiones = queueREADY;
		MyNode<Particion> aux = listParticiones.getHead();
		MyDoubleList<Particion> aux2 = new MyDoubleList<>();
		while (aux != null) {
			if (aux.getInfo() == actualParticion) {
				MyNode<Particion> previous = getPrevious(aux);
				MyNode<Particion> next = getNext(aux);
				if (previous != null && next != null) {
					System.out.println("ambas");
					if (previous.getInfo().isGap() && next.getInfo().isGap()) {
						Condensacion condensacion = new Condensacion("Con" + numCondensation, previous.getInfo(), aux.getInfo());
						numCondensation++;
						condensacion.agregarTerceraParticion(next.getInfo());
						Particion particion = condensacion.getParticion(varPart);
						queueCondensaciones.put(condensacion);   
						aux2.deleteTail();
						aux2.addElementToTail(particion);
						System.out.println("SE SUPONE QUE YA AGREGO una de 3 " + particion.getNameParticion());
						listPartInitials.addElementToTail(particion);
						queuePartFinished.put(particion);
						aux = aux.getNext().getNext();
						varPart++;
					}else if (previous.getInfo().isGap()) {
						aux = PreviousGap(aux, aux2, previous, 1);
						varPart++;
					}else if (next.getInfo().isGap()) {
						aux = nextGap(aux, aux2, next, 1);
						varPart++;
					}
				}else if (previous != null ) {
					System.out.println("previous");
					if (previous.getInfo().isGap()) {
						aux = PreviousGap(aux, aux2, previous, 1);
						varPart++;
					}
				}else if (next != null ) {                                                                                                                                                                                        { 
					System.out.println("NEXT");
					if (next.getInfo().isGap()) {
						aux = nextGap(aux, aux2, next, 1);
						varPart++;
					}
				}
				}
				if (aux != null) {
					aux2.addElementToTail(aux.getInfo());
					System.out.println("SE SUPONE QUE YA AGREGO a la que no se pudo condensar" + aux.getInfo().getNameParticion());

				}

			}else {
				aux2.addElementToTail(aux.getInfo());
				System.out.println("SE SUPONE QUE YA AGREGO si no se condensa" + aux.getInfo().getNameParticion());
			}
			if (aux != null) {
				aux = aux.getNext();
			}
		}
		listParticiones = aux2;
		fillParticiones(aux2);
		System.out.println("Imprimiendo lista despues de las cond");
		printListAct();
	}

	private MyNode<Particion> saveQueueList(MyQueue<Particion> particiones) {
		MyDoubleList<Particion> part = new MyDoubleList<>();
		while (!particiones.isEmpty()) {
			Particion particion = particiones.get();
			part.addElementToTail(particion);
		}
		return part.getHead();
	}
	private void saveQueue() {
		MyQueue<Process> aux = new MyQueue<>();
		MyNode<Particion> part = listPartInitials.getHead();
		while (part != null) {
			Particion newPartition = part.getInfo();
			queueReadyPartList.put(newPartition);
			listParticiones.addElementToTail(newPartition);
			part = part.getNext();
		}
	}
	private void fillParticiones(MyDoubleList<Particion> partsUse) {
		System.out.println("GUARDANDOOOOOOOOOO");
		MyQueue<Particion> listaAux = new MyQueue<>();
		System.err.println("177"+ queueREADY.isEmpty());
		while (!queueREADY.isEmpty()) {
			System.out.println("READYYY");
			listaAux.put(queueREADY.get());
		}
		while (!listaAux.isEmpty()) {
			System.out.println("FALATABA " + listaAux.get());
		}
		//queueREADY.removeQueue();
		MyNode<Particion> iterator = partsUse.getHead();
		while (iterator != null) {
			System.out.println("toteoooooooooooooooooooo");
			Particion particion = iterator.getInfo();
			queueREADY.put(particion);
			iterator = iterator.getNext();
		}
	}

	private MyNode<Particion> nextGap(MyNode<Particion> aux, MyDoubleList<Particion> aux2,
			MyNode<Particion> next, int n) {
		Condensacion condensacion = new Condensacion("Con" + numCondensation, aux.getInfo(), next.getInfo());
		Particion particion = condensacion.getParticion(varPart);
		if (n == 2) {
			queueReadyPartList.put(particion);
		}	
		numCondensation++;
		queueCondensaciones.put(condensacion);   

		//	listAuxPart.replace(condensacion.getParticion(varPart), aux, next, null);
		aux2.addElementToTail(particion);
		System.out.println("SE SUPONE QUE YA AGREGO a next " + particion.getNameParticion());
		listPartInitials.addElementToTail(particion);
		queuePartFinished.put(particion);
		aux = aux.getNext().getNext();
		return aux;
	}

	private MyNode<Particion> PreviousGap(MyNode<Particion> aux, MyDoubleList<Particion> aux2,
			MyNode<Particion> previous, int n) {
		Condensacion condensacion = new Condensacion("Con" + numCondensation, previous.getInfo(), aux.getInfo());
		Particion particion = condensacion.getParticion(varPart);
		if (n == 2) {
			queueReadyPartList.put(particion);
		}
		numCondensation++;
		queueCondensaciones.put(condensacion); 

		//listAuxPart.replace(condensacion.getParticion(varPart), previous, aux, null);
		aux2.deleteTail();
		aux2.addElementToTail(particion);
		System.out.println("SE SUPONE QUE YA AGREGO a prev " + particion.getNameParticion());
		listPartInitials.addElementToTail(particion);
		queuePartFinished.put(particion);
		if (aux.getNext() != null) {
			aux = aux.getNext();
		}else {
			aux = null;
		}
		return aux;
	}

	private MyNode<Particion> getPrevious(MyNode<Particion> myNode) {
		MyNode<Particion> resp = null;
		MyNode<Particion> iterator = listParticiones.getHead();
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
		MyNode<Particion> iterator = listParticiones.getHead();
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

	/**
	 * viendo haber si se puede ingresar un nuevo proceso
	 * @param actualParticion 
	 */
	private void verifyIngress(Particion actualParticion) {
		System.out.println("Estoy verificando si cabe algun proceso");
		boolean isIngres = false;
		MyQueue<Process> aux = new MyQueue<>();
		if (lastParticion == actualParticion) {
			isFin = true;
		}
		while (!queueProcessReady.isEmpty()) {
			System.out.println("hay listos");
			Process processActual =  queueProcessReady.get();// Tomamos el proceso para compararlo	
			int sizeProcess = processActual.getTama?o();
			while (actualParticion.getSizeParticion() > sizeProcess) {//si se pudo ingresar algo
			isIngres = true;
				Particion particion = createParticion(processActual, -1);
				Particion particionAux = new Particion("act ", actualParticion.getSizeParticion() - particion.getSizeParticion());
				verifyIngress(particionAux);
				System.out.println("guargeee " + particion);
//				if (!isIngres) {
//					endProcessAndParticions(memory.getParticionLast(), 1);
//					memory.removeLast();	
//				}
//				memory.addParticion(particion);
//				varPart++;
//				isIngres = true;
//				System.out.println("Ingrese " + particion.getSizeParticion());
			}
			if (isIngres) {
				createParticion(null, actualParticion.getSizeParticion());			
			}else {
				aux.put(queueProcessReady.get());
			}
				
//			else {
//				System.out.println("No hay espacio, no se ingreso nada");
//				if (isIngres) {
//					System.out.println("sobro " + memory.getSizeLibre());
//					nuevaParticion();
//				}
//				aux.put(processActual); //entonces agregue el proceso actual
//				while (!queueProcessReady.isEmpty()) {
//				}
//			}
//		}
//		while (!aux.isEmpty()) {
//			queueProcessReady.put(aux.get());
//			System.err.println("GUARDANDO " );
//		}
//		if (isIngres) {
//			if (queueProcessReady.isEmpty() && memory.getSizeLibre() > 0) {
//				nuevaParticion();
//			}
		}
		MyQueue<Particion> part = new MyQueue<>();
		queueREADY.put(actualParticion);
		while (queueREADY.get() != actualParticion) {
			
		}
		System.err.println("act " + actualParticion.toString());
	}

	private Particion createParticion(Process processActual, int size) {
		Particion particion = null;
		String namePart = "Part" + varPart;
		if (size == -1) {
			particion = new Particion(namePart, processActual);
		}else {
			particion = new Particion(namePart, size);	
		}
		System.out.println("1. Voy a agregar nueva part "+ particion.toString());
		listPartInitials.addElementToTail(particion);
		queueREADY.put(particion);
		varPart++;
		if (isFin) {
			lastParticion = particion;
			isFin = false;
		}
		return particion;
	}
	private boolean obtenerParticionAcertada(Process process) {
		MyQueue<Particion> aux = obt();
		while (!aux.isEmpty()) {
			Particion particionAUX = aux.get();
			int sizeProcess = process.getTama?o();
			if (particionAUX.isGap() && particionAUX.getSizeParticion() > sizeProcess) {
				int sizeLibre = particionAUX.getSizeParticion() - sizeProcess;
				String namePart = "Part" + varPart;
				Particion particion1 = new Particion(namePart, new Process(process.getNameProcess(),
						process.getTimeProcess(), sizeProcess));
				varPart++;
				if (sizeLibre > 0) {
					Particion particion2 = new Particion(namePart, new Process(process.getNameProcess(),
							process.getTimeProcess(), sizeProcess));
					listPartInitials.addElementToTail(particion1);
					listPartInitials.addElementToTail(particion2);
					//					memory.addParticion(particion1);
					varPart++;
//					listActual.addElementToTail(particion1);
//					listActual.addElementToTail(particion2);
				}else {
					listPartInitials.addElementToTail(particion1);
				}
				return true;
			}
		}
		return false;
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
	private void nuevaParticion() {
		String namePart = "Part" + varPart;
		Particion particion = new Particion(namePart, sizeLibre);
		listPartInitials.addElementToTail(particion);
		particion.setGap(true);
		queueREADY.put(particion);
		lastParticion = particion;
		varPart++;
	}
	
	public MyQueue<Particion> obt() {
		MyQueue<Particion> aux = new MyQueue<>();
		MyQueue<Particion> aux2 = new MyQueue<>();
		while (!queueREADY.isEmpty()) {
			Particion particion = queueREADY.get();
			aux.put(particion);
			aux2.put(particion);
		}
		while (!aux.isEmpty()) {
			queueREADY.put(aux.get());
		}
		return aux2;
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
			if (hayEspacio(processActual.getTama?o())) {
				String namePart = "Part" + varPart;
				Particion particion = new Particion(namePart, processActual);
				listPartInitials.addElementToTail(particion);
				sizeLibre = sizeLibre - processActual.getTama?o();
				queueREADY.put(particion);
				varPart++;
				if (sizeLibre == 0) {
					lastParticion = particion;
				}
			}else {
				if (sizeLibre > 0) {
					nuevaParticion();
					sizeLibre = 0;
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
		createProceso(new Process("P25", 3, 25));
		createProceso(new Process("P15", 2, 15));
		createProceso(new Process("P10", 1, 10));
		createProceso(new Process("P20", 4, 20));
		createProceso(new Process("P30", 5, 30));
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
		reports.generateReport(listPartInitials, queueInitialProcess, queueProcessFinished, queueCondensaciones, queuePartFinished, queueAuxReadyList, "prueba2");
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
			sizeLibre = size;
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
	/**
	 * LOS METODOS NUEVOS
	 */

	public boolean hayEspacio(int tam) {
		if (sizeLibre >= tam ) {
			return true;
		}
		return false;
	}
}
