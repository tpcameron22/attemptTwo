import java.util.Scanner;
import java.util.Stack;

public class Text{


    //prepare scanner
    Scanner scan = new Scanner(System.in);
    public void mainMenu(){
        Main m = new Main();
        System.out.println("Type the number of the game you wish to play \n 1. GOBLET \n 2. CHECKERS \n 3. CONNECT-FOUR \n \n To View rules, Type: 4");
        int choice = scan.nextInt();
        if(choice == 1){
            m.runGob();
        }else if(choice == 2) {
            m.runCheckers();
            System.out.println("Checkers has not been implemented please come back later");
        } else if (choice == 3) {
            //runConnect-Four();
            System.out.println("Connect-Four has not been implemented please come back later");
            mainMenu();
        } else if (choice == 4) {
            rules();
        } else {
            System.out.println("Please choose a number from the list above");
            mainMenu();
        }
    }

    public void rules() {
        System.out.println("What rules would you like to view? type the number next to the game of choice \n 1. GOBLET \n 2. CHECKERS \n 3. CONNECT-FOUR \n \n To return to the main menu, type: 4");
        int choice = scan.nextInt();
        if(choice == 1){
            System.out.println("Gobblet rules");
            rules();
        }else if(choice == 2) {
            System.out.println("Checkers rules");
            rules();
        }else if(choice == 3) {
            System.out.println("Connect-Four rules");
            rules();
        }else if(choice == 4) {
            mainMenu();
        } else {
            System.out.println("Please choose a number from the list above");
            rules();
        }
    }

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

    public void checkSingleMove(int count, IGame gameObj){
        Main m = new Main();
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
            m.executeMove("cMove", start, end, gameObj );
        }
        else if(ask.equals("jump")){
            //set up move
            System.out.println("Type the coordinate of the piece you wish to jump with");
            int start = scan.nextInt();

            System.out.println("Type the coordinate you wish to land after the jump");
            int end = scan.nextInt();

            //execute move
            m.executeMove("cJump", start, end, gameObj);

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
