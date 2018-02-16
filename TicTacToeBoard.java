// Board object for TicTacToe
public class TicTacToeBoard {
	public int[][] cell;		// 0 for empty, else player number (1 or -1)
	public int moves;
	public int winner;
	public int turn;

	public TicTacToeBoard() {
		cell = new int[TicTacToeGame.ROWS][TicTacToeGame.COLS];
		init();
	} // constructor TicTacToeBoard

	public void init() {
		moves = 0;
		winner = 0;
		turn = 1;
		for (int r = 0; r < TicTacToeGame.ROWS; r++)
			for (int c = 0; c < TicTacToeGame.COLS; c++)
				cell[r][c] = 0;
	} // init()

	public void copy(TicTacToeBoard b) {
		moves = b.moves;
		winner = b.winner;
		turn = b.turn;
		for (int r = 0; r < TicTacToeGame.ROWS; r++)
			for (int c = 0; c < TicTacToeGame.COLS; c++)
				cell[r][c] = b.cell[r][c];
	} // init()

	public void makeMove(int row, int col, int player) {
		if (player != turn)
			TicTacToeGame.error("Illegal move: Out of turn (" + player + ")");
		if ((col < 0) || (col >= TicTacToeGame.COLS) ||
			(row < 0) || (row >= TicTacToeGame.ROWS))
			TicTacToeGame.error("Illegal move: Out of range (" +
								row + "," + col + ")");
		if (cell[row][col] != 0)
			TicTacToeGame.error("Illegal move: Cell occupied (" +
								row + "," + col + ")");
		cell[row][col] = turn;
		moves++;
		turn = -turn;
		winner = checkForWinner();
	} // makeMove()

	public void unmakeMove(int row, int col) {
		if ((col < 0) || (col >= TicTacToeGame.COLS) ||
			(row < 0) || (row >= TicTacToeGame.ROWS))
			TicTacToeGame.error("Illegal move: Out of range (" +
								row + "," + col + ")");
		if (cell[row][col] == 0)
			TicTacToeGame.error("Illegal move: Cell unoccupied (" +
								row + "," + col + ")");
		cell[row][col] = 0;
		moves--;
		turn = -turn;
		winner = checkForWinner();
	} // unmakeMove()

	public int checkForWinner() {
		for (int r = 0; r < TicTacToeGame.ROWS; r++)	// check rows
			if (	(cell[r][0] == cell[r][1]) &&
			    	(cell[r][0] == cell[r][2]) &&
					(cell[r][0] != 0)	)
				return cell[r][0];
		for (int c = 0; c < TicTacToeGame.COLS; c++)	// check columns
			if (	(cell[0][c] == cell[1][c]) &&
			    	(cell[0][c] == cell[2][c]) &&
					(cell[0][c] != 0)	)
				return cell[0][c];
		if (	(cell[0][0] == cell[1][1]) &&			// check first diagonal
				(cell[0][0] == cell[2][2]) &&
				(cell[0][0] != 0)	)
			return cell[0][0];
		if (	(cell[2][0] == cell[1][1]) &&			// check other diagonal
				(cell[2][0] == cell[0][2]) &&
				(cell[2][0] != 0)	)
			return cell[2][0];
		return 0;
	} // checkForWinner()

} // class TicTacToeBoard
