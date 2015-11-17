package strategies;

import game.Board;
import game.Direction;
import game.Move;
import game.Node;
import game.Player;
import game.Status;
import game.Strategy;
import game.StrategyService;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomMaxScoreStrategy extends Strategy {
	Random random = new Random();
	StrategyService service = new StrategyService();

	@Override
	public Move makeAMove(Board board, Player myPlayer, Player adversary) {
		System.out.println("this is RandomMaxScoreStrategy!!");
		
		Move bestMove = new Move();
		
		//System.out.println("Free Node board.freeNodes.size()=" + board.freeNodes.size());
		//System.out.println("Unused Node myPlayer.getUnused()=" + myPlayer.getUnused());
		
		if(myPlayer.getUnused() > 0 && board.freeNodes.size() > 0){
			
			int maxScore = 0;

			System.out.println("test[0]!");
			
			for(int i=0; i<100; i++){
				Move move = new Move();				
				for(int j=0; j<myPlayer.getUnused(); j++){
					int nodeId = random.nextInt(board.maxNodeId+1);
					addRandomDirectionProgram(move, nodeId);
				}
				int thisScore = service.getScoreOfThisMove(board, move, myPlayer);
				
				System.out.println("thisScore=" + thisScore);
				if(thisScore > maxScore){
					bestMove = move;
					maxScore = thisScore;
				}
			}
			
		}else{
			bestMove.isPass = true;
		}
		
		System.out.println("test[5]! bestMove=" + bestMove.toString());
		return bestMove;
	}
	
	private void addRandomDirectionProgram(Move move, int startNodeID){
		int idx = random.nextInt(5);
		switch(idx){
			case 0: move.addNanoMove(startNodeID, Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT); break;
			case 1: move.addNanoMove(startNodeID, Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN); break;
			case 2: move.addNanoMove(startNodeID, Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT); break;
			case 3: move.addNanoMove(startNodeID, Direction.RIGHT, Direction.UP, Direction.LEFT, Direction.DOWN); break;
			case 4: move.addNanoMove(startNodeID, Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT); break;
		}
	}
	
	private Node findSWNode(List<Node> list){
		System.out.println("findSWNode=" + list);
		if(list == null || list.size() == 0){ return null; }
		int minX = Board.X_range;
		int minY = Board.Y_range;
		
		Node result = null;
		for(int i=0; i<list.size(); i++){
			Node thisNode = list.get(i);
			int thisX = thisNode.getxLoc();
			int thisY = thisNode.getyLoc();
			if(thisX < minX && thisY < minY){
				result = thisNode;
				minX = thisX;
				minY = thisY;
			}
		}
		return result;
	}
	

}
