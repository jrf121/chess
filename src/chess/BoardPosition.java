package chess;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoardPosition {
	private int row, col;
	
	private static Piece activePiece;
	
	private Piece resident;
	
	private JButton button;
	
	public BoardPosition(int row, int col){
		this.row = row;
		this.col = col;
		if (this.row > 8 || this.row < 0){
			throw new IllegalArgumentException("Expected row within bounds [1, 8] but got " + this.row);
		}
		if (this.col > 8 || this.col < 0){
			throw new IllegalArgumentException("Expected col within bounds [1, 8] but got " + this.col);
		}
		button = new JButton();
		button.setBackground((row + col) % 2 == 0 ? Color.GRAY : Color.WHITE);
		
		class CellListener implements ActionListener{
			private BoardPosition position;
			CellListener(BoardPosition position){
				this.position = position;
			}
			
			public void actionPerformed(ActionEvent action) {
				synchronized(Main.class) {
					if (action.getSource() != position.getButton()){
						return;
					}
					if (activePiece == null && !position.isOccupied()){
						return;
					}
					if (activePiece != null && (!position.isOccupied() || position.hasTeam(activePiece.getEnemy()))){
						activePiece.moveTo(position);
						activePiece.getBoard().unpaint();
						activePiece = null;
					}
					else {
						Piece p = position.getPiece();
						p.getBoard().unpaint();
						if (p != activePiece){
							p.paintMoves();
							activePiece = p;
						}
						else {
							activePiece = null;
						}
					}
				}
			}
		}
		button.addActionListener(new CellListener(this));
	}
	
	public BoardPosition(String p){
		int[] pos = textToNumeric(p);
		this.row = pos[0];
		this.col = pos[1];
	}
	
	public BoardPosition(int row, int col, Piece startingPiece){
		this(row, col);
		this.resident = startingPiece;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public Piece getPiece(){
		return resident;
	}
	
	public void setPiece(Piece resident){
		if (resident != null){
			this.button.setIcon(new ImageIcon(resident.getSprite().getScaledInstance(64, 64, 0)));
		}
		else {
			this.button.setIcon(null);
		}
		this.resident = resident;
	}
	
	public boolean isOccupied(){
		return resident != null;
	}
	
	public boolean hasTeam(Piece.Team team){
		return isOccupied() && getPiece().getTeam().equals(team);
	}
	
	public boolean isThreatened(Piece piece){
		for (Piece p : piece.getBoard().getPieces()){
			if (p != piece && p.getTeam().equals(piece.getEnemy()) && p.getThreatenedSpaces().contains(this)){
				return true;
			}
		}
		return false;
	}
	
	public void moveTo(Piece p){
		if (getPiece() != null && getPiece().getTeam().equals(p.getTeam())){
			throw new IllegalMoveException("Tried to move piece " + p + 
					" to space " + this + " occupied by " + getPiece());
		}
		if (getPiece() != null){
			getPiece().kill();
		}
		setPiece(p);
	}
	
	public String toString(){
		return Character.toString((char)(getRow() + 64)) + getCol();
	}
	
	public static int[] textToNumeric(String p){
		if (p.length() != 2){
			throw new IllegalArgumentException("Expected position in format \"E6\", got " + p);
		}
		char letter = p.charAt(0);
		char number = p.charAt(1);
		if (!Character.isAlphabetic(letter) || !Character.isDigit(number)){
			throw new IllegalArgumentException("Expected position in format \"E6\", got" + p);
		}
		letter = Character.toUpperCase(letter);
		
		int row, col;
		col = (int)letter - 64;
		row = Integer.parseInt(Character.toString(number));
		if (row > 8 || row < 0){
			throw new IllegalArgumentException("Expected row within bounds [1, 8] but got " + row);
		}
		if (col > 8 || col < 0){
			throw new IllegalArgumentException("Expected col within bounds [1, 8] but got " + col);
		}
		return new int[] {row, col};
	}

	public JButton getButton() {
		return button;
	}
	
	public void paint(){
		getButton().setBackground(Color.YELLOW);
	}
	
	public void unPaint(){
		getButton().setBackground(((row + col) % 2 == 0 ? Color.GRAY : Color.WHITE));
	}
}
