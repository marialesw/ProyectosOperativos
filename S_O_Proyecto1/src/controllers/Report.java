package controllers;

import java.io.FileOutputStream;

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
	
	public Report() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void generateReport(MyQueue<Process> myQueue, String name, int num, MyQueue<Process> myQueue2) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("D:\\asus\\Desktop\\2020-2\\operativos\\proyecto\\reporte" + name + ".pdf"));
			Image header = Image.getInstance("D:\\asus\\Desktop\\PROGRAMACION III V\\ProcessSO\\images\\img.jpg");
			header.scaleToFit(400,1000);
			header.setAlignment(Chunk.ALIGN_CENTER);
			
			Paragraph parrafo = new Paragraph();
			parrafo.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo.add("UNIVERSIDAD PEDAGÓGICA Y TECNOLÓGICA DE COLOMBIA \n\n");
			parrafo.setFont(FontFactory.getFont("Tahoma",14,Font.BOLD, BaseColor.DARK_GRAY));
			parrafo.add("Reporte creado para simular la ejecución de procesos que tiene un Sistema Operativo \n\n");

			document.open();
			PdfPTable tabla = new PdfPTable(2);
			tabla.addCell("N. ");
			tabla.addCell(name);
			if (num == 1) {
				int n = 1;
				while(!myQueue.isEmpty()) {
					tabla.addCell(" " + n);
					tabla.addCell(myQueue.get().getNameProcess());
					n++;
				}
			}else {
				int n = 1;
				while(!myQueue.isEmpty()) {
					tabla.addCell(" " + n);
					Process proc = myQueue.get();
					tabla.addCell(proc.getNameProcess() );
					n++;
				}
			}
			//+ " - tiempo restante:  " + proc.getTimeProcess()
			
			document.add(header);
			document.add(parrafo);
			document.add(tabla);
			document.close();
			JOptionPane.showMessageDialog(null, "reporte creado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
