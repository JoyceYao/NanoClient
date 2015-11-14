

import game.Board;
import game.Move;
import game.Node;
import game.Player;
import game.PlayerId;
import game.PropertyService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;


public class Main {

    private static StringBuffer command = new StringBuffer();
    private static String strategyConfFile = "strategy.properties";
    private static PropertyService propService = null;
    private static String myTeamName = "mv_cly";
    private static Player myPlayer = null;
    private static Player adverPlayer = null;
    private static Board board = new Board();
    
    private static String myStrategy = ""; //"RandomStrategy";

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 1377);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String state;
            propService = new PropertyService(strategyConfFile);
            System.out.println("propService=" + propService);
            myStrategy = propService.getPropValues("Strategy");
            System.out.println("myStrategy=" + myStrategy);
            
            if(args.length > 0){ myTeamName = args[0]; }
            out.println("REGISTER:" + myTeamName);
            while ((state = in.readLine()) != null) {
                if(state.equals("START")){
                    command = new StringBuffer();
                }
                else if(state.equals("END")){
                	String commandStr = command.toString();
                	// store server messages
                	board.updateBoard(commandStr);
                	updatePlayers(commandStr);
                	
                	System.out.println("commandStr=" + commandStr);
                	// make next move
                	Move nextMove = myPlayer.makeAMove(board);
            		System.out.println("myMove = " + nextMove.toString());
                	out.println(nextMove.toString());
                }else{
                    command.append(state + "\n");
                }
            }
            
            socket.close();
        } catch (IOException eIO) {
            System.out.println(eIO.getMessage());
        }
    }

    private static void updatePlayers(String command){
    	String[] lines = command.split("\n");
    	for(int i=1; i<lines.length; i++){
    		String[] playerInfo = lines[i].split(",");
    		if(playerInfo.length == 6 && !"PlayerID".equals(playerInfo[0])){
    			String playerID = playerInfo[0];
    			String playerName = playerInfo[1];
    			int unused = Integer.parseInt(playerInfo[2]);
    			int dead = Integer.parseInt(playerInfo[3]);
    			int inPlay = Integer.parseInt(playerInfo[4]);
    			int score = Integer.parseInt(playerInfo[5]);
    			if(myTeamName.equals(playerName)){
    				if(myPlayer == null){
    					myPlayer = new Player(PlayerId.valueOf(playerID), playerName, unused, myStrategy);
    				}else{
    					myPlayer.updatePlayerState(unused, dead, inPlay, score);
    				}
    			}else{
    				if(adverPlayer == null){
    					adverPlayer = new Player(PlayerId.valueOf(playerID), playerName, unused, "");
    				}else{
    					adverPlayer.updatePlayerState(unused, dead, inPlay, score);
    				}				
    			}
    		}
    	}
    	
    	if(myPlayer.getAdverPlayer() == null){
    		myPlayer.setAdverPlayer(adverPlayer);
    	}
    }

}
