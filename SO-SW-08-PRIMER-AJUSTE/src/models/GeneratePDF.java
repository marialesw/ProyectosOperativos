package models;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF {
private Document documento;
	
	public GeneratePDF() throws DocumentException, FileNotFoundException{
		FileOutputStream ficheroPDF = new FileOutputStream("./Reportes.pdf");
		documento = new Document();
		PdfWriter.getInstance(documento, ficheroPDF);
		documento.open();

	}
	
	public void writeDataInputInPDF(PdfPTable processAdded, PdfPTable duplicateProcesses, PdfPTable removedProcesses) throws FileNotFoundException, DocumentException {
		documento.add(createTitle("Reporte de datos ingresados"));
		documento.add(getDate());
		if(processAdded.size()>1){
		documento.add(createSubTitle("Procesos ingresados exitosamente"));
		documento.add(processAdded);
		}
		if(duplicateProcesses.size()>1){
			documento.add(createSubTitle("Procesos no agregados por nombre duplicado"));
			documento.add(duplicateProcesses);
		}   
		if(removedProcesses.size()>1){
			documento.add(createSubTitle("Procesos eliminados"));
			documento.add(removedProcesses);
		}
    }
	
	public void writeSimulationData(LinkedList<Partition> partitions, LinkedList<Partition> condensations, int totalCondensations, PdfPTable generalProcessFinalized, PdfPTable generalPartitionFinalized) throws DocumentException {
		documento.add(createTitle("Reportes de la simulación"));
		
//		documento.add(createSubTitle("Procesos de cada partición"));
//		 for (Partition partition : condensations.getFirst().getPartitions()) {
//			documento.add(createSubTitle("->  " + partition.getName()));
//			if(partition.getReadyQueue().size()>1){
//			documento.add(createSubTitleTwoLvl("Cola de listos " + partition.getName()));
//			documento.add(partition.getReadyQueue());
//			 }else{
//				 PdfPTable table = new PdfPTable(1);
//				 table.addCell("Ningun proceso llego a la cola de listos de la " + partition.getName());
//			 }
//			 
//			if(partition.getCpuQueue().size()>1){
//			documento.add(createSubTitleTwoLvl("Cola de ejecución " + partition.getName()));
//			documento.add(partition.getCpuQueue());
//			}else{
//				PdfPTable table = new PdfPTable(1);
//				table.addCell("Ningun proceso se ejecuto en la " + partition.getName());
//				documento.add(table);
//			}
//			
//			documento.add(createSubTitleTwoLvl("Finalizados de la " + partition.getName()));
//			if(partition.getFinalizedTable().size()>1){
//			documento.add(partition.getFinalizedTable());
//			}else{
//				PdfPTable table = new PdfPTable(1);
//				table.addCell("Ningun proceso finalizó en la " + partition.getName());
//				documento.add(table);
//			}
//		}
		documento.add(createSubTitle("Procesos ejecutados en cada partición"));
		PdfPTable partitionsTable = new PdfPTable(3);
		partitionsTable.addCell("PARTICIÓN");
		partitionsTable.addCell("TAMAÑO");
		partitionsTable.addCell("PROCESO");
		for (Partition partition : partitions) {
			partitionsTable.addCell(partition.getName());
			partitionsTable.addCell(String.valueOf(partition.getSize()));
			partitionsTable.addCell(partition.getProcess().getName());
		}
		documento.add(partitionsTable);
		 
		 documento.add(createSubTitle("Total de condensaciones:  " + totalCondensations));
			
			int i = 1; //número de condensación
			for (Partition condensation : condensations) {
//				if(condensation.getHuboCondensacion()){
					documento.add(createSubTitle("Condensación " + i ));
//					for (Partition part : condensation.getPartitions()) {
						documento.add(createSubTitleTwoLvl(condensation.getName() + "        Tamaño: "+ condensation.getSize()));
						documento.add(condensation.getAntecesores());
//					}
					i++;
//				}
			}
		 
		documento.add(createSubTitle("Orden de finalización de los procesos"));
		documento.add(generalProcessFinalized);
		documento.add(createSubTitle("Orden de finalización de las particiones"));
		documento.add(generalPartitionFinalized);
	}
	
	
	public void closeDocument(){
		documento.close();
	}
	
	private Paragraph createTitle(String tittle) {
		Paragraph titulo = new Paragraph(tittle + " \n\n",
                FontFactory.getFont("arial",
                        22,
                        Font.BOLD,
                        BaseColor.RED
                        )
        );
		return titulo;
	}
	
	private Paragraph createSubTitle(String tittle) {
		Paragraph titulo = new Paragraph(tittle + " \n\n",
				FontFactory.getFont("arial",
						17,
						Font.BOLD,
						BaseColor.BLUE
						)
				);
		return titulo;
	}
	
	private Paragraph createSubTitleTwoLvl(String tittle) {
		Paragraph titulo = new Paragraph(tittle + " \n\n",
				FontFactory.getFont("arial",
						15,
						Font.BOLD,
						BaseColor.GRAY
						)
				);
		return titulo;
	}
	
	private Paragraph getDate() {
		Paragraph date = new Paragraph("Generado el: " + LocalDate.now() + " a las " + LocalTime.now() + "\n\n" ,
                FontFactory.getFont("arial",
                        16,
                        Font.BOLD,
                        BaseColor.BLACK
                        )
        );
		return date;
	}

	
}
