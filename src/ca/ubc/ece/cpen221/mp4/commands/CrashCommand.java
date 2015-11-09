package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.vehicles.ArenaVehicle;

public class CrashCommand implements Command {

	private ArenaVehicle livingItem;
	
	public CrashCommand(ArenaVehicle vehicle) {
		this.livingItem = vehicle;
	}
	
	@Override
	public void execute(World world) throws InvalidCommandException {
		
		livingItem.loseEnergy(Integer.MAX_VALUE);
		
	}

}
