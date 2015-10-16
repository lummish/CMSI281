import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.IllegalArgumentException;
/**
A class which is passed three arguments of the following form: [d1][d2]...[d(k-1)][dk] n m
Where each [di] {@literal <} n and represents a digit of a number expressed in base n to be converted into base m. m is an optional argument, but if left
blank will default to 10. Output is of form [p1][p2]...[p(j - 1)][pj] where each [pi] {@literal <} m, and represents a number expressed in base m. Digits and 
bases are converted and stored respectively as longs, so any m or n larger than the maximum possible long will throw an exception, as will some numbers
where the sum of the digits is larger than the largest possible long.
**/
 
public class BaseConverter {
	/**
	Retuns a false if arguments are incorrectly entered. The first argument must be properly formatted as [d1][d2]...[dk], and the program must
	be passed at least 2 but no more than 3 arguments. If any digit [di] is greater than the base to be converted from, validArgs will throw an exception.
	**/
	public static boolean validArgs(String[] args) {
		if (args.length != 2 && args.length != 3) return false;
		else {
			
			if (args.length == 2 && Long.parseLong(args[1]) <= 1) 
				return false;

			else if (args.length == 3 && (Long.parseLong(args[1]) <= 1 || Long.parseLong(args[2]) <= 1)) {
				
				return false;
			}

			String str = args[0];

			Pattern p = Pattern.compile("[^\\[\\]\\d]");

			Matcher m = p.matcher(str);

			if(m.find()) {
				return false; //searches string for any non digit or bracket
							  //character and returns false if any occur.	
			}
			if (str.charAt(0) != '[') return false;
			//possible regex: "\\[[0-9]+\\]" if number of matches == number of '[' and number of ']' then this could work
			for (int i = 0; i < str.length() - 1; i++) {
				if (str.charAt(i) == ']' && str.charAt(i + 1) != '[') 
					return false;

				else if (str.charAt(i) == '[') {

					if (str.charAt(i + 1) == '[' || str.charAt(i + 1) == ']')
						return false;
				}
			}

			if (str.charAt(str.length() - 1) != ']') return false;

			String[] arg0 = 
				args[0].substring(1,args[0].length()).split("\\]\\[|\\]");
			for (int i = 0; i < arg0.length; i++) {

				if (Long.parseLong(arg0[i]) >= Long.parseLong(args[1])) //ensures each digit is positive less than
					return false;									   //the base
			}
			return true;
		}
	}
	/** 
	Outputs [p1][p2]...[pk] to standard output
	**/
	public static void main(String[] args) {
		if (validArgs(args)) { 
			long oldBase = Long.parseLong(args[1]);
			long newBase = (args.length == 2) ? 10 : Long.parseLong(args[2]);
			String result = "";
			String[] arg0 = 
				args[0].substring(1,args[0].length()).split("\\]\\[|\\]");
			
			List<Long> inNum = new ArrayList<>();

			for (int i = 0; i < arg0.length; i++) {
				inNum.add(Long.parseLong(arg0[i]));
			}
			
			boolean firstZero = true;
			int leadingZeros = 0;
			
			while (inNum.size() > 0) {

				firstZero = true;
				leadingZeros = 0;

				for (int i = 0; i < inNum.size(); i++) {
					
					long div = inNum.get(i) / newBase;
					
					if (div != 0) firstZero = false;

					if (i < inNum.size() - 1) {
						inNum.set(i + 1, inNum.get(i+1) + (inNum.get(i) - div * newBase) * oldBase);
					}
					else {
						result += "[" + (inNum.get(i) - (div * newBase)) + "]";
					}
					inNum.set(i, div);
					if (div == 0 && firstZero) { //will use to remove leading zeros
						leadingZeros++;
					}
				}
				if (inNum.size() > 1) {
					inNum = inNum.subList(leadingZeros, inNum.size());
				}
				else if (firstZero) {
					inNum.clear();
				}
			}

			//reverse result
			char c;
			String numString;

			for (int i = result.length() - 1; i >= 0; i--) {
				c = result.charAt(i);
				numString = "";
				if (c == '[') System.out.print(']');
				else if (c == ']') System.out.print('[');
				else {
					while (c != '[') {
						numString = c + numString;
						c = result.charAt(--i);
					}
					System.out.print(numString);
					i++;
				}
			}
			System.out.println();
		}
		else {
			throw new IllegalArgumentException();
		}

	}
}