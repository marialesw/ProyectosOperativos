package models;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;

public class Manager {
	private static final String DUPLICATE_PROCESS_EXCEPTION = "El proceso ya existe, por favor ingrese un nuevo nombre";
//	private static final int CPU_ATTENTION_TIME = 5;
	private Queue<MyProcess> processes;
	private LinkedList<Partition> partitions; //tiene las activas
	
//	private ArrayList<ArrayList<Partition>> condensaciones; //agrego las particiones de cada iteracion según como se condensen
	
//	Reportes 
	private GeneratePDF pdf;
	private PdfPTable processInTable;  //procesos ingresados por el usuario, es la misma cola de listos
	private PdfPTable duplicateProcessTable; //procesos que no se agregaron porque ya existian 
	private PdfPTable removedProcessTable;
//	private PdfPTable condensations; //Condensaciones
	private LinkedList<Partition> condensations;
	private PdfPTable generalProcessFinalized; // general de procesos finalizados
	private PdfPTable generalPartitionFinalized; // general de procesos finalizados
	
	private int condensationsNumber = 0;
	
	public Manager() throws FileNotFoundException, DocumentException {
		processes =new LinkedList<>();
		partitions = new LinkedList<>();
		condensations = new LinkedList<>();
		pdf = new GeneratePDF();
		
		processInTable = new PdfPTable(3);
		processInTable.addCell("NOMBRE");
		processInTable.addCell("TIEMPO");
		processInTable.addCell("TAMAÑO");
		
		duplicateProcessTable = new PdfPTable(3);
		duplicateProcessTable.addCell("NOMBRE");
		duplicateProcessTable.addCell("TIEMPO");
		duplicateProcessTable.addCell("TAMAÑO");
		
		removedProcessTable = new PdfPTable(3);
		removedProcessTable.addCell("NOMBRE");
		removedProcessTable.addCell("TIEMPO");
		removedProcessTable.addCell("TAMAÑO");
		
//		condensations = new PdfPTable(3);
//		generalReadyQueue.addCell("NOMBRE");
//		generalReadyQueue.addCell("TIEMPO");
//		generalReadyQueue.addCell("TAMAÑO");
		
		generalProcessFinalized = new PdfPTable(3);
		generalProcessFinalized.addCell("NOMBRE");
		generalProcessFinalized.addCell("TIEMPO");
		generalProcessFinalized.addCell("TAMAÑO");
		
		generalPartitionFinalized = new PdfPTable(2);
		generalPartitionFinalized.addCell("NOMBRE");
		generalPartitionFinalized.addCell("TAMAÑO");
	}
	
	public void addProcess(MyProcess process) throws Exception{
		if(searchProcess(process)==null) {
			 processes.add(process);
		}else {
			duplicateProcessTable.addCell(process.getName());
			duplicateProcessTable.addCell(String.valueOf(process.getTime()));
			duplicateProcessTable.addCell(String.valueOf(process.getSize()));
			throw new Exception(DUPLICATE_PROCESS_EXCEPTION);
		}
	}
	
	public MyProcess searchProcess(MyProcess processToSearch) {
		MyProcess process = null;
		for (MyProcess myProcess : processes) {
			if(myProcess.getName().equalsIgnoreCase(processToSearch.getName())) {
				process = myProcess;
			}
		}
		return process;
	}
	
	public void deleteProcess(MyProcess process) {
		processes.remove(process);
		removedProcessTable.addCell(process.getName());
		removedProcessTable.addCell(String.valueOf(process.getTime()));
		removedProcessTable.addCell(String.valueOf(process.getSize()));
	}

	public void updateProcess(MyProcess processToUpdate) {
		MyProcess processFound = searchProcess(processToUpdate);
		processFound.setSize(processToUpdate.getSize());
		processFound.setTime(processToUpdate.getTime());
	}
	
	public Queue<MyProcess> getProcesses() {
		return processes;
	}
	
	public void clean(){
		processes.clear();
	}

	public void saveDataIn( int memorySizeIn) throws FileNotFoundException, DocumentException {
		for (MyProcess process : processes) {
			processInTable.addCell(process.getName());
			 processInTable.addCell(String.valueOf(process.getTime()));
			 processInTable.addCell(String.valueOf(process.getSize()));
		}
		pdf.writeDataInputInPDF(processInTable, duplicateProcessTable, removedProcessTable);
	}
	
	int iPartition = 1;
	int memorySize = 0;
	public void simulate(int memory) {
		memorySize = memory;
		if(partitions.isEmpty()){ //crea las particiones iniciales
			int counterMemory = 0;
			for (MyProcess process : processes) {
				if((counterMemory+process.getSize())<=memory){
					Partition p = new Partition("part " + iPartition, process.getSize());
					process.setAttended(true);
					p.setProcess(process);
					partitions.add(p);
					iPartition+=1;
					counterMemory += process.getSize();
					
//					processEntryOrder.addCell(process.getName());
//					processEntryOrder.addCell(String.valueOf(p.getProcess().getTime()));
//					processEntryOrder.addCell(String.valueOf(p.getProcess().getSize()));
				}
			}
			if(counterMemory!=memory){ //crea una particion si uedo sobrando espacio
				Partition p = new Partition("part " + iPartition, (memory-counterMemory));
				partitions.add(p);
				iPartition +=1;
				counterMemory+= p.getSize();
			}
		}
		validateIterationWithMemory(partitions); 
	}
	
	
//	int iCounter = 1;
	private void validateIterationWithMemory(LinkedList<Partition> partitionsIn) {
		System.out.println("----------------------------------------");
		LinkedList<Partition> temp = new LinkedList<>();
		
		if(partitionsIn.size() ==1){ 
			generalPartitionFinalized.addCell(partitionsIn.get(0).getName());
			generalPartitionFinalized.addCell(String.valueOf(partitionsIn.get(0).getSize()));
			
			System.out.println("157 " + partitionsIn.get(0));
			boolean hayQueAtender = faltaAlgunoPorAtender();
//			addCompactation(partitionsIn.get(0));
			if(hayQueAtender){
				LinkedList<Partition> losQueCaben = revisarLosQueCabenEnEsaParticion(partitionsIn.get(0));
				if(!losQueCaben.isEmpty()){
					temp.addAll(losQueCaben);
				}
			}
				
		}else{
			int i = 0;
			while (i < partitionsIn.size()) {
				Partition p = partitionsIn.get(i); // voy a analizar
				System.out.println("ANALI " + p);
				if(p.getProcess().getTime()==0){ //esta en 0 y hay un siguiente 
						int cuantosSeCondensan = verificoCuantosSeCondensan(partitionsIn, i); //después del siguiente
						if(cuantosSeCondensan<2){ //cuando se tienen 2 entonces hay condensación sino 1 no se puede condesar con el mismo
							//no hay condensación 
							LinkedList<Partition> losQueCaben = revisarLosQueCabenEnEsaParticion(p);
							if(losQueCaben.isEmpty()){
								temp.add(p);
							}else{
//								System.out.println("Hay nuevas  : " + losQueCaben.size());
								temp.addAll(losQueCaben);
							}
						}else{
							
							System.out.println("Si hay condensación");
							Partition condensation = new Partition("part " + iPartition);
							
							for (int j = i; j < (i+cuantosSeCondensan); j++) {
								Partition antecesor = partitionsIn.get(j);
								System.out.println(antecesor);
//								System.out.println("jjjj               " + antecesor);
								condensation.setSize(condensation.getSize()+antecesor.getSize());
								antecesor.setCondensed(true);
								condensation.addAntecesor(antecesor);
//								
								generalPartitionFinalized.addCell(antecesor.getName());
								generalPartitionFinalized.addCell(String.valueOf(antecesor.getSize()));
							}
							condensations.add(condensation);
							temp.add(condensation);
							iPartition+=1;
							i+=(cuantosSeCondensan-1);
							
						}
				}
				else if(p.getProcess().getTime()>0){
					p.getProcess().setTime(p.getProcess().getTime()-1);
					if(p.getProcess().getTime()==0){ //aqui todavia no se debe revisar la cola porque hasta ahora quda en 0
						generalProcessFinalized.addCell(p.getProcess().getName());
						generalProcessFinalized.addCell(String.valueOf(p.getProcess().getTime()));
						generalProcessFinalized.addCell(String.valueOf(p.getProcess().getSize()));
					}
					temp.add(p);
				}
				else{
					System.out.println("No deberia entrar acá");
				}
				i++;
			}
		}
		if(!temp.isEmpty() ){
			validateIterationWithMemory(temp);
		}
	}

	private boolean faltaAlgunoPorAtender() {
		boolean faltaAlguno = false;
		for (MyProcess process : processes) {
			if(!process.isAttended()){
				faltaAlguno = true;
			}
		}
		return faltaAlguno;
	}
	
	private int verificoCuantosSeCondensan(LinkedList<Partition> partitionsIn, int next) {
		int i = next;
		
		int counter = 0;
		boolean wasZero = true;
		while(i<partitionsIn.size() &&wasZero==true){
			Partition actual = partitionsIn.get(i);
			if(actual.getProcess().getTime()==0){
				counter+=1;
					i++;
			}else{
				wasZero =false;
			}
		}
		return counter;
	}

	private LinkedList<Partition> revisarLosQueCabenEnEsaParticion(Partition p) {
//		System.out.println("Tamanio de la que entra " + p.getName() + " " + p.getSize());
		LinkedList<Partition> newPartitions = new LinkedList<>();
		int sizeFreeToAdd = p.getSize(); //valida los que se pueden agregar
//		System.out.println("sizeFree" + sizeFreeToAdd);
		for (MyProcess process : processes) { //recorre la cola de listos
			if(!process.isAttended()){
				if(process.getSize()<=sizeFreeToAdd){
					if(newPartitions.isEmpty()){
//					addPartititionFinalized(p);
					
					Partition newPartition = new Partition("part " + iPartition, process);
					process.setAttended(true);
					sizeFreeToAdd -= newPartition.getSize();
					iPartition +=1;
					newPartitions.add(newPartition);
					
//					processEntryOrder.addCell(process.getName());
//					processEntryOrder.addCell(String.valueOf(process.getTime()));
//					processEntryOrder.addCell(String.valueOf(process.getSize()));
					}
				}
			}
		}
		if(newPartitions.size()>0 && sizeFreeToAdd<p.getSize()){
			Partition pWithSobrante = new Partition("part " + iPartition, sizeFreeToAdd);
			iPartition+=1;
			newPartitions.add(pWithSobrante);
		}
		return newPartitions;
	}
	
//	Condensando de a dos
//	private void validateIteration(LinkedList<Partition> partitionsIn) {
//		LinkedList<Partition> temp = new LinkedList<>();
////		if(partitionsIn.size()>0){
//		int i = 0;
//		System.out.println("----------------------------------------");
//		while (i < partitionsIn.size()) {
//			Partition p = partitionsIn.get(i);
////				System.out.println(p);
//			Partition next = new Partition("null");
//			if((i+1)< partitionsIn.size()){
//				next = partitionsIn.get(i+1);
//			}
//			
//			if(p.getProcess().getTime()==0 && !next.getName().equals("null")){ //esta en 0 y hay un siguiente 
////					System.out.println("Hay un 0 y un siguiente");
//				if(next.getProcess().getTime()>0){
////						System.out.println("mayor a 0, entonces agrega " + p);
//					temp.add(p);
//				}else{
////						if(!p.isCondensed() && !next.isCondensed()){
//					Partition condensation = new Partition("part " + iPartition, (p.getSize()+next.getSize()));
//					p.setCondensed(true);
//					next.setCondensed(true);
//					/**Agrego al reporte las 2 particiones que terminaron**/
//					generalPartitionFinalized.addCell(p.getName());
//					generalPartitionFinalized.addCell(String.valueOf(p.getSize()));
//					
//					generalPartitionFinalized.addCell(next.getName());
//					generalPartitionFinalized.addCell(String.valueOf(next.getSize()));
////							System.out.println("que es 0, entonces hay condensación  " + condensation);
//					condensation.addAntecesor(p); //agrega los antecesores de la condensación para el reporte
//					condensation.addAntecesor(next);
//					condensations.add(condensation); //agrega a la lista pra el reporte
//					temp.add(condensation);
//					condensationsNumber +=1;
//					iPartition+=1;
//					
//					i++;//aumento una posición porque condense 2 y al final se aumenta la otra
//				}
//			}
//			else if(p.getProcess().getTime()==0 && next.getName().equals("null")){
//				if(!p.isCondensed() && partitionsIn.size()>1){
//					temp.add(p);
//				}else{
//					generalPartitionFinalized.addCell(p.getName());
//					generalPartitionFinalized.addCell(String.valueOf(p.getSize()));
//				}
//			}
//			else if(p.getProcess().getTime()>0){
//				p.getProcess().setTime(p.getProcess().getTime()-1);
//				if(p.getProcess().getTime()==0){
//					generalProcessFinalized.addCell(p.getProcess().getName());
//					generalProcessFinalized.addCell(String.valueOf(p.getProcess().getTime()));
//					generalProcessFinalized.addCell(String.valueOf(p.getProcess().getSize()));
//				}
//				temp.add(p);
//			}
//			i++;
//		}
//		if(!temp.isEmpty()){
//			validateIteration(temp);
//		}
//	}
	
//	public void simulateWithMemoryRestriction(int memory) {
//		if(partitions.isEmpty()){ //crea las particiones iniciales
//			int counterMemory = 0;
//			for (MyProcess process : processes) {
//				if((counterMemory+process.getSize())<=memory){
//					Partition p = new Partition("part " + iPartition, process.getSize());
//					process.setAttended(true);
//					p.setProcess(process);
//					partitions.add(p);
//					iPartition +=1;
//					counterMemory += process.getSize();
//				}
//			}
//			if(counterMemory!=memory){
//				Partition p = new Partition("part " + iPartition, (memory-counterMemory));
//				partitions.add(p);
//				iPartition +=1;
//				counterMemory+= p.getSize();
//			}
//		}
//		validateIterationWithMemory(partitions); 
//	}

//	private void validateIterationWithMemory(LinkedList<Partition> partitionsIn) {
//		LinkedList<Partition> temp = new LinkedList<>();
//		//		if(partitionsIn.size()>0){
//		int i = 0;
//		System.out.println("---------------------*-------------------");
//		while (i < partitionsIn.size()) {
//			Partition p = partitionsIn.get(i);
//			System.out.println(p);
//			Partition next = new Partition("null"); //para validar si hay un sisguiente
//			if((i+1)< partitionsIn.size()){
//				next = partitionsIn.get(i+1);
//			}
//			if(p.getProcess().getTime()==0 && !next.getName().equals("null")){ //esta en 0 y hay un siguiente 
//				if(next.getProcess().getTime()>0){
//					MyProcess pToAdd = algunoCabe(p.getSize()); //revisa la cola de listos a ver si hay alguno que quepa en el hueco que quedo 
//					if(pToAdd!=null){ //si hay un proceso por atender y cabe en la particion 
//						if(pToAdd.getSize()==p.getSize()){
//							p.setProcess(pToAdd); //agregar a una tabla de procesos atendidos por una partición
//							temp.add(p);
//						}else{
//							Partition pedazoUsado = new Partition("part " + iPartition, pToAdd);
//							iPartition++;
//							temp.add(pedazoUsado);
//							Partition pedazoRestante = new Partition("part " + iPartition, p.getSize()-pedazoUsado.getSize());
//							iPartition++;
//							temp.add(pedazoRestante);
//						}
////						se asigna pero toca revisar si quedo hueco para armar la otra particion
//					}else{
////					System.out.println("mayor a 0, entonces agrega " + p);
//					temp.add(p);
//					}
//				}else{
//					Partition condensation = new Partition("part " + iPartition, (p.getSize()+next.getSize()));
//					p.setCondensed(true);
//					next.setCondensed(true);
//					/**Agrego al reporte las 2 particiones que terminaron**/
//					generalPartitionFinalized.addCell(p.getName());
//					generalPartitionFinalized.addCell(String.valueOf(p.getSize()));
//					
//					generalPartitionFinalized.addCell(next.getName());
//					generalPartitionFinalized.addCell(String.valueOf(next.getSize()));
////					System.out.println("que es 0, entonces hay condensación  " + condensation);
//					condensation.addAntecesor(p); //agrega los antecesores de la condensación para el reporte
//					condensation.addAntecesor(next);
//					condensations.add(condensation); //agrega a la lista pra el reporte
//					temp.add(condensation);
//					condensationsNumber +=1;
//					iPartition+=1;
//					
////					i++;//aumen
//				}
//			}else if(p.getProcess().getTime()==0 && next.getName().equals("null")){ //hay un 0 y es el último 
//				if(!p.isCondensed() && partitionsIn.size()>1){
//					temp.add(p);
//				}else{
//					generalPartitionFinalized.addCell(p.getName());
//					generalPartitionFinalized.addCell(String.valueOf(p.getSize()));
//				}
//			}
//			else if(p.getProcess().getTime()>0){
//				p.getProcess().setTime(p.getProcess().getTime()-1);
//				//				if(p.getProcess().getTime()==0){
//				//					generalProcessFinalized.addCell(p.getProcess().getName());
//				//					generalProcessFinalized.addCell(String.valueOf(p.getProcess().getTime()));
//				//					generalProcessFinalized.addCell(String.valueOf(p.getProcess().getSize()));
//				//				}
//				System.out.println("kelly pero wtf: "+p);
//				temp.add(p);
//			}
//			i++;
//		}
//		if(!temp.isEmpty()){
//			validateIterationWithMemory(temp);
//		}
//	}
//
//	private MyProcess algunoCabe(int size) {
//		for (MyProcess process : processes) {
//			if(!process.isAttended()&&process.getSize()<=size){
//				return process;
//			}
//		}
//		return null;
//	}

	public void writeSimulationDataAndCloseDocument() throws DocumentException {
		pdf.writeSimulationData(partitions, condensations, condensationsNumber, generalProcessFinalized, generalPartitionFinalized);
		pdf.closeDocument();
	}
	
	public void openPdf() throws Exception {
		try {
			Runtime.getRuntime().exec ("rundll32 SHELL32.DLL,"
					+ "ShellExec_RunDLL " + "E:\\SO-SW-08-PRIMER-AJUSTE\\Reportes.pdf");
		} catch (Exception e) {
			throw new Exception("No se puede abrir el archivo");
		}
	}
	
}
