package datastructures;

public class MyQueue <T>{

	private MyNode<T> first;
	private MyNode<T> last;

	public MyQueue() {
		this.first = null;
		this.last = null;
	}
	
	public boolean isEmpty() {
		return this.first == null;
	}

	public void put(T info) {
		if (first == null) {
			this.first = this.last = new MyNode<>(info);
		}else {
			this.last = this.last.next = new MyNode<>(info);
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
	
	public MyNode<T> getLast() {
		return last;
	}
	
//	public int get
}
