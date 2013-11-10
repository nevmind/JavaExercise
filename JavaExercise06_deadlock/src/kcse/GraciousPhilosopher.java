//GraciousPhilosopher의 구현
package kcse;

import java.util.*;

public class GraciousPhilosopher implements Runnable {
	private final int id;
	Waiter w;
	
	public GraciousPhilosopher(int id, Waiter w) {
		this.id = id;
		this.w = w;
	}
	public int getId() {
		return id;
	}
	protected void eat() {
		try {
			System.out.println(id + " is eating.");
			Thread.sleep(10);
		} catch (Exception e) { System.out.println("exception"); }
		System.out.println(id + " eating done.");

	}


	@Override
	synchronized public void run() {
		// TODO Auto-generated method stub

		while(true) {
			System.out.println("about to pick up leftChop of Phil "+id);

			while (w.requestLeftChop(id)==false) {
				try {
					wait();
					System.out.println("Phil "+id+" Wake 1Up!!");					
				} catch (InterruptedException e) { }//System.out.println("requestLeftChop Interrupt");}
			}	
			System.out.println("got leftChop and about to pick up rightChop of Phil "+id);	
			while (w.requestRightChop(id)==false) {
				try {
					wait();
					System.out.println("Phil "+id+" Wake 2Up!!");
				} catch (InterruptedException e) { }//System.out.println("requestRightChop Interrupt"); }
			}	
			System.out.println("got rightChop of Phil "+id);			
			eat();
			w.reportToEatingDone(id);
		}
	}

	synchronized public void notifyPhil() {
		notifyAll();		
	}
}

