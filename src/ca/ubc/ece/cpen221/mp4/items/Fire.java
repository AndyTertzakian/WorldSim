package ca.ubc.ece.cpen221.mp4.items;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Fire implements Item{
	private final static ImageIcon fireImage = Util.loadImage("fire.gif");
	private static final int INITIAL_ENERGY = 10;
	
	private boolean isDead;
	private Location location;
	private int energy;
	
	public Fire(Location location){
		this.location = location;
		isDead = false;
		energy = INITIAL_ENERGY;
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
		return fireImage;
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
