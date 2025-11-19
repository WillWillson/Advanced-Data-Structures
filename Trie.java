// Imports
import java.io.UnsupportedEncodingException;
import java.lang.StringBuilder;

// class for each of the nodes
class Node{

    // Declarations of variables
    private Node[] children;
    private boolean isTerminal;

    // Constructor
    public Node(){

        // Initializing the children array
        children =  new Node[Trie.alphabetSize];

        // Allocates the array to be all null
        for (int i = 0; i < 128; i++) {
            children[i] = null;
        }

        // Initializing isTerminal to false
        isTerminal = false;
    }

    // Accessors
    // Gets the children array
    public Node[] getChildren() {
        return children;
    }

    // Checks if the node is at the end of a word
    public boolean getIsTerminal() {
        return isTerminal;
    }

    // Mutators
    public void setTerminal(boolean end) {
        isTerminal = end;
    }
}

// Trie class
class Trie {

    // Declarations of variables
    private Node root;
    public static final int alphabetSize = 128;

    // Constructor
    public Trie() {

        // Setting the root to null
        this.root = null;
    }

    // Method to insert words into the trie
    public void insert(String word) {
        
        // Creates a new node if the root is null
        if (root == null) {
            root = new Node();
        }

        // Will start at the root of the trie
        Node current = root;

        // Will move through the trie based on the char
        for (char c : word.toCharArray()) {

            // Converts the char to its coresponding ascii integer equivalent
            int asciiValueIndex = c;

            // Checks if the child node based on the character equals null, and will create a new node if true
            if (current.getChildren()[asciiValueIndex] == null) {
                current.getChildren()[asciiValueIndex] = new Node();
            }

            // Will move to the child node 
            current = current.getChildren()[asciiValueIndex];
        }

        // Will set the last node as a traversal indicating the end of the word
        current.setTerminal(true);
    }
    
    // Method to search for a word in the trie
    public boolean search(String word) {

        // Will start at the root of the trie
        Node current = root;

        // Return the final boolean 
        boolean result = false;

        // Will move through the trie based on the char
        for (char c : word.toCharArray()) {

            // Converts the char to its corresponding ASCII integer equivalent
            int asciiValueIndex = c;

            // Checks if the child node based on the character equals null, and return false if true
            if (current.getChildren()[asciiValueIndex] == null) {
                return result;
            }

            // Will move to the child node 
            current = current.getChildren()[asciiValueIndex];
        }

        // Will check if the current node does not equal null, and if the last node is a terminal node and will return true
        if (current != null && current.getIsTerminal()) {
            result = true;
        }

        // Will check if the last node in is a terminal node and will return true if true
        return result;
    }


    // Prints all the words in the trie
    public void printAllWords() {

        // String used for the final result
        StringBuilder result = new StringBuilder();

        // Calls the recursive method
        printWordsRec(root, "", result);

        // Converts the string builder to a readable string
        String words = result.toString();

        // Prints the words to the screen
        System.out.println(words);
    }

    // Prints all the words based on the given prefix
    public void printAllWords(String prefix) {

        // String used for the final result
        StringBuilder result = new StringBuilder();

        result.append("");

        // Sets a node to the root node
        Node node = root;

        // Goes to where the prefix ends
        for (char c : prefix.toCharArray()) {

            // Checks if the child node will equal null
            if (node.getChildren()[c] == null) {
                return;
            }

            // Sets the node to be that of the child
            node = node.getChildren()[c];
        }

        // Calls the recursive method
        printWordsRec(node, prefix, result);

        // Converts the string builder to a readable string
        String words = result.toString();
        
        // Prints the words to the screen
        System.out.println(words);
    }

    // Recursive helper method for printing the words
    private void printWordsRec(Node node, String prefix, StringBuilder result) {

        // Checks if the given node is a terminal node
        if (node.getIsTerminal()) {
            result.append("'").append(prefix).append("', ");
        }

        // Goes through all the children in the trie
        for (char c = 0; c < alphabetSize; c++) {

            // Checks if there is a child node and will recursively go through the the nodes
            if (node.getChildren()[c] != null) {
                printWordsRec(node.getChildren()[c], prefix + c, result);
            }
        }
    }

}