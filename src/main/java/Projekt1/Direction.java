package Projekt1;

public enum Direction {
    N, E, S, W;

    static {
        N.left = W; N.right = E;
        E.left = N; E.right = S;
        S.left = E; S.right = W;
        W.left = S; W.right = N;
    }

    private Direction left, right;
    public Direction turnTo(Turn t){ return t == Turn.LEFT ? left : right;}
}