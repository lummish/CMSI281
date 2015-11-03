import java.lang.Class;

public class Node {
	
	private Node next;
	private Node prev;
	private Object data;

	public Node() {
		next = null;
		prev = null;
		data = null;
	}

	public Node( Object o ) {
		next = null;
		prev = null;
		data = o;
	}

	public void setNext( Node n ) {
		next = n;
		if ( n == null ) return;
		n.prev = this;
	}

	public void setPrev( Node n ) {
		prev = n;
		if (n == null) return;
		n.next = this;
	}

	public Node getPrev() {
		return this.prev;
	}

	public Node getNext() {
		return this.next;
	}

	public Object get() {
		return data;
	}

}