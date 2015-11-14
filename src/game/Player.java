package game;

public class Player {

    private PlayerId playerId;
    private String playerName;
    private int unused;
    private int dead;
    private int inPlay;
    private int score;
    private Strategy strategy;
    private Player adverPlayer;


    public Player(PlayerId playerId, String playerName, int pieces, String strategyImpl){
        this.playerId = playerId;
        this.playerName = playerName;
        this.unused = pieces;
        if(!"".equals(strategyImpl)){
        	strategy = Strategy.getStrategyImpl(strategyImpl);
        }
    }
    
    public void updatePlayerState(int unused, int dead, int inPlay, int score){
    	this.setUnused(unused);
    	this.setDead(dead);
    	this.setInPlay(inPlay);
    	this.setScore(score);
    }
    
    public Move makeAMove(Board board){
    	return strategy.makeAMove(board, this, adverPlayer);
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public String getPlayerId(){
        return this.playerId.name();
    }

	public int getInPlay() {
		return inPlay;
	}

	public void setInPlay(int inPlay) {
		this.inPlay = inPlay;
	}

	public int getUnused() {
		return unused;
	}

	public void setUnused(int unused) {
		this.unused = unused;
	}

	public int getDead() {
		return dead;
	}

	public void setDead(int dead) {
		this.dead = dead;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Player getAdverPlayer(){
		return this.adverPlayer;
	}
	
	public void setAdverPlayer(Player adverPlayer){
		this.adverPlayer = adverPlayer;
	}

}
