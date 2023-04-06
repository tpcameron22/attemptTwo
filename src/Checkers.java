public class Checkers implements IGame{
    public final String BLACK = "\033[0;30m";   // BLACK
    public final String RESET = "\033[0m";  // Text Reset
    public final String WHITE_BOLD = "\033[1;97m";   // WHITE
    public final String WHITE = "\033[0;37m";   // WHITE
    public Piece[][] Board = new Piece[8][8];

    boolean doubleJump = false;

    public void fillBoard(IGame gameObj){ //doesn't need gameObj but gobblet does.
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                if (i < 3 && (i + j) % 2 == 0) {
                    Board[i][j] = new Piece(PlayerNumber.ONE, 0);
                }
                else if ((i + j) % 2 == 0 && i > 4) {
                    Board[i][j] = new Piece(PlayerNumber.TWO, 0);
                }
                else{
                    Board[i][j] = new Piece(PlayerNumber.EMPTY, 0);
                }
            }
        }
    }

    public String boardToString(){
        //create string
        StringBuilder FString = new StringBuilder();
        FString.append("\n    Checkers    \n   1   2   3   4   5   6   7   8\n");

        for(int i = 0; i <  8; i++) {
            FString.append(i+1).append("| ");
            for (int j = 0; j < 8; j++) {
                //change color
                if(Board[i][j].player == PlayerNumber.TWO){
                    FString.append(BLACK);
                } else if(Board[i][j].player == PlayerNumber.ONE){
                    FString.append(WHITE_BOLD);
                }
                //else turn white
                else{
                    FString.append(WHITE);
                }
                //print number
                FString.append(Board[i][j].value);
                //reset color for each number
                FString.append(RESET + " | ");
            }
            FString.append("\n  ---+---+---+---+---+---+---+---+\n");
        }
        return FString.toString();
    } //make have no arguments and take in the board field

    public whoWon winCheck() {

        //get the players whose turn it isn't
        PlayerNumber otherPlayer = (getPlayer() == PlayerNumber.ONE) ? PlayerNumber.TWO : PlayerNumber.ONE;

        //check to see if there is any pieces left of the other player
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(Board[i][j].player == otherPlayer){
                    return whoWon.InProgress;
                }
            }
        }
        return getPlayer() == PlayerNumber.ONE ? whoWon.PlayerOne : whoWon.PlayerTwo;
    }

    public PlayerNumber getPlayer() {
        return Main.count % 2 == 0 ? PlayerNumber.ONE : PlayerNumber.TWO;
    }

    public boolean isValidMove(int startX, int startY, int endX, int endY, IGame gameObj){
        //check to see if move is a diagonal
        int checkX = endX - startX;
        int checkY = endY - startY;
        //check to see if end space is a diagonal
        if(!(Math.abs(checkX) == 1 && Math.abs(checkY) == 1)){
            System.out.println("you must move diagonally");
            return false;
        }
        //check to make sure pieces only move forward
        if(getPlayer() == PlayerNumber.ONE && (checkY < 0)){
            System.out.println("you cant move backwards!");
            return false;
        }
        else if(getPlayer() == PlayerNumber.TWO && (checkY > 0)){
            System.out.println("you cant move backwards!");
            return false;
        }
        //check to see if endpoint is empty
        else if(Board[endY][endX].player != PlayerNumber.EMPTY){
            System.out.println("end space is not empty");
            return false;
        }
        //check to see if start spot has player whose tun it is
        else if(Board[startY][startX].player != getPlayer()){
            System.out.println("incorrect players piece");
            return false;
        }
        return true;
    }
    public boolean isValidJump(int startX, int startY, int endX, int endY, IGame gameObj){
        //check to see if move is a diagonal
        int checkX = endX - startX;
        int checkY = endY - startY;

        //get piece that is to be jumped
        int midX = (checkX)/ 2 + startX;
        int midY = (checkY)/ 2 + startY;

        //check to make sure values are on board
        if(!((startX > 0 && startX < 8) && (endX > 0 && endX < 8) && (startY > 0 && startY < 8) && (endY > 0 && endY < 8))){
            return false;
        }

        //check to see if end space is a diagonal
        if(!(Math.abs(checkX) == 2 && Math.abs(checkY) == 2)){
            if(!doubleJump) {
                System.out.println("not diagonal");
            }
            return false;
        }
        //make sure its correct direction
        else if(getPlayer() == PlayerNumber.ONE && (checkY < 0)){
            if(!doubleJump) {
                System.out.println("you cant move backwards!");
            }
            return false;
        }
        else if(getPlayer() == PlayerNumber.TWO && (checkY > 0)){
            if(!doubleJump) {
                System.out.println("you cant move backwards!");
            }
            return false;
        }
        //check to see if endpoint is empty
        else if(Board[endY][endX].player != PlayerNumber.EMPTY){
            if(!doubleJump) {
                System.out.println("end space is not empty");
            }
            return false;
        }
        //check to see if start spot has player whose tun it is
        else if(Board[startY][startX].player != getPlayer()){
            if(!doubleJump) {
                System.out.println("incorrect players piece");
            }
            return false;
        }
        //check to see if player getting jumped is opposite player
        else if(Board[midY][midX].player == getPlayer() || Board[midY][midX].player == PlayerNumber.EMPTY){
            if(!doubleJump) {
                System.out.println("piece cannot be jumped");
            }
            return false;
        }

        return true;
    }
    public void jumpPiece(IMove move, IGame gameObj){

        //starting coordinates
        int startX = Integer.parseInt(Integer.toString(move.start).substring(0, 1))-1;
        int startY = Integer.parseInt(Integer.toString(move.start).substring(1, 2))-1;
        //ending coordinates
        int endX = Integer.parseInt(Integer.toString(move.end).substring(0, 1))-1;
        int endY = Integer.parseInt(Integer.toString(move.end).substring(1, 2))-1;
        //mid coordinates (jumped piece)
        int midX = (endX - startX)/ 2 + startX;
        int midY = (endY - startY)/ 2 + startY;


        //if jump is valid, do jump
        if (isValidJump(startX, startY, endX, endY, gameObj)) {
            //update move
            Board[endY][endX] = Board[startY][startX];
            //replace moved piece
            Board[startY][startX] = new Piece(PlayerNumber.EMPTY, 0);
            //remove jumped piece
            Board[midY][midX] = new Piece(PlayerNumber.EMPTY, 0);
            //end turn
            Main.singleMove = false;
            //print board
            Text.printBoard(gameObj); //print board
        }

        //check for double jump. This is where things get tricky
        doubleJump = true; //enact double jump to turn off failure messages

        //we must do 3 for new start/end and when it is parsed into the jump, it subtracts one

        move.start = Integer.parseInt(Integer.toString(endX + 1) + Integer.toString(endY + 1)); // make new start position

        if(isValidJump(endX, endY, endX + 2, endY + 2, gameObj)){         //IF two diagonal is valid jump
            move.end = Integer.parseInt(Integer.toString(endX + 3) + Integer.toString(endY + 3)); //combine into single int
            jumpPiece(move, gameObj);                                 //do jump
            System.out.print("\n Double Jump!");

        }
        else if(isValidJump(endX, endY, endX - 2, endY + 2, gameObj)){
            move.end = Integer.parseInt(Integer.toString(endX - 1) + Integer.toString(endY + 3));
            jumpPiece(move, gameObj);
            System.out.print("\n Double Jump!");
        }
        else if(isValidJump(endX, endY, endX - 2, endY - 2, gameObj)){
            move.end = Integer.parseInt(Integer.toString(endX - 1) + Integer.toString(endY - 1));
            jumpPiece(move, gameObj);
            System.out.print("\n Double Jump!");
        }
        else if(isValidJump(endX, endY, endX + 2, endY - 2, gameObj)){
            move.end = Integer.parseInt(Integer.toString(endX + 3) + Integer.toString(endY - 1));
            jumpPiece(move, gameObj);
            System.out.print("\n Double Jump!");
        }
        doubleJump = false;// reset double jump
    }

    public void movePiece(IMove move, IGame gameObj){

        //starting coordinates
        int startX = Integer.parseInt(Integer.toString(move.start).substring(0, 1))-1;//;
        int startY = Integer.parseInt(Integer.toString(move.start).substring(1, 2))-1;
        //ending coordinates
        int endX = Integer.parseInt(Integer.toString(move.end).substring(0, 1))-1;
        int endY = Integer.parseInt(Integer.toString(move.end).substring(1, 2))-1;

        //if move is valid, do move
        if (isValidMove(startX, startY, endX, endY, gameObj)) {

            //update move
            Board[endY][endX] = Board[startY][startX];
            //replace moved piece
            Board[startY][startX] = new Piece(PlayerNumber.EMPTY, 0);
            //end turn
            Main.singleMove = false;
            //print board
            Text.printBoard(gameObj); //print board   TEXT
        }
    }


}
