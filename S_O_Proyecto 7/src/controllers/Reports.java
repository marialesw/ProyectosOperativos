package controllers;

import java.io.FileOutputStream;
import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import datastructures.MyDoubleList;
import datastructures.MyNode;
import datastructures.MyQueue;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

import models.Compactacion;
import models.Particion;
import models.Process;
import utilities.Utillidades;

public class Reports{

	private static final int TIME_EXECUTION = 1;

	private ArrayList<Particion> listaParticiones;
	public Reports() {
		// TODO Auto-generated constructor stub
	}

	private void fillList(MyDoubleList<Particion> lista){
		listaParticiones = new ArrayList<>();
		MyNode<Particion> nodeAux = lista.getHead();
		while (nodeAux != null) {
			listaParticiones.add(nodeAux.getInfo());
			nodeAux = nodeAux.getNext();
		}
	}

	public void generateReport( MyDoubleList<Particion> listaParticiones,
			MyQueue<Process> queueInitialProcess, MyQueue<Process> queueFinishProcess, 
			MyQueue<Compactacion> queueCompactacions, MyQueue<Particion> queueFinish,MyQueue<Particion> listReadyProcess, String name) {
		Document document = new Document();
		fillList(listaParticiones);
		//this.listaParticiones = listaParticiones;
		try {
			//	\OperationsOnProcesses\files\
			PdfWriter.getInstance(document, new FileOutputStream(("files/" + name + ".pdf")));
			//			Image header = Image.getInstance("D:\\asus\\Desktop\\PROGRAMACION III V\\ProcessSO\\images\\img.jpg");
			//			header.scaleToFit(400,1000);
			//			header.setAlignment(Chunk.ALIGN_CENTER);


			document.open();
			PdfPTable tablaParticiones = new PdfPTable(3);
			PdfPTable tabla = new PdfPTable(3);
			PdfPTable tablaI = new PdfPTable(1);
			PdfPTable tabla2 = new PdfPTable(5);
			PdfPTable tabla3 = new PdfPTable(3);
			PdfPTable tabla4 = new PdfPTable(2);
			PdfPTable tabla5 = new PdfPTable(3);
			PdfPTable tabla6 = new PdfPTable(1);

			Paragraph parrafoParticiones = encabezado();
			parrafoParticiones.add("\n\n LISTA DE PARTICIONES \n\n");
			parrafoParticiones.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			generarListadeParticiones(tablaParticiones);

			Paragraph parrafo1 = new Paragraph();
			parrafo1.add("\n\n LISTA DE PROCESOS INICIALES \n\n");
			reportInitial(tabla, queueInitialProcess);

			Paragraph parrafoI = new Paragraph();
			parrafoI.setAlignment(Paragraph.ALIGN_CENTER);
			parrafoI.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafoI.add("\n\n REPORTE DE ORDEN DE TERMINACION DE PROCESOS \n\n");

			endByProcess(tablaI, queueFinishProcess);
			Paragraph parrafo2 = new Paragraph();
			parrafo2.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo2.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo2.add("\n\n REPORTE DE EJECUCION \n\n");
			reporteExecution(tabla2);

			Paragraph parrafo3 = new Paragraph();
			parrafo3.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo3.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo3.add("\n\n REPORTE DE COMPACTACIONES \n\n");

			reportNoAccepted(tabla3, queueCompactacions);

			Paragraph parrafo4 = new Paragraph();
			parrafo4.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo4.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo4.add("\n\n REPORTE DE PATICIONES FINALIZADAS \n\n");
			tabla4.addCell("Nombre de la partición");
			tabla4.addCell("Tiempo total de trabajo");
			MyQueue<Particion> myQueue = queueFinish;
			while(!myQueue.isEmpty()) {
				Particion particion2 = myQueue.get();
				System.out.println(particion2.getNameParticion());
				tabla4.addCell(particion2.getNameParticion());
				tabla4.addCell(""+ particion2.getTimeInitialProcess());
			}

			Paragraph parrafo5 = new Paragraph();
			parrafo5.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo5.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo5.add("\n\n REPORTE DE PROCESOS EJECUTADOS EN CADA PARTICION \n\n");
			expiracionTime(tabla5);

			Paragraph parrafo6 = new Paragraph();
			parrafo6.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo6.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo6.add("\n\n REPORTE DE LISTOS \n\n");
			listos(tabla6, listReadyProcess);

			document.add(parrafoParticiones);
			document.add(tablaParticiones);
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
			document.close();
			Utillidades.showMessageInformation("Reporte creado", "Información");
		} catch (Exception e) {
			Utillidades.showMessageError("No se ha podido guardar " + e , "Error");
			e.printStackTrace();
		}
	}

	private void listos(PdfPTable tabla6, MyQueue<Particion> listReadyProcess) {
		tabla6.addCell("Proceso");
		MyQueue<Particion> myQueue = listReadyProcess;
		while(!myQueue.isEmpty()) {
			Particion particion = myQueue.get();
			tabla6.addCell(""+ particion.getProcess().getNameProcess());
		}
	}

	private void expiracionTime(PdfPTable tabla5) {
		tabla5.addCell("Partición");
		tabla5.addCell("Nombre del proceso");
		tabla5.addCell("Tamaño");
		for (Particion particion : listaParticiones) {
			Process process = particion.getProcess();
			if (process != null) {
				tabla5.addCell(particion.getNameParticion());
				tabla5.addCell(""+ process.getNameProcess());
				tabla5.addCell(""+ process.getTamaño());
			}

		}
	}

	private void reportNoAccepted(PdfPTable tabla3, MyQueue<Compactacion> queueCompactaciones) throws DocumentException {
		tabla3.setWidths(new float[] {100, 120, 230});
	    tabla3.addCell("Nombre Compactación");
	    tabla3.addCell("Compactación");
	    tabla3.addCell("Particiones finales");
		MyQueue<Compactacion> myQueue = queueCompactaciones;
		while(!myQueue.isEmpty()) {
			Compactacion compactacion = myQueue.get();
			tabla3.addCell(""+ compactacion.getNameCompactacion());
			tabla3.addCell(""+ compactacion.getCompactados());
			tabla3.addCell(""+ compactacion.getListFinal());
		}
	}


	private void reporteExecution(PdfPTable tabla2) {
		tabla2.addCell("Particion");
		tabla2.addCell("Nombre del proceso");
		tabla2.addCell("Tiempo inicial ");
		tabla2.addCell("Tiempo final ");
		tabla2.addCell("Tiempo de ejecución ");

		for (Particion particion : listaParticiones) {
			if (particion.getProcess() != null) {
				tabla2.addCell("-------");
				tabla2.addCell("-------");
				tabla2.addCell("-------");
				tabla2.addCell("-------");
				tabla2.addCell("------- ");
				MyQueue<Process> myQueue = particion.getQueueExecutionProcess();
				while(!myQueue.isEmpty()) {
					Process process = myQueue.get();
					tabla2.addCell(particion.getNameParticion());
					tabla2.addCell(process.getNameProcess());
					if (process.getTimeProcess() >= TIME_EXECUTION) {
						tabla2.addCell("" + process.getTimeProcess());
						tabla2.addCell("" + (process.getTimeProcess()-TIME_EXECUTION));
						tabla2.addCell("" + TIME_EXECUTION);
					}else {
						tabla2.addCell("" + process.getTimeProcess());
						tabla2.addCell("" + (process.getTimeProcess()-process.getTimeProcess()));
						tabla2.addCell("" + process.getTimeProcess());
					}
				}
			}
		}
	}


	private void endByProcess(PdfPTable tablaI, MyQueue<Process> queueFinishProcess) {
		tablaI.addCell("Nombre del proceso");
		MyQueue<Process> myQueue = queueFinishProcess;
		while(!myQueue.isEmpty()) {
			Process process = myQueue.get();
			if (process != null) {
				tablaI.addCell(process.getNameProcess());			
			}
		}
	}


	private void generarListadeParticiones(PdfPTable tablaParticiones) {
		tablaParticiones.addCell("N. ");
		tablaParticiones.addCell("Nombre de la partición");
		tablaParticiones.addCell("Tamaño ");
		int n = 0;
		for (Particion particion : listaParticiones) {
			n++;
			tablaParticiones.addCell("" + n);
			tablaParticiones.addCell(particion.getNameParticion());
			tablaParticiones.addCell("" + particion.getTamañoMaximo());
		}
	}


	private Paragraph encabezado() {
		Paragraph parrafo1 = new Paragraph();
		parrafo1.setAlignment(Paragraph.ALIGN_CENTER);
		parrafo1.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
		parrafo1.add("UNIVERSIDAD PEDAGÓGICA Y TECNOLÓGICA DE COLOMBIA \n\n");
		parrafo1.add("Reporte creado para simular la ejecución de procesos que tiene un Sistema Operativo"
				+ " \n El siguiente informe aplica al tema de Compactación del almacenamiento "
				+ "Compactación de huecos del almacenamiento en la multiprogramación con particiones variables \n\n");
		return parrafo1;
	}

	private void reportInitial(PdfPTable tabla, MyQueue<Process> queueInitialProcess) {
		tabla.addCell("proceso");
		tabla.addCell("tiempo proceso");
		tabla.addCell("tamaño proceso");
		MyQueue<Process> aux = new MyQueue<>();
		MyQueue<Process> myQueue = queueInitialProcess;
		System.out.println(queueInitialProcess.isEmpty());
		while(!myQueue.isEmpty()) {
			Process process = myQueue.get();
			tabla.addCell(process.getNameProcess());
			tabla.addCell("" + process.getTimeInitial());
			tabla.addCell("" + process.getTamaño());
			aux.put(process);
		}
	}
}