package controllers;

import java.io.FileOutputStream;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;



import models.MyQueue;
import models.Process;

public class Report {

	private static final int TIME_EXECUTION = 5;

	public Report() {
		// TODO Auto-generated constructor stub
	}


	public void generateReport(MyQueue<Process> initialQueue,  MyQueue<Process> readyQueue, MyQueue<Process> executionQueue, MyQueue<Process> queueDestroy,
			MyQueue<Process> lockedQueue, MyQueue<Process> layQueue, MyQueue<String> comunicationQueue, MyQueue<Process> finishQueue, String name) {
		Document document = new Document();
		try {
			//	\OperationsOnProcesses\files\
			PdfWriter.getInstance(document, new FileOutputStream(("files/" + name + ".pdf")));
			//			Image header = Image.getInstance("D:\\asus\\Desktop\\PROGRAMACION III V\\ProcessSO\\images\\img.jpg");
			//			header.scaleToFit(400,1000);
			//			header.setAlignment(Chunk.ALIGN_CENTER);

			Paragraph parrafo1 = new Paragraph();
			parrafo1.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo1.add("UNIVERSIDAD PEDAGÓGICA Y TECNOLÓGICA DE COLOMBIA \n\n");
			parrafo1.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo1.add("Reporte creado para simular la ejecución de procesos que tiene un Sistema Operativo \n\n");
			parrafo1.add("\n\n PROCESOS INICIALES \n\n");

			document.open();
			PdfPTable tabla = new PdfPTable(2);
			PdfPTable tablaI = new PdfPTable(2);
			PdfPTable tabla2 = new PdfPTable(4);
			PdfPTable tabla3 = new PdfPTable(2);
			PdfPTable tabla4 = new PdfPTable(1);
			PdfPTable tabla5 = new PdfPTable(1);
			PdfPTable tabla6 = new PdfPTable(1);
			PdfPTable tabla7 = new PdfPTable(1);


			reportInitial(initialQueue, name, tabla);
			
			Paragraph parrafoI = new Paragraph();
			parrafoI.setAlignment(Paragraph.ALIGN_CENTER);
			parrafoI.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafoI.add("\n\n REPORTE DE LISTOS \n\n");

			tablaI.addCell("Nombre del proceso");
			tablaI.addCell("Tiempo restante ");
			while(!readyQueue.isEmpty()) {
				Process process = readyQueue.get();
				tablaI.addCell(process.getNameProcess());
				tablaI.addCell("" + process.getTimeProcess());
			}

			Paragraph parrafo2 = new Paragraph();
			parrafo2.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo2.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo2.add("\n\n REPORTE DE EJECUCION \n\n");
			tabla2.addCell("Nombre del proceso");
			tabla2.addCell("Tiempo inicial ");
			tabla2.addCell("Tiempo final ");
			tabla2.addCell("Tiempo de ejecución ");

			while(!executionQueue.isEmpty()) {
				Process process = executionQueue.get();
				tabla2.addCell(process.getNameProcess());
				tabla2.addCell("" + process.getTimeProcess());
				if (process.getTimeProcess() >= TIME_EXECUTION) {
					tabla2.addCell("" + (process.getTimeProcess()-TIME_EXECUTION));
					tabla2.addCell("" + TIME_EXECUTION);
				}else {
					tabla2.addCell("" + (process.getTimeProcess()-process.getTimeProcess()));
					tabla2.addCell("" + process.getTimeProcess());
				}
			}
			
			Paragraph parrafo3 = new Paragraph();
			parrafo3.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo3.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo3.add("\n\n REPORTE DE DESTRUIDOS \n\n");
			
			tabla3.addCell("Nombre del proceso");
			tabla3.addCell("Tiempo que gastó de ejecucion");
			while(!queueDestroy.isEmpty()) {
				Process process = queueDestroy.get();
				tabla3.addCell(process.getNameProcess());
				tabla3.addCell("" + process.getTimeDestroy());
			}
			//+ " - tiempo restante:  " + proc.getTimeProcess()

			//document.add(header);
	
			Paragraph parrafo4 = new Paragraph();
			parrafo4.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo4.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo4.add("\n\n REPORTE DE BLOQUEADOS \n\n");
			tabla4.addCell("Nombre del proceso");
			while(!lockedQueue.isEmpty()) {
				Process process = lockedQueue.get();
				tabla4.addCell(process.getNameProcess());
			}
			
			Paragraph parrafo5 = new Paragraph();
			parrafo5.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo5.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo5.add("\n\n REPORTE DE SUSPENDIDOS \n\n");
			tabla5.addCell("Nombre del proceso");
			while(!layQueue.isEmpty()) {
				Process process = layQueue.get();
				tabla5.addCell(process.getNameProcess());
			}
			
			Paragraph parrafo6 = new Paragraph();
			parrafo6.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo6.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo6.add("\n\n REPORTE DE COMUNICACIONES \n\n");
			tabla6.addCell("comunicaciones");
			/**
			 * to do
			 */
			while(!comunicationQueue.isEmpty()) {
				String process = comunicationQueue.get();
				tabla6.addCell(process);
			}
			
			Paragraph parrafo7 = new Paragraph();
			parrafo7.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo7.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo7.add("\n\n REPORTE DE FINALIZADOS \n\n");
			tabla7.addCell("Nombre del proceso");
			/**
			 * to do
			 */
			while(!finishQueue.isEmpty()) {
				Process process = finishQueue.get();
				tabla7.addCell(process.getNameProcess());
			}
			document.add(parrafo1);
			document.add(tabla);
			document.add(parrafoI);
			document.add(tablaI);
			document.add(parrafo2);
			document.add(tabla2);
			document.add(parrafo3);
			document.add(tabla3);
			document.add(parrafo4);
			document.add(tabla4);
			document.add(parrafo5);
			document.add(tabla5);
			document.add(parrafo6);
			document.add(tabla6);	
			document.add(parrafo7);
			document.add(tabla7);	
			document.close();
			JOptionPane.showMessageDialog(null, "reporte creado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reportInitial(MyQueue<Process> initialQueue, String name, PdfPTable tabla) {
		tabla.addCell("N. ");
		tabla.addCell(name);
		int n = 1;
		MyQueue<Process> aux = new MyQueue<>();
		while(!initialQueue.isEmpty()) {
			Process process = initialQueue.get();
			tabla.addCell(" " + n);
			tabla.addCell(process.getNameProcess());
			n++;
			aux.put(process);
		}
		initialQueue = aux;
	}

}
