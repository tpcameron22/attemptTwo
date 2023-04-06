import java.util.Objects;

public class Main {
    public static int count = 0;
    public static boolean singleMove = true;
    public static boolean game = true;
    IGame gameObj;


    public static void main(String[] args) {
        Main m = new Main();
        m.runGob();
    }
    public void runGob(){
        gameObj = new Gobblet();
        //reset in case game was just played
        game = true;
        count = 0;

        //make a new text object
        Text text = new Text();

        //make pieces
        Gobblet.p1Pieces = gameObj.makePieces(PlayerNumber.ONE); //where can I put these?
        //make a new list for p2
        Gobblet.p2Pieces = gameObj.makePieces(PlayerNumber.TWO);

        //fill the object to make the board
        gameObj.fillBoard(gameObj);
        Text.printBoard(gameObj);

        //input a move
        while (game) {

            //turn checker
            boolean player1 = gameObj.getPlayer() == PlayerNumber.ONE;

            //state whose turn it is and print their available pieces
            text.displayTurn(player1);

            //ask for move
            while (singleMove) {
                text.gobSingleMove(count, gameObj);

                //check for gameStatus
                checkWinner();
            }
            count += 1;// update counter saying a move has been completed, incrementing whose turn it is
            singleMove = true; //reset singleMove //accurate pieces
        }
        //Text.mainMenu();
    }
    public void runCheckers() {
        gameObj = new Checkers();
        //reset in case game was just played
        game = true;
        count = 0;

//make objects
        Text text = new Text();

        //make and initialize board
        gameObj.fillBoard(gameObj);
        Text.printBoard(gameObj);



        while (game) {
            boolean player1 = gameObj.getPlayer() == PlayerNumber.ONE;
            //state whose turn it is
            text.displayTurn(player1);

            //execute turn
            while (singleMove) {
                //execute single move
                text.checkSingleMove(count);

                //check for gameStatus
                checkWinner();

            }
            count += 1;// update counter saying a move has been completed
            singleMove = true; //reset singleMove

        }
    }
    public void executeMove(String move, int start, int end, int pMove, IGame gameObj) {
        IMove gMove;
            if(Objects.equals(move, "gPlace")){ gMove = new gobMove(pMove);}
            else{ gMove = new gobMove(start, end);}
        gameObj.makeMove(move, gMove ,gameObj);
    }

    public void executeMove(String move, int start, int end){
        checkMove cMove = new checkMove(start, end);
        gameObj.makeMove(move, cMove ,gameObj);
    }
    public void checkWinner(){

        if (gameObj.winCheck() != whoWon.InProgress) {
            Text.checkWinnerText(gameObj.winCheck());
            game = false;//end game cycle
        }
    }
}
