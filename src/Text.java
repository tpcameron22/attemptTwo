import java.util.Scanner;
import java.util.Stack;

public class Text{


    //prepare scanner
    Scanner scan = new Scanner(System.in);

    public void displayTurn(boolean player1) {
        //state whose turn it is and print their available pieces
        if (player1) {
            System.out.print("Player Ones Turn");
            if (Gobblet.p1Pieces != null) {
                System.out.print(toString(Gobblet.p1Pieces));
            }
        } else {
            System.out.print("Player Twos Turn");
            if (Gobblet.p2Pieces != null) {
                System.out.print(toString(Gobblet.p2Pieces));
            }
        }
    }

    public void gobSingleMove(int count, IGame gameObj) {
        Main main = new Main();

        if (count < 2) {
            System.out.println("\n" + "Type place to place a new piece");
        } else {
            System.out.println("\n" + "Type place to place a new piece or move to move an existing piece");
        }

        String ask = scan.next();

        if (ask.equals("place")) {
            System.out.println("\n" + "enter move in the form of int(X axis) int(Y axis) int(Piece Size) to place a new coordinate. For Example: 123");
            int pMove = scan.nextInt();

            //execute place
            main.executeMove("gPlace", 0, 0, pMove, gameObj);//pieces reset inside of this. debug second turn starting here

        } else if (ask.equals("move")) {
            //set up move
            System.out.println("Type the coordinate of the piece you wish to move");
            int start = scan.nextInt();

            System.out.println("Type the coordinate you wish to move that piece to");
            int end = scan.nextInt();

            //execute move
            main.executeMove("gMove", start, end, 0, gameObj);
        } else {
            System.out.println("Invalid Input: please type either place or move, all lowercase"); //print board
        }
    }

    public void checkSingleMove(int count){
        if(count > 2) {
            System.out.println("\n" + "Type move to move a piece");
        } else {
            System.out.println("\n" + "Type  move to move a piece or jump to jump a piece");
        }
        String ask = scan.next();
        if (ask.equals("move")) {
            //set up move
            System.out.println("Type the coordinate of the piece you wish to move");
            int start = scan.nextInt();

            System.out.println("Type the coordinate you wish to move that piece to");
            int end = scan.nextInt();

            //execute move
            Main.executeMove("cMove", start, end);
        }
        else if(ask.equals("jump")){
            //set up move
            System.out.println("Type the coordinate of the piece you wish to jump with");
            int start = scan.nextInt();

            System.out.println("Type the coordinate you wish to land after the jump");
            int end = scan.nextInt();

            //execute move
            Main.executeMove("cJump", start, end);

        } else {
            System.out.println("Invalid Input: please type either move or jump, all lowercase"); //print board
        }
    }


    public static void printBoard(IGame iGame){
        System.out.print(iGame.boardToString());
    }

    public String toString(Stack<Piece>[] pieces) {
        StringBuilder FString = new StringBuilder("\n" + "playable pieces:  | ");

        for (int i = 0; i < 3; i++) {
            if(!pieces[i].empty()) {
                FString.append(pieces[i].peek().value);
            }else{
                FString.append(" ");
            }
            FString.append(" | ");
        }

        return FString.toString();
    }


    //only one needed because all games wincheck will return a whoWon
    public static void checkWinnerText(whoWon player) {
        System.out.println(player + " Won the game!");
    }

}
