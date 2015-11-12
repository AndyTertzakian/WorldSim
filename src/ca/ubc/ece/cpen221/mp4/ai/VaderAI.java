package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.*;
import ca.ubc.ece.cpen221.mp4.items.ForceLightning;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;

public class VaderAI extends AbstractAI {

	// Timer variable. Used to determine when Vader should use his force
	// lightning
	private int timer;

	public VaderAI() {
		this.timer = 0;
	}

	/**
	 * @param world
	 *            The ArenaWorld in which the ArenaAnimal given this ai lives in
	 * @param ArenaHero
	 *            The ArenaHero which is using this ai
	 * 
	 * @return command The Command which is chosen based on the decided
	 *         attributes of a DarthVader. In this case, DarthVaders move
	 *         randomly every turn, and use force lightning every 5 turns.
	 */
	@Override
	public Command getNextAction(ArenaWorld world, ArenaHero hero) {

		// get a random direction and use it to create a new location
		Direction direction = Util.getRandomDirection();
		Location targetLocation = new Location(hero.getLocation(), direction);
		int attemptCount = 0;

		// if the location created above is not valid, then try again, for a max
		// of 5 attempts. Otherwise, wait.
		while (!this.isLocationEmpty(world, hero, targetLocation)) {
			direction = Util.getRandomDirection();
			targetLocation = new Location(hero.getLocation(), direction);
			attemptCount++;
			if (attemptCount > 5) {
				return new WaitCommand();
			}
		}

		// increment the timer field
		this.timer++;

		// If 5 or more turns have passed, then execute a Force lightning
		// command,
		// otherwise, move to a new random location.
		if (this.timer <= 4) {
			return new MoveCommand(hero, targetLocation);
		} else {
			this.timer = 0;
			return new ForceLightningCommand(hero);
		}

	}
}
