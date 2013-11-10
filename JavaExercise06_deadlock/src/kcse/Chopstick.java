package kcse;

public class Chopstick {
	private final int id;
	private int owner;
	
	public Chopstick(int id) {
		this.id = id;
		this.owner = -1;
	}
	
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public int getId() {
		return id;
	}
	
}
