package models;

import com.itextpdf.text.pdf.PdfPTable;

public class Partition{
	private static final int CPU_ATTENTION_TIME = 5;
	private String name;
	private int size;
	
	private MyProcess process;
	private PdfPTable readyQueue;
	private PdfPTable cpuQueue;
	private PdfPTable finalized;
	
	private PdfPTable antecesores;
	private boolean isCondensed; //deteermina si la particón esta activa
	
//	private int timeSpent; //el tiempo de los procesos que se han ejecutado aca
	
	public Partition(String name, int size) {
		this.name = name;
		this.size = size;
		this.isCondensed = false;
		antecesores = new PdfPTable(2);
		antecesores.addCell("NOMBRE");
		antecesores.addCell("TAMAÑO");
		
		readyQueue = new PdfPTable(3);
		readyQueue.addCell("NOMBRE");
		readyQueue.addCell("TIEMPO");
		readyQueue.addCell("TAMAÑO");
		
		cpuQueue = new PdfPTable(3);
		cpuQueue.addCell("NOMBRE");
		cpuQueue.addCell("TIEMPO");
		cpuQueue.addCell("TAMAÑO");
		
		finalized = new PdfPTable(3);
		finalized.addCell("NOMBRE");
		finalized.addCell("TIEMPO");
		finalized.addCell("TAMAÑO");
		process = new MyProcess("", 0, 0);
	}

	public Partition(String name, MyProcess process) {
		this.name = name;
		this.size = process.getSize();
		this.isCondensed = false;
		antecesores = new PdfPTable(2);
		antecesores.addCell("NOMBRE");
		antecesores.addCell("TAMAÑO");
		
		readyQueue = new PdfPTable(3);
		readyQueue.addCell("NOMBRE");
		readyQueue.addCell("TIEMPO");
		readyQueue.addCell("TAMAÑO");
		
		cpuQueue = new PdfPTable(3);
		cpuQueue.addCell("NOMBRE");
		cpuQueue.addCell("TIEMPO");
		cpuQueue.addCell("TAMAÑO");
		
		finalized = new PdfPTable(3);
		finalized.addCell("NOMBRE");
		finalized.addCell("TIEMPO");
		finalized.addCell("TAMAÑO");
		this.process = process;
	}
	
	public Partition(String name) {
		this.name = name;
		this.size = 0;
		this.isCondensed = false;
		antecesores = new PdfPTable(2);
		antecesores.addCell("NOMBRE");
		antecesores.addCell("TAMAÑO");
		
		readyQueue = new PdfPTable(3);
		readyQueue.addCell("NOMBRE");
		readyQueue.addCell("TIEMPO");
		readyQueue.addCell("TAMAÑO");
		
		cpuQueue = new PdfPTable(3);
		cpuQueue.addCell("NOMBRE");
		cpuQueue.addCell("TIEMPO");
		cpuQueue.addCell("TAMAÑO");
		
		finalized = new PdfPTable(3);
		finalized.addCell("NOMBRE");
		finalized.addCell("TIEMPO");
		finalized.addCell("TAMAÑO");
		process = new MyProcess("", 0, 0);
	}
	
	public void addreadyQueue(MyProcess process){
		//entra a cola de listos de la partición
		readyQueue.addCell(process.getName());
		readyQueue.addCell(String.valueOf(process.getTime()));
		readyQueue.addCell(String.valueOf(process.getSize()));
		//se despacha (pero no estoy reportando transiciones)
		//la partición atiende el proceso
	}
	
	
	public void setName(String name) {
		this.name = name;
	}

	public void addCpuQueue(MyProcess process){
		if(process.getTime()<CPU_ATTENTION_TIME){
			process.setTime(0);
		}else{
//			timeSpent+= CPU_ATTENTION_TIME;
			process.setTime(process.getTime()-CPU_ATTENTION_TIME);
		}
		cpuQueue.addCell(process.getName());
		cpuQueue.addCell(String.valueOf(process.getTime()));
		cpuQueue.addCell(String.valueOf(process.getSize()));
		//para que no siga recorriendo las otras particiones
	}
	
	public PdfPTable getReadyQueue() {
		return readyQueue;
	}
	
	public PdfPTable getCpuQueue(){
		return cpuQueue;
	}

	public MyProcess getProcess() {
		return process;
	}

	public void setProcess(MyProcess process) {
		this.process = process;
	}
	
	public void addAntecesor(Partition partition){
//		size += partition.getSize();
		antecesores.addCell(partition.getName());
		antecesores.addCell(String.valueOf(partition.getSize()));
	}

	public PdfPTable getAntecesores() {
		return antecesores;
	}

	public void addFinalized(MyProcess process){
		finalized.addCell(process.getName());
		finalized.addCell(String.valueOf(process.getTime()));
		finalized.addCell(String.valueOf(process.getSize()));
	}
	
	public PdfPTable getFinalizedTable(){
		return finalized;
	}
	
	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setCondensed(boolean b) {
		this.isCondensed = b;
	}

	public boolean isCondensed() {
		return isCondensed;
	}

	@Override
	public String toString() {
		return name + "  " + process.getName() + " -> " + size + "  " + process.getTime();
	}
}
