
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is passed the full text
 * to be encoded or decoded, so this is a good place to construct the tree. You should store this
 * tree in a field and then use it in the encode and decode methods.
 */
public class HuffmanCoding {

    private HuffmanTree huffmanTree;
    private HashMap<Character, Integer> frequency;
    private HashMap<Character, String> huffmanCodes;

    /**
     * This would be a good place to compute and store the tree.
     */
    public HuffmanCoding(String text) {
      //first, get the frequency of each character
      this.frequency = new HashMap<Character, Integer>();
      for (char c : text.toCharArray()) {
          if (frequency.containsKey(c)) {
              frequency.put(c, frequency.get(c) + 1);
          } else {
              frequency.put(c, 1);
          }
      }
      // then add all leaves into the priority queue
      PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
      for (char c : frequency.keySet()) {
          HuffmanNode n = new HuffmanNode(c);
          n.setFrequence(frequency.get(c));
          queue.offer(n);
      }
      // then iterate through all leaves, poll them out and build towards the root.
      int size = queue.size();
      for (int i = 1; i < size; i++) {
          HuffmanNode n1 = queue.poll();
          HuffmanNode n2 = queue.poll();
          HuffmanNode parentNode = new HuffmanNode('\0');
          parentNode.setFrequence(n1.getFrequence() + n2.getFrequence());
          parentNode.setLeftNode(n1);
          parentNode.setRightNode(n2);
          n1.setParent(parentNode);
          n2.setParent(parentNode);
          queue.offer(parentNode);
      }
        this.huffmanTree = new HuffmanTree(queue.poll());

        // if node has code c, assign 0 to the left child, 1 to the right child
        this.huffmanCodes = new HashMap<>();

        Stack<HuffmanNode> stack = new Stack<>();

        stack.push(huffmanTree.getRoot());

        while (!stack.isEmpty()) {
            HuffmanNode pop = stack.pop();
            HuffmanNode left = pop.getLeftNode();
            HuffmanNode right = pop.getRightNode();
            // if left != null, then there must be value in right
            if (left != null) {
                left.setCoding(pop.getCoding() + '0');
                stack.push(left);
                right.setCoding(pop.getCoding() + '1');
                stack.push(right);
            } else {
              huffmanCodes.put(pop.getCha(), pop.getCoding());
            }
        }
    }

    /**
     * Take an input string, text, and encode it with the stored tree. Should return the encoded
     * text as a binary string, that is, a string containing only 1 and 0.
     */
    public String encode(String text) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray())
            encoded.append(huffmanCodes.get(c));
        return encoded.toString();
    }

    /**
     * Take encoded input as a binary string, decode it using the stored tree, and return the
     * decoded text as a text string.
     */
    public String decode(String text) {
        StringBuilder decoded = new StringBuilder();
        char[] charArray = text.toCharArray();
        int index = 0;
        HuffmanNode root = huffmanTree.getRoot();
        HuffmanNode nodePointer = root;
        while (index < charArray.length) {
            char charPointer = charArray[index];
            if (charPointer == '0') {
                nodePointer = nodePointer.getLeftNode();
                if (nodePointer.getLeftNode() == null) {
                    decoded.append(nodePointer.getCha());
                    nodePointer = root;
                }
            } else if (charPointer == '1') {
                nodePointer = nodePointer.getRightNode();
                if (nodePointer.getLeftNode() == null) {
                    decoded.append(nodePointer.getCha());
                    nodePointer = root;
                }
            }
            index++;
        }
        return decoded.toString();
    }

    /**
     * The getInformation method is here for your convenience, you don't need to fill it in if you
     * don't wan to. It is called on every run and its return value is displayed on-screen. You
     * could use this, for example, to print out the encoding tree.
     */
    public String getInformation() {
        // prints out all char and their code
        StringBuilder output = new StringBuilder("\nThe encoding of each char:\n");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet())
          output.append(entry.getKey()+"="+entry.getValue()+"   ");
        return output.toString();
    }

    private class HuffmanTree {
        private HuffmanNode root;

        public HuffmanTree(HuffmanNode root) {
            this.root = root;
        }

        public HuffmanNode getRoot() {
            return root;
        }
    }


    private class HuffmanNode implements Comparable<HuffmanNode> {

        private final char cha;
        private String coding = "";
        private int frequency = 0;
        @SuppressWarnings("unused")
        private HuffmanNode parent;
        private HuffmanNode leftNode;
        private HuffmanNode rightNode;

        public HuffmanNode(char cha) {
            this.cha = cha;
        }

        @Override
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }

        public int getFrequence() {
            return frequency;
        }

        public void setFrequence(int frequence) {
            this.frequency = frequence;
        }

        public char getCha() {
            return cha;
        }

        public String getCoding() {
            return this.coding;
        }

        public void setCoding(String coding) {
            this.coding = coding;

        }

        public void setParent(HuffmanNode parent) {
            this.parent = parent;
        }

        public HuffmanNode getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(HuffmanNode leftNode) {
            this.leftNode = leftNode;
        }

        public HuffmanNode getRightNode() {
            return rightNode;
        }

        public void setRightNode(HuffmanNode rightNode) {
            this.rightNode = rightNode;
        }

        @Override
        public String toString() {
            return "char: " + this.cha + ", coding: " + this.coding + ".";
        }

    }
}