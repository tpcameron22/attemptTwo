import java.util.Stack;

public class Gobblet implements IGame{

    public static Stack<Piece>[] p1Pieces;
    public static Stack<Piece>[] p2Pieces;
    public final String BLACK = "\033[0;30m";   // BLACK
    public final String RESET = "\033[0m";  // Text Reset
    public final String WHITE_BOLD = "\033[1;97m";   // WHITE
    public final String WHITE = "\033[0;37m";   // WHITE
    public Stack<Piece>[][] Board = new Stack[4][4];


    public void fillBoard(IGame gameObj) {
        //Board = new Stack[4][4];
        //fill board with squares
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //initialize each square to have 0
                Stack<Piece> Square = new Stack<>();
                Square.push(new Piece(PlayerNumber.EMPTY, 0));
                //put square on board
                Board[i][j] = Square;
            }
        }
        p1Pieces = gameObj.makePieces(PlayerNumber.ONE); //where can I put these?
        //make a new list for p2
        p2Pieces = gameObj.makePieces(PlayerNumber.TWO);
    }

    public String boardToString(){

        //create string
        StringBuilder FString = new StringBuilder();
        FString.append("\n      GOBLET  \n   1   2   3   4\n");

        for(int i = 0; i <  4; i++) {
            FString.append(i+1).append("| ");
            for (int j = 0; j < 4; j++) {
                //change color
                if(Board[i][j].peek().player == PlayerNumber.TWO){
                    FString.append(BLACK);
                }

                //else turn white
                else if(Board[i][j].peek().player == PlayerNumber.ONE){
                    FString.append(WHITE_BOLD);
                }
                else{
                    FString.append(WHITE);
                }
                //print number
                FString.append(Board[i][j].peek().value);
                //if player two turn black
                FString.append(RESET + " | ");
            }
            FString.append("\n  ---+---+---+----\n");
        }
        return FString.toString();
    }

    public whoWon winCheck() {

        int i, j;
        int counter = 0;

        PlayerNumber playerTurn = getPlayer();
        //iterate through board
        //row
        for (i = 0; i < Board.length; i++) {
            for (j = 0; j < Board[0].length; j++) {
                //check to see if number is whose turn it is
                if (Board[i][j].peek().player != playerTurn) {
                    counter = 0;
                    break;
                }
                counter += 1;
                //check to see if there was a full iteration
                if (counter == 4) {
                    return getPlayer() == PlayerNumber.ONE ? whoWon.PlayerOne : whoWon.PlayerTwo;
                }
            }
        }
        //column
        for (j = 0; j < Board[0].length; j++) {
            for (i = 0; i < Board.length; i++) {
                //check to see if number is whose turn it is
                if (Board[i][j].peek().player != playerTurn) {
                    counter = 0;
                    break;
                }
                counter += 1;
                //check to see if there was a full iteration
                if (counter == 4) {
                    return getPlayer() == PlayerNumber.ONE ? whoWon.PlayerOne : whoWon.PlayerTwo;

                }
            }
        }
        //diagonal

        for (i = 0; i < Board.length; i++) {
            if (Board[i][i].peek().player != playerTurn) {
                counter = 0;
                break;
            }
            counter += 1;
            if (counter == 4) {
                return getPlayer() == PlayerNumber.ONE ? whoWon.PlayerOne : whoWon.PlayerTwo;
            }
        }

        //diagonal2
        for (i = 0; i < Board.length; i++) {
            if (Board[i][Board.length - 1 - i].peek().player != playerTurn) {
                //don't need to reset counter here because it is the last iteration. it'll reset at the top of function. but in case it doesn't work, counter = 0;
                break;
            }
            counter += 1;
            if (counter == 4) {
                return getPlayer() == PlayerNumber.ONE ? whoWon.PlayerOne : whoWon.PlayerTwo;
            }
        }

        //return win
        return whoWon.InProgress;
    }

    public PlayerNumber getPlayer() {
        return Main.count % 2 == 0 ? PlayerNumber.ONE : PlayerNumber.TWO;
    }


    public Stack<Piece>[] makePieces(PlayerNumber player) {
        Stack<Piece>[] pieces = new Stack[3];
        for (int i = 0; i < 3; i++) {
            pieces[i] = new Stack<>();
            pieces[i].push(new Piece(player, 1));
            pieces[i].push(new Piece(player, 2));
            pieces[i].push(new Piece(player, 3));
            pieces[i].push(new Piece(player, 4));
        }

        return pieces;
    }

    //checks to see if the player has the given piece. inverted because it's quicker. It'll stop after finding it.
    public boolean hasPiece(int piece, Stack<Piece>[] playerPieces) {
        for (int i = 0; i < 3; i++) {
            if (!playerPieces[i].empty() && (playerPieces[i].peek().value == piece)) {
                return true;
            }
        }
        return false;
    }

    //removes piece from playerPieces set
    public void subPiece(int piece, Stack<Piece>[] playerPieces) {
        for (int i = 0; i < 3; i++) {
            if (!playerPieces[i].empty() && playerPieces[i].peek().value == piece) {
                playerPieces[i].pop();
                break;
            }
        }
    }

    public boolean isValidPlace(int moveX, int moveY, int piece, IGame gameObj) {
        //check out of bounds
        if (moveX > 3 || moveY > 3) {
            System.out.print("Move Out Of Bounds! Move must be a space from 1 to 4");
            return false;
        }

        //check to see if players have the piece?
        if (gameObj.getPlayer() == PlayerNumber.ONE) { //check what players turn is it
            //check to see if player1 Has given piece
            if (!hasPiece(piece, p1Pieces)) {
                System.out.print("You don't have the piece!");
                return false;
            }
        } else if (gameObj.getPlayer() == PlayerNumber.TWO) {
            //check to see if player2 has given piece
            if (!hasPiece(piece, p2Pieces)) {
                System.out.print("You don't have the piece!");
                return false;
            }
        }

        //check to see if piece is larger than piece on the square
        if (Board[moveY][moveX].peek().value >= piece) { //I don't know why I switched this, but it works, so im doing it for everything now I guess.
            System.out.print("The piece " + piece % 10 + " isn't large enough to be placed at " + (moveX + 1) + "," + (moveY + 1));
            return false;
        }
            //iGame Board is empty here
        return true;
    }

    public boolean isValidMove(int startX, int startY, int endX, int endY, IGame gameObj) {
        //need to check is the start position contains a piece that is the player.
        if (Board[startY][startX].peek().player != gameObj.getPlayer()) {
            System.out.println("You cant move that piece");
            return false;
        }
        //need to check to see if the end position is a valid move
        if (Board[startY][startX].peek().value <= Board[endY][endX].peek().value) {
            System.out.println("Piece cannot be moved there because it is not larger enough");
            return false;
        }

        return true;
    }

    public void placePiece(IMove move, IGame gameObj) {
        gobMove gMove = (gobMove) move;
        //get row and col
        int moveX = Integer.parseInt(Integer.toString(gMove.pMove).substring(0, 1)) - 1;//first digit  cant be zero which is why i have to do this whole shabang
        int moveY = Integer.parseInt(Integer.toString(gMove.pMove).substring(1, 2)) - 1;//second digit
        int piece = Integer.parseInt(Integer.toString(gMove.pMove).substring(2, 3));//third digit


        //execute move
        if (isValidPlace(moveX, moveY, piece, gameObj)) {   //the Igame here contains an empty board which makes no sense
            Board[moveY][moveX].push(new Piece(getPlayer(), piece));//add piece to stack in array
            Main.singleMove = false;

            //remove
            if (getPlayer() == PlayerNumber.ONE) {
                subPiece(piece, p1Pieces);
            } else {
                subPiece(piece, p2Pieces);
            }
            Text.printBoard(gameObj);
        }
    }

    public void movePiece(IMove move, IGame gameObj) {
        gobMove gMove = (gobMove) move;
        //starting coordinates
        int startX = Integer.parseInt(Integer.toString(gMove.start).substring(0, 1)) - 1;//;
        int startY = Integer.parseInt(Integer.toString(gMove.start).substring(1, 2)) - 1;
        //ending coordinates
        int endX = Integer.parseInt(Integer.toString(gMove.end).substring(0, 1)) - 1;//;
        int endY = Integer.parseInt(Integer.toString(gMove.end).substring(1, 2)) - 1;

        //if move is valid, do move
        if (isValidMove(startX, startY, endX, endY, gameObj)) {
            //execute move. remove start piece and place onto end piece
            Board[endY][endX].push(Board[startY][startX].pop());
            //end turn
            Main.singleMove = false;
            //print board
            Text.printBoard(gameObj);
        }
    }


    //only here to fulfill the interface
    public void jumpPiece(IMove move, IGame gameObj){}
}

