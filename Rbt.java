// Imports
import java.lang.StringBuilder;

class RbtNode {

    // Class fields
    private int data;
    private byte color;
    public static final byte CL_RED = 0;
    public static final byte CL_BLACK = 1;
    private RbtNode left;
    private RbtNode right;
    private RbtNode parent;

    // Constructor
    public RbtNode(int data) {

        // Sets the data equal to data
        this.data = data;

        // Every node that is inserted will automatically be red
        this.color = 0;

        // Setting null to each of the nodes
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    // Accessors

    // Accessor for data
    public int getData() {
        return this.data;
    }

    // Accessor for color
    public byte getColor() {
        return this.color;
    }

    // Accessor for left child
    public RbtNode getLeft() {
        return this.left;
    }

    // Accessor for right child
    public RbtNode getRight() {
        return this.right;
    }

    // Accessor for the parent
    public RbtNode getParent() {
        return this.parent;
    }

    // Mutators
    
    // Mutator for color
    public void setColor(byte color) {
        this.color = color;
    }

    // Mutator for the left child
    public void setLeft(RbtNode left) {
        this.left = left;
    }

    // Mutator for the right child
    public void setRight(RbtNode right) {
        this.right = right;
    }

    // Mutator for the parent
    public void setParent(RbtNode parent) {
        this.parent = parent;
    }

    // Helper function to get the uncle
    public RbtNode getUncle() {

        // Checks if there is a grandparent, and if there isn't then there is no uncle
        if (parent == null || parent.parent == null) {
            return null;
        }

        // Checks if the parent is equal to the right leaf node of the grandparent and will return the left leaf node of the grandparent which is the uncle
        if (parent == parent.parent.right) {
            return parent.parent.left;
        }
        
        // Will return the uncle on the left of the grandparent otherwise
        else {
            return parent.parent.right;
        }
    }

    // Helper function to get the grandparent
    public RbtNode getGrandParent() {
        
        // Checks if there is a grandparent
        if (parent == null || parent.parent == null) {
            return null;
        }

        // Returns the grandparent
        return parent.parent;
    }
}

public class Rbt {

    // Class fields
    public RbtNode root;

    // Constructor
    public Rbt() {

        // Sets the root to null 
        this.root = null;
    }

    // Insert method
    public void insert(int value) {
        
        // Creates a new node of the given value
        RbtNode node = new RbtNode(value);

        // Checks if the root equals null, and will make the root equal the node of the given value
        if (root == null) {

            // Set the root to the value and make its color black
            root = node;
            root.setColor(RbtNode.CL_BLACK);
        }

        // If the tree is not empty then will insert the value
        else {

            // Call the recursive insert method
            root = insertRecursive(root, node);

            // Every new node added will always be red
            node.setColor(RbtNode.CL_RED);

            // Call the check violations function to see if there are any violations after every insert
            checkViolations(node);
        }
    }

    // Recursive method for insert
    private RbtNode insertRecursive(RbtNode current, RbtNode node) {

        // If the current node is null, then that is where the node will be inserted
        if (current == null) {
            return node;
        }

        // Will compare the values and if the node value is smaller then it will insert it into the left side of the tree
        if (node.getData() < current.getData()) {
            current.setLeft(insertRecursive(current.getLeft(), node));

            // Updates the parent of the left child
            current.getLeft().setParent(current);
        }

        // Will compare the values and if the node value is larger then it will insert it into the right side of the tree
        else if (node.getData() > current.getData()) {
            current.setRight(insertRecursive(current.getRight(), node));

            // Updates the parent of the right child
            current.getRight().setParent(current);
        }

        // returns the current node after inserting
        return current;
    }

    // Method to check all the violations of the tree and fix them
    private void checkViolations(RbtNode node) {
        
        // Creating a paren, grandparent, and uncle for easy reference
        RbtNode parent = node.getParent();
        RbtNode uncle = node.getUncle();
        RbtNode grandParent = node.getGrandParent();

        // Sets the root to always be black: root property
        if (node == root) {
            node.setColor(RbtNode.CL_BLACK);
        } 
        
        // Checks if the parent of the new node is red
        else if (parent.getColor() == RbtNode.CL_RED) {

            // Case 1: if there is a red uncle
            if (uncle != null && uncle.getColor() == RbtNode.CL_RED) {

                // Set each of their colors
                parent.setColor(RbtNode.CL_BLACK);
                uncle.setColor(RbtNode.CL_BLACK);
                grandParent.setColor(RbtNode.CL_RED);

                // Call the check for violations method
                checkViolations(grandParent);
            } 
            
            else {

                // Case 2: left right case
                if (parent == grandParent.getLeft() && node == parent.getRight()) {

                    // Rotate the parent left and update the nodes
                    rotateLeft(parent);
                    node = parent;
                    parent = node.getParent();
                } 

                // Case 3: right left case
                else if (parent == grandParent.getRight() && node == parent.getLeft()) {

                    // Rotate the parent right update the nodes
                    rotateRight(parent);
                    node = parent;
                    parent = node.getParent();
                }

                // Case 4: left left case
                if (parent == grandParent.getLeft() && node == parent.getLeft()) {

                    // Rotate the grandparent right and change the parent and grandparent colors
                    rotateRight(grandParent);
                    parent.setColor(RbtNode.CL_BLACK);
                    grandParent.setColor(RbtNode.CL_RED);
                } 
                
                // Case 5: right right case
                else if (parent == grandParent.getRight() && node == parent.getRight()) {

                    // Rotate the grandparent left and change the parent and grandparent colors
                    rotateLeft(grandParent);
                    parent.setColor(RbtNode.CL_BLACK);
                    grandParent.setColor(RbtNode.CL_RED);
                }
            }
        }
    }


    // Right rotate method
    private void rotateRight(RbtNode node) {

        // Create a left child for it to become the new parent
        RbtNode leftChild = node.getLeft();

        // Checks if a rotation can be done
        if (leftChild == null) {
            return;
        }

        // Update the parent of the left child to the original parent
        leftChild.setParent(node.getParent());

        // If the node is the root, update the root
        if (node == root) {
            root = leftChild;
        } 

        else {

            // If node is equal to the parents left child then the parents left child will be the left child
            if (node == node.getParent().getLeft()) {
                node.getParent().setLeft(leftChild);
            } 
            
            // Else the parents left child will be set to the right child
            else {
                node.getParent().setRight(leftChild);
            }
        }

        // Update the left child to be lefts right child
        node.setLeft(leftChild.getRight());

        // Update the left child to be the new right child
        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(node);
        }

        // Make the original node the right child of newParent.
        leftChild.setRight(node);

        // Update the parent
        node.setParent(leftChild);
    }

    // Left rotate method
    private void rotateLeft(RbtNode node) {

        // Create a right child for it to become the new parent
        RbtNode rightChild = node.getRight();

        // Checks if a rotation can be done
        if (rightChild == null) {
            return;
        }

        // Update the parent of the right child to the original parent
        rightChild.setParent(node.getParent());

        // If the node is the root, update the root
        if (node == root) {
            root = rightChild;
        } 
        
        else {

            // If node is equal to the parents left child then the parents left child will be the right child
            if (node == node.getParent().getLeft()) {
                node.getParent().setLeft(rightChild);

            } 
            
            // Else the parents right child will be set to the right child
            else {
                node.getParent().setRight(rightChild);
            }
        }

        // Update the right child to be rights left child
        node.setRight(rightChild.getLeft());

        // Update the right child to be the new left child
        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(node);
        }

        // Make the original node the left child
        rightChild.setLeft(node);

        // Update the parent
        node.setParent(rightChild);
    }

    // Search method
    public boolean search(int value) {

        // Checks if root is null and will return false
        if (root == null) {
            return false;
        }
        
        // Will return the result of the recursive method
        return searchRecursive(root, value);
    }

    // Recursive method to determine if it is true or not
    private boolean searchRecursive(RbtNode node, int value) {

        // Checks if the node is null which means the value is not in the tree
        if (node == null) {
            return false;
        }

        // Checks if the vaule is equal to the data at a specific node and will return true
        if (value == node.getData()) {
            return true;
        }

        // Checks if the node is null or not
        else if (node != null) {

            // Checks if the value is less than the value at a specific node and will go to the left of that node if true
            if (value < node.getData()) {
                return searchRecursive(node.getLeft(), value);
            }

            // Checks if the value is greater than the value at a specific node and will go to the right of that node if true
            else if (value > node.getData()) {
                return searchRecursive(node.getRight(), value);
            }
        }

        return false;
    }

    // Returns the min value in the tree
    public int min() {

        // Node for manuvering through the tree
        RbtNode node = root;

        // Checks if the root is null and returns -1
        if (root == null) {
            return -1;
        }

        // Will loop through the tree all the way to the left till it reaches null
        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        // Will return the min value in the tree
        return node.getData();
    }

    // Returns the max value in the tree
    public int max() {

        // Node for manuvering through the tree
        RbtNode node = root;

        // Checks if the root is null and returns -1
        if (root == null) {
            return -1;
        }

        // Will loop through the tree all the way to the right till it reaches null
        while (node.getRight() != null) {
            node = node.getRight();
        }

        // Will return the max value in the tree
        return node.getData();
    }

    // Returns the size of the tree
    public int size() {

        // Call the recursive helper method to count the nodes starting from the root
        return sizeRecursive(root);
    }

    // Recursive helper method to count the nodes
    private int sizeRecursive(RbtNode node) {

        // If the node is null then return 0
        if (node == null) {
            return 0;
        }

        // Count the nodes in the left and right subtrees
        int leftSize = sizeRecursive(node.getLeft());
        int rightSize = sizeRecursive(node.getRight());

        // Return 1 for the current node and the left and right nodes
        return 1 + leftSize + rightSize;
    }


    // Method to print the values inorder
    public String inorder() {

        // Final string that will be shown
        StringBuilder result = new StringBuilder();

        // Calls the recursive inorder method to continuously get the values in order
        inorderRecursive(root, result);

        // Returning the resulting string
        return result.toString();
    }

    // Recursive helper method for inorder
    private void inorderRecursive(RbtNode node, StringBuilder result) {

        // Checks if the root is null and will set the resulting string to be an empty string
        if (root == null) {
            result.append("No nodes in the tree");
        }

        if (node != null) {

            // Will recursively call the function to get the nodes in order
            inorderRecursive(node.getLeft(), result);

            // Will add the values to the string 
            result.append(node.getData());
            result.append(" ");

            // Will recursively call the function to get to the right node
            inorderRecursive(node.getRight(), result);
        }
    }
}