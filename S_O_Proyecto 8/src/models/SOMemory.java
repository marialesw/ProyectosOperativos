package models;

import datastructures.MyDoubleList;
import datastructures.MyNode;
import datastructures.MyQueue;

public class SOMemory {

	private int size;
	private int posicionInicial;
	private int sizeLibre;
	private Particion particionLast;
	private MyQueue<Particion> particiones;

	public SOMemory() {
		super();
		this.particiones = new MyQueue<>();
		this.posicionInicial = 10;
	}
	
	public void setSize(int size) {
		this.size = size;
		this.sizeLibre = size;
	}

	public Particion getParticionLast() {
		return particionLast;
	}
	
	public int getSizeLibre() {
		return sizeLibre;
	}

	public void setPosicionInicial(int posicionInicial) {
		this.posicionInicial = posicionInicial;
	}
	
	public void removeLast() {
		MyQueue<Particion> aux = obt();
		MyQueue<Particion> aux2 = new MyQueue<>();
		while (!aux.isEmpty()) {
			Particion particion = aux.get();
			if (particion != particionLast) {
				aux2.put(particion);
			}
		}
		particiones.removeQueue();
		particionLast = null;
		while (!aux2.isEmpty()) {
			particiones.put(aux2.get());
		}
	}

	public void addParticion(Particion particion) {
		int initial = posicionInicial;
		if (particion.getProcess() == null) {
			sizeLibre = particion.getSizeParticion();
		}else {
			sizeLibre = sizeLibre - particion.getTamañoMaximo();
		}
		if (particionLast != null) {
			initial = particionLast.getDirFinal();
		}
		particion.setDirInitial(initial);
		particion.setDirFinal(initial + particion.getSizeParticion());
		particionLast = particion;
		particiones.put(particion);
	}

	public int getSize() {
		return size;
	}

	public boolean gapTotal() {
		MyQueue<Particion> aux = obt();
		while (!aux.isEmpty()) {
			if (!aux.get().isGap()) {
				return false;
			}
		}
		return true;

	}

	public void decrementTime(int time, Particion particion) {
		MyQueue<Particion> aux = obt();
		MyQueue<Particion> auxFinal = new MyQueue<>();
		while (!aux.isEmpty()) {
			Particion particionAux = aux.get();
			if (particionAux.getNameParticion().equals(particion.getNameParticion())) {
				particionAux.setTimeProcess(particionAux.getTimeProcess() - time);
			}
			auxFinal.put(particionAux);
		}
		while (!auxFinal.isEmpty()) {
			particiones.removeQueue();
			particionLast = null;
			addParticion(auxFinal.get());
		}
	}

	public MyQueue<Particion> getParticiones() {
		return obt();
	}
	public MyQueue<Particion> obt() {
		MyQueue<Particion> aux = new MyQueue<>();
		Particion particion = particiones.get();
		while (particion != particionLast) {
			particiones.put(particion);
			aux.put(particion);
			particion = particiones.get();

		}
		particiones.put(particion);
		aux.put(particion);
		return aux;
	}

	public boolean isLast(Particion particion) {
		if (particionLast == particion) {
			return true;
		}
		return false;
	}

	public boolean isFinal() {
		MyQueue<Particion> aux = obt();
		int n = 0;
		while (!aux.isEmpty()) {
			n++;
		}
		if (n == 2) {
			return true;
		}
		return false;
	}

	public void fillParticiones(MyQueue<Particion> partsUse, Particion partsVacie) {
		if (partsVacie != null) {
			particiones.removeQueue();
			particionLast = null;
			while (!partsUse.isEmpty()) {
				Particion particion = partsUse.get();
				addParticion(particion);
			}	
			addParticion(partsVacie);

		}
	}

	public boolean existGap(int n) {
		MyQueue<Particion> aux = obt();
		while (!aux.isEmpty()) {
			Particion particion = aux.get();
			if (n == 1) {
				if (particion.isGap() && particion != particionLast) {
					return true;
				}
			}else {
				if (particion.isGap()) {
					return true;
				}
			}

		}
		return false;
	}

	public boolean hayEspacio(int tam) {
		if (sizeLibre >= tam ) {
			return true;
		}
		return false;
	}
	
	public boolean hayOcupadas() {
		MyQueue<Particion> aux = obt();
		while (!aux.isEmpty()) {
			Particion particion = aux.get();
			if (!particion.isGap()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public void fillParticiones(MyDoubleList<Particion> partitions) {
			particiones.removeQueue();
			particionLast = null;
			MyNode<Particion> iterator = partitions.getHead();
			while (iterator != null) {
				System.out.println("toteoooooooooooooooooooo");
				Particion particion = iterator.getInfo();
				addParticion(particion);
				iterator = iterator.getNext();
			}
	}
}