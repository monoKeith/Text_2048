import java.util.*;

public class main {
    public static void main(String[] args){
        Random random = new Random();
        Scanner input = new Scanner(System.in);
        //Setup game board
        int[][] g_board = new int[4][4];
        int[][] previous_board = new int[4][4];
        //Set two 2 in a random box
        g_board[random.nextInt(4)][random.nextInt(4)] = 2;
        g_board = new_num(g_board);
        previous_board = coopy(g_board);
        //Starts game loop
        boolean keep_running = true, over = false, invalid_option = false;
        while(keep_running){
            //Show current board
            display_board(g_board);
            System.out.print("Enter Next Step: ");
            switch(input.nextLine()) {
                case "w":
                    g_board = function_w(g_board);
                    break;

                case "s":
                    g_board = function_s(g_board);
                    break;

                case "a":
                    g_board = function_a(g_board);
                    break;

                case "d":
                    g_board = function_d(g_board);
                    break;

                case "quit":
                    keep_running = false;
                    break;
                default:
                    invalid_option = true;
                    System.out.println("Invalid option!");
            }
            if(!invalid_option && keep_running){
                if(check_space(g_board)) {
                    if(!Arrays.deepEquals(previous_board, g_board)) {
                        g_board = new_num(g_board);
                        previous_board = coopy(g_board);
                    }else{
                        System.out.println("No change happened! Please type another direction.");
                    }
                }else{ over = true; keep_running = false;
                }
            }else{
                invalid_option = false;
            }
        }
        if(over) System.out.println("Game Over!");

    }

    //s function
    public static int[][] function_s(int[][] g_board){
        //Move boxes down.
        g_board = mov_down_s(g_board);
        //Merge possible boxes.
        for(byte y = 3; y != 0; y--){
            for(byte x = 0; x < 4; x++){
                if(g_board[y][x] == g_board[y-1][x]){
                    g_board[y][x] *= 2;
                    g_board[y-1][x] = 0;
                }
            }
        }
        //Move boxes down.
        g_board = mov_down_s(g_board);
        //return
        return g_board;
    }

    //w function
    public static int[][] function_w(int[][] g_board){
        //Move boxes up.
        g_board = mov_up_w(g_board);
        //Merge possible boxes.
        for(byte y = 0; y < 3; y++){
            for(byte x = 0; x < 4; x++){
                if(g_board[y][x] == g_board[y+1][x]){
                    g_board[y][x] *= 2;
                    g_board[y+1][x] = 0;
                }
            }
        }
        //Move boxes up.
        g_board = mov_up_w(g_board);
        //return
        return g_board;
    }

    //a function
    public static int[][] function_a(int[][] g_board){
        //Move boxes left.
        g_board = mov_left_a(g_board);
        //Merge possible boxes.
        for(byte x = 0; x < 3; x++){
            for(byte y = 0; y < 4; y++){
                if(g_board[y][x] == g_board[y][x+1]){
                    g_board[y][x] *= 2;
                    g_board[y][x+1] = 0;
                }
            }
        }
        //Move boxes left.
        g_board = mov_left_a(g_board);
        //return
        return g_board;
    }

    //d function
    public static int[][] function_d(int[][] g_board){
        //Move boxes right.
        g_board = mov_right_d(g_board);
        //Merge possible boxes.
        for(byte x = 3; x != 0; x--){
            for(byte y = 0; y < 4; y++){
                if(g_board[y][x] == g_board[y][x-1]){
                    g_board[y][x] *= 2;
                    g_board[y][x-1] = 0;
                }
            }
        }
        //Move boxes right.
        g_board = mov_right_d(g_board);
        //return
        return g_board;
    }

    //s function move down
    public static int[][] mov_down_s(int[][] g_board){
        boolean modification = true;
        while(modification){
            modification = false;
            for(byte y = 3; y != 0; y--){
                for(byte x = 0; x < 4; x++){
                    if(g_board[y][x] == 0 && g_board[y-1][x] != 0){
                        g_board[y][x] = g_board[y-1][x];
                        g_board[y-1][x] = 0;
                        modification = true;
                    }
                }
                if(modification) break;
            }
        }
        return g_board;
    }

    //w function move up
    public static int[][] mov_up_w(int[][] g_board){
        boolean modification = true;
        while(modification){
            modification = false;
            for(byte y = 0; y < 3; y++){
                for(byte x = 0; x < 4; x++){
                    if(g_board[y][x] == 0 && g_board[y+1][x] != 0){
                        g_board[y][x] = g_board[y+1][x];
                        g_board[y+1][x] = 0;
                        modification = true;
                    }
                }
                if(modification) break;
            }
        }
        return g_board;
    }

    //a function move left
    public static int[][] mov_left_a(int[][] g_board){
        boolean modification = true;
        while(modification){
            modification = false;
            for(byte x = 0; x < 3; x++){
                for(byte y = 0; y < 4; y++){
                    if(g_board[y][x] == 0 && g_board[y][x+1] != 0){
                        g_board[y][x] = g_board[y][x+1];
                        g_board[y][x+1] = 0;
                        modification = true;
                    }
                }
                if(modification) break;
            }
        }
        return g_board;
    }

    //d function move right
    public static int[][] mov_right_d(int[][] g_board){
        boolean modification = true;
        while(modification){
            modification = false;
            for(byte x = 3; x != 0; x--){
                for(byte y = 0; y < 4; y++){
                    if(g_board[y][x] == 0 && g_board[y][x-1] != 0){
                        g_board[y][x] = g_board[y][x-1];
                        g_board[y][x-1] = 0;
                        modification = true;
                    }
                }
                if(modification) break;
            }
        }
        return g_board;
    }

    //Generate new number (2 or 4) on empty space
    public static int[][] new_num(int[][] g_board){
        Random random = new Random();
        boolean not_modified = true;
        int x, y;
        while(not_modified){
            x = random.nextInt(4); y = random.nextInt(4);
            if(g_board[y][x] == 0){
                g_board[y][x] = 2 * (1 + random.nextInt(2));
                not_modified = false;
            }
        }
        return g_board;
    }

    //Check space
    public static boolean check_space(int[][] g_board){
        boolean valid = false;
        for(byte y = 0; y < 4; y++){
            for(byte x = 0; x < 4; x++){
                if(g_board[y][x] == 0){ valid = true; break; }
            }
            if(valid) break;
        }
        return valid;
    }

    //Print method
    public static void display_board(int[][] g_board){
        for(byte y = 0; y < 4; y ++){
            System.out.print("   | ");
            for(byte x = 0; x < 4; x++){
                if(g_board[y][x] != 0) System.out.print(String.format("%4d", g_board[y][x]) + " ");
                else System.out.print("     ");
            }
            System.out.println("|");
        }
    }

    //Deep copy list
    public static int[][] coopy(int[][] g_board){
        int[][] result = new int[4][4];
        for(byte y = 0; y < 4; y++){
            for(byte x = 0; x < 4; x++){
                result[y][x] = g_board[y][x];
            }
        }
        return result;
    }

}
