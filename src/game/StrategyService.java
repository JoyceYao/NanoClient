package game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import game.PlayerId;
import game.Piece;
import game.Direction;
import game.Node;
import game.Player;

public class StrategyService {
	
	private LinkedList<Piece> unused = new LinkedList<Piece>();
	private List<Piece> inPlay = new ArrayList<Piece>();
	private List<Piece> dead = new ArrayList<Piece>();
	
	
	public int getScoreOfThisMove(Board board, Move move, Player player){
		if(move.isPass){ return 0; }
		
		unused.clear();
		inPlay.clear();
		dead.clear();
		
		resetTmpState(board);
		addUnusedList(player);
		move(board, move.toString());
		while(inPlay.size() > 0){
			advanceTime(board, player.getPlayerId());
		}
		recoverState(board);
		return getScore();
		
	}
	
	private void resetTmpState(Board board){
		for(int i=0; i<=board.maxNodeId; i++){
			Node node = board.nodesMap.get(i);
			node.tmpStatus = node.getStatus();
		}
	}
	
	private void recoverState(Board board){
		for(int i=0; i<=board.maxNodeId; i++){
			Node node = board.nodesMap.get(i);
			node.setStatus(node.tmpStatus);
		}
	}
	
	
	private void addUnusedList(Player player){
		for(int i=0; i<player.getUnused(); i++){
			Piece piece = new Piece(player.getPlayerId());
			unused.add(piece);
		}
	}

	
    private void move(Board board, String nextMove){
    	System.out.println("nextMove==" + nextMove);
    	
    	
        //If pass then do nothing
        if(nextMove.toLowerCase().trim().equals("pass")){
            return;
        }

        //Otherwise make all the moves
        String [] moves = nextMove.split("\\|");
        for(String singleMove: moves){
        	String[] move = singleMove.split(",");
        	Integer nodeId = Integer.valueOf(move[0]);

        	List<Direction> program = new LinkedList<Direction>();

        	for(int i = 1; i < 5; i++){
        		String value = move[i].toLowerCase().trim();
        		
        		if(value.equals("up")){
        			program.add(Direction.UP);
        		}else if(value.equals("down")){
        			program.add(Direction.DOWN);
        		}else if(value.equals("left")){
        			program.add(Direction.LEFT);
        		}else if(value.equals("right")){
        			program.add(Direction.RIGHT);
        		}
        	}

        	Node node = board.nodesMap.get(nodeId);

        	place(node, program);

        }
    }
    
    private void advanceTime(Board board, PlayerId advantage){
        for(Node node: board.nodesMap.values()){
            node.moveTime(advantage);
        }
        advanceTime();
    }
    
    private void advanceTime(){
        //For all inplay pieces move them
        for(Piece piece: inPlay){
            piece.advance();
        }
        //If any of the pieces become dead then remove from the inplay
        Iterator<Piece> iterator = inPlay.iterator();
        while (iterator.hasNext()){
            Piece piece = iterator.next();
            if(piece.isDead()){
                iterator.remove();
                dead.add(piece);
            }
        }
    }

    /**
     * Used to make the initial move for a an unused piece, requires the node and the program for the piece
     */
    private boolean place(Node node, List<Direction> program){
        if(node.isFree() && unused.size() > 0){
            Piece nextPiece = unused.pop();
            nextPiece.programPiece(program);
            nextPiece.placeOnNode(node);
            node.place(nextPiece);
            inPlay.add(nextPiece);
            return true;
        }else {
            return false;
        }
    }
    
    private int getScore(){
        int score  = 0;
        
        System.out.println("getScore this.inPlay.size()=" +  this.inPlay.size());
        
        for(Piece piece: this.inPlay){
            score = score + piece.getNodesEaten();
        }

        for(Piece piece: this.dead){
            score = score + piece.getNodesEaten();
        }
        return score;
    }
}
