import java.util.Random;

public class Board {

	private Square[] squares;
	private int[] dice;
	private int diceTot;
	private Random r;
	
	public Board() {
		squares = new Square[9];
		for(int i = 0; i < squares.length; i++) {
			squares[i] = new Square(i);
		}
		dice = new int[] {-1, -1};
		r = new Random();
	}
	
	public Square[] getSquares() {
		return squares;
	}
	
	public int hit(int num) {
		if(diceTot > 0) {
			if(squares[num].isSquareHit()) {
				return 0;	// Square already picked
			}
			if(!validMove(num)) {
			    return 4;
			}
			squares[num].hit();
			diceTot -= squares[num].value();
			if(diceTot > 0) {
				return 1;	// More squares need to be picked.  Pick another!
			} else {
				return 2;	// Done picking squares. Roll again!
			}
		}
		return 3;	// Roll new dice first!
	}
	
	public boolean validMove(int target) {
	    // if target is not hit and the value = current dice total, then this is a valid move
	    if(!squares[target].isSquareHit() && squares[target].value() == diceTot) {
	        return true;
	    }
	    // if target is > dice total, then this is not a valid move
	    if(squares[target].value() > diceTot) {
	        return false;
	    }
	    for(int i = 0; i < squares.length; i++) {
	        // if square is already hit or is our target square, skip it.
	        if(squares[i].isSquareHit() || squares[i].value() == squares[target].value()) {
                continue;
            }
	        // if square1 + target = dice, then this is a valid move
	        if(squares[i].value() + squares[target].value() == diceTot) {
	            return true;
	        }
	        for(int j = i + 1; j < squares.length; j++) {
	            // if square is already hit or is our target square, skip it.
	            if(squares[j].isSquareHit() || squares[j].value() == squares[target].value()) {
	                continue;
	            }
	            // if square1 + square2 + target = dice, then this is a valid move
	            if(squares[i].value() + squares[j].value() + squares[target].value() == diceTot) {
	                return true;
	            } 
	            // if square1 + square2 + target > dice, then don't check next squares (it will only be more greater)
	            else if(squares[i].value() + squares[j].value() + squares[target].value() > diceTot) {
	                break;
	            }
	        }
	    }
	    return false;
	}
	
	public int[] rollDice(int num) {
		if(canRoll()) {
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
	
	public boolean canUseOneDice() {
		if(squares[6].isSquareHit() && squares[7].isSquareHit() && squares[8].isSquareHit()) {
			return true;
		}
		return false;
	}
	
	public boolean canRoll() {
		if(getDiceTotal() <= 0) {
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
	    System.out.println("diceTot = " + diceTot);
	    return diceTot;
	}
	
	public void resetDiceTotal() {
	    diceTot = 0;
	}

}
