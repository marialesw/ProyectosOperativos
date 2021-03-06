package models;

import datastructures.MyQueue;

public class Particion {

	private String nameParticion;
	private int tamaņoParticion;
	private Process process; 
	private MyQueue<Process> queueExecutionProcess;
	private boolean gap;
	private boolean isc;
	private int numCondensed;
	private int dirInitial;
	private int dirFinal;

	public Particion(String nameParticion, Process process) {
		super();
		this.process = process;
		this.nameParticion = nameParticion;
		this.numCondensed = 0;
		this.tamaņoParticion = process.getTamaņo();
		queueExecutionProcess = new MyQueue<>();
		if (process.getTimeProcess() != 0) {
			gap = false;
		}
	}

	public boolean isIsc() {
		return isc;
	}

	public int getDirInitial() {
		return dirInitial;
	}

	public void setDirInitial(int dirInitial) {
		this.dirInitial = dirInitial;
	}

	public int getDirFinal() {
		return dirFinal;
	}

	public void setDirFinal(int dirFinal) {
		this.dirFinal = dirFinal;
	}

	public void setIsc(boolean isc) {
		this.isc = isc;
	}


	public Particion(String nameParticion2, int totalSize) {
		super();
		this.nameParticion = nameParticion2;
		this.tamaņoParticion = totalSize;
		this.gap = true;
	}
	
	public void setNumCondensed(int numCondensed) {
		this.numCondensed = numCondensed;
	}
	public int getNumCondensed() {
		return numCondensed;
	}
	
	private boolean isEmpty() {
		return (process == null)?true:false;
	}

	public boolean isPosible(Process process){
		if (process.getTamaņo() <= tamaņoParticion) {
			return true;
		}
		return false;
	}

	public void setGap(boolean gap) {
		this.gap = gap;
	}
	
	public void executarProcess() {
		Process processAux = new Process(process.getNameProcess(), process.getTimeProcess(), process.getTamaņo() );
		queueExecutionProcess.put(processAux);
	}
	public Process getProcess() {
		return process;
	}

	public int getSizeTotalParticion() {
		return tamaņoParticion;
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

	public int getTamaņoMaximo() {
		return tamaņoParticion;
	}

	public void setTamaņoMaximo(int tamaņoMaximo) {
		this.tamaņoParticion = tamaņoMaximo;
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
		}
		return time;
	}
	
	public int getTimeProcess() {
		int time = -1;
		if (process != null) {
			time = process.getTimeProcess();
		}
		return time;
	}
	
	public String getNameProcess() {
		String name = "";
		if (process != null) {
			name = process.getNameProcess();
		}
		return name;
	}
	public int getSizeProcess() {
		int size = 0;
		if (process != null) {
			size = process.getTamaņo();
		}
		return size;
	}
	
	public int getSizeParticion() {
		return tamaņoParticion;
	}
	public boolean isGap() {
		return gap;
	}
	
	@Override
	public String toString() {
		return "[ " + nameParticion + " tamaņo " + tamaņoParticion +
				" Dir. inicial " + dirInitial + " Dir. final " + dirFinal + " ]";	
	}
}