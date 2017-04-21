/**
 * Created by GozdeDogan on 18.04.2017.
 */
import java.util.*;
import java.io.*;

/** Class to represent and build a Huffman tree.
 *   @author Koffman and Wolfgang
 * */

public class HuffmanTree implements Serializable {

    // Nested Classes
    /** A datum in the Huffman tree. */
    public static class HuffData implements Serializable {
        // Data Fields
        /** The weight or probability assigned to this HuffData. */
        private double weight;

        /** The alphabet symbol if this is a leaf. */
        private Character symbol;

        public HuffData(double weight, Character symbol) {
            this.weight = weight;
            this.symbol = symbol;
        }
    }

    // Data Fields
    /** A reference to the completed Huffman tree. */
    private BinaryTree <HuffData> huffTree;

    /** A Comparator for Huffman trees; nested class. */
    private static class CompareHuffmanTrees implements Comparator < BinaryTree < HuffData >> {
        /** Compare two objects.
         @param treeLeft The left-hand object
         @param treeRight The right-hand object
         @return -1 if left less than right,
         0 if left equals right,
         and +1 if left greater than right
         */
        public int compare(BinaryTree < HuffData > treeLeft, BinaryTree < HuffData > treeRight) {
            double wLeft = treeLeft.getData().weight;
            double wRight = treeRight.getData().weight;
            return Double.compare(wLeft, wRight);
        }
    }

    /** Builds the Huffman tree using the given alphabet and weights.
     post:  huffTree contains a reference to the Huffman tree.
     @param symbols An array of HuffData objects
     */
    public void buildTree(HuffData[] symbols) {
        Queue < BinaryTree < HuffData >> theQueue = new PriorityQueue < BinaryTree < HuffData >>(symbols.length, new CompareHuffmanTrees());
        // Load the queue with the leaves.
        for (HuffData nextSymbol : symbols) {
            BinaryTree < HuffData > aBinaryTree = new BinaryTree < HuffData > (nextSymbol, null, null);
            theQueue.offer(aBinaryTree);
        }

        // Build the tree.
        while (theQueue.size() > 1) {
            BinaryTree < HuffData > left = theQueue.poll();
            BinaryTree < HuffData > right = theQueue.poll();
            double wl = left.getData().weight;
            double wr = right.getData().weight;
            HuffData sum = new HuffData(wl + wr, null);
            BinaryTree < HuffData > newTree = new BinaryTree < HuffData > (sum, left, right);
            theQueue.offer(newTree);
        }

        // The queue should now contain only one item.
        huffTree = theQueue.poll();
    }

    /** Outputs the resulting code.
     @param out A PrintStream to write the output to
     @param code The code up to this node
     @param tree The current node in the tree
     */
    private void printCode(PrintStream out, String code, BinaryTree < HuffData > tree) {
        HuffData theData = tree.getData();
        if (theData.symbol != null) {
            if (theData.symbol.equals(" ")) {
                out.println("space: " + code);
            }
            else {
                out.println(theData.symbol + ": " + code);
            }
        }
        else {
            printCode(out, code + "0", tree.getLeftSubtree());
            printCode(out, code + "1", tree.getRightSubtree());
        }
    }

    /** Method to decode a message that is input as a string of
     digit characters '0' and '1'.
     @param codedMessage The input message as a String of
     zeros and ones.
     @return The decoded message as a String
     */
    public String decode(String codedMessage) {
        StringBuilder result = new StringBuilder();
        BinaryTree < HuffData > currentTree = huffTree;
        for (int i = 0; i < codedMessage.length(); i++) {
            if (codedMessage.charAt(i) == '1') {
                currentTree = currentTree.getRightSubtree();
            }
            else {
                currentTree = currentTree.getLeftSubtree();
            }
            if (currentTree.isLeaf()) {
                HuffData theData = currentTree.getData();
                result.append(theData.symbol);
                currentTree = huffTree;
            }
        }
        return result.toString();
    }

    /**
     * verilen stringi kodlayan metot
     *
     * @param message encode edilmesi istenen string
     * @return girilen stringin encode edilmis hali
     */
    public String encode(String message) {
        String encodedCode = "[";
        String encoding = "";
        for (int i = 0; i < message.length(); ++i)
            encodedCode += encodingChar(message.charAt(i), this.huffTree, encoding);
        encodedCode += "]";
        return encodedCode;
    }

    /**
     * verilen karakteri kodlayan metot
     *
     * @param symbol encode edilecek karakter
     * @param huffTree uzerinden encode yapilacak tree
     * @param encoding encode edilen string
     * @return bir karakterin encode edilmis stringi
     */
    public String encodingChar(char symbol, BinaryTree<HuffData> huffTree, String encoding) {

        String value = "";
        if (huffTree.isLeaf()) { //leaf olan bir node un kendisine bakilir esit mi diye!
            HuffData theData = huffTree.getData();
            if (theData.symbol.equals(symbol))
                value = encoding;
        } else
            //sola gidiyor, eger donen string bossa, solda bulunamadiysa saga gidiyor
            if ((value = encodingChar(symbol, huffTree.getLeftSubtree(), encoding + "0")) == "")
                value = encodingChar(symbol, huffTree.getRightSubtree(), encoding + "1");
        return value;
    }

    /**
     * preorder olarak dolasip tree yi string'e ceviren metot
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(huffTree.root, 1, sb);
        return sb.toString();
    }

    /**
     * Perform a preorder traversal.
     * @param root huffData
     * @param depth The depth
     * @param sb The string buffer to save the output
     */
    private void preOrderTraverse(BinaryTree.Node<HuffData> root, int depth, StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append("  ");
        }
        if (root == null) {
            sb.append("null\n");
        }
        else {
            sb.append(root.data.symbol);
            sb.append("\n");
            preOrderTraverse(root.left, depth + 1, sb);
            preOrderTraverse(root.right, depth + 1, sb);
        }
    }
}
