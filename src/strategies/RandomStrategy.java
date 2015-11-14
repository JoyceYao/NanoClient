package strategies;

import game.Board;
import game.Direction;
import game.Move;
import game.Node;
import game.Player;
import game.Strategy;

import java.util.Random;

public class RandomStrategy extends Strategy {
	Random random = new Random();

	@Override
	public Move makeAMove(Board board, Player myPlayer, Player adversary) {
		Move move = new Move();
		
		if(myPlayer.getUnused() > 0 && board.freeNodes.size() > 0){
			int nanoMoveNo = 1;//random.nextInt(myPlayer.getUnused()+1);
			for(int i=0; i<nanoMoveNo; i++){
				Node startNode = board.freeNodes.get(random.nextInt(board.freeNodes.size()));
				addRandomDirectionProgram(move, startNode.id);
			}
		}else{
			move.isPass = true;
		}
		return move;
	}
	
	private void addRandomDirectionProgram(Move move, int startNodeID){
		int idx = random.nextInt(4);
		switch(idx){
			case 0: move.addNanoMove(startNodeID, Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT); break;
			case 1: move.addNanoMove(startNodeID, Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN); break;
			case 2: move.addNanoMove(startNodeID, Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT); break;
			case 3: move.addNanoMove(startNodeID, Direction.RIGHT, Direction.UP, Direction.LEFT, Direction.DOWN); break;
		}
	}

}
