package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.vehicles.ArenaVehicle;

public class AbstractAI implements AI {

	public Direction oppositeDir(Direction dir) { // returns opposite direction
													// of direction dir
		if (dir == Direction.EAST) {
			return Direction.WEST;
		} else if (dir == Direction.WEST) {
			return Direction.EAST;
		} else if (dir == Direction.SOUTH) {
			return Direction.NORTH;
		} else {
			return Direction.SOUTH;
		}
	}

	public boolean isLocationEmpty(ArenaWorld world, ArenaAnimal animal, Location location) { // returns
																								// true
																								// if
																								// location
																								// is
																								// empty
		if (!Util.isValidLocation(world, location)) {
			return false;
		}
		Set<Item> possibleMoves = world.searchSurroundings(animal);
		Iterator<Item> it = possibleMoves.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}

	public boolean isLocationEmpty(ArenaWorld world, ArenaHero hero, Location location) { // returns
		// true
		// if
		// location
		// is
		// empty
		if (!Util.isValidLocation(world, location)) {
			return false;
		}
		Set<Item> possibleMoves = world.searchSurroundings(hero);
		Iterator<Item> it = possibleMoves.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		return new WaitCommand();
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaHero hero) {
		return new WaitCommand();
	}

	public Command getNextAction(ArenaWorld world, ArenaVehicle vehicle) {
		return new WaitCommand();
	}
}
