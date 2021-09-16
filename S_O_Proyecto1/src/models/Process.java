package models;

public class Process {

	private String nameProcess;
	private int timeProcess;
	private boolean locked;
	

	public Process(String nameProcess, int timeProcess, boolean locked) {
		super();
		this.nameProcess = "Proceso " + nameProcess;
		this.timeProcess = timeProcess;
		this.locked = locked;
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
	@Override
	public String toString() {
		return nameProcess;
	}
	
}
