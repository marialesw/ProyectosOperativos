package datastructures;

public class MyDoubleList<T> {

	private MyNode<T> head;
	private MyNode<T> last;

	public MyNode<T> getLast() {
		return last;
	}

	public MyDoubleList() {
	}

	public void addElement(MyNode<T> newNode, int option){
		switch (option) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;

		default:
			break;
		}
	}

	public void addElementToHead(T info){
		MyNode<T> aux = this.head;
		if (head == null) {
			head = new MyNode<>(info);
			last = head;
		}else{
			MyNode<T> aux2 = head;
			head = new MyNode<>(info);
			aux2.setPrevious(head);
			head.setNext(aux2);
		}
	}

	public void addElementToTail(T info){
		MyNode<T> nodeAux = new MyNode<>(info);
		MyNode<T> aux = last;
		if (head == null) {
			head = nodeAux;
			last = head;
		}else{
			last = nodeAux;
			aux.setNext(last);
			last.setPrevious(aux);
		}
	}
	public void replace(T newNode, MyNode<T> node1, MyNode<T> node2, MyNode<T> node3 ) {
		MyNode<T> aux = head;
		if (head != null) {
			while (aux != null) {
				if (aux == node1) {
					if (node3 != null) {
						MyNode<T> temp = aux.getNext().getNext().getNext().getNext();
						aux = new MyNode<>(newNode);
						aux.setNext(temp);
					}else {
						MyNode<T> temp = aux.getNext().getNext();
						aux =  new MyNode<>(newNode);
						aux.setNext(temp);
					}
				}
				aux = aux.getNext();
			}
		}
	}

	public void replace(T newNode) {
		MyNode<T> aux = this.head;
		while (aux != null) {
			if (aux.getInfo().equals(newNode)) {
				aux.setInfo(newNode);
			}
			aux = aux.getNext();
		}
	}

	public MyNode getPreviousTo(MyNode<T> node){
		MyNode<T> aux = head;
		MyNode<T> ret = null;
		while (aux != null) {
			if (aux.getInfo().equals(node)) {
				ret = aux.getPrevious();
			}
			aux = aux.getNext();
		}
		return ret;

	}

	public MyNode<T> getHead() {
		return head;
	}

	public int generateNumberNodes(){
		int number = 0;
		MyNode<T> aux = head;
		while (aux != null) {
			aux = aux.getNext();
			number++;
		}
		return number;
	}

	public MyNode<T> getTail(){
		MyNode<T> aux = head;
		while (aux.getNext() != null) {
			aux = aux.getNext();
		}
		return aux;
	}

	public void addElementToHalf(T newInfo, T lastInfo){
		MyNode<T> aux = head;
		MyNode<T> nodeNew = new MyNode<>(newInfo);
		MyNode<T> nodeLast = new MyNode<>(lastInfo);

		if (head == null) {
			head = nodeNew;
			last = head;
		}else{
			while (aux.getNext() != null) {
				if (aux.getInfo().equals(nodeLast.getInfo())) {
					MyNode<T> temp = aux.getNext();
					aux.setNext(nodeNew);
					aux.getNext().setPrevious(aux);
					aux.getNext().setNext(temp);
					aux.getNext().getNext().setPrevious(aux.getNext());
				}
				aux = aux.getNext();
			}
		}
	}

	public MyNode<T> getNodeNextTo(MyNode<T> lastNode){
		MyNode<T> aux = head;
		MyNode<T> ret = null;
		while (aux.getNext() != null) {
			if (aux.getInfo().equals(lastNode.getInfo())) {
				ret = aux.getNext();
			}
			aux = aux.getNext();
		}
		return ret;	
	}

	public void deleteHead(){
		head = head.getNext();
	}

	public void deleteTail(){
		MyNode<T> aux = head;
		if (head == last) {
			head = null;
			last = null;
		}else {
			while (aux.getNext() != last) {
				aux = aux.getNext();
			}
			last = aux;
		}
	}
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}

	public void deleteToHalf(T myNode){
		MyNode<T> aux = head;
		while (aux.getNext() != null) {
			if (aux.getNext().getInfo().equals(myNode)) {
				MyNode<T> temp = aux.getNext().getNext();
				aux.setNext(temp);
			}
			aux = aux.getNext();
		}
	}
	public void printList() {
		MyNode<T> aux = head;
		while(aux != null) {
			System.out.println(aux.info.toString());
			aux = aux.next;
		}
	}

}