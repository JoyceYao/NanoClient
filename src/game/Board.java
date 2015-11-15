package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
	final int X_range = 20;
	final int Y_range = 10;
	// get node by nodeId
	public Map<Integer, Node> nodesMap;
	// get node by x and y location
	public Node[][] nodesMatrix;
	// list of all free nodes
	public List<Node> freeNodes;

	public Board(){
		nodesMap = new HashMap<Integer, Node>();
		nodesMatrix = new Node[X_range][Y_range];
		freeNodes = new ArrayList<Node>();
	}
    
	public void updateBoard(String command){
		if(nodesMap.size() == 0){
			initialBoard(command);
		}else{
			updateStatus(command);
		}
	}

	// receive command from server, use it to initial board information
	private void initialBoard(String command){
		String[] lines = command.split("\n");
		for(int i=1; i<lines.length; i++){
			String[] nodeInfo = lines[i].split(",");
			if(nodeInfo.length == 8){
				int id = Integer.parseInt(nodeInfo[0]);
				int xLoc = Integer.parseInt(nodeInfo[1]);
				int yLoc = Integer.parseInt(nodeInfo[2]);
				Node node = new Node(id, xLoc, yLoc);
				nodesMap.put(id, node);
				nodesMatrix[xLoc][yLoc] = node;
				freeNodes.add(node);
			}
		}

		// add adjacent nodes
		for(int i=1; i<lines.length; i++){
			String[] nodeInfo = lines[i].split(",");
			if(nodeInfo.length == 8){
				int id = Integer.parseInt(nodeInfo[0]);
				Node node = nodesMap.get(id);
				if(node != null){
					// up
					if(!"null".equals(nodeInfo[4])){
						node.up = nodesMap.get(Integer.parseInt(nodeInfo[4]));
					}

					// down
					if(!"null".equals(nodeInfo[5])){
						node.down = nodesMap.get(Integer.parseInt(nodeInfo[5]));
					}

					// left
					if(!"null".equals(nodeInfo[6])){
						node.left = nodesMap.get(Integer.parseInt(nodeInfo[6]));
					}

					// right
					if(!"null".equals(nodeInfo[7])){
						node.right = nodesMap.get(Integer.parseInt(nodeInfo[7]));
					}
				}
			}
		}
	}
    
	// receive command from server, use it to update node status
	private void updateStatus(String command){
		freeNodes.clear();

		String[] lines = command.split("\n");
		for(int i=1; i<lines.length; i++){
			String[] nodeInfo = lines[i].split(",");
			if(nodeInfo.length == 8){
				int id = Integer.parseInt(nodeInfo[0]);
				Node node = nodesMap.get(id);
				Status status = Status.valueOf(nodeInfo[3]);
				node.setStatus(status);
				if(status == Status.FREE){
					freeNodes.add(node);
				}
			}
		}
	}
}
