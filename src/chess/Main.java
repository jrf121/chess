package chess;

import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import chess.AI.Move;
import chess.Piece.Team;

public class Main {
	public static Board board;
	public static void main(String[] args) throws InterruptedException{
		JFrame frame = new JFrame("Chess");
		GridLayout gl = new GridLayout(8,8);
		frame.setLayout(gl);
		frame.setSize(512,512);
		board = new Board();
		for (int row = 1; row <= 8; row++){
			for (int col = 1; col <=8; col++){
				JButton b = board.getPosition(row, col).getButton();
				frame.add(b);
			}
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		AI black = new AI(Team.black, board);
		AI white = new AI(Team.white, board);
		List<Move> bMoves;
		List<Move> wMoves;
		while (true) {
			synchronized(Main.class) {
				wMoves = white.getMoves();
				if (!board.getKing(Team.white).isAlive()){
					System.out.println("Black wins");
					break;
				}
				wMoves.get((int)(Math.random() * wMoves.size())).exicute();
				//Thread.sleep(100);
				bMoves = black.getMoves();
				if (!board.getKing(Team.black).isAlive()){
					System.out.println("White wins");
					break;
				}
				bMoves.get((int)(Math.random() * bMoves.size())).exicute();
				//Thread.sleep(100);
			}
			while(board.getGlobalMoves() % 2 == 1) Thread.sleep(10);
		
		} 
		
	}

}
