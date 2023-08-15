import java.util.*;
import java.io.*;  


public class BFS_15correto {
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
        path=bfs(initial,configF);
        long endTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        for (int[] array : path) {
            System.out.println(Arrays.toString(array));
        }
        System.out.print("time : " + (endTime-startTime) + " milliseconds \nmoves : " + (path.size()-1));
        System.out.println("\nmemoria: " + (Math.pow(4,(path.size()-1))));
        System.out.println("\nmemoria: " + usedMemory);
    }

    static List<int[]> bfs(List<int[]> lista_moves,int[] configF) throws IOException{
        if (!to_STD(lista_moves.get(0))){
            throw new IOException("no solution"); 
        }

        Queue<List<int[]>> fila = new LinkedList<>();
        Set<String> repetidos = new HashSet<>();
        int max_nodes=0;

        fila.add(lista_moves);
        while(!fila.isEmpty()){
            max_nodes=Math.max(max_nodes,fila.size());
            List<int[]> path = fila.poll();
            int[] currentState = path.get(path.size()-1); 
            repetidos.add(Arrays.toString(currentState));

            if(Arrays.equals(currentState,configF)){
                return path;
            }
            
            if(iteration_up(currentState) != null && !repetidos.contains(Arrays.toString(iteration_up(currentState)))) {
                List<int[]> new_path = new ArrayList<>();
                new_path.addAll(path);
                new_path.add(iteration_up(currentState));
                fila.offer(new_path);
                repetidos.add(Arrays.toString(iteration_up(currentState)));
            }
            if(iteration_down(currentState) != null && !repetidos.contains(Arrays.toString(iteration_down(currentState)))) {
                List<int[]> new_path = new ArrayList<>();
                new_path.addAll(path);
                new_path.add(iteration_down(currentState));
                fila.offer(new_path);
                repetidos.add(Arrays.toString(iteration_down(currentState)));
            }
            if(iteration_right(currentState) != null && !repetidos.contains(Arrays.toString(iteration_right(currentState)))) {
                List<int[]> new_path = new ArrayList<>();
                new_path.addAll(path);
                new_path.add(iteration_right(currentState));
                fila.offer(new_path);
                repetidos.add(Arrays.toString(iteration_right(currentState)));
            }
            if(iteration_left(currentState) != null && !repetidos.contains(Arrays.toString(iteration_left(currentState)))) {
                List<int[]> new_path = new ArrayList<>();
                new_path.addAll(path);
                new_path.add(iteration_left(currentState));
                fila.offer(new_path);
                repetidos.add(Arrays.toString(iteration_left(currentState)));
            }
        }
        return new ArrayList<>();
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
        int[] b={};
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
