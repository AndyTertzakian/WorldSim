package ca.ubc.ece.cpen221.mp4.items;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;

/**
 * The Rain does not show up in the world, but it creates a {@link Water} at
 * a random location each step if more than three quarters of the world's locations are
 * empty. Also extinguishes Charizard's fire.  Don't worry, Water doesn't just appear...
 */
public class Rain implements Actor {

	@Override
	public int getCoolDownPeriod() {
		// Acts every step.
		return 1;
	}

	@SuppressWarnings("unused")
	@Override
	public Command getNextAction(World world) {
		int occupiedLocations = 0;
		for (Item item : world.getItems()) {
			occupiedLocations++;
			if(item.getName().equals("fire")){
				item.loseEnergy(2);
			}
		}

		// If the number of occupied locations is less than a quarter of the total
		// number of locations, this Rain creates Water at a random location.
		int totalLocations = world.getHeight() * world.getWidth();
		if (occupiedLocations < totalLocations / 4) {
			final Location loc = Util.getRandomEmptyLocation(world);

			// An anonymous Command class which creates water.
			return new Command() {
				@Override
				public void execute(World world) {
					world.addItem(new Water(loc));
				}
			};

		}

		// Else it does nothing at all.
		return new WaitCommand();
	}

}
