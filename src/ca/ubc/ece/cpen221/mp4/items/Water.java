package ca.ubc.ece.cpen221.mp4.items;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Water implements Item {
	private final static ImageIcon waterImage = Util.loadImage("water.gif");
	
	private Location location;
	private boolean isDead;

	public Water(Location location){
		this.location = location;
		this.isDead = false;
	}
	
	@Override
	public int getPlantCalories() {
		return 15;
	}

	@Override
	public int getMeatCalories() {
		return 0;
	}

	@Override
	public ImageIcon getImage() {
		return waterImage;
	}

	@Override
	public String getName() {
		return "water";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getStrength() {
		return 10;
	}

	@Override
	public void loseEnergy(int energy) {
		isDead = true;
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

}
