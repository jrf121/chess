package chess;

import java.util.ArrayList;
import java.util.List;

import chess.Piece.Team;

public final class Board {
	private BoardPosition[][] spaces = new BoardPosition[8][8];
	private King blackKing;
	private King whiteKing;
	
	private final List<Piece> pieces = new ArrayList<Piece>();
	
	private int moveCounter = 0;
	
	
	public Board(){
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				spaces[i][j] = new BoardPosition(i+1, j+1);
			}
		}
		pieces.add(blackKing = new King(Team.black, getPosition("E8"), this));
		pieces.add(whiteKing = new King(Team.white, getPosition("E1"), this));
		pieces.add(new Queen(Team.black, getPosition("D8"), this));
		pieces.add(new Queen(Team.white, getPosition("D1"), this));
		pieces.add(new Rook(Team.black, getPosition("A8"), this));
		pieces.add(new Rook(Team.black, getPosition("H8"), this));
		pieces.add(new Rook(Team.white, getPosition("A1"), this));
		pieces.add(new Rook(Team.white, getPosition("H1"), this));
		pieces.add(new Knight(Team.black, getPosition("B8"), this));
		pieces.add(new Knight(Team.black, getPosition("G8"), this));
		pieces.add(new Knight(Team.white, getPosition("B1"), this));
		pieces.add(new Knight(Team.white, getPosition("G1"), this));
		pieces.add(new Bishop(Team.black, getPosition("C8"), this));
		pieces.add(new Bishop(Team.black, getPosition("F8"), this));
		pieces.add(new Bishop(Team.white, getPosition("C1"), this));
		pieces.add(new Bishop(Team.white, getPosition("F1"), this));
		for (int i = 0; i < 8; i++){
			pieces.add(new Pawn(Team.black, getPosition(7, i+1), this));
			pieces.add(new Pawn(Team.white, getPosition(2, i+1), this));
		}
		for (Piece p : pieces){
			p.getPosition().setPiece(p);
		}
	}
	
	
	public BoardPosition getPosition(int row, int col){
		return spaces[row - 1][col - 1];
	}
	
	public void unpaint(){
		for (int i = 1; i <=8; i++){
			for (int j = 1; j <=8; j++){
				getPosition(i, j).unPaint();
			}
		}
	}
	
	public BoardPosition getPosition(String position){
		int[] pos = BoardPosition.textToNumeric(position);
		return getPosition(pos[0], pos[1]);
	}
	
	public void moveMade(){
		System.out.println(moveCounter);
		moveCounter++;
	}
	
	public int getGlobalMoves(){
		return moveCounter;
	}
	
	public King getKing(Team team){
		if (team.equals(Team.white)){
			return whiteKing;
		}
		return blackKing;
	}
	
	public List<Piece> getPieces(){
		return pieces;
	}

}
