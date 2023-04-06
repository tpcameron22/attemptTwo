public class gobMove implements IMove{

    int start;
    int end;
    int pMove;

    public gobMove(int pMove){
        this.pMove = pMove;
    }
    public gobMove(int start, int end){
        this.start = start;
        this.end = end;
    }
}
