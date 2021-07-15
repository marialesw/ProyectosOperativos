package models;

public class Process {

	private String nameProcess; // nombre del proceso
	private int timeProcess; //tiempo de proceso
	private int tamaño; //bloqueado
	private int timeInitial;
	
	public Process(String nameProcess, int timeProcess, int tamaño) {
		super();
		this.nameProcess = nameProcess;
		this.timeProcess = timeProcess;
		this.tamaño = tamaño; 
		timeInitial = timeProcess;
	}

	public String getNameProcess() {
		return nameProcess;
	}

	public void setNameProcess(String nameProcess) {
		this.nameProcess = nameProcess;
	}

	public int getTimeProcess() {
		return timeProcess;
	}

	public void setTimeProcess(int timeProcess) {
		this.timeProcess = timeProcess;
	}

	public int getTamaño() {
		return tamaño;
	}

	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
	}

	public int getTimeInitial() {
		return timeInitial;
	}
	
	@Override
	public String toString() {
		return "nombre " + nameProcess + " Tiempo: " + timeProcess + " tamaño " + tamaño;
	}
}
