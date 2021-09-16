package models;

import java.util.Comparator;

public class Order<T> {

	private Comparator<T> comparator;
	private MyQueue<T> myQueueFActual;
	private MyQueue<T> myQueue1;
	private MyQueue<T> myQueue2;
	private boolean isFinished;

	public Order(Comparator<T> comparator) {
		this.comparator = comparator;
		myQueue1 = new MyQueue<>();
		myQueue2 = new MyQueue<>();
		myQueueFActual = new MyQueue<>();
		isFinished = false;
	}

	public void sortQueue(MyQueue<T> queue) {
		myQueueFActual = queue; // cola actual
		while (!isFinished) {
			while(!myQueueFActual.isEmpty()) {
				if (myQueue1.isEmpty()) {
					myQueue1.put(myQueueFActual.get());
				}else {
					T obj1 = myQueue1.getLast().info;
					T obj2 = myQueueFActual.get();
					saveToQueue(obj1, obj2);
				}
			}
			if (myQueue2.isEmpty()) {
				isFinished = true;
			}
				addQueueToActual();
		}
	}

	private void addQueueToActual() {
		while (!myQueue2.isEmpty()) {
			T obj = (myQueue2.get());
			myQueueFActual.put(obj);
		}
		while (!myQueue1.isEmpty()) {
			T obj2 = (myQueue1.get());
			myQueueFActual.put(obj2);
		}
		
	}

	private void saveToQueue(T object1, T object2){
		if(comparator.compare(object1, object2)>0) {//1 mayor que 2
			myQueue2.put(object2);
		}else if(comparator.compare(object1, object2)<=0) { //2 mayor que 1
			myQueue1.put(object2);
		}  
	}

	public void printQueues() {
		while (!myQueueFActual.isEmpty()) {
			System.out.println("Qa " + myQueueFActual.get());
		}
	}
}