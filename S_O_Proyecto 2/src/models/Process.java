package models;

import java.util.ArrayList;

public class Process {

	private String nameProcess; // nombre del proceso
	private int timeProcess; //tiempo de proceso
	private boolean locked; //bloqueado
	private boolean layOff; //suspendido
	private int priorityProcess; //prioridad del proceso
	private boolean isDestroyed; //si se destruye el proceso
	private int timeDestroy; //tiempo en el que se destruye el proceso 
	private ArrayList<String> comunicationsList; // lista de procesos con los que se va a comunicar dicho proceso

	public Process(String nameProcess, int timeProcess, boolean locked, boolean layOff, boolean isDestroyed, int timeDestroy, int priorityProcess, ArrayList<String> comunicationsList) {
		super();
		this.nameProcess = nameProcess;
		this.timeProcess = timeProcess;
		this.locked = locked;
		this.layOff = layOff;
		this.priorityProcess = priorityProcess;
		this.isDestroyed = isDestroyed;
		this.timeDestroy = timeDestroy;
		this.comunicationsList = comunicationsList;
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

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLayOff() {
		return layOff;
	}

	public void setLayOff(boolean layOff) {
		this.layOff = layOff;
	}

	public int getPriorityProcess() {
		return priorityProcess;
	}

	public void setPriorityProcess(int priorityProcess) {
		this.priorityProcess = priorityProcess;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public int getTimeDestroy() {
		return timeDestroy;
	}

	public void setTimeDestroy(int timeDestroy) {
		this.timeDestroy = timeDestroy;
	}

	public ArrayList<String> getComunicationsList() {
		return comunicationsList;
	}

	public void setComunicationsList(ArrayList<String> comunicationsList) {
		this.comunicationsList = comunicationsList;
	}

	@Override
	public String toString() {
		return "nombre " + nameProcess + " Prioridad: " + priorityProcess + " tiempo restante " + timeProcess;
	}
}
