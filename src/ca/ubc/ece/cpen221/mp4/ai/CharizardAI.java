package ca.ubc.ece.cpen221.mp4.ai;

import java.util.*;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreatheFireCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class CharizardAI extends AbstractAI {

	public CharizardAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		Set<Item> surroundingsList = world.searchSurroundings(animal);
		Map<Item, Integer> surroundings = new HashMap<Item, Integer>();
		Map<Direction, Integer> directions = new HashMap<Direction, Integer>();
		directions.put(Direction.NORTH, 0);
		directions.put(Direction.SOUTH, 0);
		directions.put(Direction.EAST, 0);
		directions.put(Direction.WEST, 0);
		int width = world.getWidth();
		int height = world.getHeight();

		for (Item i : surroundingsList) {
			surroundings.put(i, i.getLocation().getDistance(animal.getLocation()));
		}

		if (animal.getEnergy() < animal.getMaxEnergy()  - 100) {// The "- 100" is just to show Charizard's functionality.
			int x = animal.getLocation().getX();				// He moves around sometimes, and sometimes kills, we will 
			int y = animal.getLocation().getY();				// optimize him later.  Change this to "/ 2" when he is done.
			Location finalLoc = animal.getLocation();

			for (Item i : surroundings.keySet()) {
				Location adjacent = Util.getRandomEmptyAdjacentLocation((World) world, i.getLocation());

				if (surroundings.get(i) == 1 && i.getName().equals("mana")) {
					return new EatCommand(animal, i);
				} else if (i.getName().equals("mana") && Util.isLocationEmpty((World) world, adjacent)) {
					return new MoveCommand(animal, adjacent);
				} else if (x < width / 2 && y < height / 2) {
					for (int d = 0; d < animal.getViewRange(); d++) {
						finalLoc = new Location(finalLoc, Direction.EAST);
					}
				} else if (x > width / 2 && y < height / 2) {
					for (int d = 0; d < animal.getViewRange(); d++) {
						finalLoc = new Location(finalLoc, Direction.SOUTH);
					}
				} else if (x > width / 2 && y > height / 2) {
					for (int d = 0; d < animal.getViewRange(); d++) {
						finalLoc = new Location(finalLoc, Direction.WEST);
					}
				} else {
					for (int d = 0; d < animal.getViewRange(); d++) {
						finalLoc = new Location(finalLoc, Direction.NORTH);
					}
				}
				if (Util.isValidLocation((World) world, finalLoc) && Util.isLocationEmpty((World) world, finalLoc)
						&& finalLoc.getDistance(animal.getLocation()) <= animal.getViewRange()) {
					return new MoveCommand(animal, finalLoc);
				}
			}
		}
		
		for (Item i : surroundings.keySet()) {
			if (i.getLocation().getDistance(animal.getLocation()) <= 5) {
				Direction direction = Util.getDirectionTowards(animal.getLocation(), i.getLocation());
				int numItems = directions.get(direction);
				directions.replace(direction, numItems, numItems + 1);
			}
		}

		Direction optimal = null;
		int max = 0;

		for (Direction d : directions.keySet()) {
			if (directions.get(d) > max) {
				optimal = d;
				max = directions.get(d);
			}
		}
		if (optimal != null && directions.get(optimal) > 2) {
			return new BreatheFireCommand(optimal, animal);
		}

		return new WaitCommand();
	}

}
