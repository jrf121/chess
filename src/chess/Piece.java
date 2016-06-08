package chess;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;



public abstract class Piece {
	public enum Team{
		white, black
	};
	
	private Team team;
	private boolean isAlive = true;
	private Image sprite;
	private BoardPosition position;
	private int moveCounter = 0;
	private Board board;
	
	protected Piece(Team team, BoardPosition startingPosition, Board board){
		this.board = board;
		this.team = team;
		this.position = startingPosition;
	}
	
	public List<BoardPosition> getAvailableMoveLocations() {
		List<BoardPosition> l = getThreatenedSpaces();
		return l;
	}
	
	public abstract List<BoardPosition> getThreatenedSpaces();
	
	public Team getTeam(){
		return team;
	}
	
	protected void setTeam(Team team){
		this.team = team;
	}
	
	public BoardPosition getPosition(){
		return position;
	}
	
	protected void setPosition(BoardPosition position){
		this.position = position;
	}
	
	protected void setSprite(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {}
		this.sprite = img;
	}
	
	public Image getSprite(){
		return sprite;
	}
	
	public int getRow(){
		return getPosition().getRow();
	}
	
	public int getCol(){
		return getPosition().getCol();
	}
	
	public int getNumMoves(){
		return moveCounter;
	}
	
	
	public Team getEnemy(){
		if (getTeam().equals(Team.black)){
			return Team.white;
		}
		return Team.black;
	}
	
	public void paintMoves(){
		List<BoardPosition> moveLocations = getAvailableMoveLocations();
		for (BoardPosition s : moveLocations){
			s.paint();
		}
	}
	
	protected Piece forceMoveTo(BoardPosition p, Piece replace){
		Piece oldResident = p.getPiece();
		this.position.setPiece(replace);
		if (replace != null){
			replace.position = this.position;
		}
		p.setPiece(this);
		this.position = p;
		return oldResident;
	}
	
	public void moveTo(BoardPosition p) throws IllegalMoveException{
		if (!getAvailableMoveLocations().contains(p)){
			throw new IllegalMoveException("Requested move not allowed: " + this.toString() + " to " + p);
		}
		getBoard().moveMade();
		moveCounter++;
		if (p.isOccupied()){
			p.getPiece().kill();
		}
		position.setPiece(null);
		position = p;
		p.setPiece(this);
	}
	
	
	public void kill(){
		isAlive = false;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public boolean isThreatened(){
		Piece ghost = new Rook(this.getTeam(), this.getPosition(), this.getBoard());
		for (BoardPosition position : ghost.getThreatenedSpaces()){
			if ((position.getPiece() instanceof Rook ||
					position.getPiece() instanceof Queen) && position.getPiece().getTeam().equals(this.getEnemy())){
				return true;
			}
		}
		ghost = new Bishop(this.getTeam(), this.getPosition(), this.getBoard());
		for (BoardPosition position : ghost.getThreatenedSpaces()){
			if (position.getPiece() instanceof Bishop && position.getPiece().getTeam().equals(this.getEnemy())){
				return true;
			}
		}
		ghost = new Knight(this.getTeam(), this.getPosition(), this.getBoard());
		for (BoardPosition position : ghost.getThreatenedSpaces()){
			if (position.getPiece() instanceof Knight && position.getPiece().getTeam().equals(this.getEnemy())){
				return true;
			}
		}
		ghost = new Pawn(this.getTeam(), this.getPosition(), this.getBoard());
		for (BoardPosition position : ghost.getThreatenedSpaces()){
			if (position.getPiece() instanceof Pawn && position.getPiece().getTeam().equals(this.getEnemy())){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		return "[" + this.getTeam() + " " + this.getClass() + " at " + this.getPosition() + "]"; 
	}

}
