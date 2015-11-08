package ca.ubc.ece.cpen221.mp4.items;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class ForceLightning implements Item{
	private static ImageIcon forceLightningImage;
	private static final int INITIAL_ENERGY = 10;
	
	private boolean isDead;
	private Location location;
	private int energy;
	
	public ForceLightning(Location location, Direction direction){
		this.location = location;
		isDead = false;
		energy = INITIAL_ENERGY;
		if(direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
			forceLightningImage = Util.loadImage("lightningVert.gif");
		}else {
			forceLightningImage = Util.loadImage("lightningHor.gif");
		}
	}

	@Override
	public int getPlantCalories() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMeatCalories() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ImageIcon getImage() {
		return forceLightningImage;
	}

	@Override
	public String getName() {
		return "fire";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getStrength() {
		return 100;
	}

	@Override
	public void loseEnergy(int energy) {
		this.energy -= energy;
	}

	@Override
	public boolean isDead() {
		return energy <= 0;
	}

}
