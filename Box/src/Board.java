import java.awt.EventQueue;
import java.util.Random;

public class Board {

	private Square[] squares;
	private int turns;
	private int[] dice;
	private int diceTot;
	private Random r;
	
	public Board() {
		squares = new Square[9];
		for(int i = 0; i < squares.length; i++) {
			squares[i] = new Square(i);
		}
		turns = 2;
		dice = new int[] {-1, -1};
		r = new Random();
	}
	
	public Square[] getSquares() {
		return squares;
	}
	
	public boolean haveTurns(int num) {
		if(turns < 2) {
			return true;
		}
		return false;
	}
	
	public int hit(int num) {
		int badPlay = 1;
		//System.out.println("turns before = " + turns);
		if(haveTurns(num)) {
			if(squares[num].isSquareHit()) {
				return 0;	// Square already picked
			} if(squares[num].value() > diceTot || (turns == 1 && squares[num].value() < diceTot)) {
				return 4;	// Square value is greater than dice result, or second pick doesn't add up to dice result
			} if(turns == 0) {
				for(int i = 0; i < squares.length; i++) {
					if(squares[i].isSquareHit() || squares[i].value() == squares[num].value()) {
						continue;
					} else if(squares[i].value() + squares[num].value() == diceTot || (!squares[num].isSquareHit() && squares[num].value() == diceTot)) {
						badPlay = 0;
					}
				}
				if(badPlay == 1) {
					return 4;
				}
			}
			squares[num].hit();
			turns++;
			//System.out.println("turns after = " + turns + ", total remain = " + diceTot);
			if(squares[num].value() == diceTot) {
				turns++;
				diceTot = 0;
				//System.out.println("turns increased again! turns = " + turns);
			}
			if(turns == 1) {
				diceTot -= squares[num].value();
				//System.out.println("syke, total remain = " + diceTot);
				return 1;	// First square just picked
			} else {
				diceTot = 0;
				return 2;	// Second square just picked
			}
		}
		return 3;	// User has no turns left
	}
	
	public int[] rollDice(int num) {
		if(canRoll()) {
			turns = 0;
			dice[0] = r.nextInt(6) + 1;
			if(num == 2) {
				dice[1] = r.nextInt(6) + 1;
			} else {
				dice[1] = 0;
			}
			diceTot = dice[0] + dice[1];
			return dice;
		}
		dice[0] = -1;
		dice[1] = -1;
		return dice;
	}
	
	public boolean canUseTwoDice() {
		if(squares[6].isSquareHit() && squares[7].isSquareHit() && squares[8].isSquareHit()) {
			return false;
		}
		return true;
	}
	
	public boolean canRoll() {
		if(turns >= 2) {
			return true;
		}
		return false;
	}
	
	public boolean checkLose() {
		for(int i = 0; i < squares.length; i++) {
			if(squares[i].isSquareHit()) {
				continue;
			}
			if(squares[i].value() == diceTot) {
				return false;
			}
			if(squares[i].value() > diceTot) {
				break;
			}
			for(int j = i + 1; j < squares.length; j++) {
				if(squares[j].isSquareHit()) {
					continue;
				}
				if(squares[i].value() + squares[j].value() == diceTot) {
					return false;
				}
				if(squares[j].value() > diceTot) {
					break;
				}
			}
		}
		return true;
	}
	
	public int checkScore() {
		int score = 0;
		for(int i = 0; i < squares.length; i++) {
			if(!squares[i].isSquareHit()) {
				score += squares[i].value();
			}
		}
		return score;
	}
	
	public int getDiceTotal() {
		return diceTot;
	}
	
	public void setTurns(int turns) {
		this.turns = turns;
	}
}
