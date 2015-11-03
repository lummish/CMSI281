import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**A linked deque that does not allow null elements.**/
public class LinkedDeque {

	private Node left;
	private Node right;
	private int size;

	public LinkedDeque() {
		left = null;
		right = null;
		size = 0;
	}

	/**Inserts a new node with argument as data at the head of the deque. Throws
	   an exception if argument passed in is null.**/
	public void insertLeft( Object o ) {
		
		if ( o == null ) {
			throw new NullPointerException();
		}
		if (left == null || right == null) {
			left = new Node( o );
			right = left;
			size++;
		}

		else {
			Node new_left = new Node( o );
			new_left.setNext( left );
			left.setPrev( new_left );
			left = new_left;
			size++;
		}
	}

	/**Inserts a new node with argument as data at the tail of the deque**/
	public void insertRight( Object o ) {

		if ( o == null ) {
			throw new NullPointerException();
		}
		if ( left == null || right == null) {
			left = new Node( o );
			right = left;
			size++;
		}

		else {
			Node new_right = new Node( o );
			new_right.setPrev( right );
			right.setNext( new_right );
			right = new_right;
			size++;
		}
	}

	/**Deletes the leftmost (head) element in the deque. Throws NoSuchElementException() if
	   deque is empty.**/
	public void deleteLeft() {

		if ( left == null ) {
			throw new NoSuchElementException();
		}

		size--;
		left = left.getNext();

		if ( size == 0 ) {
			right = null;
			return; //if list had one element, it's now empty
		}

		left.setPrev( null );
		
	}

	/**Deletes the rightmost (tail) element in the deque. Throws NoSuchElementException() if
	   deque is empty.**/
	public void deleteRight() {

		if ( right == null ) {
			throw new NoSuchElementException();
		}

		size--;
		right = right.getPrev();

		if ( size == 0 ) {
			left = null;
			return; //if list had one element, it's now empty
		}

		right.setNext( null );
		
	}

	/**Returns the leftmost (head) element stored in the deque. Returns null if the
	   deque is empty.*/
	public Object left() {
		if ( size == 0 ) return null;
		return left.get();
	}

	/**Returns the rightmost (tail) element stored in the deque. Returns null if the
	   deque is empty.*/
	public Object right() {
		if ( size == 0 ) return null;
		return right.get();
	}

	/**Returns the size of the deque**/
	public int size() {
		return size;
	}

	/**Returns a stringified deque. [] if deque is empty.**/
	public String toString() {
		
		Node cur = left;
		String out = "[";

		while( cur != null ) {
			out += cur.get() + "][";
			cur = cur.getNext();
		}

		if ( out.length() == 1 ) return "[]";
		else {
			return out.substring( 0, out.length() - 1 );
		}
		
	}
	
	public java.util.Iterator iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Object> {
		
		private int index;
		private Node cur;
		private int start_size;

		public DequeIterator() {
			index = 0;
			start_size = LinkedDeque.this.size;
			cur = LinkedDeque.this.left;
		}

		public boolean hasNext() {
			return cur != null;
			//return cur.getNext() != null;
		}

		public Object next() {
			if (LinkedDeque.this.size() != start_size)
				throw new ConcurrentModificationException();
			if (this.hasNext()) {
				Object out = cur.get();
				cur = cur.getNext();
				index++;
				return out;
			}

			throw new NoSuchElementException();
		}

		public int get_index() {
			return index;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public Object get( int index ) {
		if ( index < 0 || index >= size ) {
			throw new NoSuchElementException();
		}

		int count = 0;
		/*
		for ( Object o : this ) {
			if (count == index) return o;
			count++;
		}*/

		Iterator itr = this.iterator();

		while ( itr.hasNext() ) {
			if ( count++ == index ) return itr.next();
		}

		return null; //returns null if there is no object at that index
	}
	
}