package ca.ubc.ece.cpen221.mp4.ai;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.*;
import ca.ubc.ece.cpen221.mp4.vehicles.ArenaVehicle;

public class AstonAI extends AbstractAI {

	public AstonAI() {
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaVehicle vehicle) {
		
		
		
		return new WaitCommand();

	}

}
