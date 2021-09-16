package models;

import java.util.Comparator;

public class SimpleList <T>{

	private Node<T> head;
	private Comparator<T> comparator;

	public SimpleList() {
		this.head = null;
		comparator = null;
	}

	public SimpleList(Comparator<T> comparator) {
		this.head = null;
		this.comparator = comparator;
	}

	public boolean isEmpty() {
		return this.head == null;
	}

	/**
	 * Adicciona al final de la lista
	 * @param info
	 */
	public void addLast(T info) {
		Node<T> aux = this.head;
		if (aux == null) {
			this.head = new Node<>(info);
		}else {
			while(aux.next!=null) {
				aux = aux.next;
			}
			aux.next = new Node<>(info);
		}
	}

	/**
	 * Inserta por la cabeza
	 * @param info
	 */
	public void insert(T info) {
		if(comparator== null) {
			this.head = new Node<T>(info, this.head);
		}else {
			addSort(info);
		}
	}

	private void addSort(T info) {
		if(comparator.compare(this.head.info, info)>0) {
			System.out.println("comparador > 0");
			this.head = new Node<>(info, this.head);
		}else {
			System.out.println("comparador < 0");
			Node<T> aux = this.head;
			Node<T> ant = this.head;
			while(aux!= null && comparator.compare(aux.info, info)<=0) {
				ant = aux;
				aux = aux.next;
			}
			ant.next = new Node<>(info, aux);//ultimo revisar correjir y probar cuando no entra al while(corregido)
		}
	}

	public void add(T info) {
		if(this.head!=null) {
			if(comparator!= null) {
				System.out.println("existe comparador");
				addSort(info);
			}else {
				System.out.println("no comparador");
				addLast(info);
			}
		}else {
			this.head = new Node<>(info);
		}
	}

	public Node<T> getHead() {
		return head;
	}
}