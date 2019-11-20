
/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {

    public boolean bruteForce = true;


	public KMP(String pattern, String text) {

	}
	 /**
     * Compute the match table for KMP search
     *
     * @param pattern
     * @return the table
     */
	public int[] tableBuilder(String pattern) {
	  if (pattern.length() == 0) {
        return null;
      } else if (pattern.length() == 1) {
        return new int[] { -1 };
      }
	  //build the table
      int[] M = new int[pattern.length()];
      M[0] = -1;
      M[1] = 0;
      int j = 0, pos = 2;
      while(pos < pattern.length()) {
        if(pattern.charAt(pos-1) == pattern.charAt(j)) {
          M[pos] = j + 1;
          pos++;j++;
        }else if(j>0) {
          j = M[j];
        }else {
          M[pos] = 0;
          pos++;
        }
      }
      return M;
	}
	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 *
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */
	public int search(String pattern, String text) {
	  if (pattern.length() == 0 || text.length() == 0)
        return -1;
	  int m = pattern.length(), n = text.length();
	  long s = 0,e=0;
	  //brute force search
	  if(bruteForce) {
	    s = System.nanoTime();
    	  for(int k = 0; k < (n - m);k++) {
    	    boolean found = true;
    	    for(int i = 0;i < m; i++) {
    	      if(pattern.charAt(i) != text.charAt(k+i)) {
    	        found = false;
    	        break;
    	      }
    	    }
    	    if(found) {
      	      e = System.nanoTime();
              System.out.println("brt Spend "+(e-s)+" nano seconds");
              bruteForce = false;
              search(pattern,text);
      	      return k;
              }
    	  }
	  }else {
    	  //KMP search

	    int[] M = tableBuilder(pattern);
	    s = System.nanoTime();
    	  int k= 0, i= 0;
    	  while(k + i < n) {
    	    if(pattern.charAt(i) == text.charAt(k+i)) {
    	      i++;
    	      if(i == m) {
    	        e = System.nanoTime();
                System.out.println("KMP Spend "+(e-s)+" nano seconds \n==================================");
    	        return k;
    	        }
    	    }else if(M[i] == -1){
    	      k = k + i + 1;
    	      i = 0;
    	    }else {
    	      k = k + i - M[i];
    	      i = M[i];
    	    }
    	  }
	  }
		return -1;
	}
}
