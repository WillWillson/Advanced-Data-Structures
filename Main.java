import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Character> stack = new Stack<>();

        char[] charArray = new char[stack.size()];
        stack.push('c');
        stack.push('d');
        stack.push('e');
        String stackFileName = "stack.txt";

        System.out.println("Stack values:");
        for (char value : stack) {
            charArray.add(value);
            System.out.println(value);
        }
        System.out.println(charArray);
    }
}

