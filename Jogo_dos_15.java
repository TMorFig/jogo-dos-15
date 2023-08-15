import java.util.*;
import java.io.*;  
public class Jogo_dos_15 {
    public static void main(String[] args) throws Exception {
        System.out.println("Escolha o seu algoritomo\nopções\n<<BFS DFS IDFS A-misplaced A-Manhattan A-Manhattan Greedy-Manhattan>>");
        Scanner stdin = new Scanner(System.in);
        String a = stdin.nextLine();
        if (a.equals("BFS")){
            BFS_15correto.main(args);
        }
        if(a.equals("DFS")){
            DFS.main(args);
        }
        if(a.equals("IDFS")){
            IDFS.main(args);
        }
        if(a.equals("A-misplaced")){
            Astar_misplaced.main(args);
        }
        if(a.equals("A-Manhattan")){
            Astar_manhattan.main(args);
        }
        if(a.equals("A-Manhattan")){
            greedy_misplaced.main(args);
        }
        if(a.equals("Greedy-Manhattan")){
            greedy_manhattan.main(args);
        }
    }
}
