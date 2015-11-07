package ca.ubc.ece.cpen221.mp4.items;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Mana implements Item {
	private static final ImageIcon manaImage = Util.loadImage("mana.gif");
	
	private boolean isDead;
	private Location location;
	
	public Mana(Location location){
		this.location = location;
		this.isDead = false;
	}

	@Override
	public int getPlantCalories() {
		return 200;
	}

	@Override
	public int getMeatCalories() {
		return 0;
	}

	@Override
	public ImageIcon getImage() {
		return manaImage;
	}

	@Override
	public String getName() {
		return "mana";
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
		isDead = true;
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

}
