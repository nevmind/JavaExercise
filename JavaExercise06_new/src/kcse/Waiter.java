package kcse;

public class Waiter {
	private final GraciousPhilosopher[] phil;
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

	public void requestLeftChop(int id) {
		boolean isRequesting = true;
		Chopstick left = getLeft(id);
		while(isRequesting){
			synchronized (this) {	
				//자신 외에 모든 철학자가 왼쪽 젓가락을 들었을 경우에는 왼쪽 젓가락을 집게 해서는 안된다
				//모든 사람이 왼쪽 젓가락을 집는다면 모두 오른 젓가락을 기다리므로 데드락이 걸림
				if ((left.getOwner()==-1)&& (hasAllPhilLeftChopGotExceptMe()==false))
				{
					left.setOwner(id);
					isRequesting = false;		
				}
			}
			if(isRequesting)
				synchronized(left){
					try {
						left.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
	}
	public void requestRightChop(int id) {
		boolean isRequesting = true;
		Chopstick right = getRight(id);
		while(isRequesting){
			synchronized (this) {		
				
				if (right.getOwner()==-1) 
				{
					right.setOwner(id);
					isRequesting = false;		
				}
			}
			if(isRequesting)
				synchronized(right){
					try{
						right.wait();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
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
			
			synchronized(getLeft(id)){
				getLeft(id).notifyAll();
			}
			synchronized(getRight(id)){
				getRight(id).notifyAll();
			}

			System.out.println("Sent notifyAll! from id "+ id);
		}
		
		
	}
}
