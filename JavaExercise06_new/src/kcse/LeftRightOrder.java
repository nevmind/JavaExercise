package kcse;

public class LeftRightOrder implements ChopstickOrder{
	@Override
	public Chopstick[] getOrder(Chopstick left, Chopstick right) {
		Chopstick chopsticks[] = {left, right};
		return chopsticks;
	}
}
