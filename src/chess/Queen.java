package chess;


import java.util.List;

public class Queen extends Bishop{
	
	public Queen(Team team, BoardPosition startingPosition, Board board){
		super(team, startingPosition, board);
		setSprite("./data/queen_"+(getTeam().equals(Team.white) ? "w": "b") + ".png");
	}

	@Override
	public List<BoardPosition> getAvailableMoveLocations() {
		List<BoardPosition> l = getThreatenedSpaces();
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
	public List<BoardPosition> getThreatenedSpaces() {
		List<BoardPosition> l = super.getThreatenedSpaces();
		for (int i = Math.max(getRow() - 1, 1); i >= 1; i--){
			BoardPosition p = getBoard().getPosition(i, getCol());
			if (p.isOccupied()){
				if (p.hasTeam(getEnemy())){
					l.add(p);
				}
				break;
			}
			l.add(p);
		}
		
		for (int i = Math.min(getRow() + 1, 8); i <= 8; i++){
			BoardPosition p = getBoard().getPosition(i, getCol());
			if (p.isOccupied()){
				if (p.hasTeam(getEnemy())){
					l.add(p);
				}
				break;
			}
			l.add(p);
		}
		
		for (int j = Math.max(getCol() - 1, 1); j >= 1; j--){
			BoardPosition p = getBoard().getPosition(getRow(), j);
			if (p.isOccupied()){
				if (p.hasTeam(getEnemy())){
					l.add(p);
				}
				break;
			}
			l.add(p);
		}
		
		for (int j = Math.min(getCol() + 1, 8); j <= 8; j++){
			BoardPosition p = getBoard().getPosition(getRow(), j);
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
