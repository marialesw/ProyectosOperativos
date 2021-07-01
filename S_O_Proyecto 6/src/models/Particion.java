package models;

import datastructures.MyQueue;

public class Particion {

	private String nameParticion;
	private int tama�oParticion;
	private Process process; 
	private MyQueue<Process> queueExecutionProcess;
	private boolean gap;

	public Particion(String nameParticion, Process process) {
		super();
		this.process = process;
		this.nameParticion = nameParticion;
		this.tama�oParticion = process.getTama�o();
		queueExecutionProcess = new MyQueue<>();
		if (process.getTimeProcess() != 0) {
			gap = false;
		}
	}

	public Particion(String nameParticion2, int totalSize) {
		super();
		this.nameParticion = nameParticion2;
		this.tama�oParticion = totalSize;
	}
	
	private boolean isEmpty() {
		return (process == null)?true:false;
	}

	public boolean isPosible(Process process){
		if (process.getTama�o() <= tama�oParticion) {
			return true;
		}
		return false;
	}

	public void setGap(boolean gap) {
		this.gap = gap;
	}
	
	public void executarProcess() {
		Process processAux = new Process(process.getNameProcess(), process.getTimeProcess(), process.getTama�o() );
		System.out.println("Parti 46 "+ processAux.getTimeProcess());
		queueExecutionProcess.put(processAux);
	}
	public Process getProcess() {
		return process;
	}

	public int getSizeTotalParticion() {
		return tama�oParticion;
	}
	
	public void setTimeProcess(int time) {
		process.setTimeProcess(time);
	}

	public String getNameParticion() {
		return nameParticion;
	}	

	public void setNameParticion(String nameParticion) {
		this.nameParticion = nameParticion;
	}

	public int getTama�oMaximo() {
		return tama�oParticion;
	}

	public void setTama�oMaximo(int tama�oMaximo) {
		this.tama�oParticion = tama�oMaximo;
	}

	public void setExecutionProcess(MyQueue<Process> executionProcess) {
		this.queueExecutionProcess = executionProcess;
	}

	public MyQueue<Process> getQueueExecutionProcess() {
		return queueExecutionProcess;
	}
	
	public int getTimeInitialProcess() {
		int time = 0;
		if (process != null) {
			time = process.getTimeInitial();
		}else {
			System.out.println("No hay proceso");
		}
		return time;
	}
	
	public int getTimeProcess() {
		int time = 0;
		if (process != null) {
			time = process.getTimeProcess();
		}else {
			System.out.println("No hay proceso");
		}
		return time;
	}
	
	public String getNameProcess() {
		String name = "";
		if (process != null) {
			name = process.getNameProcess();
		}else {
			System.out.println("No hay proceso");
		}
		return name;
	}
	public int getSizeProcess() {
		int size = 0;
		if (process != null) {
			size = process.getTama�o();
		}else {
			System.out.println("No hay proceso");
		}
		return size;
	}
	
	public boolean isGap() {
		return gap;
	}
	
	@Override
	public String toString() {
		return " Particion " + nameParticion + "tama�o " + tama�oParticion;
	}
}