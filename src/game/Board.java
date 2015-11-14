package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
	final int X_range = 20;
	final int Y_range = 10;
    public Map<Integer, Node> nodes;
    public Node[][] matrix;  
    public List<Node> freeNodes;
    
    public Board(){
    	nodes = new HashMap<Integer, Node>();
    	matrix = new Node[X_range][Y_range];
    	freeNodes = new ArrayList<Node>();
    }
    
    public void updateBoard(String command){
    	if(nodes.size() == 0){
    		initialBoard(command);
    	}else{
    		updateStatus(command);
    	}
    }
    
    private void initialBoard(String command){
    	String[] lines = command.split("\n");
    	for(int i=1; i<lines.length; i++){
    		String[] nodeInfo = lines[i].split(",");
    		if(nodeInfo.length == 8){
    			int id = Integer.parseInt(nodeInfo[0]);
    			int xLoc = Integer.parseInt(nodeInfo[1]);
    			int yLoc = Integer.parseInt(nodeInfo[2]);
    			Node node = new Node(id, xLoc, yLoc);
    			nodes.put(id, node);
    			matrix[xLoc][yLoc] = node;
    			freeNodes.add(node);
    		}
    	}
    	
    	// add adjacent
    	for(int i=1; i<lines.length; i++){
    		String[] nodeInfo = lines[i].split(",");
    		if(nodeInfo.length == 8){
    			int id = Integer.parseInt(nodeInfo[0]);
    			Node node = nodes.get(id);
    			if(node != null){
    				// up
    				if(!"null".equals(nodeInfo[4])){
    					node.up = nodes.get(Integer.parseInt(nodeInfo[4]));
    				}
    				
    				// down
    				if(!"null".equals(nodeInfo[5])){
    					node.down = nodes.get(Integer.parseInt(nodeInfo[5]));
    				}

    				// left
    				if(!"null".equals(nodeInfo[6])){
    					node.left = nodes.get(Integer.parseInt(nodeInfo[6]));
    				}

    				// right
    				if(!"null".equals(nodeInfo[7])){
    					node.right = nodes.get(Integer.parseInt(nodeInfo[7]));
    				}
    			}
    		}
    	}
    }
    
    private void updateStatus(String command){
    	freeNodes.clear();
    	
    	String[] lines = command.split("\n");
    	for(int i=1; i<lines.length; i++){
    		String[] nodeInfo = lines[i].split(",");
    		if(nodeInfo.length == 8){
    			int id = Integer.parseInt(nodeInfo[0]);
    			Node node = nodes.get(id);
    			Status status = Status.valueOf(nodeInfo[3]);
    			node.setStatus(status);
    			if(status == Status.FREE){
    				freeNodes.add(node);
    			}
    		}
    	}
    }
    
}
