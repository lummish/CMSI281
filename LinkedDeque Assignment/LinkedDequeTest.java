import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedDequeTest {
	
	public static int success_count = 0;
	public static int test_count = 0;
	public static int total_success = 0;
	public static int total_tests = 0;
	public static final int NUM_TESTS = 20;

	public static void print_success( String test, boolean b ) {
		String success = b ? "Success!" : "Failure.";
		System.out.println(test + "... " + success);
	}

	public static void print_success( String test, boolean b, String explain ) {
		String success = b ? "Success!" : "Failure.";
		System.out.println(test + "... " + success + " " + explain);
	}

	public static void end_test( String test ) {
		System.out.println();
		System.out.println(success_count + "/" + test_count + " Tests Passed for " + test);
		total_tests += test_count;
		total_success += success_count;
		success_count = 0;
		test_count = 0;
		System.out.println();
	}

	public static void final_output() {
		System.out.println("Total: " + total_success + "/" + total_tests);
		
		if (total_success == total_tests && total_tests == NUM_TESTS) {
			System.out.println("All tests passed, jeez louise!");
		}

		if (total_success == total_tests && total_tests != NUM_TESTS) {
			System.out.println("Looks like you skipped some tests. " +
				"You can still do better.");
		}
		System.out.println();
		
		total_success = 0;
		total_tests = 0;
	}

	public static void insertLeftTest() {
		
		LinkedDeque d_1 = new LinkedDeque();
		LinkedDeque d_2 = new LinkedDeque();
		LinkedDeque d_3 = new LinkedDeque();

		System.out.println();
		System.out.println("insertLeft() Tests: ");

		try { 
			test_count++;
			d_1.insertLeft( null ); //null test
			print_success("insertLeft( null )", false);
		}
		
		catch (Exception e) {
			print_success("insertLeft( null )", true);
			success_count++;
		}

		try {
			test_count++;

			d_1.insertLeft( new Long( 1 ));
			d_1.insertLeft( new Long( 2 ));
			d_1.insertLeft( new Long( 3 ));

			String d_1_string = d_1.toString();
			boolean check = d_1_string.equals("[3, 2, 1]");

			if (check) success_count++;

			print_success("insertLeft 3 Longs", check);
		}

		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}

		try {
			test_count++;

			d_2.insertLeft( 1 );
			d_2.insertLeft( 2 );
			d_2.insertLeft( 3 );

			String d_2_string = d_2.toString();
			boolean check = d_2_string.equals("[3, 2, 1]");

			if (check) success_count++;

			print_success("insertLeft 3 int", check);

		}

		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}

		try {
			test_count++;

			Iterator itr_1 = d_1.iterator();
			Iterator itr_2 = d_2.iterator();

			while (itr_1.hasNext()) {
				d_3.insertLeft( itr_1.next() );
			}


			for (int i = 0; i < 3; i++) {
				d_3.deleteLeft();
			}

			while (itr_2.hasNext()) {
				d_3.insertLeft( itr_2.next() );
			}

			boolean check = d_3.toString().equals("[1, 2, 3]"); // need to check reverse

			print_success("insertions after delete", check);

			if (check) success_count++;
		}

		catch (Exception e) {
			if ( e instanceof UnsupportedOperationException ) {
				System.out.println("Test skipped, iterator() not supported.");
				test_count--;
			} 
			else {
				print_success("insertions after delete", false);
			}

		}

		try {
			test_count++;

			d_1 = new LinkedDeque();
			
			d_1.insertLeft("Hello");
			d_1.insertLeft("World");
			d_1.insertLeft(1);

			print_success("multiple object types", true);

			success_count++;
		}

		catch (Exception e) {
			print_success("multiple object types", false);
		}

		end_test("insertLeft()");
	}

	public static void insertRightTest() {
		LinkedDeque d_1 = new LinkedDeque();
		LinkedDeque d_2 = new LinkedDeque();
		LinkedDeque d_3 = new LinkedDeque();

		System.out.println("insertRight() Tests: ");

		try { 
			test_count++;
			d_1.insertRight( null ); //null test
			print_success("insertRight( null )", false);
		}
		
		catch (Exception e) {
			print_success("insertRight( null )", true);
			success_count++;
		}

		try {
			test_count++;

			d_1.insertRight( new Long( 1 ));
			d_1.insertRight( new Long( 2 ));
			d_1.insertRight( new Long( 3 ));

			String d_1_string = d_1.toString();
			boolean check = d_1_string.equals("[1, 2, 3]");

			if (check) success_count++;

			print_success("insertRight 3 Longs", check);
		}

		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}

		try {
			test_count++;

			d_2.insertRight( 1 );
			d_2.insertRight( 2 );
			d_2.insertRight( 3 );

			String d_2_string = d_2.toString();
			boolean check = d_2_string.equals("[1, 2, 3]");

			if (check) success_count++;

			print_success("insertRight 3 int", check);

		}

		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}

		try {
			test_count++;

			Iterator itr_1 = d_1.iterator();
			Iterator itr_2 = d_2.iterator();

			while (itr_1.hasNext()) {
				d_3.insertRight( itr_1.next() );
			}


			for (int i = 0; i < 3; i++) {
				d_3.deleteRight();
			}

			while (itr_2.hasNext()) {
				d_3.insertRight( itr_2.next() );
			}

			boolean check = d_3.toString().equals("[1, 2, 3]"); // need to check reverse
			
			print_success("insertions after delete", check);

			if (check) success_count++;

		}

		catch (Exception e) {
			if ( e instanceof UnsupportedOperationException ) {
				System.out.println("Test skipped, iterator() not supported.");
				test_count--;
			} 
			else {
				print_success("insertions after delete", false);
				System.out.println(e.getLocalizedMessage());
				System.out.println(e.getMessage());
				System.out.println(Arrays.toString(e.getStackTrace()));
			}

		}

		try {
			test_count++;

			d_1 = new LinkedDeque();
			
			d_1.insertRight("Hello");
			d_1.insertRight("World");
			d_1.insertRight(1);

			print_success("multiple object types", true);

			success_count++;
		}

		catch (Exception e) {
			print_success("multiple object types", false);
		}

		end_test("insertRight()");
	}

	public static void deleteLeftTest() {
		
		System.out.println( "deleteLeft() Tests:" );

		try {
			test_count++;

			LinkedDeque d_1 = new LinkedDeque();
			
			d_1.deleteLeft();
			print_success("delete for empty list", false);
		}
		catch (Exception e) {
			if (e instanceof NoSuchElementException) {
				print_success("delete for empty list", true);
				success_count++;
			}

			else {
				print_success("delete for empty list", false, "Wrong exception type.");
			}
		}

		try {
			test_count++;

			LinkedDeque d_1 = new LinkedDeque();
			Integer[] int_array = new Integer[] {1, 3, 5, 7, 9, 11};

			for ( int i = 0; i < int_array.length; i++ ) {
				d_1.insertRight(int_array[i]);
			}

			for ( int i = 0; i < 3; i++ ) {
				d_1.deleteLeft();
			}

			boolean check = d_1.toString().equals("[7, 9, 11]");

			print_success("deleteLeft() of same type", check);

			if (check) success_count++;
		}
		catch (Exception e) {
			print_success("deleteLeft() of same type", false);
		}

		try {
			test_count++;

			LinkedDeque d_1 = new LinkedDeque();
			Integer[] int_array = new Integer[] {1, 3, 5, 7, 9, 11};
			String[] string_array = new String[] {"Hello", "World", "Bingo"};

			for ( int i = 0; i < int_array.length; i++ ) {
				d_1.insertRight(int_array[i]);
			}
			for ( int i = 0; i < string_array.length; i++ ) {
				d_1.insertRight(string_array[i]);
			}

			for ( int i = 0; i < 8; i++ ) {
				d_1.deleteLeft();
			}

			boolean check = d_1.toString().equals("[Bingo]");
			print_success("deleteLeft() of multiple types", check);

			if (check) success_count++;
		}
		catch (Exception e) {
			print_success("deleteLeft() of multiple types", false);
		}

		end_test("deleteLeft()");
	}

	public static void deleteRightTest() {
		
		System.out.println( "deleteRight() Tests:" );

		try {
			test_count++;

			LinkedDeque d_1 = new LinkedDeque();
			
			d_1.deleteRight();
			print_success("delete for empty list", false);
		}
		catch (Exception e) {
			if (e instanceof NoSuchElementException) {
				print_success("deleteRight() for empty list", true);
				success_count++;
			}

			else {
				print_success("deleteRight() for empty list", false, "Wrong exception type.");
			}
		}

		try {
			test_count++;

			LinkedDeque d_1 = new LinkedDeque();
			Integer[] int_array = new Integer[] {1, 3, 5, 7, 9, 11};

			for ( int i = 0; i < int_array.length; i++ ) {
				d_1.insertLeft(int_array[i]);
			}

			for ( int i = 0; i < 3; i++ ) {
				d_1.deleteRight();
			}

			boolean check = d_1.toString().equals("[11, 9, 7]");

			print_success("deleteRight() of same type", check);

			if (check) success_count++;
		}
		catch (Exception e) {
			print_success("deleteRight() of same type", false);
		}

		try {
			test_count++;

			LinkedDeque d_1 = new LinkedDeque();
			Integer[] int_array = new Integer[] {1, 3, 5, 7, 9, 11};
			String[] string_array = new String[] {"Hello", "World", "Bingo"};

			for ( int i = 0; i < int_array.length; i++ ) {
				d_1.insertLeft(int_array[i]);
			}
			for ( int i = 0; i < string_array.length; i++ ) {
				d_1.insertLeft(string_array[i]);
			}

			for ( int i = 0; i < 8; i++ ) {
				d_1.deleteRight();
			}

			boolean check = d_1.toString().equals("[Bingo]");
			print_success("deleteRight() of multiple types", check);

			if (check) success_count++;
		}
		catch (Exception e) {
			print_success("deleteRight() of multiple types", false);
		}

		end_test("deleteRight()");
	}

	public static void leftTest() {
		try {
			test_count++;

			System.out.println( "left() Tests:" );

			LinkedDeque d_1 = new LinkedDeque();
			LinkedDeque d_2 = new LinkedDeque();
			Long[] l_ar = new Long[] {2L, 3L, 5L, 7L, 11L, 13L};
			Long first;

			for ( Long l : l_ar ) {
				d_1.insertRight(l);
			}

			for ( int i = 0; i < l_ar.length; i++ ) {
				first = (Long) d_1.left();
				d_1.deleteLeft();
				d_2.insertRight(first);				
			}

			boolean check = d_2.toString().equals( "[2, 3, 5, 7, 11, 13]" );
			print_success( "left() on valid elements", check );
			if ( check ) success_count++;

			test_count++;

			check = d_1.left() == null;
			print_success( "left() on empty deque", check );
			if ( check ) success_count++;
		}
		catch ( Exception e ) {
			print_success( "left()", false, "Unexpected Exception");
		}

		end_test( "left()" );
	}

	public static void rightTest() {
		try {
			test_count++;

			System.out.println( "right() Tests:" );

			LinkedDeque d_1 = new LinkedDeque();
			LinkedDeque d_2 = new LinkedDeque();
			Long[] l_ar = new Long[] {2L, 3L, 5L, 7L, 11L, 13L};
			Long first;

			for ( Long l : l_ar ) {
				d_1.insertRight(l);
			}

			for ( int i = 0; i < l_ar.length; i++ ) {
				first = (Long) d_1.right();
				d_1.deleteRight();
				d_2.insertRight(first);				
			}

			boolean check = d_2.toString().equals( "[13, 11, 7, 5, 3, 2]" );
			print_success( "right() on valid elements", check );
			if ( check ) success_count++;

			test_count++;

			check = d_1.right() == null;
			print_success( "right() on empty deque", check );
			if ( check ) success_count++;
		}
		catch ( Exception e ) {
			print_success( "right()", false, "Unexpected Exception");
		}

		end_test( "right()" );
	}

	public static void main(String[] args) {
		insertLeftTest();
		insertRightTest();
		deleteLeftTest();
		deleteRightTest();
		leftTest();
		rightTest();
		final_output();
	}
}