package app;

public class GameTimer {
	private int  waitTime;
	private long startTime;

	public GameTimer(int waitTime) {
		startTime = System.currentTimeMillis();
		this.waitTime = waitTime;
	}

	public boolean isTime() {
		if (System.currentTimeMillis() - startTime >= waitTime) {
			startTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	
	public int timeLeft(){
		return (int) (waitTime - (System.currentTimeMillis() - startTime));
	}

}
