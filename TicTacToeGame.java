import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.AWTEvent;

// Minimal GUI for TicTacToe
public class TicTacToeGame extends JFrame {
	public static final int ROWS = 3;
	public static final int COLS = 3;
	static final int SIZE = 100;
	static Polygon cross;
	private int turn;				// current player  (player 1 starts)
	int humanPlayer;
	TicTacToePlayer computerPlayer;
	private TicTacToeBoard board;

	public TicTacToeGame(int human) {
		super("TicTacToe Test GUI");
		humanPlayer = human;
		board = new TicTacToeBoard();
		computerPlayer = new TicTacToePlayer(this, -human);
		int[] x = new int[]{15, SIZE/2, SIZE-15,
							SIZE-8, SIZE/2+5, SIZE-8,
							SIZE-15, SIZE/2, 15,
							8, SIZE/2-5, 8};
		int[] y = new int[]{8, SIZE/2-5, 8,
							15, SIZE/2, SIZE-15,
							SIZE-8, SIZE/2+5, SIZE-8,
							SIZE-15, SIZE/2, 15};
		cross = new Polygon(x, y, 12);
		init();
		setSize(COLS*SIZE, ROWS*SIZE);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	} // constructor TicTacToeBoard

	public void init() {
		board.init();
		turn = 1;	// player 1 starts
		repaint();
		System.out.println("New game: Player 1 starts");
		if (humanPlayer == -1)
			computerPlayer.makeMove();
	} // init()

	public void copy(TicTacToeBoard b) {
		b.copy(board);
	} // copy()

	public void makeMove(int row, int col, int player) {
		board.makeMove(row, col, player);
	} // makeMove()

	public static void error(String message) {
		System.err.println("Error: " + message);
		System.exit(1);
	} // error()

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, COLS * SIZE, ROWS * SIZE);
		g.setColor(Color.black);
		for (int r = 1; r < ROWS; r++)
			g.fillRect(0, r*SIZE - 2, SIZE*COLS, 4);
		for (int c = 1; c < COLS; c++)
			g.fillRect(c*SIZE - 2, 0, 4, SIZE*ROWS);
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++) {
				if (board.cell[r][c] == 1) {
					g.setColor(Color.black);
					g.fillOval(c*SIZE+10, r*SIZE+10, SIZE-20, SIZE-20);
					g.setColor(Color.white);
					g.fillOval(c*SIZE+20, r*SIZE+20, SIZE-40, SIZE-40);
				} else if(board.cell[r][c] == -1) {
					cross.translate(c*SIZE, r*SIZE);
					g.setColor(Color.black);
					g.fillPolygon(cross);
					cross.translate(-c*SIZE, -r*SIZE);
				}
			}
	} // paint()

	public void update(Graphics g) {
		paint(g); // reduce flicker
	} // update()

	public void processMouseEvent(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_CLICKED) {
			if ((board.moves == 9) || (board.winner != 0))
				init();	// start new game
			else {
				int c = (e.getX()-1) / SIZE;
				int r = (e.getY()-1) / SIZE;
				board.makeMove(r, c, humanPlayer);
				if (board.winner != 0)
					System.out.println("Win for player " + board.winner);
				else if (board.moves == 9)
					System.out.println("Drawn game");
				repaint();
				if ((board.moves < 9) && (board.winner == 0))
					computerPlayer.makeMove();
			}
		}
	} // processMouseEvent()

	public static void main(String[] args) {
		int humanPlayer = 1;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-1"))
				humanPlayer = -1;
		}
		TicTacToeGame gui = new TicTacToeGame(humanPlayer);
	} // main

} // class TicTacToeBoard
