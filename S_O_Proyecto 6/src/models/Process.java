package models;

public class Process {

	private String nameProcess; // nombre del proceso
	private int timeProcess; //tiempo de proceso
	private int tama�o; //bloqueado
	private int timeInitial;
	
	public Process(String nameProcess, int timeProcess, int tama�o) {
		super();
		this.nameProcess = nameProcess;
		this.timeProcess = timeProcess;
		this.tama�o = tama�o; 
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

	public int getTama�o() {
		return tama�o;
	}

	public void setTama�o(int tama�o) {
		this.tama�o = tama�o;
	}

	public int getTimeInitial() {
		return timeInitial;
	}
	
	@Override
	public String toString() {
		return "nombre " + nameProcess + " Tiempo: " + timeProcess + " tama�o " + tama�o;
	}
}
