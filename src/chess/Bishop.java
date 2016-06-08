package chess;

import java.util.LinkedList;
import java.util.List;


public class Bishop extends Piece{

	protected Bishop(Team team, BoardPosition startingPosition, Board board) {
		super(team, startingPosition, board);
		setSprite("./data/bishop_"+(getTeam().equals(Team.white) ? "w": "b") + ".png");
	}

	@Override
	public List<BoardPosition> getThreatenedSpaces(){
		List<BoardPosition> l = new LinkedList<BoardPosition>();
		for(int n = 1; getRow() + n <= 8 && getCol() + n <=8; n++){
			BoardPosition p = getBoard().getPosition(getRow() + n, getCol() + n);
			if (p.isOccupied()){
				if (p.hasTeam(getEnemy())){
					l.add(p);
				}
				break;
			}
			l.add(p);
		}
		
		for(int n = 1; getRow() + n <= 8 && getCol() - n >=1; n++){
			BoardPosition p = getBoard().getPosition(getRow() + n, getCol() - n);
			if (p.isOccupied()){
				if (p.hasTeam(getEnemy())){
					l.add(p);
				}
				break;
			}
			l.add(p);
		}
		
		for(int n = 1; getRow() - n >= 1 && getCol() + n <=8; n++){
			BoardPosition p = getBoard().getPosition(getRow() - n, getCol() + n);
			if (p.isOccupied()){
				if (p.hasTeam(getEnemy())){
					l.add(p);
				}
				break;
			}
			l.add(p);
		}
		
		for(int n = 1; getRow() - n >= 1 && getCol() - n >= 1; n++){
			BoardPosition p = getBoard().getPosition(getRow() - n, getCol() - n);
			if (p.isOccupied()){
				if (p.hasTeam(getEnemy())){
					l.add(p);
				}
				break;
			}
			l.add(p);
		}
		return l;
	}



}
