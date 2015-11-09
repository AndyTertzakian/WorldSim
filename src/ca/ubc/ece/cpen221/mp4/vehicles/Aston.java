package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.ai.AstonAI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Aston implements ArenaVehicle {
	private static final int INITIAL_SPEED = 16;
	private static final int TURNING_SPEED = 10;
	private static final int ACCELERATION = 2;
	private static final int MAX_SPEED = 2;
	private static final int STRENGTH = 300;
	private static final int VIEW_RANGE = 5;
	private static final int DISTANCE_TO_CRASH = 3;
	private static final ImageIcon astonImage = Util.loadImage("Aston.gif");

	private final AI ai;

	private int speed;
	private Location location;
	private boolean isDead;

	public Aston(AstonAI ai, Location initialLocation) {
		this.ai = ai;
		this.location = initialLocation;
		isDead = false;
		this.speed = INITIAL_SPEED;
	}

	public int getTurningSpeed() {
		return TURNING_SPEED;
	}

	public void updateSpeed(boolean speedUp) {

		if (speedUp && speed > MAX_SPEED) {
			this.speed = this.speed / ACCELERATION;
		} else if (!speedUp && speed >= MAX_SPEED) {
			this.speed = this.speed * ACCELERATION;
		} else {
			return;
		}
	}

	public int getViewRange() {
		return VIEW_RANGE;
	}

	@Override
	public void moveTo(Location targetLocation) {
		this.location = targetLocation;
	}

	@Override
	public int getMovingRange() {
		return speed;
	}

	@Override
	public ImageIcon getImage() {
		return astonImage;
	}

	@Override
	public String getName() {
		return "Aston";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void loseEnergy(int energy) {
		isDead = true;
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public int getPlantCalories() {
		return 0;
	}

	@Override
	public int getMeatCalories() {
		return 0;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public int getAcceleration() {
		return ACCELERATION;
	}

	@Override
	public int getEnergy() {
		return STRENGTH;
	}

	@Override
	public LivingItem breed() {
		return null;
	}

	@Override
	public void eat(Food food) {

	}

	@Override
	public int getCoolDownPeriod() {
		return speed;
	}

	@Override
	public Command getNextAction(World world) {
		Command nextAction = ai.getNextAction(world, this);
		return nextAction;
	}

	@Override
	public int getDistanceToCrash() {
		return DISTANCE_TO_CRASH;
	}

}
