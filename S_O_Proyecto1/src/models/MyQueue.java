package models;

public class MyQueue <T>{

	private Node<T> first;
	private Node<T> last;

	public MyQueue() {
		this.first = null;
		this.last = null;
	}

	public boolean isEmpty() {
		return this.first == null;
	}

	public void put(T info) {
		if (first == null) {
			this.first = this.last = new Node<>(info);
		}else {
			this.last = this.last.next = new Node<>(info);
		}
	}

	public T get() {
		if (first != null) {
			T aux = this.first.info;
			this.first = this.first.next;
			return aux;
		}else {
			return null;
		}
	}
}
