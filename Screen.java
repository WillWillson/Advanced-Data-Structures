// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

interface StateEnterExitMeth {
    void invoke();
}

interface StateStayMeth {
    boolean invoke();
}

enum State{
    IDLE,
    STACK,
    QUEUE,
    LIST
}

public class Screen {

    // Setting variable for each of the file names
    private final String stackFileName = "stack.txt";
    private final String queueFileName = "queue.txt";
    public final String listFileName = "list.txt";

    public Stack<Character> stack;
    public Queue<Character> queue;
    public ArrayList<Character> arrayList;

    private HashMap<State, StateEnterExitMeth> stateEnterMeths;
    private HashMap<State, StateStayMeth> stateStayMeths;
    private HashMap<State, StateEnterExitMeth> stateExitMeths;

    public State currentState;

    // Constructor
    public Screen() {
        stack = new Stack<>();
        queue = new LinkedList<>();
        arrayList = new ArrayList<>();

        stateEnterMeths = new HashMap<>();
        stateStayMeths = new HashMap<>();
        stateExitMeths = new HashMap<>();

        // Initializing each of the different states
        stateEnterMeths.put(State.IDLE, () -> StateEnterIdle());
        stateEnterMeths.put(State.STACK, () -> StateEnterStack());
        stateEnterMeths.put(State.QUEUE, () -> StateEnterQueue());
        stateEnterMeths.put(State.LIST, () -> StateEnterList());

        stateStayMeths.put(State.IDLE, () -> StateStayIdle());
        stateStayMeths.put(State.STACK, () -> StateStayStack());
        stateStayMeths.put(State.QUEUE, () -> StateStayQueue());
        stateStayMeths.put(State.LIST, () -> StateStayList());

        stateExitMeths.put(State.IDLE, () -> StateExitIdle());
        stateExitMeths.put(State.STACK, () -> StateExitStack());
        stateExitMeths.put(State.QUEUE, () -> StateExitQueue());
        stateExitMeths.put(State.LIST, () -> StateExitList());

        currentState = State.IDLE;

    }

    // Will add the values to the stack that are already in the given file
    private void initialStackValues() {

        // Reading the file and putting it in a format to be added to the stack
        try {
            BufferedReader reader = new BufferedReader(new FileReader(stackFileName));
            String tempStr = reader.readLine();
            String newTempStr =  tempStr.replace(",", "");
            char[] charArray = newTempStr.toCharArray();
            for (char c : charArray) {
                stack.push(c);
            }
        } catch(Exception e){}
    }

    // Will add the values to the queue that are already in the given file
    private void initialQueueValues() {

        // Reading the file and putting it in a format to be added to the queue
        try {
            BufferedReader reader = new BufferedReader(new FileReader(queueFileName));
            String tempStr = reader.readLine();
            String newTempStr =  tempStr.replace(",", "");
            char[] charArray = newTempStr.toCharArray();
            for (char c : charArray) {
                queue.offer(c);
            }
        } catch(Exception e){}
    }

    // Will add the values to the list that are already in the given file
    private void initialListValues() {

        // Reading the file and putting it in a format to be added to the list
        try {
            BufferedReader reader = new BufferedReader(new FileReader(listFileName));
            String tempStr = reader.readLine();
            String newTempStr =  tempStr.replace(",", "");
            char[] charArray = newTempStr.toCharArray();
            for (char c : charArray) {
                arrayList.add(c);
            }
        } catch(Exception e){}
    }

    // Used for drawing the stack on the screen
    private void stackDrawing() {

        // Temp list for making the correct visualization
        ArrayList<Character> tempList = new ArrayList<>(stack);
        
        // Will print the top of the stack if there are no entries
        if (stack.isEmpty()) {
            // Printing the top of the stack
            System.out.println("|   |");
            System.out.println("|---|");
        }

        // Will print the full visual of the stack with the entries
        else {
            System.out.println("|   |");
            System.out.println("|---|");
            for (int i = tempList.size() - 1; i >= 0; i--) {
                System.out.println("| " + tempList.get(i) + " |");
                System.out.println("|---|");
            }
        }
    }

    // Used for drawing the queue on the screen
    private void queueDrawing() {

        // Will print the start of the queue if there are no entries
        if (queue.isEmpty()) {
            System.out.println("|   |");
        }

        // Will print the full visual of the queue with the entries
        else {
            System.out.print("| ");
            for (char c : queue) {
                System.out.print(c + " | ");
            }
            System.out.println("");
        }
    }

    // Used for drawing the list on the screen
    private void arrayListDrawing() {

        // Will print the start of the list if there are no entries
        if (arrayList.isEmpty()) {
            System.out.println("{ }");
        }

        // Will print the full visual of the list with the entries
        else {
            System.out.print("{ ");
            for (char c : arrayList) {
                System.out.print(c + ", ");
            }
            System.out.print(" }");
        }
    }

    // Method that saves the contents of the stack to the txt file
    private void saveStack() {

        // String for sending the line to the file
        String result = "";

        // Used to write to the file
        try {

            // Opens the writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(stackFileName));

            // Gets the values from the stack and adds it to the result string
            for (char c : stack) {
                result += c + ",";
            }

            // Writes the result string to the file
            writer.write(result);
            writer.close();
        } catch (Exception e) {}
    }

    // Method that saves the contents of the queue to the txt file
    private void saveQueue() {

        // String for sending the line to the file
        String result = "";

        // Used to write to the file
        try {

            // Opens the writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(queueFileName));

            // Gets the values from the queue and add it to the result string
            for (char c : queue) {
                result += c + ",";
            }

            // Writes the result string to the file
            writer.write(result);
            writer.close();
        } catch (Exception e) {}
    }

    // Method that saves the contents of the list to the txt file
    private void saveList() {

        // String for sending the line to the file
        String result = "";

        // Used to write to the file
        try {

            // Opens the writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(listFileName));

            // Gets the values from the list and add it to the result string
            for (char c : stack) {
                result += c + ",";
            }

            // Writes the result string to the file
            writer.write(result);
            writer.close();
        } catch (Exception e) {}
    }

    public boolean doState() {
        // Used for keeping track of if the program is running
        boolean keepRunning = true;

        while (keepRunning) {

            stateEnterMeths.get(currentState).invoke();
            
            // Determine which state method to execute based on the current state
            switch (currentState) {
                case IDLE:
                    keepRunning = stateStayMeths.get(State.IDLE).invoke();
                    break;
                case STACK:
                    keepRunning = stateStayMeths.get(State.STACK).invoke();
                    break;
                case QUEUE:
                    keepRunning = stateStayMeths.get(State.QUEUE).invoke();
                    break;
                case LIST:
                    keepRunning = stateStayMeths.get(State.LIST).invoke();
                    break;
            }
        }

        return keepRunning;
    }

    /// ENTER
    private void StateEnterIdle() {

        // Displays all the different choices
        System.out.println("1. Stack");
        System.out.println("2. Queue");
        System.out.println("3. List");
        System.out.println("4. Quit");
        System.out.print("? ");
    }

    private void StateEnterStack() {

        // Checks if the stack is empty and will add the values to the stack
        if (stack.isEmpty()) {
            initialStackValues();
        }

        // Draw the initial stack
        System.out.println("");
        stackDrawing();

        // Displays all the different choices
        System.out.println("1. Push");
        System.out.println("2. Pop");
        System.out.println("3. Save & Move to Queue");
        System.out.println("4. Save & Move to List");
        System.out.println("5. Quit");
        System.out.print("? ");
    }

    private void StateEnterQueue() {

        // Checks if the queue is empty and will add the values to the queue
        if (queue.isEmpty()) {
            initialQueueValues();
        }
        
        // Draw the initial queue
        System.out.println("");
        queueDrawing();

        // Displays all the different choices
        System.out.println("1. Enqueue");
        System.out.println("2. Dequeue");
        System.out.println("3. Save & Move to Stack");
        System.out.println("4. Save & Move to List");
        System.out.println("5. Quit");
        System.out.print("? ");
    }

    private void StateEnterList() {

        // Checks if the stack is empty and will add the values to the stack
        if (stack.isEmpty()) {
            initialListValues();
        }

        // Draw the initial list
        System.out.println("");
        arrayListDrawing();

        // Displays all the different choices
        System.out.println("1. Append");
        System.out.println("2. Remove");
        System.out.println("3. Save & Move to Stack");
        System.out.println("4. Save & Move to Queue");
        System.out.println("5. Quit");
        System.out.print("? ");
    }
    
    /// STAY
    private boolean StateStayIdle() {

        // Gets the current input
        Scanner input = new Scanner(System.in);
        
        // Gets the next integer that was inputed
        int choice = input.nextInt();
        
        // Will change the current state based off of the input from the user
        switch (choice) {

            // Will change the state to STACK
            case 1:
                currentState = State.STACK;
                break;
            
            // Will change the state to QUEUE
            case 2:
                currentState = State.QUEUE;
                break;

            // Will change the state to LIST
            case 3:
                currentState = State.LIST;
                break;

            // Will return false to quit the program
            case 4:
                return false;
            
            // Will break if the input isn't available
            default:
                break;
        }

        // Will always return true unless quit is selected
        return true;
    }

    // State function for what to do based on the input
    private boolean StateStayStack() {

        // Gets the current input
        Scanner scanner = new Scanner(System.in);
        
        // Will be used if the choice is one so the given value can be added to the stack
        String tempStr = scanner.nextLine();

        // Will give the choice and potentially the value to add if specified
        String[] input = tempStr.split(" ");

        // Both the choice and value strings
        String choiceStr = input[0];

        // The choice and value in their coresponding data
        int choice = Integer.parseInt(choiceStr);

        switch (choice) {
            // Will add a value to the stack
            case 1:

                // If the choise is 1 then it will grab the value to add from the string array and add to the stack
                String valueToAddStr = input[1];
                char valueToAdd = valueToAddStr.charAt(0);

                // Pushes the char to the stack
                stack.push(valueToAdd);
                break;

            // Will remove the element from the stack
            case 2:

                // Removes the value from the stack
                if (!stack.isEmpty()) {
                    stack.pop();
                }
                break;

            // Will save the stack and move to the queue
            case 3:
                
                // Call the save stack method
                saveStack();

                // Will change the state to QUEUE
                currentState = State.QUEUE;
                break;

            // Will save the stack and move to the list
            case 4:

                // Call the save stack method
                saveStack();

                // Will change the state to LIST
                currentState = State.LIST;
                break;

            // Will return false to quit the program
            case 5:
                return false;

            // Will break if the input isn't available
            default:
                break;
        }

        // Will always return true unless quit is selected
        return true;
    }

    // State function for what to do based on the input
    private boolean StateStayQueue() {
        
        // Gets the current input
        Scanner scanner = new Scanner(System.in);

        // Will be used if the choice is one so the given value can be added to the queue
        String tempStr = scanner.nextLine();

        // Will get the choice and potentially the value to add if specified
        String[] input = tempStr.split(" ");

        // Both the choice and value strings
        String choiceStr = input[0];

        // The choice and value in their coresponding data
        int choice = Integer.parseInt(choiceStr);
        
        switch (choice) {
            // Will add a value to the queue
            case 1:

                // If the choise is 1 then it will grab the value to add from the string array and add to the stack
                String valueToAddStr = input[1];
                char valueToAdd = valueToAddStr.charAt(0);

                // Pushes the char to the queue
                queue.offer(valueToAdd);
                break;

            // Will remove the element from the queue
            case 2:

                // Removes the value from the queue
                if (!queue.isEmpty()) {
                    queue.poll();
                }
                break;

            // Will save the queue and move to the stack
            case 3:
                
                // Call the save queue method
                saveQueue();

                // Will change the state to STACK
                currentState = State.STACK;
                break;

            // Will save the queue and move to the list
            case 4:

                // Call the save queue method
                saveQueue();

                // Will change the state to LIST
                currentState = State.LIST;
                break;

            // Will return false to quit the program
            case 5:
                return false;

            // Will break if the input isn't available
            default:
                break;
        }

        // Will always return true unless quit is selected
        return true;
    }

    // State function for what to do based on the input
    private boolean StateStayList() {
        
        // Gets the current input
        Scanner scanner = new Scanner(System.in);

        // Will be used if the choice is one so the given value can be added to the list
        String tempStr = scanner.nextLine();

        // Will give the choice and potentially the value to add if specified
        String[] input = tempStr.split(" ");

        // Both the choice and value strings
        String choiceStr = input[0];

        // The choice and value in their coresponding data
        int choice = Integer.parseInt(choiceStr);

        switch (choice) {
            // Will add a value to the list
            case 1:

                // If the choise is 1 then it will grab the value to add from the string array and add to the stack
                String valueToAddStr = input[1];
                char valueToAdd = valueToAddStr.charAt(0);

                // Pushes the char to the queue
                queue.add(valueToAdd);
                break;

            // Will remove the element from the list
            case 2:

                // Removes the value from the list
                if (!queue.isEmpty()) {
                    queue.remove();
                }
                break;

            // Will save the list and move to the stack
            case 3:
                
                // Call the save list method
                saveList();

                // Will change the state to STACK
                currentState = State.STACK;
                break;

            // Will save the list and move to the queue
            case 4:

                // Call the save list method
                saveList();

                // Will change the state to QUEUE
                currentState = State.QUEUE;
                break;

            // Will return false to quit the program
            case 5:
                return false;

            // Will break if the input isn't available
            default:
                break;
        }

        // Will always return true unless quit is selected
        return true;
    }

    /// EXIT
    private void StateExitIdle() {}
    private void StateExitStack() {}
    private void StateExitQueue() {}
    private void StateExitList() {}

}