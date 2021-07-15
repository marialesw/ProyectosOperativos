package datastructures;

/**
 * 
 * @author Lisbeth
 *
 * @param <T>
 */
public class MyNode <T>{
	protected T info;
	protected MyNode<T> next;
	private MyNode<T> previous;

	/**
	 * 
	 * @param info
	 * @param next
	 */
	public MyNode(T info, MyNode<T> next, MyNode<T> previous) {
		this.info = info;
		this.next = next;
		this.previous = previous;
	}
	/**
	 * 
	 * @param info
	 */
	public MyNode(T info) {
		this.info = info;
		this.next = null;
		this.previous = null;
	}

	public T getInfo() {
		return info;
	}
	
	public MyNode<T> getNext() {
		return next;
	}
	public void setInfo(T info) {
		this.info = info;
	}
	
	public void setNext(MyNode<T> next) {
		this.next = next;
	}
	
	public MyNode<T> getPrevious() {
		return previous;
	}
	
	public void setPrevious(MyNode<T> previous) {
		this.previous = previous;
	}
}
