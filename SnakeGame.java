import javax.swing.JFrame;

public class SnakeGame extends JFrame {
    Board board;
    // constructor
    SnakeGame(){
        board = new Board();
        add(board);
        pack();// get size of 400*400 from boarc class
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        // initialize snake game

        SnakeGame snakeGame = new SnakeGame();
    }
}
