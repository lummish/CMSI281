import java.util.Arrays;

public class BaseTester {
	public static void main(String[] args) {
		BaseConverter b = new BaseConverter();
		
		System.out.println("Tests with different numbers of correct args");
		System.out.println();

		String[] s = {"[11][1][3][4]", "13", "16"};

		System.out.print("3 correct args (should give result): ");
		try {
			b.main(s); //3 args
		} 
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for wrong number of args.");
		}
		
		System.out.print("2 correct args (should give result): ");
		try {
			b.main(Arrays.copyOfRange(s, 0, 2)); //2
		} 
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for for wrong number of args.");
		}
		
		System.out.print("1 correct arg (should throw Exception): ");
		try {
			b.main(Arrays.copyOfRange(s, 0, 1)); //1 arg
		} 
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for for wrong number of args.");
		}
		
		System.out.println();

		System.out.println("Tests with first arg bad brackets");
		
		System.out.println();		

		s[0] = "[12]][1][3][4]";
		System.out.print("[12]]: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for bad brackets");
		}
		
		s[0] = "[[12][1][3][4]";
		System.out.print("[[12]: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for bad brackets");
		}

		s[0] = "[12][[1][3][4]";
		System.out.print("[12][[1]: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for bad brackets");
		}

		s[0] = "]12][1][3][4]";
		System.out.print("] as first char: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for bad brackets");
		}

		s[0] = "[12][1][3][4[";
		System.out.print("[ as last char: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for bad brackets");
		}

		System.out.println();

		s[0] = "[a][b]";
		System.out.print("Non bracket or number character: ");

		System.out.println();

		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown for non-essential character");
		}
		System.out.println();

		s[0] = "[1][2][3][4]";
		s[1] = "3";

		System.out.print("Number greater than base in 1st arg: ");

		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown, digit too large");
		} 

		System.out.println();
		System.out.println("Bad bases");
		System.out.println();

		s[1] = "0";
		System.out.print("Base n = 0: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown, base too small");
		} 

		s[1] = "1";
		System.out.print("Base n = 1: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown, base too small");
		} 

		s[1] = "5";
		s[2] = "0";
		System.out.print("Base m = 0: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown, base too small");
		} 

		s[2] = "1";
		System.out.print("Base m = 1: ");
		try {
			b.main(s);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Exception thrown, base too small");
		} 
	}
}