package kcse;

public class Philosopher implements Runnable {
	public final int id;
	private final Chopstick[] chopsticks;
	protected final ChopstickOrder order;
	public Philosopher(int id, Chopstick[] chopsticks, ChopstickOrder order) {
		this.id = id;
		this.chopsticks = chopsticks;
		this.order = order;
	}
	


	protected void eat() {
		System.out.println(id + " eating ready.");
		Chopstick[] chopstickOrder = order.getOrder(getLeft(), getRight());
		System.out.println(id + " get chop order");
		synchronized (chopstickOrder[0]) {
			System.out.println(id + " chop("+id+") got");
			synchronized (chopstickOrder[1]) {
				System.out.println(id + " chop("+(id + 1) % chopsticks.length+") got");	
			try {
				System.out.println(id + " is eating.");
				Thread.sleep(10);
				} catch (Exception e) { System.out.println("exception"); }
			}
		}
		System.out.println(id + " eating done.");
	}
	Chopstick getLeft() {
		return chopsticks[id];
	}
	Chopstick getRight() {
		return chopsticks[(id + 1) % chopsticks.length];
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			eat();
		}
		
	}
}

//문제해답 Thread.txt 
//아래를 보면 모든 Philosopher가 왼쪽chopstick을 모두 든 상태가 되면
//오른쪽 chopstick이 전부 lock에 걸려 들수 없는 deadlock상태가 된다.
//따라서 계속 대기하게 되는 상태가 되어 중지가 되는 것임
//0 eating ready.
//4 eating ready.
//2 eating ready.
//3 eating ready.
//1 eating ready.
//3 get chop order
//2 get chop order
//4 get chop order
//0 get chop order
//4 chop(4) got
//2 chop(2) got
//3 chop(3) got
//1 get chop order
//1 chop(1) got
//0 chop(0) got