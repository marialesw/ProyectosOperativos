package models;

import datastructures.MyQueue;

public class Compactacion {

	private String nameCompactacion;
	private MyQueue<Particion> queueParticions;
	private MyQueue<Particion> queueCompactados;
	
	public Compactacion(String nameCompactacion, MyQueue<Particion> queueParticions, MyQueue<Particion> queueCompactados) {
		super();
		this.nameCompactacion = nameCompactacion;
		this.queueParticions = queueParticions;
		this.queueCompactados = queueCompactados;
	}

	public String getNameCompactacion() {
		return nameCompactacion;
	}

	public MyQueue<Particion> getQueueCompactados() {
		return queueCompactados;
	}
	
	public void setQueueCompactados(MyQueue<Particion> queueCompactados) {
		this.queueCompactados = queueCompactados;
	}
	public void setNameCompactacion(String nameCompactacion) {
		this.nameCompactacion = nameCompactacion;
	}

	public MyQueue<Particion> getQueueParticions() {
		return queueParticions;
	}

	public void setQueueParticions(MyQueue<Particion> queueParticions) {
		this.queueParticions = queueParticions;
	}
	public String getCompactados() {
		String result = "";
		MyQueue<Particion> aux = new MyQueue<>();
		while (!queueCompactados.isEmpty()) {
			Particion particion = queueCompactados.get();
			result = result + particion.getNameParticion() + " + ";
			aux.put(particion);
		}
		while (!aux.isEmpty()) {
			queueCompactados.put(aux.get());
		}
		return result;
	}
	
	public String getNuevaComp() {
		String result = "";
		MyQueue<Particion> aux = new MyQueue<>();
		Particion particion = queueParticions.get();
		while (!queueParticions.isEmpty()) {
			aux.put(particion);
			particion = queueParticions.get();
		}
		result = particion.getNameParticion() + " Tamaño: " + particion.getSizeParticion();
		while (!aux.isEmpty()) {
			queueParticions.put(aux.get());
		}
		return result;
	}
	
	public String getListFinal() {
		String result = "";
		MyQueue<Particion> aux = new MyQueue<>();
		while (!queueParticions.isEmpty()) {
			Particion particion = queueParticions.get();
			result = result + particion.toString()+ " \n";
			aux.put(particion);
		}
		
		return result;
	}
}
