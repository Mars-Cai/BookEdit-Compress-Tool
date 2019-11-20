import java.util.Scanner;

/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	public String compress(String input) {
	  StringBuffer output = new StringBuffer();
      int cursor = 1, windowSize = 100;

      output.append("[0|0|" + input.charAt(0) + "]");
      while (cursor < input.length()) {
          int length = 0, prevMatch = 0, match;
          while (true) {
              int endOfWindow = cursor-1;
              int startOfWindow = (cursor < windowSize) ? 0 : cursor-windowSize;
              String haystack = input.substring(startOfWindow, endOfWindow+1);
              String needle = input.substring(cursor, cursor+length+1);
              match = haystack.indexOf(needle);
              if (match != -1 && (cursor+length) <= input.length()-2) {
                  prevMatch = match;
                  length = length + 1;
              } else {
                  output.append("[" + (length == 0 ? 0 : cursor - (startOfWindow + prevMatch)) + "|" + length + "|" + input.charAt(cursor+length) + "]");
                  cursor = cursor + length + 1;
                  break;
              }
          }
      }
      return output.toString();

	}

	/**
	 * Take compressed input as a text string, decompress it, and return it as a
	 * text string.
	 */
	public String decompress(String compressed) {
	  Scanner scan = new Scanner(compressed);
      int cursor = 0, offset = 0, length = 0;
      String ch;
      StringBuffer output = new StringBuffer();

      scan.useDelimiter("\\[|\\]\\[|\\]|\\|");

      while (scan.hasNext()) {
          offset = scan.nextInt();
          length = scan.nextInt();
          ch = scan.next();
          for (int j = 0; j < length; j++) {
              output.append(output.charAt(cursor-offset));
              cursor++;
          }
          output.append(ch);
          cursor++;
      }
      scan.close();
      return output.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You can use this to print out any relevant
	 * information from your compression.
	 */
	public String getInformation() {
		return "";
	}
}
