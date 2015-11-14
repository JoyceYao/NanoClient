package game;

import strategies.*;

public abstract class Strategy {
	private static final String path = "strategies.";
	public abstract Move makeAMove(Board board, Player myPlayer, Player adversary);
	public static Strategy getStrategyImpl(String className){
		try {
			Strategy impl = (Strategy)Class.forName(path+className).newInstance();
			return impl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
