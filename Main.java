// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        // Constant variables for both of the filenames
        final String fileName1 = "input.txt";
        final String fileName2 = "input_weights.txt";

        // Reads the files from the folder for finding the size of the adjacency matrices
        Scanner inputGraphVertices = new Scanner(new File(fileName1));
        Scanner inputWeightsGraphVertices = new Scanner(new File(fileName2));

        // Starting vertex
        int startVertex = 'a' - 'a';

        // Grab the number of vertices from each file
        // Create two variables
        int inputGraphNumVertices = 0;
        int weightsGraphNumVertices = 0;

        // Will increase the counter for the input.txt file to get the number of vertices
        while(inputGraphVertices.hasNextLine()) {
            inputGraphVertices.nextLine();
            inputGraphNumVertices++;
        }

        // Will increase the counter for the input_weights.txt file to get the number of vertices
        while(inputWeightsGraphVertices.hasNextLine()) {
            inputWeightsGraphVertices.nextLine();
            weightsGraphNumVertices++;
        }

        // Arrays used for later
        String[] inputGraphVerticeChars = new String[inputGraphNumVertices];
        String[] inputWeightsGraphVerticeChars = new String[weightsGraphNumVertices];

        // Close the scanners
        inputGraphVertices.close();
        inputWeightsGraphVertices.close();

        // Creating the two adjacency matrices for all the algorithms
        int[][] adjMat = new int[inputGraphNumVertices][inputGraphNumVertices];
        int[][] weightMat = new int[weightsGraphNumVertices][weightsGraphNumVertices];

        // Put zero's in every vertex to start and change later based on the input graph
        for(int i = 0; i < inputGraphNumVertices; i++) {
            for(int j = 0; j < inputGraphNumVertices; j++) {
                adjMat[i][j] = 0;
            }
        }

        // Put zero's in every vertex to start and change later based on the input weights graph
        for(int i = 0; i < weightsGraphNumVertices; i++) {
            for(int j = 0; j < weightsGraphNumVertices; j++) {
                weightMat[i][j] = 0;
            }
        }

        // Reads the files, and will add the values to the matrix
        Scanner inputGraph = new Scanner(new File(fileName1));
        Scanner inputWeightsGraph = new Scanner(new File(fileName2));

        // Counters
        int counter1 = 0;
        int counter2 = 0;

        // Add the values of the input graph txt file to the corresponding adjacency matrix
        while(inputGraph.hasNextLine()) {

            // Grab the line from the input grap file and convert to a string
            String inputLine = inputGraph.nextLine();

            // Split the string into a string array
            String[] vertices = inputLine.split(":");

            // Add the vertice to the array
            inputGraphVerticeChars[counter1] = vertices[0];

            // Set the index based off of the given char from the input graph file
            int startingIndex = vertices[0].charAt(0) - 'a';

            // Checks if there are neighbors in the vertices array
            if(vertices.length > 1) {

                // Split the vertices into each neighbor
                String[] neighbors = vertices[1].split(",");

                // For each neighbor grab its index and set it to one in the adjacnecy matrix
                for(String neighbor : neighbors) {
                    int neighborIndex = neighbor.charAt(0) - 'a';
                    adjMat[startingIndex][neighborIndex] = 1;
                }
            }

            // Increase counter
            counter1++;
        }

        // Add the values of the weighted txt file to the corresponding adjacency matrix
        while(inputWeightsGraph.hasNextLine()) {
            
            // Grab the line from the input grap file and convert to a string
            String inputLineWeights = inputWeightsGraph.nextLine();

             // Split the string into a string array
            String[] vertices = inputLineWeights.split(":");

            // Add the vertice to the array
            inputWeightsGraphVerticeChars[counter2] = vertices[0];

            // Set the index based off of the given char from the input graph file
            int startingIndex = vertices[0].charAt(0) - 'a';

            // Checks if there are neighbors in the vertices array
            if(vertices.length > 1) {

                // Split the vertices into each neighbor
                String[] neighbors = vertices[1].split(",");

                // For each neighbor grab its index and set it to the weight in the adjacnecy matrix
                for(String neighbor : neighbors) {
                    String[] parts = neighbor.split("_");
                    char neighborChar = parts[0].charAt(0);
                    int weight = Integer.parseInt(parts[1]);
                    int neighborIndex = neighborChar - 'a';
                    weightMat[startingIndex][neighborIndex] = weight;
                }
            }

            // Increase counter
            counter2++;
        }

        // Close the scanners
        inputGraph.close();
        inputWeightsGraph.close();

        // Conduct the BFS algorithm on the input graph
        String bfsOutput = BFS(adjMat, startVertex);

        // Conduct the DFS algorithm on the input graph
        String dfsOutput = DFS(adjMat, startVertex);

        // Conduct the Dijkstra algorithm on the input weights graph
        String dijkstraOutput = dijkstra(weightMat, startVertex);

        fileWriter(adjMat, weightMat, inputGraphVerticeChars, inputWeightsGraphVerticeChars, bfsOutput, dfsOutput, dijkstraOutput);
    }

    // BFS method
    public static String BFS(int[][] adjMat, int startVertex) {

        // Queue used for BFS
        Queue<Integer> queue = new LinkedList<>();

        // Boolean array for the visited vertices
        boolean[] seenList = new boolean[adjMat.length];

        // Put the starting vertex in the seen list
        seenList[startVertex] = true;

        // Add the starting vertex to the queue
        queue.add(startVertex);

        // Used to get the final result
        StringBuilder result = new StringBuilder();

        // Start of the search
        while(!queue.isEmpty()) {

            // Dequeue the current vertex
            int curVert = queue.poll();

            // Add it to the final result
            result.append((char) (curVert + 'a'));

            // Grab the neighbors of the current vertex
            for(int neighbor = 0; neighbor < adjMat.length; neighbor++) {

                // Checks if the neighbor is adjacent to the current vertex, and not in the seen list
                if (adjMat[curVert][neighbor] == 1 && !seenList[neighbor]) {

                    // Sets the neighbor as seen
                    seenList[neighbor] = true;

                    // Add the neighbors to the queue
                    queue.add(neighbor);
                }
            }
        }

        // Return the final result
        return "BFS: " + result.toString();
    }

    // DFS method
    public static String DFS(int[][] adjMat, int startVertex) {

        // Used to get the final result
        StringBuilder result = new StringBuilder();

        // Stack used for DFS
        Stack<Integer> stack = new Stack<>();

        // Boolean array for the visited vertices
        boolean[] seenList = new boolean[adjMat.length];

        // Put the starting vertex in the seen list
        seenList[startVertex] = true;

        // Add the starting vertex to the stack
        stack.push(startVertex);

        // Start of the search
        while(!stack.isEmpty()) {

            // Dequeue the current vertex
            int curVert = stack.pop();

            // Add it to the final result
            result.append((char) (curVert + 'a'));

            // Grab the neighbors of the current vertex
            for(int neighbor = 0; neighbor < adjMat.length; neighbor++) {

                // Checks if the neighbor is adjacent to the current vertex, and not in the seen list
                if (adjMat[curVert][neighbor] == 1 && !seenList[neighbor]) {

                    // Sets the neighbor as seen
                    seenList[neighbor] = true;

                    // Add the neighbors to the stack
                    stack.push(neighbor);
                }
            }
        }

        // Return the final result
        return "DFS: " + result.toString();
    }

    public static String dijkstra(int[][] weightMat, int startVertex){

        // Used to get the final result
        StringBuilder result = new StringBuilder();

        // Variable to represent infinity, not really infinity but the highest possible integer
        int infinity = Integer.MAX_VALUE;

        // distance list, and seen list
        int[] distance = new int[weightMat.length];
        boolean[] seenList = new boolean[weightMat.length];

        // Puts infinity in the distance list and sets all the values to have not been seen
        for(int i = 0; i < weightMat.length; i++) {
            distance[i] = infinity;
            seenList[i] = false;
        }

        // Setting the starting vertex
        distance[startVertex] = 0;

        // Dijkstra's algorithm
        for(int i = 0; i < weightMat.length - 1; i++) {

            // Finds which vertex had the smallest distanceand set it to true in the seen list
            int minDistance = minDistanceCalculator(distance, seenList, infinity);
            seenList[minDistance] = true;

            // Finds the neighbors of the current vertex
            for(int j = 0; j < weightMat.length; j++) {

                // Check if the neighbor is not visited, there is an edge, and the new distance is shorter
                if(!seenList[j] && weightMat[minDistance][j] != 0 && distance[minDistance] != infinity && distance[minDistance] + weightMat[minDistance][j] < distance[j]) {
                    distance[j] = distance[minDistance] + weightMat[minDistance][j];
                }
            }
        }

        // Used to get the ouput in the right format
        for(int i = 0; i < weightMat.length; i++) {
            result.append((char) (i + 'a'));
            result.append(":");
            result.append(distance[i]);
            result.append(",");
        }

        // Return the final result
        return "Dijkstra’s: " + result.toString();
    }

    // Helper method to find the minimum distance
    private static int minDistanceCalculator(int[] distance, boolean[] seenList, int infinity) {
        int minDistance = infinity;
        int minDistanceVertex = -1;

        // Loops through all the vertices
        for(int i = 0; i < distance.length; i++) {

            // Checks if the vertex has not been seen, and the distance is smaller than minDistance
            if(!seenList[i] && distance[i] <= minDistance) {

                // Updtate the minimum distance
                minDistance = distance[i];

                // Update the vertex based off of the distance
                minDistanceVertex = i;
            }
        }
        
        // Returns the minimum vertex
        return minDistanceVertex;
    }

    // Used to write everything to the output file
    public static void fileWriter(int[][] adjMat, int[][] weightMat, String[] inputGraphVertices, String[] inputWeightsGraphVertices, String bfsOutput, String dfsOutput, String dijkstraOutput) {

        // Constant file name
        final String outputFileName = "output.txt";

        // Creating the output file
        File outputFile = new File(outputFileName);

        // Writing all the contents to the file
        try {

            // Used to write to the file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter (outputFile));

            // The heading for the first input graph
            bufferedWriter.write("Adjacency Matrix (undirected):");
            bufferedWriter.newLine();

            // String used for later
            String vertexString1 =  "  ";

            // Grabs all the vertices from the List and adds to the string
            for(int i = 0; i < inputGraphVertices.length; i++) {
                vertexString1 += inputGraphVertices[i] + " ";
            }

            // Write the char string to the file
            bufferedWriter.write(vertexString1);
            bufferedWriter.newLine();

            // Used for printing the adjacency matrix
            for(int i = 0; i < adjMat.length; i++) {
                bufferedWriter.write(inputGraphVertices[i] + " ");
                for(int j = 0; j < adjMat[i].length; j++) {
                    bufferedWriter.write(adjMat[i][j] + " ");
                }
                bufferedWriter.newLine();
            }

            // Write a space
            bufferedWriter.newLine();

            // Writes the vertices after applying the BFS algorithm
            bufferedWriter.write(bfsOutput);
            bufferedWriter.newLine();
            
            // Writes the vertices after applying the DFS algorithm
            bufferedWriter.write(dfsOutput);
            bufferedWriter.newLine();

            // Write a space
            bufferedWriter.newLine();

            // The heading for the weights graph
            bufferedWriter.write("Adjacency Matrix (directed w/wights):");
            bufferedWriter.newLine();

            // String used for later
            String vertexString2 = "  ";

            // Grabs all the vertices from the List and adds to the string
            for(int i = 0; i < inputWeightsGraphVertices.length; i++) {
                vertexString2 += inputWeightsGraphVertices[i] + " ";
            }

            // Write the char string to the file
            bufferedWriter.write(vertexString2);
            bufferedWriter.newLine();

            // Used for printing the weight adjacency matrix
            for(int i = 0; i < weightMat.length; i++) {
                bufferedWriter.write(inputWeightsGraphVertices[i] + " ");
                for(int j = 0; j < weightMat[i].length; j++) {
                    bufferedWriter.write(weightMat[i][j] + " ");
                }
                bufferedWriter.newLine();
            }

            // Write a space
            bufferedWriter.newLine();

            // Writes the vertices after applying the dijkstra algorithm
            bufferedWriter.write(dijkstraOutput);
            bufferedWriter.newLine();

            // Close the buffered writer
            bufferedWriter.close();
        }

        catch (IOException e){
            e.getStackTrace();
        }
    }
}