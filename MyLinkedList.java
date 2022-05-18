import java.util.Iterator;

public class MyLinkedList<E> implements MyList<E> {
	protected int size = 0; // Number of elements in the list
	protected Node<E> head,tail;

	class Node<E>{
		Node previous;
		Node next;
		E data;
		public Node(E data){
			this.data = data;
		}
	}

		/** Create an empty list */
	public MyLinkedList() {
		head = null;
		tail = null;
	}

	/** Create a list from an array of objects */
	public MyLinkedList(E[] objects) {

		for(int i = 0;i<objects.length;i++){
			Node newNode = new Node(objects[i]);
			if (head == null){
				head = newNode;
				tail = newNode;
				head.previous = null;
			}
			else {
				tail.next = newNode;
				newNode.previous = tail;
				tail = newNode;
			}
			tail.next = null;
		}
	}

	/** Return the head element in the list */
	public E getFirst() {
		return head.data;
	}

	/** Return the last element in the list */
	public E getLast() {
		Node<E> current = head;
		while (current.next != null){
			current = current.next;
		}
		return current.data;
	}

	/** Add an element to the beginning of the list */
	public void addFirst(E e) {
		Node newNode = new Node(e);
		head.previous = newNode;
		newNode.next = head;
		newNode.previous =null;
		head = newNode;
	}

	/** Add an element to the end of the list */
	public void addLast(E e) {
		Node<E> current = head;
		while (current.next!=null){
			current = current.next;
		}
		Node newNode = new Node(e);
		current.next = newNode;
		newNode.previous = current;
		newNode.next = null;
		current = newNode;
	}

	@Override /** Add a new element at the specified index
	 * in this list. The index of the head element is 0 */
	public void add(int index, E e) {
		Node newNode = new Node(e);
		int counter = 0;
		if (index == 0){
			if (head != null){
				Node temp = head;
				temp.previous = newNode;
				newNode.next = temp;
				head = newNode;
				tail = newNode;
				head.previous = null;
			}
			else {
				head = newNode;
				tail = newNode;
				head.previous = null;
			}

		}
		else {
			Node<E> current = head;
			for(int i = 0;i<index-1;i++){
				current = current.next;
			}
			E temp = current.data;
			Node tempNext = current;
			Node tempPrev = current.next;
			newNode.previous = tempNext;
			newNode.next = tempPrev;
			if (newNode.next != null){
				newNode.next.previous = newNode;
			}
			if(newNode.previous!=null){
				newNode.previous.next = newNode;
			}


		}
	}

	/** Remove the head node and
	 *  return the object that is contained in the removed node. */
	public E removeFirst() {
		E temp =head.data;
		head = head.next;
		head.previous = null;
		return temp;
	}

	/** Remove the last node and
	 * return the object that is contained in the removed node. */
	public E removeLast() {
		E temp = tail.data;
		tail = tail.previous;
		tail.next = null;
		return temp;
	}

	@Override /** Remove the element at the specified position in this
	 *  list. Return the element that was removed from the list. */
	public E remove(int index) {
		Node<E> current = head;
		for(int i = 0; i<index;i++){
			current = current.next;
		}
		E temp = current.data;
		Node tempNext = current.next;
		Node tempPrev = current.previous;
		if(current.previous!=null){
			current.previous.next = tempNext;
		}
		else {
			head = current.next;
		}
		if(current.next !=null){
			current.next.previous = tempPrev;
		}
		return temp;
	}

	@Override /** Override toString() to return elements in the list */
	public String toString() {
		StringBuilder result = new StringBuilder("[");

		Node<E> current = head;
		while(current!=null){
			result.append(current.data);
			current = current.next;
			if (current != null) {
				result.append(", "); // Separate two elements with a comma
			}
			else {
				result.append("]"); // Insert the closing ] in the string
			}
		}
		return result.toString();
	}

	@Override /** Clear the list */
	public void clear() {
		head.next = null;
		head = null;
	}

	@Override /** Return true if this list contains the element e */
	public boolean contains(Object e) {
		boolean state;
		Node<E> current = head;
		while (current!=null){
			System.out.println(current.data);
			System.out.println(e);
			if (current.data.equals(e)){
				return true;
			}
			current = current.next;
		}
		return false;

	}

	@Override /** Return the element at the specified index */
	public E get(int index) {
		Node<E> current = head;
		for(int i = 0; i<index;i++){
			current = current.next;
		}
		E temp = current.data;
		return temp;
	}

	@Override /** Return the index of the first matching element in
	 *  this list. Return -1 if no match. */
	public int indexOf(Object e) {
		int counter = 0;
		Node<E> current = head;
		while (current.next!=null){
			counter++;
			if (current.data.equals(e)){
				return counter;
			}
			current = current.next;
		}
		counter = -1;
		return counter;

	}

	@Override /** Return the index of the last matching element in
	 *  this list. Return -1 if no match. */
	public int lastIndexOf(E e) {
		Node<E> current = head;
		int counter = 0;

		while (current!=null){
			current = current.next;
			counter++;
		}
		while (current != null){
			current =current.previous;
			counter--;
			if (e.equals(current.data)){
				return counter;
			}
		}
		counter = -1;
		return counter;


	}

	@Override /** Replace the element at the specified position
	 *  in this list with the specified element. */
	public E set(int index, E e) {
		Node<E> current = head;
		for(int i = 0; i<index;i++){
			current = current.next;
		}

		E temp = current.data;
		Node tempNext = current;
		Node tempPrev = current.next;
		Node newNode = new Node(e);
		newNode.previous = tempNext;
		newNode.next = tempPrev;
		newNode.next.previous = newNode;
		newNode.previous.next = newNode;
		return temp;
	}

	@Override /** Override iterator() defined in Iterable */
	public java.util.Iterator<E> iterator() {
		return new LinkedListIterator();
	}

	private class LinkedListIterator
	implements java.util.Iterator<E> {
		private Node<E> current = head; // Current node
		private int index=-1; // initial index before head

		@Override
		public boolean hasNext() {
			return (current != null);
		}

		@Override
		public E next() {
			E e = current.data;
			index++;
			current = current.next;
			return e;
		}

		@Override
		// remove the last element returned by the iterator
		public void remove() {
			MyLinkedList.this.remove(index);
		}
	}

	@Override /** Return the number of elements in this list */
	public int size() {
		int counter = 0;
		Node<E> current = head;
		while (current!=null){
			counter++;
			current = current.next;
		}

		return counter;
	}

}