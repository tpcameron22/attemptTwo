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
    public void makeMove(String move, IMove iMove, IGame gameObj);




    //Gobblet only
    public Stack<Piece>[] makePieces(PlayerNumber player);

}
