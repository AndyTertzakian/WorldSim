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

public class PlatypusAI extends AbstractAI {

	public PlatypusAI() {

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
		int numWater = 0;
		HashMap<Item, Integer> surroundingsMap = new HashMap<Item, Integer>();
		Set<Item> surroundingsList = world.searchSurroundings(animal);
		
		for (Item i : surroundingsList) {
			surroundingsMap.put(i, i.getLocation().getDistance(animal.getLocation()));
		}

		for (Item i : surroundingsMap.keySet()) {
			if (surroundingsMap.get(i) == 1) {
				if (i.getName().equals("water")
						&& (animal.getMaxEnergy() - animal.getEnergy() >= i.getPlantCalories())) {
					return new EatCommand(animal, i);
				}
			} else {
				if (i.getName().equals("water")) {
					northDistance += i.getLocation().getDistance(northLoc);
					southDistance += i.getLocation().getDistance(southLoc);
					eastDistance += i.getLocation().getDistance(eastLoc);
					westDistance += i.getLocation().getDistance(westLoc);
					numWater++;
				}
			}
		}

		if (numWater > 2 && animal.getEnergy() > 50) {
			return new BreedCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation()));
		}

		if (northDistance <= southDistance && northDistance <= eastDistance && northDistance <= westDistance) {
			if (Util.isLocationEmpty((World) world, northLoc)) {
				return new MoveCommand(animal, northLoc);
			}
		} else if (southDistance <= eastDistance && southDistance <= westDistance) {
			if (Util.isLocationEmpty((World) world, southLoc)) {
				return new MoveCommand(animal, southLoc);
			}
		} else if (eastDistance <= westDistance) {
			if (Util.isLocationEmpty((World) world, eastLoc)) {
				return new MoveCommand(animal, eastLoc);
			}
		} else if (Util.isLocationEmpty((World) world, westLoc)) {
			return new MoveCommand(animal, westLoc);
		}

		if (!Util.isLocationEmpty((World) world, northLoc) && !Util.isLocationEmpty((World) world, southLoc)
				&& !Util.isLocationEmpty((World) world, eastLoc) && !Util.isLocationEmpty((World) world, westLoc)) {
			return new WaitCommand();
		}

		return new MoveCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation()));
	}

}
