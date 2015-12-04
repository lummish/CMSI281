public class BinaryTreeTester {

	public static int success_count = 0;
	public static int test_count = 0;
	public static int total_success = 0;
	public static int total_tests = 0;
	public static final int NUM_TESTS = 20; //will need to change

	public static void constructTest() {
		String testName = "Constructor Test (Null)";
		try {
			BinaryTree t = new BinaryTree(null);
			print_success(testName, false);
		}
		catch (Exception e) {
			print_success(testName, true);
		}
		end_test("Constructor");
	}

	public static void containsTest() {
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
		
		try {
			System.out.println("Contains Test (1-8)");
			for (Long i = 0L; i < 9L; i++) {
				print_success(i + "" , t.contains(i));
			}
		}
		catch (Exception e) {
			System.out.println("FAILURE");
		}

		print_success("Contains (for object not in tree)", !t.contains("Hello"));
		print_success("Contains (null)", !t.contains(null));
		end_test("Contains");
	}

	public static void similarTest() {
		System.out.println("Tests for similar(): ");
		System.out.println();

		BinaryTree t1 = new BinaryTree(1L);
		t1.putCursorAtRoot();
		t1.attachLeftSonAtCursor(2L);
		t1.attachRightSonAtCursor(3L);
		t1.putCursorAtLeftSon();
		t1.attachLeftSonAtCursor(4L);
		t1.putCursorAtLeftSon();
		t1.attachLeftSonAtCursor(5L);
		t1.attachRightSonAtCursor(6L);
		t1.putCursorAtRoot();
		t1.putCursorAtRightSon();
		t1.attachLeftSonAtCursor(7L);
		t1.attachRightSonAtCursor(8L);

		BinaryTree t2 = new BinaryTree(1L);
		t2.putCursorAtRoot();
		t2.attachLeftSonAtCursor(5L);
		t2.attachRightSonAtCursor(8L);
		t2.putCursorAtLeftSon();
		t2.attachLeftSonAtCursor(9L);
		t2.putCursorAtLeftSon();
		t2.attachLeftSonAtCursor(2L);
		t2.attachRightSonAtCursor(3L);
		t2.putCursorAtRoot();
		t2.putCursorAtRightSon();
		t2.attachLeftSonAtCursor(6L);
		t2.attachRightSonAtCursor(7L);

		BinaryTree t3 = new BinaryTree(1L);
		t3.putCursorAtRoot();
		t3.attachLeftSonAtCursor(5L);
		t3.attachRightSonAtCursor(8L);
		t3.putCursorAtLeftSon();
		t3.attachLeftSonAtCursor(9L);
		t3.putCursorAtLeftSon();
		t3.attachLeftSonAtCursor(2L);
		t3.attachRightSonAtCursor(3L);
		t3.putCursorAtRightSon();
		t3.attachLeftSonAtCursor(6L);
		t3.attachRightSonAtCursor(7L);

		print_success("\t(same size and structure)", t1.similar(t2));
		print_success("\t(same size and structure)", t2.similar(t1));
		print_success("\t(same size, different structure)", !t1.similar(t3));
		print_success("\t(compare to wrong class)", !t1.similar("Hello"));
		print_success("\t(compare to null)", !t1.similar(null));

		end_test("Similar");
	}

	public static void equalsTest() {
		System.out.println("Tests for equals(): ");
		System.out.println();

		BinaryTree t1 = new BinaryTree(1L);
		t1.putCursorAtRoot();
		t1.attachLeftSonAtCursor(2L);
		t1.attachRightSonAtCursor(3L);
		t1.putCursorAtLeftSon();
		t1.attachLeftSonAtCursor(4L);
		t1.putCursorAtLeftSon();
		t1.attachLeftSonAtCursor(5L);
		t1.attachRightSonAtCursor(6L);
		t1.putCursorAtRoot();
		t1.putCursorAtRightSon();
		t1.attachLeftSonAtCursor(7L);
		t1.attachRightSonAtCursor(8L);

		BinaryTree t2 = new BinaryTree(1L);
		t2.putCursorAtRoot();
		t2.attachLeftSonAtCursor(2L);
		t2.attachRightSonAtCursor(3L);
		t2.putCursorAtLeftSon();
		t2.attachLeftSonAtCursor(4L);
		t2.putCursorAtLeftSon();
		t2.attachLeftSonAtCursor(5L);
		t2.attachRightSonAtCursor(6L);
		t2.putCursorAtRoot();
		t2.putCursorAtRightSon();
		t2.attachLeftSonAtCursor(7L);
		t2.attachRightSonAtCursor(8L);

		BinaryTree t3 = new BinaryTree(new Integer(1));
		t3.putCursorAtRoot();
		t3.attachLeftSonAtCursor(new Integer(2));
		t3.attachRightSonAtCursor(new Integer(3));
		t3.putCursorAtLeftSon();
		t3.attachLeftSonAtCursor(new Integer(4));
		t3.putCursorAtLeftSon();
		t3.attachLeftSonAtCursor(new Integer(5));
		t3.attachRightSonAtCursor(new Integer(6));
		t3.putCursorAtRoot();
		t3.putCursorAtRightSon();
		t3.attachLeftSonAtCursor(new Integer(7));
		t3.attachRightSonAtCursor(new Integer(8));

		print_success("\t(equal trees)", t1.equals(t2));
		print_success("\t(equal values, different types)", !t1.equals(t3));
		print_success("\t(compare to null)", !t1.equals(null)); //what if tree is empty?

		end_test("equals()");
	}

	public static void hashCodeTest() {
		System.out.println("Tests for hashCode");

		BinaryTree t1 = new BinaryTree(1L);
		t1.putCursorAtRoot();
		t1.attachLeftSonAtCursor(2L);
		t1.attachRightSonAtCursor(3L);
		t1.putCursorAtLeftSon();
		t1.attachLeftSonAtCursor(4L);
		t1.putCursorAtLeftSon();
		t1.attachLeftSonAtCursor(5L);
		t1.attachRightSonAtCursor(6L);
		t1.putCursorAtRoot();
		t1.putCursorAtRightSon();
		t1.attachLeftSonAtCursor(7L);
		t1.attachRightSonAtCursor(8L);

		BinaryTree t2 = new BinaryTree(1L);
		t2.putCursorAtRoot();
		t2.attachLeftSonAtCursor(2L);
		t2.attachRightSonAtCursor(3L);
		t2.putCursorAtLeftSon();
		t2.attachLeftSonAtCursor(4L);
		t2.putCursorAtLeftSon();
		t2.attachLeftSonAtCursor(5L);
		t2.attachRightSonAtCursor(6L);
		t2.putCursorAtRoot();
		t2.putCursorAtRightSon();
		t2.attachLeftSonAtCursor(7L);
		t2.attachRightSonAtCursor(8L);

		BinaryTree t3 = new BinaryTree(1L);
		t3.putCursorAtRoot();
		t3.attachLeftSonAtCursor(5L);
		t3.attachRightSonAtCursor(8L);
		t3.putCursorAtLeftSon();
		t3.attachLeftSonAtCursor(9L);
		t3.putCursorAtLeftSon();
		t3.attachLeftSonAtCursor(2L);
		t3.attachRightSonAtCursor(3L);
		t3.putCursorAtRightSon();
		t3.attachLeftSonAtCursor(6L);
		t3.attachRightSonAtCursor(7L);

		print_success("equal trees, same hashcode", t1.hashCode() == t2.hashCode());
		print_success("different trees, different hashcode", t2.hashCode() != t3.hashCode());
		end_test("hashCode");
	}

	//need to test if pruning can result in empty tree
	public static void print_success( String test, boolean b ) {
		test_count++;
		String success = b ? "Success!" : "Failure.";
		if (b) {
			success_count++;
		}
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

	public static void main(String[] args) {
		similarTest();
		equalsTest();
		hashCodeTest();
	}
}