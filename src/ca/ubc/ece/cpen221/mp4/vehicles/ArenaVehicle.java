package ca.ubc.ece.cpen221.mp4.vehicles;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public interface ArenaVehicle extends MoveableItem{
	
	int getSpeed();
	
	int getStrength();

	int getMovingRange(int speed);

	int getAcceleration();
}
