package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;

public class DumpTruck implements ArenaVehicle {
	
	
	private Location location;

	@Override
	public void moveTo(Location targetLocation) {
		this.location = targetLocation;
	}

	@Override
	public int getMovingRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ImageIcon getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loseEnergy(int energy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
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
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStrength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMomentum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMovingRange(int speed) {
		// TODO Auto-generated method stub
		return 0;
	}

}
