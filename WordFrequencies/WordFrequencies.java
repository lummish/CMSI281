import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class WordFrequencies {
	public static void main(String[] args) {

		HashMap<String, Integer> map = new HashMap<>();
		Scanner in = new Scanner(System.in);
		in = in.useDelimiter("[^a-zA-Z0-9-']"); 
		String str = "";
		Integer curCount = new Integer(0);

		if (args.length > 0 && args[0].equals("-s")) {
			while (in.hasNext()) {
				str = in.next().replaceAll("[-']", "");
				if (str.equals("")) { //handles dashes separated by space
					continue;
				}
				curCount = map.get(str);

				if (curCount == null) {
					map.put(str, new Integer(1));
				} else {
					map.put(str, curCount + 1);
				}
			}
		} 
		else {
			while (in.hasNext()) {
				str = in.next().toUpperCase().replaceAll("[-']", "");
				if (str.equals("")) { //handles dashes separated by space
					continue;
				}
				curCount = map.get(str);

				if ( curCount == null) {
					map.put(str, new Integer(1));
				} else {
					map.put(str, curCount + 1);
				}
			}
		}

		ArrayList<String> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys);
		for (String k : keys) {
			System.out.println(k + " " + map.get(k));
		}

	}
}