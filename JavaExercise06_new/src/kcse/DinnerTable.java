package kcse;

public class DinnerTable {
	/**
	* @param args
	*/
	public static void main(String[] args) {
		int PhilNumber = 4;
		Chopstick[] chops = new Chopstick[PhilNumber];
		GraciousPhilosopher[] phils = new GraciousPhilosopher[PhilNumber];
		Waiter w = new Waiter(phils,chops);
		for(int i=0;i<chops.length;i++){
			chops[i] = new Chopstick(i);
			phils[i] = new GraciousPhilosopher(i,w);
		}
		for(int i=0;i<phils.length;i++){
			new Thread(phils[i],"Thread : "+new Integer(i).toString()).start();
		}

		

	}
}