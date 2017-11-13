public class Piece {

    private boolean moved;
    private int id;
    private int colorID; // white = 0, black = 1, empty = 2

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public int getId() {
        return id;
    }

    public String getPrt() {
        switch (id) {
            case 0:
                return "▢";
            case 1:
                return "♝";
            case 2:
                return "♗";
            case 3:
                return "♞";
            case 4:
                return "♘";
            case 5:
                return "♜";
            case 6:
                return "♖";
            case 7:
                return "♛";
            case 8:
                return "♕";
            case 9:
                return "♚";
            case 10:
                return "♔";
            case 11:
                return "♟";
            case 12:
                return "♙";
        }
        return "▢";
    }

    public int getColorID() {
        return colorID;
    }

    public Piece(int id, int colorID) {
        this.moved = false;
        this.id = id;
        this.colorID = colorID;
    }
}
