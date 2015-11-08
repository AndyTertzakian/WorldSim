package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;

public abstract class AbstractArenaVehicle implements ArenaVehicle {
	private int STRENGTH;
	private int TURNING_SPEED;
	private int MOMENTUM;
	private ImageIcon image;
	
	private Location location;
	private int energy;
	private int speed;

	@Override
	public void moveTo(Location targetLocation) {
		this.location = targetLocation;
	}

	@Override
	public int getMovingRange(int speed) {
		return speed * MOMENTUM;
	}

	@Override
	public ImageIcon getImage() {
		return image;
	}

	@Override
	public abstract String getName();

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void loseEnergy(int energy) {
		this.energy -= energy;
	}

	@Override
	public boolean isDead() {
		return energy <= 0;
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
	public int getMomentum() {
		return MOMENTUM;
	}

}
