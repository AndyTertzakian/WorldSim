package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.Pacman;

public class PacmanAI extends AbstractAI {
	private Direction direction;
	private Direction previousDirection = null;
	int foundFood;

	public PacmanAI() {
		this.direction = Util.getRandomDirection();
		foundFood = 0;
	}

	/**
	 * @param world
	 *            The ArenaWorld in which the ArenaAnimal given this ai lives in
	 * @param ArenaHero
	 *            The ArenaHero which is using this ai
	 * 
	 * @return command The Command which is chosen based on the decided
	 *         attributes of a Pacman. In this case, pacmans move in the same
	 *         direction for seven turns. If they haven't eaten anything in that
	 *         time, they change direction and start again. They also change
	 *         direction every time they eat
	 */
	@Override
	public Command getNextAction(ArenaWorld world, ArenaHero hero) {

		Location loc;

		if (previousDirection != null
				&& Util.isValidLocation(world, new Location(hero.getLocation(), previousDirection))) {
			loc = new Location(hero.getLocation(), previousDirection);
			previousDirection = null;
			if (Util.isValidLocation(world, loc) && isLocationEmpty(world, hero, loc))
				return new MoveCommand(hero, loc);
		}

		if (foundFood > 7) {
			direction = Util.getRandomDirection();
			foundFood = 0;
		}

		if (!Util.isValidLocation(world, new Location(hero.getLocation(), direction))) {
			direction = oppositeDir(direction);
			if (!Util.isValidLocation(world, new Location(hero.getLocation(), direction))) {
				return new WaitCommand();
			}
		}

		Set<Item> surroundings = world.searchSurroundings(hero);

		for (Item i : surroundings) {
			if (Util.getDirectionTowards(hero.getLocation(), i.getLocation()).equals(direction) && !i.equals(hero)) {
				previousDirection = direction;
				direction = Util.getRandomDirection();
				foundFood = 0;
				return new EatCommand(hero, i);
			} else {
				foundFood++;
			}
		}

		loc = new Location(hero.getLocation(), direction);
		if (Util.isValidLocation(world, loc) && isLocationEmpty(world, hero, loc))
			return new MoveCommand(hero, loc);
		else {
			return new WaitCommand();
		}
	}

}
