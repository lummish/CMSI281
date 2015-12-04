public class Node {
	private Object data;
	private Node right;
	private Node left;
	private Node dad;

	public Node( Object o ) {
		data = o;
		dad = null;
		right = null;
		left = null;
	}

	public void setRight( Object o ) {
		if (o == null) {
			right = null;
		}
		else {
			right = new Node(o);
		}
	}

	public void setLeft( Object o ) {
		if (o == null) {
			left = null;
		}
		else {
			left = new Node(o);
		}
	}

	public void setDad( Node n ) {
		dad = n;
	}
	public boolean hasLeft() {
		return left != null;
	}
	public boolean hasRight() {
		return right != null;
	}
	public boolean hasSon() {
		return (hasLeft() || hasRight());
	}
	public Node getRight() {
		return right;
	}

	public Node getLeft() {
		return left;
	}

	public Node getDad() {
		return dad;
	}

	public Object get() {
		return data;
	}

}