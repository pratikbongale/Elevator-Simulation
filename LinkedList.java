class LinkedList<T extends Comparable<T>> {
	
	Node<T> head;
	
	LinkedList() {
		head = null;
	}

	void addAsc(T obj){
		if(head == null)
			head = new Node<T>(obj,head);
		else {
			// find where to insert this node and then make the appropriate links
			Node<T> newNode = new Node<>(obj,null);
			// if this list contains and such node
			Node<T> current = head;
			Node<T> previous = head;
			while(current != null) {

				if( current.data.compareTo(newNode.data) >= 0 ) {	// found element less than the element to be inserted
					if( current != previous ) {
						newNode.next = current;			// so insert this new node after
						previous.next = newNode;
						break;	
					} else { 		// so you need to insert at the first position
						newNode.next = current;			// so insert this new node after
						head = newNode;
						break;
					}

					
				}
				/*else {
					newNode.next = current;
					previous.next = newNode;
				}*/
				previous = current;
				current = current.next;
			}	

			if(current == null) {
				previous.next = newNode;
			}

		}
	}

	void addDesc(T obj){
		if(head == null)
			head = new Node<T>(obj,head);
		else {
			// find where to insert this node and then make the appropriate links
			Node<T> newNode = new Node<>(obj,null);
			// if this list contains and such node
			Node<T> current = head;
			Node<T> previous = head;
			while(current != null) {

				if( current.data.compareTo(newNode.data) <= 0 ) {	// found element less than the element to be inserted
					if( current != previous ) {
						newNode.next = current;			// so insert this new node after
						previous.next = newNode;
						break;	
					} else { 		// so you need to insert at the first position
						newNode.next = current;			// so insert this new node after
						head = newNode;
						break;
					}

					
				}
				
				previous = current;
				current = current.next;
			}	

			if(current == null) {
				previous.next = newNode;
			}

		}
	}

	void remove(T obj) {
		Node<T> current = head;
		Node<T> previous = head;
		while(current != null) {

			//if( current.data.compareTo(obj) == 0 ) {	// found element less than the element to be inserted
			if( current.data == obj) {					// found element 
				if( current != previous ) {
					previous.next = current.next;			// so remove the current node
					break;
				} else { 							// this is the first node
					head = head.next;							// so increment the head
					break;
				}

				
			}
			
			previous = current;
			current = current.next;
		}	



	}

	public String toString() {
		Node<T> current = head;
		String temp = "\n[";
	
		while(current != null) {
			temp = temp + current.data + " ";
			current = current.next;
		}

		temp = temp + "] \n";

		return temp;
	}

	public static void main(String args[]) {
		LinkedList<Integer> list = new LinkedList<>();
		list.addAsc(10);
		list.addAsc(20);
		list.addAsc(5);
		list.addAsc(45);
		System.out.println(list);

		LinkedList<Integer> listD = new LinkedList<>();
		listD.addDesc(10);
		listD.addDesc(20);
		listD.addDesc(5);
		listD.addDesc(45);
		listD.remove(20);
		listD.remove(5);
		System.out.println(listD);
	}
}

class Node<E extends Comparable<E>> {
	E data;
	Node<E> next;

	Node( E data, Node<E> next ) {
		this.data = data;
		this.next = next;
	}
}