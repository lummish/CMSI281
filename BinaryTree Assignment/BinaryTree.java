import java.util.Stack;
import java.util.Iterator;
import java.util.HashMap;


public class BinaryTree implements Iterable {
	private int size;
	private Node root;
	private Node cursor;
	private boolean allowPrune;

	public BinaryTree() {
		size = 0;
		allowPrune = true;
	}

	public BinaryTree( Object obj ) {
		
		if (obj == null) {
			throw new NullPointerException(); //right exception?
		}

		size = 1;
		root = new Node(obj);
		cursor = root;
		allowPrune = true;
	}

	/**Returns true if the argument is stored in this tree**/
	public boolean contains( Object obj ) {
		if (isEmpty()) {
			return false;
		}
		Iterator itr = iterator();

		while (itr.hasNext()) {
			if (itr.next().equals(obj)) {
				return true;
			}
		}

		return false;
	}

	/**Returns true if the argument is a binary tree with equivalent structure
	to the current instance.**/
	public boolean similar( Object obj ) {
		if (obj instanceof BinaryTree) {
			BinaryTree t = (BinaryTree) obj;
		
			if (t.size() != size()) {
				return false;
			}
			else {
				Object[] thisPreOrder = preOrderToArray();
				Object[] thisInOrder = inOrderToArray();
				Object[] tPreOrder = t.preOrderToArray();
				Object[] tInOrder = t.inOrderToArray();
				int j;

				for (int i = 0; i < thisPreOrder.length; i++) {
					//.equals or ==?
					j = 0;
					
					while (!thisPreOrder[i].equals(thisInOrder[j])) {
						j++;
					}

					if (!tPreOrder[i].equals(tInOrder[j])) {
						return false;
					}
				}
				return true;
			}
		}
		else {
			return false;
		}
	}

	/**Returns an array version of the tree's preorder traversal**/
	private Object[] preOrderToArray() {
		if (isEmpty()) {
			return null;
		}

		Object[] out = new Object[size()];
		Iterator itr = iterator();
		int idx = 0;

		while (itr.hasNext()) {
			out[idx++] = itr.next();
		}

		return out;
	}

	/**Returns an array version of the tree's inorder traversal**/
	private Object[] inOrderToArray() {
		if (isEmpty()) {
			return null;
		}

		Object[] out = new Object[size()];
		Iterator itr = inOrder();
		int idx = 0;

		while (itr.hasNext()) {
			out[idx++] = itr.next();
		}

		return out;
	}

	/**Returns whether the tree is equivalent to another object**/
	public boolean equals( Object obj) { 
		if (obj instanceof BinaryTree) {
			
			Iterator itr1 = iterator();
			Iterator itr2 = ((BinaryTree) obj).iterator();

			while (itr1.hasNext()) {
				
				if(!itr2.hasNext()) {
					return false;
				}

				if (!itr1.next().equals(itr2.next())) {
					return false;
				}
			}

			if (itr2.hasNext()) {
				return false;
			}

			itr1 = inOrder();
			itr2 = ((BinaryTree) obj).inOrder();

			while (itr1.hasNext()) {

				if(!itr2.hasNext()) {
					return false;
				}

				if (!itr1.next().equals(itr2.next())) {
					return false;
				}
			}

			if (itr2.hasNext()) {
				return false;
			}

			return true;
			

		}
		else {
			return false;
		}
	}

	/**Returns true if the tree has no nodes**/
	public boolean isEmpty() {
		return size == 0;
	}

	/**Returns an iterator that traverses the tree in preorder**/
	public Iterator iterator() {
		return new preOrderItr();
	}

	private class preOrderItr implements Iterator<Object> {
		
		private Stack<Node> rightStack;
		private int idx = 0;

		public preOrderItr() {
			rightStack = new Stack<Node>();
			BinaryTree.this.allowPrune = false;
		}

		public boolean hasNext() {
			//return idx < BinaryTree.this.size;
			if (idx > 0) {
				return (!rightStack.isEmpty() || BinaryTree.this.cursor.hasSon());
			}
			else {
				BinaryTree.this.allowPrune = true;
				return !BinaryTree.this.isEmpty();
			}
		}

		public Object next() {
			if (idx == 0) {
				idx++;
				BinaryTree.this.putCursorAtRoot();
				return BinaryTree.this.cursor.get();
			}
			else {
				idx++;
				if (cursor.getRight() != null) { //may need to change
					rightStack.push(BinaryTree.this.cursor.getRight());	
				}
				if (!BinaryTree.this.putCursorAtLeftSon()) {
					BinaryTree.this.cursor = rightStack.pop();
				}
				return BinaryTree.this.cursor.get();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**Returns an iterator that traverses the tree in order**/
	public Iterator inOrder() {
		return new inOrderator();
	}

	private class inOrderator implements Iterator<Object>{
		private Stack<Node> rootStack; //to store nodes
		private boolean skipLeft; //keeps track of whether left subtree traversed
		private Node p; //cursor
		private int idx;

		public inOrderator() {
			p = BinaryTree.this.root;
			rootStack = new Stack<>();
			skipLeft = false;
			idx = 0;
			BinaryTree.this.allowPrune = false;
		}

		public boolean hasNext() {
			if (idx < BinaryTree.this.size) {
				return true;
			}
			else {
				BinaryTree.this.allowPrune = true;
				return false;
			}
		}

		public Object next() {
			idx++;
			while (p.hasLeft() && !skipLeft) {
				rootStack.push(p);
				p = p.getLeft();
			}
			if (!p.hasSon()) {
				
				Node toReturn = p;
				if (!rootStack.isEmpty()) {
					p = rootStack.pop();
					skipLeft = true;
				}
				return toReturn.get();
			}
			
			if(skipLeft) {
				Node toReturn = p;
				if (p.hasRight()) {
					p = p.getRight();
					skipLeft = false;
				}
				else if (p != root) {
					p = rootStack.pop();
					skipLeft = true;
				}
				else {
					return p.get();
				}
				return toReturn.get();
			}
			skipLeft = true;
			return next();
		}
	}
	
	/**Places cursor at root of tree if root exists.**/
	public boolean putCursorAtRoot() {
		
		if (isEmpty()) {
			return false;
		}

		cursor = root;
		return true;
	}

	/**Places cursor at current cursor's left son**/
	public boolean putCursorAtLeftSon() { //if cursor is null?

		if (isEmpty()) {
			return false;
		}
		else {
			if (cursor.getLeft() == null) {
				return false;
			}
			else {
				cursor = cursor.getLeft();
				return true;
			}
		}
	}

	/**Places cursor at current cursor's right son**/
	public boolean putCursorAtRightSon() {

		if (isEmpty()) {
			return false;
		}
		else {
			if (cursor.getRight() == null) {
				return false;
			}
			else {
				cursor = cursor.getRight();
				return true;
			}
		}
	}

	/**Places cursor at current cursor's parent node**/
	public boolean putCursorAtFather() {

		if (isEmpty()) {
			return false;
		}
		else {
			if (cursor.getDad() == null) {
				return false;
			}
			else {
				cursor = cursor.getDad();
				return true;
			}
		}
	}

	/**Attaches right son to current cursor if none already exists and returns
	whether or not tree has changed.**/
	public boolean attachLeftSonAtCursor(Object obj) { //what if obj is null?
		if (cursor == null) {
			return false;
		}
		else {
			if (!cursor.hasLeft()) {
				size++;
				cursor.setLeft(obj);
				cursor.getLeft().setDad(cursor);
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**Attaches right son to current cursor if none already exists and returns
	whether or not tree has changed.**/
	public boolean attachRightSonAtCursor(Object obj) { //what if obj is null?
		if (cursor == null) {
			return false;
		}
		else {
			if (!cursor.hasRight()) {
				size++;
				cursor.setRight(obj);
				cursor.getRight().setDad(cursor);
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**Prunes subtree beginning at cursor, then places cursor at root of tree**/

	public boolean pruneFromCursor() { 
		if (cursor == null) {
			return false;
		}
		else if (!allowPrune) {
			throw new java.util.ConcurrentModificationException();
		}
		else if(cursor == root) {
			cursor = null;
			root = null;
			size = 0;
			return true;
		}
		else {
			System.out.println(cursor.get());
			cursor = null;

			int newSize = 0;	//to ensure proper size change
			Iterator sizeFinder = iterator();
			
			while(sizeFinder.hasNext()) {
				newSize++;
				sizeFinder.next();
			}
			
			this.size = newSize;
			putCursorAtRoot();

			return true;
		}
	}

	/**Returns a string version of preorder and inorder traversals, each surrounded by brackets, 
	then concatenates them with a '|' character.**/
	public String toString() {

		String result = "";
		
		if (isEmpty()) {
			return result;
		}

		Iterator preO = iterator();
		Iterator inO = inOrder();

		String preString = "[";
		String inString = "[";

		while(preO.hasNext()) {
			preString += preO.next() + " ";
			inString += inO.next() + " ";
		}

		preString = preString.replaceAll("\\s+$", "]");
		inString = inString.replaceAll("\\s+$", "]");

		result = preString + " | " + inString;
		return result;

	}

	/**Returns unique hashcode of tree**/
	public int hashCode() {	

		Iterator preItr = iterator();
		Iterator inItr = inOrder();

		String result = "";
		
		while (preItr.hasNext()) {
			result += preItr.next().hashCode() + " ";
		}
		
		result += "|";
		
		while (inItr.hasNext()) {
			result += inItr.next().hashCode() + " ";
		}
		
		result = result.replace("\\s$", "");
		return result.hashCode();
	}

	/**Returns size of tree**/
	public int size() {
		return size;
	}

	public static void main(String[] args) {
		BinaryTree t = new BinaryTree(1L);
		t.putCursorAtRoot();
		t.attachLeftSonAtCursor(2L);
		t.attachRightSonAtCursor(3L);
		t.putCursorAtLeftSon();
		t.attachLeftSonAtCursor(4L);
		t.putCursorAtLeftSon();
		t.attachLeftSonAtCursor(5L);
		t.attachRightSonAtCursor(6L);
		t.putCursorAtRoot();
		t.putCursorAtRightSon();
		t.attachLeftSonAtCursor(7L);
		t.attachRightSonAtCursor(8L);

		Iterator itr2 = t.inOrder();
		while (itr2.hasNext()) {
			System.out.print(itr2.next() + " ");
		}
		System.out.println();
		System.out.println(t.toString());

		BinaryTree t2 = new BinaryTree(1L);
		t2.attachLeftSonAtCursor(2L);
		t2.attachRightSonAtCursor(3L);
		t2.putCursorAtLeftSon();
		t2.attachLeftSonAtCursor(4L);
		t2.attachRightSonAtCursor(6L);
		t2.putCursorAtLeftSon();
		t2.attachRightSonAtCursor(5L);
		t2.putCursorAtRoot();
		t2.attachRightSonAtCursor(3L);

		t2.putCursorAtLeftSon();
		Iterator it3 = t2.inOrder();
		while (it3.hasNext()) {
			System.out.print(it3.next() + " ");
		}
		System.out.println();
		System.out.println(t2.toString());
		t2.putCursorAtRoot();
		t2.putCursorAtLeftSon();
		t2.pruneFromCursor();
		System.out.println(t2.toString());
		System.out.println(t2.size);
	}
}