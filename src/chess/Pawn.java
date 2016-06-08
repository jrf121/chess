package chess;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece{
	private int lastGlobalMoveID = 0;

	protected Pawn(Team team, BoardPosition startingPosition, Board board) {
		super(team, startingPosition, board);
		setSprite("./data/pawn_"+(getTeam().equals(Team.white) ? "w": "b") + ".png");
	}

	@Override
	public List<BoardPosition> getAvailableMoveLocations() {
		List<BoardPosition> l = new LinkedList<BoardPosition>();
		if (getRow() == 8 || getRow() == 1){
			return l;
		}
		int direction = (getTeam().equals(Team.white) ? 1 : -1);
		BoardPosition p = getBoard().getPosition(getRow() + direction, getCol());
		//Normal move
		if (!p.isOccupied()){
			l.add(p);
			//Double move
			if (getNumMoves() == 0 && !(p = getBoard().getPosition(getRow() + 2*direction, getCol())).isOccupied()){
				l.add(p);
			}
		}
		
		//Normal attacks
		if (getCol() - 1 >= 1 && (p = getBoard().getPosition(getRow() + direction, getCol() - 1)).hasTeam(getEnemy())){
			l.add(p);
		}
		if (getCol() + 1 <= 8 && (p = getBoard().getPosition(getRow() + direction, getCol() + 1)).hasTeam(getEnemy())){
			l.add(p);
		}
		//En passants
		if (getCol() - 1 >= 1 && (p = getBoard().getPosition(getRow(), getCol() - 1)).hasTeam(getEnemy()) &&
				p.getPiece() instanceof Pawn && p.getPiece().getNumMoves() == 1 &&
				((Pawn) p.getPiece()).lastGlobalMoveID == getBoard().getGlobalMoves() - 1){
			l.add(getBoard().getPosition(getRow() + direction, getCol() - 1));
		}
		if (getCol() + 1 <= 8 && (p = getBoard().getPosition(getRow(), getCol() + 1)).hasTeam(getEnemy()) &&
				p.getPiece() instanceof Pawn && p.getPiece().getNumMoves() == 1 &&
				((Pawn) p.getPiece()).lastGlobalMoveID == getBoard().getGlobalMoves() - 1){
			l.add(getBoard().getPosition(getRow() + direction, getCol() + 1));
		}
		Object[] arr = l.toArray();
		if (this.getBoard().getKing(this.getTeam()).isThreatened() || isThreatened()){
			for (int i = 0; i < arr.length; i++){
				if (moveThreatensKing((BoardPosition) arr[i])){
					l.remove(arr[i]);
				}
			}
		}
		return l;
	}
	
	@Override
	public List<BoardPosition> getThreatenedSpaces(){
		List<BoardPosition> l = new LinkedList<BoardPosition>();
		if (getRow() == 8 || getRow() == 1){
			return l;
		}
		int direction = (getTeam().equals(Team.white) ? 1 : -1);
		if (getCol() - 1 >= 1){
			l.add(getBoard().getPosition(getRow() + direction, getCol() - 1));
		}
		if (getCol() + 1 <= 8){
			l.add(getBoard().getPosition(getRow() + direction, getCol() + 1));
		}
		return l;
	}
	
	@Override
	public void moveTo(BoardPosition p) throws IllegalMoveException{
		lastGlobalMoveID = getBoard().getGlobalMoves();
		int oldC = this.getCol();
		super.moveTo(p);
		int newC = this.getCol();
		if (newC == oldC){
			return;
		}
		int direction = (getTeam().equals(Team.white) ? -1 : 1);
		int newR = this.getRow();
		Piece piece = getBoard().getPosition(newR + direction, newC).getPiece();
		if (piece != null && piece instanceof Pawn && ((Pawn)piece).lastGlobalMoveID == this.lastGlobalMoveID - 1){
			piece.kill();
			getBoard().getPosition(newR + direction, newC).setPiece(null);
		}
	}
	
	

}
