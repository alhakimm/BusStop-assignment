
/**
 * The BusStopLocation class represents a bus stop location system that allows users to find routes
 * between different bus stops and displays the distances of all possible paths.
 */
// The `import` statements at the beginning of the code are used to import necessary classes and
// packages from the Java standard library.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BusStopLocation {
    // The line `private int[][] adjacencyMatrix;` declares a private instance variable
    // `adjacencyMatrix` of type `int[][]` (a 2-dimensional array of integers). This variable is used
    // to store the distances between different bus stops in the bus stop location system. The
    // adjacency matrix represents the connections between bus stops, where each element in the matrix
    // represents the distance between two bus stops.
    private int[][] adjacencyMatrix;

    // The `public BusStopLocation(int numBusStops)` constructor is used to initialize a
    // `BusStopLocation` object with a given number of bus stops.
    public BusStopLocation(int numBusStops) {
        this.adjacencyMatrix = new int[numBusStops][numBusStops];

        // The below code is initializing a 2D array called adjacencyMatrix with all elements set to 0.
        // It then sets the diagonal elements of the matrix to 0. This code is typically
        // used to create an adjacency matrix for a graph, where each element represents the connection
        // between two vertices.
        for (int i = 0; i < numBusStops; i++) {
            Arrays.fill(adjacencyMatrix[i], 0);
            adjacencyMatrix[i][i] = 0;
        }
    }

    /**
     * The addBusLocation function adds a bus route between two bus stops with a specified distance.
     * - busStop1 The index of the first bus stop in the adjacency matrix. This represents the
     * starting point of the bus route.
     * - busStop2 The parameter "busStop2" represents the second bus stop in the route.
     * - distance The distance parameter represents the distance between two bus
     * stops.
     */
    public void addBusLocation(int busStop1, int busStop2, int distance) {
        adjacencyMatrix[busStop1][busStop2] = distance;
        adjacencyMatrix[busStop2][busStop1] = distance; // Assuming the Location is undirected
    }

    /**
     * The function returns the distance between two bus stops based on the adjacency matrix.
     * - busStop1 The parameter busStop1 represents the index or identifier of the first bus stop
     * in the adjacency matrix.
     * - busStop2 The parameter "busStop2" represents the index of the second bus stop in the
     * adjacency matrix.
     * - The method is returning the distance between two bus stops.
     */
    public int getDistance(int busStop1, int busStop2) {
        return adjacencyMatrix[busStop1][busStop2];
    }

    /**
     * The function `findAllPaths` finds all possible paths between a start bus stop and a destination
     * bus stop using depth-first search.
     * - startBusStop The starting bus stop from where we want to find all paths.
     * - destinationBusStop The destination bus stop is the bus stop where you want to reach.
     * It is the final destination of the path you are trying to find.
     * - The method is returning a list of lists of integers. Each inner list represents a path
     * from the startBusStop to the destinationBusStop.
     */
    public List<List<Integer>> findAllPaths(int startBusStop, int destinationBusStop) {
        boolean[] visited = new boolean[adjacencyMatrix.length];
        List<List<Integer>> allPaths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        dfs(startBusStop, destinationBusStop, visited, currentPath, allPaths);
        return allPaths;
    }

    /**
     * The function performs a depth-first search to find all paths from a current bus stop to a
     * destination bus stop in a graph represented by an adjacency matrix.
     * - currentBusStop The current bus stop that we are at during the depth-first
     * search.
     * - destinationBusStop The destination bus stop is the bus stop that we want to
     * reach. It is the target bus stop for our depth-first search algorithm.
     * - visited The `visited` array is a boolean array that keeps track of whether
     * a bus stop has been visited or not during the depth-first search (DFS) traversal. It is used
     * to prevent visiting the same bus stop multiple times and getting stuck in an infinite
     * loop.
     * - currentPath The currentPath parameter is a list that stores the current
     * path being explored in the depth-first search algorithm. It keeps track of the bus stops
     * visited so far.
     * - allPaths A list of lists that will store all the paths from the current bus
     * stop to the destination bus stop.
     */
    private void dfs(int currentBusStop, int destinationBusStop, boolean[] visited,
            List<Integer> currentPath, List<List<Integer>> allPaths) {
        visited[currentBusStop] = true;
        currentPath.add(currentBusStop);

        if (currentBusStop == destinationBusStop) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
                if (adjacencyMatrix[currentBusStop][neighbor] != 0 && !visited[neighbor]) {
                    dfs(neighbor, destinationBusStop, visited, currentPath, allPaths);
                }
            }
        }

        visited[currentBusStop] = false;
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * The function displays the distances of all paths in a list of lists of integers.
     * allPaths The parameter `allPaths` is a list of lists of integers. Each inner
     * list represents a path, where each integer represents a bus stop.
     */
    public void displayPathsDistances(List<List<Integer>> allPaths) {
        System.out.println("\nDistances of all paths:");
        for (List<Integer> path : allPaths) {
            int totalDistance = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                int busStop1 = path.get(i);
                int busStop2 = path.get(i + 1);
                int distance = getDistance(busStop1, busStop2);
                totalDistance += distance;
            }
            System.out.printf("Total distance for path %s: %d\n", path, totalDistance);
            System.out.println("-------------------------");
        }
    }

    /**
     * The function `displayRoute` takes an adjacency matrix and a width parameter,and prints the
     * matrix with appropriate formatting.
     * - adjacencyMatrix The adjacencyMatrix parameter is a 2D array that represents
     * the adjacency matrix of a graph. Each element in the matrix represents the weight or
     * distance between two vertices in the graph.
     * - width The width parameter is used to specify the width of each column when
     * displaying the adjacency matrix.
     */
    public static void displayRoute(int[][] adjacencyMatrix, int width) {
        int count = 0;

        System.out.println("-------------------------");
        System.out.printf("%3s", "");
        for (int i = 0; i < adjacencyMatrix[0].length; i++) {
            System.out.printf("%" + width + "d", i);
        }
        System.out.println();

        for (int[] row : adjacencyMatrix) {
            System.out.printf("%-3d", count);
            for (int value : row) {
                System.out.printf("%" + width + "d", value);
            }
            System.out.println();
            count++;
        }
    }

    public static void main(String[] args) {
        // The code is creating a `BusStopLocation` object called `Location` with 10 bus
        // stops. It then adds bus routes between different bus stops with their corresponding
        // distances using the `addBusLocation` method. Each `addBusLocation` call specifies the starting
        // bus stop, the destination bus stop, and the distance between them.
        int numBusStops = 10;
        BusStopLocation Location = new BusStopLocation(numBusStops);

        // Adding edges with their distances
        Location.addBusLocation(0, 1, 13);
        Location.addBusLocation(0, 3, 4);
        Location.addBusLocation(0, 4, 5);
        Location.addBusLocation(3, 2, 12);
        Location.addBusLocation(4, 7, 21);
        Location.addBusLocation(4, 1, 7);
        Location.addBusLocation(2, 8, 24);
        Location.addBusLocation(2, 7, 27);
        Location.addBusLocation(7, 5, 14);
        Location.addBusLocation(5, 6, 11);
        Location.addBusLocation(1, 5, 20);
        Location.addBusLocation(2, 9, 8);

        // The line `Scanner getNum = new Scanner(System.in);` creates a new `Scanner` object named
        // `getNum` that is used to read input from the user. The `System.in` parameter passed to the
        // `Scanner` constructor indicates that the input will be read from the standard
        // input stream,which is typically the keyboard.
        Scanner getNum = new Scanner(System.in);

        // The line `System.out.println("\n\t\tWelcome to the USM Bus Stop Route Finder!\n");`
        // is printing a welcome message to the console. The message is displayed with some
        // formatting,including leading tabs and new lines, to make it more visually appealing.
        System.out.println("\n\t\tWelcome to the USM Bus Stop Route Finder!\n");

        // The line `int choice = 0;` is declaring and initializing a variable `choice` of type `int`
        // with an initial value of 0. This variable is used to store the user's choice from the main
        // menu.
        int choice = 0;

        // The below code is implementing a menu-driven program for a bus stop route finder. It
        // displays a menu with three options:
        // 1. Display the whole map route
        // 2. Find a route between two bus stops
        // 3. Exit the program
        do {
            System.out.println("\t\t\t-----Main Menu-----");
            System.out.println("\t\t\t1. Display Whole Map Route\n\t\t\t2. Find Route\n\t\t\t3. Exit\n");

            System.out.println("Enter your choice: ");
            choice = getNum.nextInt();

            // If the user's choice is 1, the code calls the `displayRoute` method and passes the
            // `adjacencyMatrix` of the `Location` object and the width parameter of 4. The
            // `displayRoute` method is responsible for printing the adjacency matrix with appropriate
            // formatting.
            if (choice == 1) {
                displayRoute(Location.adjacencyMatrix, 4);
            }
            // Below is an else if statement that checks if the value of the variable "choice"
            // is equal to 2. If it is, it prompts the user to enter the initial and final destinations
            // for a bus route. It then calls the "findAllPaths" method of the "Location" class to find all
            // possible paths between the initial and final destinations.
            // If no paths are found, it prints a message indicating that.
            // Otherwise, it prints all the paths and calls the "displayPathsDistances" method
            // of the "Location" class to display the distances of all paths.
            else if (choice == 2) {
                System.out.println("Enter the initial destination: ");
                int indest = getNum.nextInt();
                System.out.println("Enter the final destination: ");
                int findest = getNum.nextInt();

                List<List<Integer>> allPaths = Location.findAllPaths(indest, findest);

                if (allPaths.isEmpty()) {
                    System.out.println("No paths found between bus stop " + indest + " and bus stop " + findest);
                } else {
                    System.out.println("\nAll paths between bus stop " + indest + " and bus stop " + findest + ":");
                    for (List<Integer> path : allPaths) {
                        System.out.println(path);
                    }
                    Location.displayPathsDistances(allPaths);
                }
            }
            // The below code is part of a Java program and it is handling the case when the
            // condition in an if statement is false. It prints a message "Thank you for
            // using the USM Bus Stop Route Finder! Wishing you a safe journey"
            // and then exits the program with a status code of 0.
            else {
                System.out.println("Thank you for using the USM Bus Stop Route Finder! Wishing you a safe journey");
                System.exit(0);
            }

            System.out.println("\n\n");
        }
        // The below code is creating a while loop that will continue running as long as
        // the variable "choice" is not equal to 3.
        while (choice != 3);

    }
}
