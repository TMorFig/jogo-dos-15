import java.io.File;
import java.io.IOException;
import java.util.*;


public class greedy_manhattan {

    public static void main(String[] args) throws IOException {
        File file = new File("input.txt");
        Scanner input=new Scanner(file);
        int[] configI= new int[16];
        for(int i=0;i<16;i++){
            configI[i]=input.nextInt();
        }
        int[] configF= new int[16];
        for(int i=0;i<16;i++){
            configF[i]=input.nextInt();
        }

        long startTime = System.currentTimeMillis();
        List<int[]> path = solvePuzzle(configI,configF);
        long endTime = System.currentTimeMillis();

        for (int[] state : path) {
            System.out.println(Arrays.toString(state));
        }
        System.out.print("time : " + (endTime-startTime) + " milliseconds \nmoves : " + (path.size()-1));
    }

    public static List<int[]> solvePuzzle(int[] configI,int[] configF) throws IOException{
        if (!to_STD(configI)){
            throw new IOException("no solution"); 
        }
        PriorityQueue<int[]> openSet = new PriorityQueue<>(new PuzzleComparator(configF));
        Set<int[]> closedSet = new HashSet<>();
        Map<int[], int[]> pathTo = new HashMap<>();
        
        openSet.add(configI);
        while (!openSet.isEmpty()) {
            int[] currentState = openSet.poll();
            if (Arrays.equals(currentState, configF)) {
                return reconstructPath(currentState, pathTo);
            }
            closedSet.add(currentState);
            int emptyIndex = findEmptyIndex(currentState);
            int[] neighbors = {emptyIndex - 4, emptyIndex + 1, emptyIndex + 4, emptyIndex - 1};
            for (int neighborIndex : neighbors) {
                if (neighborIndex >= 0 && neighborIndex < 16 && (emptyIndex % 4 == neighborIndex % 4 || emptyIndex / 4 == neighborIndex / 4)) {
                    int[] neighborState = Arrays.copyOf(currentState, 16);
                    swapTiles(neighborState, emptyIndex, neighborIndex);
                    if (!closedSet.contains(neighborState)) {
                        openSet.add(neighborState);
                        pathTo.put(neighborState, currentState);
                    }
                }
            }
        }
        return null;
    }

    public static List<int[]> generateNextStates(int[] currentState) {
        List<int[]> nextStates = new ArrayList<>();

    
        int emptyIndex = findEmptyIndex(currentState);

     
        if (emptyIndex > 3) {
            int[] nextState = Arrays.copyOf(currentState, currentState.length);
            swapTiles(nextState, emptyIndex, emptyIndex - 4);
            nextStates.add(nextState);
        }
        if (emptyIndex % 4 != 0) {
            int[] nextState = Arrays.copyOf(currentState, currentState.length);
            swapTiles(nextState, emptyIndex, emptyIndex - 1);
            nextStates.add(nextState);
        }
        if (emptyIndex % 4 != 3) {
            int[] nextState = Arrays.copyOf(currentState, currentState.length);
            swapTiles(nextState, emptyIndex, emptyIndex + 1);
            nextStates.add(nextState);
        }
        if (emptyIndex < 12) {
            int[] nextState = Arrays.copyOf(currentState, currentState.length);
            swapTiles(nextState, emptyIndex, emptyIndex + 4);
            nextStates.add(nextState);
        }

        return nextStates;
    }

    public static int findEmptyIndex(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void swapTiles(int[] state, int i, int j) {
        int temp = state[i];
        state[i] = state[j];
        state[j] = temp;
    }

    public static List<int[]> reconstructPath(int[] configF, Map<int[], int[]> pathTo) {
        List<int[]> path = new ArrayList<>();
        int[] currentState = configF;
        while (currentState != null) {
            path.add(0, currentState);
            currentState = pathTo.get(currentState);
        }
        return path;
    }

    public static class PuzzleComparator implements Comparator<int[]> {
        private final int[] finalState;
        
        public PuzzleComparator(int[] finalState) {
            this.finalState = finalState;
        }
    
        public int compare(int[] state1, int[] state2) {
            int h1 = calculateManhattanDistance(state1, finalState);
            int h2 = calculateManhattanDistance(state2, finalState);
            return Integer.compare(h1, h2);
        }
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

    static boolean is_solvable(int[] state1,int[] state2){
        return to_STD(state1)==to_STD(state2);
    }

    static boolean to_STD(int[] state){
        return ((count_inversions(state)%2==0 && blank_row(state)%2!=1) || (count_inversions(state)%2==1 && blank_row(state)%2!=0));
    }
    static int count_inversions(int[] state){
        int inversions=0;
        for(int i=0;i<state.length;i++){
            for(int j=i+1;j<state.length;j++){
                if (state[i]!=0 && state[j]!=0 && state[i] > state[j]){
                    inversions++;
                }
            }
        }
        return inversions;
    }

    static int blank_row(int[] state){
        for(int i=0;i<state.length;i++){
            if (state[i]==0){
                return (int) (i/Math.sqrt(state.length)+1);
            }
        }
        return -1;
    }
}
