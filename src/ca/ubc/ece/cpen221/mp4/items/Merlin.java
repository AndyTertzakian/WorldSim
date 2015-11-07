package ca.ubc.ece.cpen221.mp4.items;

import java.util.Set;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;

/**
 * Merlin does not show up in the world, but he spawns {@link Mana} at a random
 * location every 100 steps.  May the best hero win...
 */
public class Merlin implements Actor {

	@Override
	public int getCoolDownPeriod() {
		return 100;
	}

	@SuppressWarnings("unused")
	@Override
	public Command getNextAction(World world) {
		final Location loc = Util.getRandomEmptyLocation(world);

		// An anonymous Command class which spawns mana.
		return new Command() {
			@Override
			public void execute(World world) {
				world.addItem(new Mana(loc));
			}

		};
	}

}