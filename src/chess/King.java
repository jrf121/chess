package chess;

import java.util.LinkedList;
import java.util.List;


public class King extends Piece{

	protected King(Team team, BoardPosition startingPosition, Board board) {
		super(team, startingPosition, board);
		setSprite("./data/king_"+(getTeam().equals(Team.white) ? "w": "b") + ".png");
	}


	
	@Override
	public List<BoardPosition> getThreatenedSpaces(){
		List<BoardPosition> l = new LinkedList<BoardPosition>();
		for (int i = Math.max(getRow() - 1, 1); i <= Math.min(getRow() + 1, 8); i++){
			for (int j = Math.max(getCol() - 1, 1); j <= Math.min(getCol() + 1, 8); j++){
				BoardPosition p = getBoard().getPosition(i,j);
				if (!p.hasTeam(this.getTeam())){
					l.add(p);
				}
			}
		}
		return l;
	}

	
	@Override
	public void moveTo(BoardPosition p) throws IllegalMoveException{
		int oldC = this.getCol();
		super.moveTo(p);
		int newC = this.getCol();
		if (Math.abs(oldC - newC) == 2){
			if (newC == 3){
				getBoard().getPosition(this.getRow(), 1).getPiece().forceMoveTo(getBoard().getPosition(this.getRow(), 4), null);
			}
			else if (newC == 7){
				getBoard().getPosition(this.getRow(), 8).getPiece().forceMoveTo(getBoard().getPosition(this.getRow(), 6), null);
			}
		}
	}
	
	
	
	

}
