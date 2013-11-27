package kcse;

public class Waiter {
	private final GraciousPhilosopher[] phil=0;
	private final Chopstick[] chopsticks;	
	
	boolean init = false;

	Waiter(GraciousPhilosopher[] phil, Chopstick[] chopsticks) {
		this.phil = phil;
		this.chopsticks= chopsticks;
	}
	
	private Chopstick getLeft(int id) {
		return chopsticks[id];
	}
	private Chopstick getRight(int id) {
		return chopsticks[(id + 1) % chopsticks.length];
	}

	public boolean requestLeftChop(int id) {
		synchronized (this) {
			boolean result = false;
			Chopstick left = getLeft(id);
			if ((left.getOwner()==-1)&& (hasAllPhilLeftChopGotExceptMe()==false))
			{
				left.setOwner(id);
				result = true;		
			}
			System.out.println("requestLeftChop from Phil "+id+" "+result);
			return result;
		}
	}
	public boolean requestRightChop(int id) {
		synchronized (this) {		
			boolean result = false;
			Chopstick right = getRight(id);
			if (right.getOwner()==-1) 
			{
				right.setOwner(id);
				result = true;		
			}
			System.out.println("requestRightChop from Phil "+id+" "+result);			
			return result;
		}
	}
	private boolean hasAllPhilLeftChopGotExceptMe() {
		boolean result = false;
		int count = 0;
		for (Chopstick c:chopsticks) {
			if (c.getOwner()==c.getId())
				count++;
		}
		if (count == chopsticks.length-1) {
			System.out.println("Not allowed to pick up left chopstick to prevent deadlock");
			result = true;
		}
		
		return result;
	}

	public void reportToEatingDone(int id) {
		synchronized (this) {	
			if ( (getLeft(id).getOwner() == -1) || (getRight(id).getOwner() == -1) ) {
				throw new RuntimeException();
			}
			getLeft(id).setOwner(-1);
			getRight(id).setOwner(-1);	
			
			for(GraciousPhilosopher p:phil) {
				p.notifyPhil();
			}
			System.out.println("Sent notifyAll! from id "+ id);
		}
	}
}
