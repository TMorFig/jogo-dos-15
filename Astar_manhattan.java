import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Astar_manhattan {
    
    private static final int SIZE = 4; 

    private static class Node implements Comparable<Node> {
        int[] state;
        int g;
        int h;
        Node parent;
        
        public Node(int[] state, int g, int h, Node parent) {
            this.state = state;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }
        
        public int f() {
            return g + h;
        }
        
        public int compareTo(Node other) {
            return Integer.compare(this.f(), other.f());
        }
    }
    
    public static void main(String[] args) throws IOException {
        File file = new File("input.txt");
        Scanner input =new Scanner(file);
        int[] configI= new int[16];
        for(int i=0;i<16;i++){
            configI[i] = input.nextInt();
        }
        int[] configF= new int[16];
        for(int i=0;i<16;i++){
            configF[i]=input.nextInt();
        }
        long startTime = System.currentTimeMillis();
        List<Node> path = aStar(configI,configF);
        long endTime = System.currentTimeMillis();
        if (path == null) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solution found!");
            for (Node node : path) {
                printBoard(node.state);
            }
        }
        System.out.print("time : " + (endTime-startTime) + " milliseconds \nmoves : " + (path.size()-1));
        System.out.println("\nmemoria: " + (Math.pow(4,(path.size()-1))));
    }
    
  
    public static List<Node> aStar(int[] initialState,int[] finalState) {
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node initialNode = new Node(initialState, 0, calculateManhattanDistance(initialState,finalState), null);
        queue.add(initialNode);
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (Arrays.equals(currentNode.state, finalState)) {
                return getPath(currentNode);
            }
            visited.add(currentNode);
            for (Node neighbor : getNeighbors(currentNode)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }
    
    public static int calculateManhattanDistance(int[] puzzle, int[] finalConfig) {
        int manhattanDistance = 0;
        int rowDistance, colDistance, targetRow, targetCol;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != 0) {
                targetRow = (finalConfig[puzzle[i] - 1] - 1) / 4; 
                targetCol = (finalConfig[puzzle[i] - 1] - 1) % 4;
                rowDistance = Math.abs((i / 4) - targetRow);
                colDistance = Math.abs((i % 4) - targetCol);
                manhattanDistance += (rowDistance + colDistance);
            }
        }
        return manhattanDistance;
    }
    

    public static List<Node> getNeighbors(Node node) {
        int[] configF={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
        List<Node> neighbors = new ArrayList<>();
        int zeroIndex = -1;
        for (int i = 0; i < SIZE * SIZE; i++) {
            if (node.state[i] == 0) {
                zeroIndex = i;
                break;
            }
        }
        int zeroRow = zeroIndex / SIZE;
        int zeroCol = zeroIndex % SIZE;
      
        if (zeroRow > 0) {
            int[] newState = copyState(node.state);
            swap(newState, zeroIndex, zeroIndex - SIZE);
            neighbors.add(new Node(newState, node.g + 1, calculateManhattanDistance(newState,configF), node));
        }
        if (zeroRow < SIZE - 1) {
            int[] newState = copyState(node.state);
            swap(newState, zeroIndex, zeroIndex + SIZE);
            neighbors.add(new Node(newState, node.g + 1, calculateManhattanDistance(newState,configF), node));
        }
        if (zeroCol > 0) {
            int[] newState = copyState(node.state);
            swap(newState, zeroIndex, zeroIndex - 1);
            neighbors.add(new Node(newState, node.g + 1, calculateManhattanDistance(newState,configF), node));
        }
        if (zeroCol < SIZE - 1) {
            int[] newState = copyState(node.state);
            swap(newState, zeroIndex, zeroIndex + 1);
            neighbors.add(new Node(newState, node.g + 1, calculateManhattanDistance(newState,configF), node));
        }
        return neighbors;
    }
    

    public static int[] copyState(int[] state) {
        int[] newState = new int[SIZE*SIZE];
        for (int i = 0; i < SIZE*SIZE; i++) {
            newState[i] = state[i];
        }
        return newState;
    }
    
 
    public static void swap(int[] state, int i1, int i2) {
        int temp = state[i1];
        state[i1] = state[i2];
        state[i2] = temp;
    }
    
   
    public static List<Node> getPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node);
            node = node.parent;
        }
        return path;
    }
    

    public static void printBoard(int[] state) {
        for (int i = 0; i < SIZE*SIZE; i++) {
            System.out.print(state[i] + " ");
        }
        System.out.println();
    }
}
