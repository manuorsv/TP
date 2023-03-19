package tp.pr3;

public class GameState
{
	private Cell[][] boardState;
	private int score;
	private int bestValue;
	
	/**Crea una copia del tablero y su mejor valor*/
	public GameState(Cell[][] state, int boardScore, int boardBest)
	{
		boardState = new Cell[state.length][state.length];
		for(int i=0; i<state.length; ++i){
			for(int j=0; j<state.length; ++j){
				boardState[i][j] = new Cell();
				boardState[i][j].modify(state[i][j].getValue());
			}
		}
		score = boardScore;
		bestValue = boardBest;
	}
	/**Devuelve el tablero del estado*/
	public Cell[][] getState(){ 		return boardState;}		//Accedentes
	/**Devuelve los puntos del estado*/
	public int getPoints(){				return score;}
	/**Devuelve el mejor valor del estado*/
	public int getBest(){				return bestValue;}
}
