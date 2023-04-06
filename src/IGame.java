import java.util.Stack;

public interface IGame {

//unsure how to create board field because it has different types

    //makeBoard
    public void fillBoard(IGame gameObj);
    //winCheck
    public whoWon winCheck();
    //boardToString
    public String boardToString();
    //getPlayer
    public PlayerNumber getPlayer();

    public void movePiece(IMove move, IGame iGame);

    public boolean isValidPlace(int moveX, int moveY, int piece, IGame iGame);

    public boolean isValidMove(int startX, int startY, int endX, int endY, IGame iGame);


    //Gobblet only
    public Stack<Piece>[] makePieces(PlayerNumber player);
    public void placePiece(IMove move, IGame iGame);


    //checkers only
    public void jumpPiece(IMove move, IGame gameObj);
}
