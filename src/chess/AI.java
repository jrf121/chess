package chess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import chess.Piece.Team;

public class AI {
	
	class Move{
		BoardPosition moveTo;
		Piece piece;
		
		Move(BoardPosition moveTo, Piece piece){
			this.moveTo = moveTo;
			this.piece = piece;
		}
		void exicute(){
			piece.moveTo(moveTo);
		}
	}
	
	private Team team;
	private Board board;
	private ArrayList<Piece> pieces;
	public AI(Team team, Board board){
		this.team = team;
		this.board = board;
		this.pieces = new ArrayList<Piece>(16);
		List<Piece> pieces = board.getPieces();
		for (int i = 0; i < pieces.size(); i++){
			if (pieces.get(i).getTeam().equals(this.team)){
				this.pieces.add(pieces.get(i));
			}
		}
	}
	
	public List<Move> getMoves(){
		List<Move> moves = new LinkedList<Move>();
		for (int i = 0; i < pieces.size(); i++){
			List<BoardPosition> bp = pieces.get(i).getAvailableMoveLocations();
			for(int j = 0; j < bp.size(); j++){
				moves.add(new Move(bp.get(j), pieces.get(i)));
			}
		}
		return moves;
	}

}
