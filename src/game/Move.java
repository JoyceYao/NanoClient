package game;

import java.util.ArrayList;
import java.util.List;

/*
 * format: (nodId),(direction1),(direction2),(direction3),(direction4)|(nodId),(direction1),(direction2),(direction3),(direction4)
 * example: 74,UP,DOWN,LEFT,RIGHT|88,LEFT,RIGHT,DOWN,UP
 */
public class Move {
	public boolean isPass;
	List<String> nanoMoves = new ArrayList<String>();
	
	public String toString(){
		if(isPass || nanoMoves.size() == 0){ return "pass"; }
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<nanoMoves.size(); i++){
			sb.append(nanoMoves.get(i));
			sb.append("|");
		}
		
		// remove the extra "|" in the end
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public void addNanoMove(int startNode, Direction d1, Direction d2, Direction d3, Direction d4){
		nanoMoves.add(startNode + "," + d1 + "," + d2 + "," + d3 + "," + d4);
	}
}
