package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;

import ca.ubc.ece.cpen221.mp4.ai.DumpTruckAI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class DumpTruck implements ArenaVehicle {
	private static final int INITIAL_SPEED = 20;
	private static final int TURNING_SPEED = 2;
	private static final int ACCELERATION = 2;
	private static final int STRENGTH = 500;
	private static final int VIEW_RANGE = 5;
	private static final ImageIcon dumpTruckImage = Util.loadImage("trucks.gif");
	
	private final AI ai;
	

	private int speed;
	private Location location;
	private boolean isDead;
	
	public DumpTruck(DumpTruckAI ai, Location initialLocation){
		this.ai = ai;
		this.location = initialLocation;
		isDead = false;
		this.speed = INITIAL_SPEED;
	}
	
	public int getTurningSpeed(){
		return TURNING_SPEED;
	}
	
	public void updateSpeed(boolean speedUp){
		if(speedUp){
			this.speed = this.speed * ACCELERATION;
		}else{
			this.speed = this.speed / ACCELERATION;
		}
	}
	
	public int getViewRange(){
		return VIEW_RANGE;
	}

	@Override
	public void moveTo(Location targetLocation) {
		this.location = targetLocation;
	}

	@Override
	public int getMovingRange() {
		return speed * ACCELERATION;
	}

	@Override
	public ImageIcon getImage() {
		return dumpTruckImage;
	}

	@Override
	public String getName() {
		return "DumpTruck";
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
	public int getMovingRange(int speed) {
		return speed * ACCELERATION;
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

}
