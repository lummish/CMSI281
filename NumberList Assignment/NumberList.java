	/** An object of this class represents a number list, i.e., an ordered collection
    of integers, each of Java class <a href="http://docs.oracle.com/javase/7/docs/api/java/lang/Long.html">Long</a>, 
    with duplicates permitted. Be sure to read the Java documentation on
    <a href="http://docs.oracle.com/javase/7/docs/api/java/util/Collection.html">interface java.util.Collection</a>.
*/
import java.util.ArrayList; //for testing only
import java.util.Iterator;
import java.util.NoSuchElementException;

public class NumberList implements java.util.Collection {
	
	private Long[] lst;
	private int minFill;
	private int lastFull; //last filled index in array
	private int maxFill;

    /** Constructs an empty number list. */
    public NumberList() {
        this.maxFill = 1;
        this.minFill = 1;
        this.lastFull = -1;
        this.lst = new Long[1];
    }


    /** Constructs a number list from an array of Longs. Linear worst case time*/
    public NumberList( Long[] l ){
        
        this.maxFill = l.length * 2;
        this.lst = new Long[this.maxFill];
        this.minFill = this.maxFill / 4; //sets smallest number of filled indices at which array size will be cut
        this.lastFull = l.length - 1;

        for (int i = 0; i < l.length; i++) {
        	this.lst[i] = l[i];
        }

    }
    

    /** Increases by one the number of instances of the given element in this collection. Constant amortized time*/
    public boolean add ( Object obj ) {

        if (obj.equals(null)) throw new NullPointerException();

        if (obj instanceof Long) {
            this.lst[++this.lastFull] = (Long) obj;
        
            if (this.lastFull == this.maxFill - 1) { //if lst is filled, double its size.
            
                Long[] oldLst = this.lst;
                this.lst = new Long[this.maxFill * 2];
                this.maxFill *= 2;
                this.minFill = this.maxFill / 4;

                for (int i = 0; i < oldLst.length; i++) {
                    this.lst[i] = oldLst[i];
                }
            }

            return true;
        }
        else {
            return false;
        }
    }
    

    /** Adds all of the elements of the given number list to this one. Runs in linear worst case time as size of c increases*/
    public boolean addAll ( java.util.Collection c  ) { 
        
        if (c.equals(null)) throw new NullPointerException();
        
        if (c instanceof java.util.Collection) {

            Iterator itr = c.iterator();
            boolean bool = true;

            while( itr.hasNext() && bool ) {
                bool = this.add(itr.next());
            }

            return true; 
        
        }

        else {
            return false;
        }
    }
 

    /** Removes all of the elements from this collection. Worst case constant time.*/
    public void clear () {

        this.lst = new Long[1];
        this.lastFull = -1;
        this.maxFill = 1;
        this.minFill = 1; //???

    }
 

    /** Returns true iff this number list contains at least one instance of the specified element. Worst case linear time.*/
    public boolean contains ( Object obj ) {
        
        if (this.isEmpty()) return false;

        for (int i = 0; i < this.sizeIncludingDuplicates(); i++) {

        	if (this.lst[i].equals(obj)) return true;
        
        }

        return false;
    }
 

    /** Returns true iff this number list contains at least one instance of each element 
        in the specified list. Multiple copies of some element in the argument do not
        require multiple copies in this number list. Time increases like size * c.size()*/
    public boolean containsAll ( java.util.Collection c ) {
    
        Iterator itr_c = c.iterator();
        Boolean contains = true;
        
        while (itr_c.hasNext() && contains) {
            contains = this.contains(itr_c.next());
        }

        return contains;
    }


    /** Compares the specified object with this collection for equality. Worst Case Linear time*/
    public boolean equals ( Object obj ) {

        if (obj instanceof NumberList) {
            Iterator itr_1 = this.iterator();
            Iterator itr_2 = ((NumberList) obj).iterator();

            while (itr_1.hasNext() && itr_2.hasNext()) {

                if (! itr_1.next().equals(itr_2.next()) )
                    return false;

            }

            if ( itr_2.hasNext() || itr_1.hasNext() ) return false; //if lists are of different size, false;
            
            return true;

        } 

        else {
            return false;
        }
    }


    /** Returns the hashcode value for this collection. Linear worst case time*/
    public int hashCode () {        
        return this.toString().hashCode();
    }


    /** Returns true if this collection contains no elements. Constant worse case time*/
    public boolean isEmpty () {
    	//return this.lst[0] != null;
        return this.lastFull == -1;
    }


    /** Returns an iterator over the elements in this collection. Replicated elements should
        be "iterated over" just once. Constant worse case time*/

    public java.util.Iterator iterator () { 
        return new NumIterator();
    }

    private class NumIterator implements Iterator<Object> {

        private int cursor;
        private int last;

        public NumIterator() {
            this.cursor = 0;
            this.last = NumberList.this.lastFull + 1;
        }


        public boolean hasNext() {
            return this.cursor < this.last;
        }


        public Object next() {
            if (this.hasNext()) {
                return NumberList.this.lst[cursor++];
            }
            throw new NoSuchElementException();
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    /** Removes a single instance of the specified element from 
        this collection, if it is present. Linear worst case time*/
    public boolean remove ( Object obj ) {
        
        if (!(obj instanceof Long)) return false;

        Iterator itr = this.iterator();

        if (!this.contains(obj)) return false;

    	if (this.sizeIncludingDuplicates() > this.minFill) { //switch to iterator

    		for (int i = 0; i < this.sizeIncludingDuplicates(); i++ ) {

    			if (obj.equals(this.lst[i])) {

    				for (int j = i + 1; j < this.sizeIncludingDuplicates(); j++) {
    					this.lst[j-1] = this.lst[j];
    				}

    				this.lastFull--;

    				return true;
    			}
    		}
    		return false;
    	} 
        else {

            int newLastFull = this.sizeIncludingDuplicates();
            int newMinFill = this.minFill / 2;
            int newMaxFill = this.maxFill / 2;
            Long[] newLst = new Long[ newMaxFill ];
            int index = 0;

            while (itr.hasNext()) {

                Long n = (Long) itr.next();

                if (!n.equals((Long) obj)) {

                    newLst[index++] = n;

                }
            }

            this.lst = newLst;
            this.maxFill = newMaxFill;
            this.minFill = newMinFill;
            this.lastFull = index - 1;

            return true;
        }
    }


    /** Removes all of this collection's elements that are also contained 
        in the specified collection. Worst case n^2*/
    public boolean removeAll ( java.util.Collection c ) { //need to test
        
        if (this.equals(c)) { //if c is same as this, clear array
            this.clear();
            return true;
        }

        for (Object o : c) {
            while(this.remove((Long) o));
        }
        
        return true;
    }


	/** Retains only the elements in this collection that are contained in the specified collection. 
		 In other words, removes from this collection all of its elements that are not contained in the 
		 specified collection. Worst case n^2*/
	public boolean retainAll ( java.util.Collection c ) {
        
        if (c.isEmpty()) {
            this.clear();
            return true;
        }

        Iterator itr = this.iterator();
        NumberList removeList = new NumberList();

        while (itr.hasNext()) {
            
            Object cur = itr.next();
            
            if ( !c.contains(cur) ) {
                removeList.add(cur);
            }
        }

        this.removeAll(removeList);

        return true;
        
	}


    /** Returns the number of elements in this number list, including duplicates. Constant worse case time*/
    public int sizeIncludingDuplicates () {
        return this.lastFull + 1;
    }
    

    /** Returns a NumberList[] containing all of the elements in this collection, not including duplicates. 
    Worst case n^2*/
    public Long[] toArray () {
        
        if (this.isEmpty()) return new Long[0];

        NumberList no_dup = new NumberList(); 
        NumberList n = new NumberList();
        n.lastFull = this.lastFull;
        n.maxFill = this.maxFill;
        n.minFill = this.minFill;
        n.lst = new Long[this.lst.length];

        for (int i = 0; i < this.lst.length; i++) {
            n.lst[i] = this.lst[i];
        }

        while (!n.isEmpty()) { //While emptying n, add unique elements to result
            Long first = n.lst[0];
            no_dup.add(first);
            while(n.remove(first));
        }

        Long[] result = new Long[no_dup.sizeIncludingDuplicates()];
        int j = 0;

        for (Object l : no_dup) {
            result[j++] = (Long) l;
        } 

        return result;
    }


    /** Not supported for this class. */
    public Object[] toArray ( Object[] obj ) {
        throw new UnsupportedOperationException();
    }


    /** Returns the number of elements in this number list, not including duplicates. Worst case n^2 if all elements are duplicates, 
    amortized linear time*/
    public int size () {

        if (this.isEmpty()) return 0;

        NumberList n = new NumberList();
        n.lastFull = this.lastFull;
        n.maxFill = this.maxFill;
        n.minFill = this.minFill;
        n.lst = new Long[this.lst.length];

        for (int i = 0; i < this.lst.length; i++) {
            n.lst[i] = this.lst[i];
        }

        int count = 0;

        while (!n.isEmpty()) {
            count++;
            Object first = n.lst[0];
            while(n.remove(first));
        }
    
        return count;
    }


    /** Returns the number of instances of the given element in this number list. Worst case linear time*/
    public int count ( Object obj ) {

        int count = 0;
        Iterator itr = this.iterator();

        while (itr.hasNext()) {
            if ( itr.next().equals( (Long) obj )) count++;
        }
        
        return count;

    }
    

    /** This returns a stringy version of this number list. Worst case linear time.*/
    public String toString () { // overrides Object.toString()
        if (this.isEmpty()) return "[]";

        Iterator itr = this.iterator();
        String result = "[";
        
        while ( itr.hasNext() ) {
            result += itr.next() + ", ";
        }

        result = result.substring(0, result.length() - 2) + "]";

        return result;

    }
    

    /** This so-called "static factory" returns a new number list comprised of the numbers in the specified array.
        Note that the given array is long[], not Long[]. Worst case linear time*/
    public static NumberList fromArray ( long[] l ) {
        
        NumberList n = new NumberList();
        
        for (int i = 0; i < l.length; i++) {
            n.add((Long)l[i]);
        }

        return n;
    }

    
    /** This main method is just a comprehensive test program for the class. */
    public static void main ( String[] args ) {
        ArrayList<Long> ar = new ArrayList<Long>();
        
        ar.add( new Long(1) );
        ar.add( new Long(2) );
        ar.add( new Long(5) );
        ar.add( new Long(3) );
        ar.add( new Long(9) );
        ar.add( new Long(12) );

        Long[] l = {new Long(1), new Long(2), new Long(3), new Long(9), 
                    new Long(5), new Long(12)};

        String tst;

        //Constructor Test
        NumberList nl = new NumberList(l); 

        //size() test
        tst = (nl.sizeIncludingDuplicates() == ar.size()) ? "Good" : "Bad!";
        System.out.println("Testing sizeIncludingDuplicates()... " + tst);
        System.out.println();

        //isEmpty()
        tst = nl.isEmpty() ? "Bad!" : "Good";
        System.out.println("Testing isEmpty() for non-empty... " + tst);
        nl.clear();
        tst = nl.isEmpty() ? "Good" : "Bad!";
        System.out.println("Testing clear(), isEmpty()..." + tst);
        System.out.println();

        nl = new NumberList(l);

        //containsAll()
        tst = nl.containsAll(ar) ? "Good" : "Bad!";
        System.out.println("Testing contains(), containsAll()... " + tst);
        System.out.println();

        //add()
        try {
            tst = nl.add( (long) 25) ? "Good" : "Bad!";
            System.out.println("Testing add()... " + tst);
            nl.add(5);
        } catch( Exception e ) {
            System.out.println("Successful exception catch");
        }
  

        //add() when add exceeds maxfill
        for (long i = 0; i < 40; i++) {
            nl.add( i );
        }

        //sizeIncludingDuplicates(), size(), count()
        System.out.println(nl.toString());
        System.out.println("Size w/ duplicates (should be 47): " + nl.sizeIncludingDuplicates());
        System.out.println("size() and remove() (Should be 40): " + nl.size());
        
        try {
            nl.remove("Hello, world.");
        } 

        catch (Exception e) {
            System.out.println("Remove test with object of incorrect type test... Good");
        }
        System.out.println("Size w/ duplicates (ensuring no modifications to original array): " + nl.sizeIncludingDuplicates());
        System.out.println();
        
        Boolean bool = false;
        
        for ( int i = 0; i < l.length; i++ ) {
            bool = nl.count(l[i]) == 2;
        }

        tst = bool ? "Good" : "Bad!";
        System.out.println("Testing count()... " + tst);
        System.out.println("Max Fill: " + nl.maxFill);
        System.out.println("lastFull: " + nl.lastFull);
        System.out.println();
        
        nl.add( (long) 2);
        System.out.println("Should double in maxFill");
        System.out.println("Size (should be 48): " + nl.sizeIncludingDuplicates());
        System.out.println("Max Fill (should be 96): " + nl.maxFill);
        System.out.println("lastFull: " + nl.lastFull);
        System.out.println();

        //.equals
        NumberList nl_1 = new NumberList();
        Iterator itr = nl.iterator();
        
        while (itr.hasNext()) {
            nl_1.add(itr.next());
        }

        tst = nl_1.equals(nl) ? "Good" : "Bad"; //nl_1 max fill =/= nl maxFill
        System.out.println("Testing equals()... " + tst);
        tst = nl_1.hashCode() == nl.hashCode() ? "Good":"Bad!";
        System.out.println("Testing hashCode()... " + tst); //should come up with some more 
        System.out.println();

        //removeAll(), fromArray(), toString() ----- need to test removeall with smaller array
        long[] n = {(long) 1, (long) 2, (long) 3, (long) 5, 
                    (long) 9, (long) 12};
        NumberList nl_2 = NumberList.fromArray(n);
        

        nl = new NumberList();

        for (long i = 0; i < 40; i++) {
            nl.add( i );
        }

        System.out.println("Max Fill before Remove All from Self: " + nl_1.maxFill);
        System.out.println("Min Fill before Remove All from Self: " + nl_1.minFill);
        nl_1.removeAll(nl_1);
        System.out.println("Max Fill after Remove All from Self: " + nl_1.maxFill);
        System.out.println("Min Fill after Remove All from Self: " + nl_1.minFill);
        tst = (nl_1.isEmpty()) ? "Good":"Bad";

        System.out.println();
        System.out.println("Empty test... " + tst);
        System.out.println();

        for (long i = 0; i < 40; i++) {
            nl_1.add( i );
        }

        nl_1.removeAll(nl_2);
        nl.removeAll(nl_1); //should only be elements in nl_2 now
        tst = nl.equals(nl_2) ? "Good" : "Bad!";
        System.out.println("Testing removeAll()... " + tst);
        System.out.println("Max Fill after Remove All: " + nl.maxFill);
        System.out.println("Min Fill after Remove All: " + nl.minFill);
        System.out.println();
        //retainAll();
        nl = new NumberList();

        for (long i = 0; i < 40; i++) {
            nl.add( i );
        }

        //iterator tests
        try {
            
            itr = nl.iterator();
            
            while (true) {
                itr.next();
            }

        } catch (Exception e) {
           
            try {

                itr = nl.iterator();
                itr.remove();

            } catch (Exception f) {

                System.out.println("Testing Iterator... Good");
            }
        } 

        long[] m = {(long) 1, (long) 2, (long) 3, (long) 5, 
                    (long) 9, (long) 12};
        System.out.println();
        nl_2 = NumberList.fromArray(m);

        try {
            System.out.println(nl_2.count("c"));
        } 

        catch (Exception e) {

            System.out.println("Caught exception when string is arg.");
            System.out.println();

        }

        System.out.println(nl_2.toString());
        nl.retainAll(nl_2); //what if size is 0
        System.out.println(nl.toString());
        tst = nl.equals(nl_2) ? "Good" : "Bad";
        System.out.println("Testing retainAll()... " + tst);
        
        Long[] p = nl.toArray();
        String toArrayString = "{";
        for (int i = 0; i < nl.sizeIncludingDuplicates(); i++) {
            toArrayString += p[i] + ", ";
        }
        toArrayString = toArrayString.substring(
                            0, toArrayString.length() - 2) + "}";

        tst = toArrayString.equals(nl.toString()) ? "Good":"Bad";

        NumberList nl_3 = new NumberList();
        nl.retainAll(nl_3);
        tst = nl.isEmpty() ? "Good":"Bad";
        System.out.println("Testing retainAll() with empty arg... " + tst);
        System.out.println("Testing toArray()... " + tst);

        System.out.println(nl_2.toString());
        nl_2.add( (long) 2);
        nl_2.add( new Long(3));
        System.out.println(nl_2.size());

        Long[] littleTst = {new Long(1),new Long(2),new Long(3), new Long(4)};
        NumberList nl_4 = new NumberList(littleTst);
        
        nl_4.remove(new Long(1));
        System.out.println("1: " + nl_4.toString());
        System.out.println("Size: " + nl_4.size());
        System.out.println("maxFill: " + nl_4.maxFill);
        System.out.println("minFill: " + nl_4.minFill);
        System.out.println("lastFull: " + nl_4.lastFull);

        nl_4.remove(new Long(2));
        System.out.println("2: " + nl_4.toString());
        System.out.println("Size: " + nl_4.size());
        System.out.println("maxFill: " + nl_4.maxFill);
        System.out.println("minFill: " + nl_4.minFill);
        System.out.println("lastFull: " + nl_4.lastFull);

        nl_4.remove(new Long(3));
        System.out.println("3: " + nl_4.toString());
        System.out.println("Size: " + nl_4.size());
        System.out.println("maxFill: " + nl_4.maxFill);
        System.out.println("minFill: " + nl_4.minFill);
        System.out.println("lastFull: " + nl_4.lastFull);

        nl_4.remove(new Long(4));
        System.out.println("4: " + nl_4.toString());
        System.out.println("Size: " + nl_4.size());
        System.out.println("maxFill: " + nl_4.maxFill);
        System.out.println("minFill: " + nl_4.minFill);
        System.out.println("lastFull: " + nl_4.lastFull);

        nl_4.add(new Long(5));
        System.out.println("add 5: " + nl_4.toString());
        System.out.println("Size: " + nl_4.size());
        System.out.println("maxFill: " + nl_4.maxFill);
        System.out.println("minFill: " + nl_4.minFill);
        System.out.println("lastFull: " + nl_4.lastFull);

        nl_4.add(new Long(5));
        System.out.println("add 5: " + nl_4.toString());
        System.out.println("Size: " + nl_4.size());
        System.out.println("maxFill: " + nl_4.maxFill);
        System.out.println("minFill: " + nl_4.minFill);
        System.out.println("lastFull: " + nl_4.lastFull);

        nl_4.add(new Long(5));
        System.out.println("add 5: " + nl_4.toString());
        System.out.println("Size: " + nl_4.size());
        System.out.println("maxFill: " + nl_4.maxFill);
        System.out.println("minFill: " + nl_4.minFill);
        System.out.println("lastFull: " + nl_4.lastFull);



    }   
}
