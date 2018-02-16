// TicTacToe with minimax search
import java.util.ArrayList;
class TicTacToePlayer {


	TicTacToeGame game;
	int computerPlayer;
	TicTacToeBoard board;

	public TicTacToePlayer(TicTacToeGame game, int computerPlayer) {
		this.game = game;
		this.computerPlayer = computerPlayer;
		board = new TicTacToeBoard();
	} // constructor

	public void printBoard(TicTacToeBoard b){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				System.out.print(b.cell[i][j] + " | ");
			}
			System.out.println("");
		}

	}

	// Computer player: calls minimax algorithm to search complete game tree
	public void makeMove() {
		game.copy(board);	// Get a local copy of the TicTacToeBoard object
							// for use in the minimax search
		int[] result = new int[3];
		try{
			result = max(board);
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println( "THE EXCEPTION: " +e.getMessage());

		}

		System.out.println("this runs");
		game.makeMove(result[0], result[1], computerPlayer);  // execute the move

	} // makeMove()

	private int[] max(TicTacToeBoard board){
		System.out.println("in max turn " + board.turn);
		if(terminalTest(board)){
			System.out.println("terminal max");
			System.out.println(utility(board));
			return new int[] {0, 0, utility(board)};
		}
		int[] best = new int[] {0,0, Integer.MIN_VALUE};

		for(int[] a: actions(board)){
			System.out.println("test max move " + a[0] + ", " + a[1]);
			board.makeMove(a[0], a[1], this.computerPlayer);
			System.out.println("max making move");
			printBoard(board);
			int value = min(board)[2];
			if(value > best[2]){
				best = new int[] {a[0], a[1], value};
				System.out.println("best max move " + best[0] + ", " + best[1]);
			}
			board.unmakeMove(a[0], a[1]);
			System.out.println("max unmaking move");
			printBoard(board);
		}
		System.out.println("max return best " + best[0] + ", " + best[1] + " utility " + best[2]);
		return best;
	}

	private int[] min(TicTacToeBoard board){
		System.out.println("in min turn " + board.turn);
		if(terminalTest(board)){
			System.out.println("terminal min");
			return new int[] {0, 0, utility(board)};
		}
		int[] best = new int[] {0, 0, Integer.MAX_VALUE};

		for(int[] a: actions(board)){//if null?
			System.out.println("possible action " + a[0] + ", " + a[1]);
		}
		for(int[] a: actions(board)){

			System.out.println("test min move " + a[0] + ", " + a[1]);
			board.makeMove(a[0], a[1], -this.computerPlayer);
			System.out.println("min making move");
			printBoard(board);
			int value = max(board)[2];
			System.out.println("best min" + best[2]);
			if(value < best[2]){
				System.out.println("best min move "+ best[0] + ", " + best[1]);
				best = new int[] {a[0], a[1], value};
			}

			board.unmakeMove(a[0], a[1]);
			System.out.println("min unmaking move");
			printBoard(board);
			System.out.println("removed " + a[0] + ", " + a[1]);
		}
		System.out.println("min return best " + best[0] + ", " + best[1] + " utility " + best[2]);
		return best;
	}

	private int utility(TicTacToeBoard board){
		if(board.winner == this.computerPlayer){
			return 1;
		}else if(board.winner == 0){
			return 0;
		}else{
			return -1;
		}
	}

	private boolean terminalTest(TicTacToeBoard board){
		if((board.winner != 0)|| (board.moves == 9)){
			return true;
		}else{
			return false;
		}
	}

	private ArrayList<int[]> actions(TicTacToeBoard board){
		ArrayList<int[]> result = new ArrayList();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(board.cell[i][j] == 0){ // cell is empty
					result.add(new int[] {i, j});
				}
			}
		}
		return result;
	}


	// MORE CODE HERE

} // class TicTacToePlayer
