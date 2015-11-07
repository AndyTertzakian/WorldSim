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
		boolean pacmanFound = false;
		Direction pacmanDirection = null;

		for (Item i : surroundingsList) {
			surroundings.put(i, i.getLocation().getDistance(animal.getLocation()));
		}

		if (animal.getEnergy() < animal.getMaxEnergy() - 100) {
			int x = animal.getLocation().getX();
			int y = animal.getLocation().getY();
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
				if (i.getName().equals("Pacman")) {
					pacmanFound = true;
					pacmanDirection = Util.getDirectionTowards(animal.getLocation(), i.getLocation());
				}

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
		} else if (pacmanFound) {
			return new BreatheFireCommand(pacmanDirection, animal);
		} else {
			Map<Direction, Integer> distances = new HashMap<Direction, Integer>();
			distances.put(Direction.NORTH, 0);
			distances.put(Direction.SOUTH, 0);
			distances.put(Direction.EAST, 0);
			distances.put(Direction.WEST, 0);
			int totalDistance = 0;
			int numItems = 0;

			for (Item i : surroundings.keySet()) {
				int distance = animal.getLocation().getDistance(i.getLocation());
				Direction direction = Util.getDirectionTowards(animal.getLocation(), i.getLocation());
				int directionDist = distances.get(direction);
				totalDistance += distance;
				numItems++;

				directions.replace(direction, directionDist, directionDist + distance);
			}

			Direction direction1;
			Direction direction2;
			Direction optimalPrime = null;
			int distance1;
			int distance2;
			int travelDistance = totalDistance / numItems;
			if (travelDistance > animal.getViewRange()) {
				travelDistance = animal.getViewRange();
			}

			Map.Entry<Direction, Integer> max1 = null;
			Map.Entry<Direction, Integer> max2 = null;

			for (Map.Entry<Direction, Integer> dirEntry : distances.entrySet()) {
				if (max1 == null || dirEntry.getValue() > max1.getValue()) {
					max1 = dirEntry;
				}
			}

			direction1 = max1.getKey();
			distance1 = directions.remove(max1.getKey());

			for (Map.Entry<Direction, Integer> dirEntry : directions.entrySet()) {
				if (max2 == null || dirEntry.getValue() > max2.getValue()) {
					max2 = dirEntry;
				}
			}

			direction2 = max2.getKey();
			distance2 = directions.remove(max2.getKey());

			if (oppositeDir(direction1).equals(direction2)) {
				if (distance1 > distance2) {
					optimalPrime = direction1;
				} else {
					optimalPrime = direction2;
				}
			}

			int x;
			int y;
			Location finalLoc = animal.getLocation();

			if (optimalPrime == null) {
				Double distRatio = (double) (distance1 / (distance1 + distance2));
				if (direction1.equals(Direction.WEST) || direction1.equals(Direction.EAST)) {
					Double xVal = travelDistance * distRatio;
					if (animal.getLocation().getX() + xVal.intValue() > world.getWidth()
							&& direction1.equals(Direction.EAST)) {
						x = world.getWidth() - animal.getLocation().getX();
					} else if (animal.getLocation().getX() - xVal.intValue() < 0 && direction1.equals(Direction.WEST)) {
						x = animal.getLocation().getX();
					} else {
						x = xVal.intValue();
					}
					if (animal.getLocation().getY() + (travelDistance - x) > world.getHeight()
							&& direction2.equals(Direction.SOUTH)) {
						y = world.getHeight() - animal.getLocation().getY();
					} else if (animal.getLocation().getY() - (travelDistance - x) < 0
							&& direction2.equals(Direction.NORTH)) {
						y = animal.getLocation().getY();
					} else {
						y = travelDistance - x;
					}
				} else {
					Direction temp = direction2;
					direction2 = direction1;
					direction1 = temp;
					Double yVal = travelDistance * distRatio;
					if (animal.getLocation().getY() + yVal.intValue() > world.getHeight()
							&& direction1.equals(Direction.SOUTH)) {
						y = world.getHeight() - animal.getLocation().getY();
					} else
						if (animal.getLocation().getY() - yVal.intValue() < 0 && direction2.equals(Direction.NORTH)) {
						y = animal.getLocation().getY();
					} else {
						y = yVal.intValue();
					}
					if (animal.getLocation().getX() + (travelDistance - y) > world.getWidth()
							&& direction2.equals(Direction.EAST)) {
						x = world.getWidth() - animal.getLocation().getX();
					} else if (animal.getLocation().getX() - (travelDistance - y) < 0
							&& direction2.equals(Direction.WEST)) {
						x = animal.getLocation().getX();
					} else {
						x = travelDistance - y;
					}
				}

				for (int i = 0; i < x; i++) {
					finalLoc = new Location(finalLoc, direction1);
				}
				for (int i = 0; i < y; i++) {
					finalLoc = new Location(finalLoc, direction2);
				}
			} else {
				for (int i = 0; i < travelDistance; i++) {
					finalLoc = new Location(finalLoc, optimalPrime);
				}
			}

			if (Util.isValidLocation(world, finalLoc) && Util.isLocationEmpty((World) world, finalLoc)) {
				return new MoveCommand(animal, finalLoc);
			} else {
				return new MoveCommand(animal,
						Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation()));
			}
		}
	}

}
