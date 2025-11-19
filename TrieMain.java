// Imports
import java.util.Scanner;
import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class TrieMain {
    public static void main(String[] args) {

        // String for checking the aargument
        StringBuilder argsString = new StringBuilder();

        // Adds the command line argument to a string to determine later if the correct argument were entered
        argsString.append(args[0]);

        // Switch case based on the arguments passed
        switch (argsString.toString()) {

            // Case for when wanting to use the test mode
            case "test":
                // Creates the trie
                Trie trieTest = new Trie();

                // Will add the initial words to the trie
                String[] words = {"banana", "bandana", "bandaid", "bandage", "letter", "lettuce", "let", "tool", "toy", "toilet"};
                for (String word : words) {
                    trieTest.insert(word);
                }

                //While loop to run the visualization
                while (true) {

                    // Will clear the screen when it gets back to the begining after running an option in the menu
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    // Method call to print the menu
                    printMenu();

                    // Gets the current input
                    Scanner scanner = new Scanner(System.in);

                    // Will be used if the choice is one so the given value can be added to the list
                    String tempStr = scanner.nextLine();

                    // Will give the choice and potentially the value to add if specified
                    String[] input = tempStr.split(" ");

                    // Both the choice and value strings
                    String choice = input[0];

                    switch (choice) {
                        case "add":

                            // Will insert, or add the given word to the trie
                            trieTest.insert(input[1]);

                            // Will break when finished with this case
                            break;
                
                        case "printAll":

                            System.out.println("");

                            // Starting string for the output
                            System.out.print("All words: ");

                            // Get all the words from the trie
                            trieTest.printAllWords();

                            // Print a space
                            System.out.println("");

                            // Tells the user to press enter to move on
                            System.out.println("Press Enter to continue");

                            // Waits for enter
                            scanner.nextLine();

                            // Will break when finished with this case
                            break;

                        case "startsWith":

                            // Print a space
                            System.out.println("");

                            // String builder for this option
                            StringBuilder printAllWords = new StringBuilder();

                            // Appends the string to the printAllWords 
                            printAllWords.append("All words that start with '");
                            printAllWords.append(input[1]);
                            printAllWords.append("': ");

                            // Print the printAllWords string
                            System.out.print(printAllWords.toString());

                            // Method call to print all the words with the given prefix
                            trieTest.printAllWords(input[1]);

                            // Print a space
                            System.out.println("");

                            // Tells the user to press enter to move on
                            System.out.println("Press Enter to continue");

                            // Waits for enter
                            scanner.nextLine();

                            // Will break when finished with this case
                            break;
                
                        // Case for if the option is search and will run the search function
                        case "search":

                            // Print a space
                            System.out.println("");

                            // Creates the sting that will print the output based on the search
                            StringBuilder searchString = new StringBuilder();

                            // Checks if the search is true and if so it will print a sentence to the terminal
                            if (trieTest.search(input[1]) == true) {
                                searchString.append("word '");
                                searchString.append(input[1]);
                                searchString.append("' was found in the trie");

                                // Print the searchString
                                System.out.println(searchString.toString());

                                // Print a space
                                System.out.println("");
                            }

                            // Will print a new sentence if the search is false
                            else {
                                searchString.append("word '");
                                searchString.append(input[1]);
                                searchString.append("' was not found in the trie");

                                // Print the searchString
                                System.out.println(searchString.toString());

                                // Print a space
                                System.out.println("");
                            }

                            // Tells the user to press enter to move on
                            System.out.println("Press Enter to continue");

                            // Waits for enter
                            scanner.nextLine();
                    
                            // Will break when finished with this case
                            break;

                        case "quit":
                            System.exit(0);
                            break;

                        // Used for if none of the cases match
                        default:
                            
                            // Print a space
                            System.out.println("");

                            // Tells user that input is incorrect
                            System.out.println("Option not selected. Type what is shown in {} ");

                            // Print a space
                            System.out.println("");

                            // Tells the user to press enter to move on
                            System.out.println("Press Enter to continue");

                            // Waits for enter
                            scanner.nextLine();

                    }
                }
            
            // Case for when wanting to use the memuse mode 
            case "memuse":
                
                // Setting up the visualization size and increment
                Display.setYMax(900);
                Display.setYInc(15);

                // Creating a new Trie and HashSet
                Trie trieMem = new Trie();
                HashSet<String> hashMem = new HashSet<>();

                // Create an array or list to store the words from the file
                List<String> wordList = new ArrayList<>();

                // Tracks the memory usage and word count for both data structures
                long heapMemoryInBytes = 0;
                int wordCountTrie = 0;
                int wordCountHash = 0;

                // Gets the runtime
                Runtime rt = Runtime.getRuntime();

                // Reading the input
                Scanner inputScanner = new Scanner(System.in);

                // Used to add the words to both data structures and display the trie part in the visualization
                while (inputScanner.hasNextLine()) {

                    // Storing the word in the word 
                    String word = inputScanner.nextLine();
                    
                    // Store the word in the list for later
                    wordList.add(word);

                    // Add the word to your trie
                    trieMem.insert(word);

                    // Add the word to your hashset
                    hashMem.add(word);

                    // Update memory usage
                    heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();

                    // Increase the word count for the trie
                    wordCountTrie++;

                    // Call the show function for the Trie
                    Display.show(trieMem, heapMemoryInBytes, wordCountTrie);
                }

                // Cleanup for the Trie before comparing with HashSet
                trieMem = null;
                
                // Activate the garbage collector
                rt.gc();

                // For loop to insert the words into the hashset and display memory usage
                for (String word : wordList) {

                    // Increase the word count for the hash
                    wordCountHash++;

                    // Update memory usage
                    heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();

                    // Call the show function for the hashset
                    Display.show(hashMem, heapMemoryInBytes, wordCountHash);
                }

                // Will break when finished with this case
                break;

            // Output for when either case is not given
            default:
                System.out.println("Incorrect argument were given");
        }
    }

    // Prints out the menu and will be called in the main
    private static void printMenu() {
        System.out.println("");
        System.out.println("{add WORD}: Add a word");
        System.out.println("{printAll}: Print all words");
        System.out.println("{startsWith PREFIX}: Print words that start with");
        System.out.println("{search WORD}: Match a specific word");
        System.out.println("{quit}: Quit");
        System.out.println("");
        System.out.print("? ");
    }
}