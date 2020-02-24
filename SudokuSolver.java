/*
Joshua S. Tolbert
 */
package sudokusolver;
import java.util.Scanner;
import java.lang.Math;
import java.util.InputMismatchException;
/**
 *
 * @author josht
 */
public class SudokuSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] board = {{0, 0, 0, 0, 0, 0, 0, 0, 8},
                         {1, 8, 0, 0, 0, 2, 3, 0, 0},
                         {0, 6, 0, 0, 5, 7, 0, 0, 1},
                         {0, 7, 0, 9, 6, 0, 0, 0, 0},
                         {0, 9, 0, 7, 0, 4, 0, 1, 0},
                         {0, 0, 0, 0, 8, 1, 0, 4, 0},
                         {6, 0, 0, 2, 4, 0, 0, 8, 0},
                         {0, 0, 4, 5, 0, 0, 0, 9, 3},
                         {5, 0, 0, 0, 0, 0, 0, 0, 0}};
        int[][] sBoard = copyArr(board);
        boolean cont = true;
        Scanner keyboard = new Scanner(System.in);
        while(cont) {
            printBoard(board);
            System.out.println("Enter the number you want to add, followed by the row "
                    + "and the column, all on seperate lines. \nIf you want me to " 
                    + "solve it, enter 100 for all fields.");
            try {
                int test = keyboard.nextInt();
                keyboard.nextLine();
                int x = keyboard.nextInt();
                keyboard.nextLine();
                int y = keyboard.nextInt();
                keyboard.nextLine();
                if (x == 100 && y == 100) {
                    System.out.println("Well, you did your best but I am superior."
                            + " Below is the solved board.");
                    solver(sBoard);
                    printBoard(sBoard);
                    break;
                }
                if(validVal(test)&&validVal(x)&&validVal(y)){
                    makeMove(test, x, y, board);
                }
                else{
                    System.out.println("One of your values is invalid, they must be "
                            + "between 1 and 9.");
                }
                if (isComplete(board)) {
                    cont = false;
                    System.out.println("You did it! Congrats! It only took you about 83 years!..."
                            + "stupid humans....");
                    printBoard(board);
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid Input, as expected from a human..");
                return;
            }
            
        }
    }
    
    public static boolean validVal(int val) {
        if(val >= 1 && val <= 9) {
            return true;
        }
        else{
            return false;
        }
    }
    
    public static int[][] copyArr(int[][] arr) {
        int[][] bd = new int[arr.length][arr.length];
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                bd[i][j] = arr[i][j];
            }
        }
        return bd;
    }
    
    public static void printBoard(int[][] arr) {
        System.out.println("______________Board_______________");
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j]+" | ");
            }
            if(i == 2 || i == 5 || i == 8){
                System.out.println("\n__________|___________|___________|");
            }
            else{
                System.out.println("\n__ ___ ___|___ ___ ___|___ ___ ___|");
            }
        }
    }
    
    public static boolean testRC(int x1, int y1, int testNum, int[][] bd) {
        boolean valid = true;
        for(int i = 0; i < bd.length; i++) {
            if(bd[x1][i] == testNum) {
                valid = false;
            }
            if(bd[i][y1] == testNum) {
                valid = false;
            }
        }
        
        return valid;
    }
    
    public static boolean testBox(int x1, int y1, int testNum, int[][] bd) {
        boolean valid = true;
        int sq = (int) Math.sqrt(bd.length);
        int boxRow = x1 - (x1 % sq);
        int boxCol = y1 - (y1 % sq);
        for(int i = boxRow; i <= (boxRow + 2); i++) {
            for(int j = boxCol; j <= (boxCol + 2); j++) {
                if(bd[i][j] == testNum) {
                    valid = false;
                }
            }
        }
        return valid;
    }
    
    public static boolean test(int test, int x, int y, int[][] bd) {
        if(testRC(x, y, test, bd) && testBox(x, y, test, bd)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean isComplete(int[][] bd) {
        for(int i = 0; i < bd.length; i++) {
            for(int j = 0; j < bd[i].length; j++) {
                if(bd[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean solver(int[][] bd) {
        int len = bd.length;
        int row = -1;
        int col = -1;
        boolean empty = true;
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(bd[i][j] == 0) {
                    row = i;
                    col = j;
                    empty = false;
                    break;
                }
            }
            if(!empty){
                break;
            }
        }
        if(empty) {
            return true;
        }
        for(int i = 1; i <= len; i++) {
            if(test(i, row, col, bd)) {
                bd[row][col] = i;
                if(solver(bd)) {
                    return true;
                }
                else{
                    bd[row][col] = 0;
                }
            }
        }
        return false;    
    }
    
    public static void makeMove(int test, int x, int y, int[][] bd) {
        x--;
        y--;
        if(test(test, x, y, bd)) {
            System.out.println("Valid Move!");
            bd[x][y] = test;  
        }
        else{
            System.out.println("Invalid Move :/");
        }
    }
}
