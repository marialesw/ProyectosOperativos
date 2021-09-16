package models;

/**
 * 
 * @author Dario Baron
 *
 * @param <T>
 */
public class Node <T>{
	protected T info;
	protected Node<T> next;

	/**
	 * 
	 * @param info
	 * @param next
	 */
	public Node(T info, Node<T> next) {
		this.info = info;
		this.next = next;
	}
	/**
	 * 
	 * @param info
	 */
	public Node(T info) {
		this.info = info;
		this.next = null;
	}

	public T getInfo() {
		return info;
	}
	
	public Node<T> getNext() {
		return next;
	}
	public void setInfo(T info) {
		this.info = info;
	}
	
	public void setNext(Node<T> next) {
		this.next = next;
	}
}
