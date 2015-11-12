package ca.ubc.ece.cpen221.mp4.ai;

import java.util.*;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class AlbatrossAI extends AbstractAI {

	private ArrayList<String> prey = new ArrayList<String>();

	public AlbatrossAI() {
		prey.add("Fox");
		prey.add("Platypus");
		prey.add("water");
	}

	/**
	 * @param world
	 *            The ArenaWorld in which the ArenaAnimal given this ai lives in
	 * @param animal
	 *            The ArenaAnimal which is using this ai
	 * 
	 * @return command The Command which is chosen based on the decided
	 *         attributes of an albatross. in this case, albatross' seek out
	 *         playpuses and water and eat both. They prioritze playpuses over
	 *         water.
	 */
	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		HashMap<Item, Integer> surroundingsMap = new HashMap<Item, Integer>();
		Set<Item> surroundingsList = world.searchSurroundings(animal);
		Item nextEatFox = null;
		Item nextEatPlat = null;
		Item nextEatWater = null;
		int north = 0;
		int south = 0;
		int east = 0;
		int west = 0;
		int totalDistance = 0;
		int numItems = 0;
		Map<Direction, Integer> directions = new HashMap<Direction, Integer>();

		for (Item i : surroundingsList) {

			int distance = i.getLocation().getDistance(animal.getLocation());

			totalDistance += distance;
			numItems++;

			Direction direction = Util.getDirectionTowards(animal.getLocation(), i.getLocation());
			surroundingsMap.put(i, i.getLocation().getDistance(animal.getLocation()));
			if (i.getName().equals("Fox")) {
				if (i.getLocation().getDistance(animal.getLocation()) == 1) {
					nextEatFox = i;
				} else if (direction.toString().equals("NORTH")) {
					north += 3;
				} else if (direction.toString().equals("SOUTH")) {
					south += 3;
				} else if (direction.toString().equals("EAST")) {
					east += 3;
				} else {
					west += 3;
				}

			} else if (i.getName().equals("Platypus")) {
				if (distance == 1) {
					nextEatPlat = i;
				} else if (direction.toString().equals("NORTH")) {
					north += 2;
				} else if (direction.toString().equals("SOUTH")) {
					south += 2;
				} else if (direction.toString().equals("EAST")) {
					east += 2;
				} else {
					west += 2;
				}

			} else if (i.getName().equals("water")) {
				if (distance == 1) {
					nextEatWater = i;
				} else if (direction.toString().equals("NORTH")) {
					north += 1;
				} else if (direction.toString().equals("SOUTH")) {
					south += 1;
				} else if (direction.toString().equals("EAST")) {
					east += 1;
				} else {
					west += 1;
				}
			}
		}

		if (nextEatFox != null && animal.getEnergy() < (animal.getMaxEnergy() - nextEatFox.getMeatCalories())) {
			return new EatCommand(animal, nextEatFox);
		} else
			if (nextEatPlat != null && animal.getEnergy() < (animal.getMaxEnergy() - nextEatPlat.getMeatCalories())) {
			return new EatCommand(animal, nextEatPlat);
		} else if (nextEatWater != null
				&& animal.getEnergy() < (animal.getMaxEnergy() - nextEatWater.getMeatCalories())) {
			return new EatCommand(animal, nextEatWater);
		}

		if (totalDistance != 0) {
			int travelDistance = totalDistance / numItems;
			if (travelDistance > animal.getMovingRange()) {
				travelDistance = animal.getMovingRange();
			}
			directions.put(Direction.NORTH, north);
			directions.put(Direction.SOUTH, south);
			directions.put(Direction.EAST, east);
			directions.put(Direction.WEST, west);
			Direction direction1;
			Direction direction2;
			Direction optimal = null;
			int distance1;
			int distance2;

			Map.Entry<Direction, Integer> max1 = null;
			Map.Entry<Direction, Integer> max2 = null;

			for (Map.Entry<Direction, Integer> dirEntry : directions.entrySet()) {
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
					optimal = direction1;
				} else {
					optimal = direction2;
				}
			}

			int x;
			int y;
			Location finalLoc = animal.getLocation();
			Double distRatio;

			if (optimal == null) {
				if (distance1 + distance2 != 0.0) {
					distRatio = (double) (distance1 / (distance1 + distance2));
				} else {
					distRatio = 1.0;
				}

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
			} else if (optimal != null) {
				for (int i = 0; i < travelDistance; i++) {
					finalLoc = new Location(finalLoc, optimal);
				}
			}

			if (Util.isValidLocation(world, finalLoc) && this.isLocationEmpty(world, animal, finalLoc)) {
				return new MoveCommand(animal, finalLoc);
			} else {
				return new MoveCommand(animal,
						Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation()));
			}

		}

		return new WaitCommand();
	}
}
