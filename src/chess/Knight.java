package chess;

import java.util.LinkedList;
import java.util.List;


public class Knight extends Piece{

	protected Knight(Team team, BoardPosition startingPosition, Board board) {
		super(team, startingPosition, board);
		setSprite("./data/knight_"+(getTeam().equals(Team.white) ? "w": "b") + ".png");
	}


	@Override
	public List<BoardPosition> getThreatenedSpaces() {
		List<BoardPosition> l = new LinkedList<BoardPosition>();
		BoardPosition p;
		if (getRow() - 2 >= 1 && getCol() - 1 >= 1 && 
				!(p = getBoard().getPosition(getRow() - 2, getCol() - 1)).hasTeam(getTeam())){
			l.add(p);
		}
		if (getRow() - 2 >= 1 && getCol() + 1 <= 8 && 
				!(p = getBoard().getPosition(getRow() - 2, getCol() + 1)).hasTeam(getTeam())){
			l.add(p);
		}
		if (getRow() + 2 <= 8 && getCol() - 1 >= 1 && 
				!(p = getBoard().getPosition(getRow() + 2, getCol() - 1)).hasTeam(getTeam())){
			l.add(p);
		}
		if (getRow() + 2 <= 8 && getCol() + 1 <= 8 && 
				!(p = getBoard().getPosition(getRow() + 2, getCol() + 1)).hasTeam(getTeam())){
			l.add(p);
		}
		if (getRow() - 1 >= 1 && getCol() - 2 >= 1 && 
				!(p = getBoard().getPosition(getRow() - 1, getCol() - 2)).hasTeam(getTeam())){
			l.add(p);
		}
		if (getRow() - 1 >= 1 && getCol() + 2 <= 8 && 
				!(p = getBoard().getPosition(getRow() - 1, getCol() + 2)).hasTeam(getTeam())){
			l.add(p);
		}
		if (getRow() + 1 <= 8 && getCol() - 2 >= 1 && 
				!(p = getBoard().getPosition(getRow() + 1, getCol() - 2)).hasTeam(getTeam())){
			l.add(p);
		}
		if (getRow() + 1 <= 8 && getCol() + 2 <= 8 && 
				!(p = getBoard().getPosition(getRow() + 1, getCol() + 2)).hasTeam(getTeam())){
			l.add(p);
		}
		return l;
	}
	

}
