package ca.ubc.ece.cpen221.mp4.ai;

import java.util.*;
import java.util.Map.Entry;

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
import ca.ubc.ece.cpen221.mp4.items.animals.*;

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {

	public FoxAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {

		Location northLoc = new Location(animal.getLocation(), Direction.NORTH);
		Location southLoc = new Location(animal.getLocation(), Direction.SOUTH);
		Location eastLoc = new Location(animal.getLocation(), Direction.EAST);
		Location westLoc = new Location(animal.getLocation(), Direction.WEST);
		int northDistance = 0;
		int southDistance = 0;
		int eastDistance = 0;
		int westDistance = 0;
		int numRabbits = 0;
		HashMap<Item, Integer> surroundingsMap = new HashMap<Item, Integer>();
		Set<Item> surroundingsList = world.searchSurroundings(animal);

		for (Item i : surroundingsList) {
			surroundingsMap.put(i, i.getLocation().getDistance(animal.getLocation()));
		}

		for (Item i : surroundingsMap.keySet()) {
			if (surroundingsMap.get(i) == 1) {
				if (i.getName().equals("Rabbit")
						&& (animal.getMaxEnergy() - animal.getEnergy() >= i.getMeatCalories())) {
					return new EatCommand(animal, i);
				}
			} else {
				if (i.getName().equals("Rabbit")) {
					northDistance += i.getLocation().getDistance(northLoc);
					southDistance += i.getLocation().getDistance(southLoc);
					eastDistance += i.getLocation().getDistance(eastLoc);
					westDistance += i.getLocation().getDistance(westLoc);
					numRabbits++;
				}
			}
		}

		if (!this.isLocationEmpty(world, animal, northLoc) && !this.isLocationEmpty(world, animal, southLoc)
				&& !this.isLocationEmpty(world, animal, eastLoc) && !this.isLocationEmpty(world, animal, westLoc)) {
			return new WaitCommand();
		}

		if (numRabbits > 2 && animal.getEnergy() >= 100) {
			Direction randDir = Util.getRandomDirection();
			Location randLoc = new Location(animal.getLocation(), randDir);
			while(!this.isLocationEmpty(world, animal, randLoc)) {
				randDir = Util.getRandomDirection();
				randLoc = new Location(animal.getLocation(), randDir);
			}
			return new BreedCommand(animal, randLoc);
			//return new BreedCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation()));
		}

		if (northDistance < southDistance && northDistance < eastDistance && northDistance < westDistance) {
			if (this.isLocationEmpty(world, animal, northLoc)) {
				return new MoveCommand(animal, northLoc);
			}
		} else if (southDistance < eastDistance && southDistance < westDistance) {
			if (this.isLocationEmpty(world, animal, southLoc)) {
				return new MoveCommand(animal, southLoc);
			}
		} else if (eastDistance < westDistance) {
			if (this.isLocationEmpty(world, animal, eastLoc)) {
				return new MoveCommand(animal, eastLoc);
			}
		} else if (westDistance < northDistance && this.isLocationEmpty(world, animal,westLoc)) {
			return new MoveCommand(animal, westLoc);
		}
		
		Direction randDir = Util.getRandomDirection();
		Location randLoc = new Location(animal.getLocation(), randDir);
		while(!this.isLocationEmpty(world, animal, randLoc)) {
			randDir = Util.getRandomDirection();
			randLoc = new Location(animal.getLocation(), randDir);
		}
		
		return new MoveCommand(animal, randLoc);
		//return new MoveCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation()));
	}

}
