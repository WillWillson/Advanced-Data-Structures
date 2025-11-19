import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class Main2 {
    public static void main(String[] args) {
        ArrayList<Character> stack = new ArrayList<>(); // Moved the declaration here

        stack.append('c');
        stack.append('d');
        stack.append('e');
        String stackFileName = "list.txt";

        // Strings for sending the line to the file
        String result = "";

        // Used to write to the file
        try {

            // Opens the writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(stackFileName));

            // Gets the values from the stack and add it to the temp string
            for (char c : stack) {
                result += c + ",";
            }

            // Writes the reversed string to the file
            writer.write(result); // Changed 'bufferedWriter' to 'writer'
            writer.close();
        } catch (Exception e) {}
    }
}
