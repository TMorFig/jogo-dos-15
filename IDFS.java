import java.util.*;

import org.w3c.dom.css.Counter;

import java.io.*;  


public class IDFS {
    public static void main(String[] args) throws IOException{
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
        List<int[]> path = new ArrayList<>();
        List<int[]> initial = new ArrayList<>();
        initial.add(configI);
        long startTime = System.currentTimeMillis();
        int manhattan = calculateManhattanDistance(configI, configF);
        int counter = 0;
        while (path.isEmpty() == true){
            path=dfs(configI, initial , configF,counter);
            counter+=1;
        }
        long endTime = System.currentTimeMillis();
        for (int[] array : path) {
            System.out.println(Arrays.toString(array));
        }
        System.out.print("time : " + (endTime-startTime) + " milliseconds \nmoves : " + (path.size()-1));
        System.out.println("\nmemoria : "+ (counter*counter)+ " kbytes");
    }
    static List<int[]> dfs(int[] state, List<int[]> visited, int[] finalState, int depth) {
        if (!to_STD(state)) {
            return new ArrayList<>();
        }
        if (Arrays.equals(state, finalState)) {
            return visited;
        }
        if (visited.size() > 50_000 || depth <= 0) {
            return new ArrayList<>();
        }
        int[] newState;
        if ((newState = iteration_up(state)) != null && !visited.contains(newState)) {
            List<int[]> newVisited = new ArrayList<>(visited);
            newVisited.add(newState);
           
            List<int[]> result = dfs(newState, newVisited, finalState, depth - 1);
         
            if (!result.isEmpty()) {
                return result;
            }
        }
        if ((newState = iteration_down(state)) != null && !visited.contains(newState)) {
            List<int[]> newVisited = new ArrayList<>(visited);
            newVisited.add(newState);
           
            List<int[]> result = dfs(newState, newVisited, finalState, depth - 1);
           
            if (!result.isEmpty()) {
                return result;
            }
        }
        if ((newState = iteration_right(state)) != null && !visited.contains(newState)) {
            List<int[]> newVisited = new ArrayList<>(visited);
            newVisited.add(newState);
            
            List<int[]> result = dfs(newState, newVisited, finalState, depth - 1);
            
            if (!result.isEmpty()) {
                return result;
            }
        }
        if ((newState = iteration_left(state)) != null && !visited.contains(newState)) {
            List<int[]> newVisited = new ArrayList<>(visited);
            newVisited.add(newState);
           
            List<int[]> result = dfs(newState, newVisited, finalState, depth - 1);
           
            if (!result.isEmpty()) {
                return result;
            }
        }
        return new ArrayList<>();
    }

    static boolean is_in(int[] array,List<int[]> lista){
        for(int i=0;i<lista.size();i++){
            if(Arrays.equals(lista.get(i),array)){
                return true;
            }
        }
        return false;
    }

    static int[] iteration_up(int[] lista_moves){
        int a=0;
        for(int i=0;i<lista_moves.length;i++){
            if(lista_moves[i]==0){
                a=i;
                break;
            }
        }
        if(a>=0 && a<=3){
            return null; 
        }
        int[] config_f = Arrays.copyOf(lista_moves, lista_moves.length);
        config_f[a]=lista_moves[a-4];
        config_f[a-4]=0;
        return config_f;
    }

    static int[] iteration_down(int[] lista_moves){
        int a=0;
        for(int i=0;i<lista_moves.length;i++){
            if(lista_moves[i]==0){
                a=i;
                break;
            }
        }
        if(a>=12 && a<=15){
            return null; 
        }
        int[] config_f = Arrays.copyOf(lista_moves, lista_moves.length);
        config_f[a]=lista_moves[a+4];
        config_f[a+4]=0;
        return config_f;
    }

    static int[] iteration_left(int[] lista_moves){
        int a=0;
        for(int i=0;i<lista_moves.length;i++){
            if(lista_moves[i]==0){
                a=i;
            }
        }
        if(a==0 || a==4 || a==8 || a==12){
            return null; 
        }
        int[] config_f = Arrays.copyOf(lista_moves, lista_moves.length);
        config_f[a]=lista_moves[a-1];
        config_f[a-1]=0;
        return config_f;
    }

    static int[] iteration_right(int[] lista_moves){
        int a=0;
        for(int i=0;i<lista_moves.length;i++){
            if(lista_moves[i]==0){
                a=i;
            }
        }
        if(a==3 || a==7 || a==11 || a==15){
            return null; 
        }
        int[] config_f = Arrays.copyOf(lista_moves, lista_moves.length);
        config_f[a]=lista_moves[a+1];
        config_f[a+1]=0;
        return config_f;
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

    static boolean to_STD(int[] state){
        return ((count_inversions(state)%2==0 && blank_row(state)%2!=1) || (count_inversions(state)%2==1 && blank_row(state)%2!=0));
    }

    static boolean is_solvable(int[] state1,int[] state2){
        return to_STD(state1)==to_STD(state2);
    }
} 
