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
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;

public class CharizardAI extends AbstractAI {

	public CharizardAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaHero hero) {
		Set<Item> surroundingsList = world.searchSurroundings(hero);
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
			surroundings.put(i, i.getLocation().getDistance(hero.getLocation()));
		}

		if (hero.getEnergy() < hero.getMaxEnergy() / 2) {
			int x = hero.getLocation().getX();
			int y = hero.getLocation().getY();
			Location finalLoc = hero.getLocation();

			for (Item i : surroundings.keySet()) {
				/// FIX CASTING
				Location adjacent = Util.getRandomEmptyAdjacentLocation((World) world, i.getLocation());

				if (surroundings.get(i) == 1 && i.getName().equals("mana")) {
					return new EatCommand(hero, i);
				} else if (i.getName().equals("mana") && this.isLocationEmpty(world, hero, adjacent)) {
					return new MoveCommand(hero, adjacent);
				} else if (x < width / 2 && y < height / 2) {
					for (int d = 0; d < hero.getViewRange(); d++) {
						finalLoc = new Location(finalLoc, Direction.EAST);
					}
				} else if (x > width / 2 && y < height / 2) {
					for (int d = 0; d < hero.getViewRange(); d++) {
						finalLoc = new Location(finalLoc, Direction.SOUTH);
					}
				} else if (x > width / 2 && y > height / 2) {
					for (int d = 0; d < hero.getViewRange(); d++) {
						finalLoc = new Location(finalLoc, Direction.WEST);
					}
				} else {
					for (int d = 0; d < hero.getViewRange(); d++) {
						finalLoc = new Location(finalLoc, Direction.NORTH);
					}
				}
				if (Util.isValidLocation(world, finalLoc) && this.isLocationEmpty(world, hero, finalLoc)
						&& finalLoc.getDistance(hero.getLocation()) <= hero.getViewRange()) {
					return new MoveCommand(hero, finalLoc);
				}
			}
		}

		for (Item i : surroundings.keySet()) {
			if (i.getLocation().getDistance(hero.getLocation()) <= 5) {
				if (i.getName().equals("Pacman")) {
					pacmanFound = true;
					pacmanDirection = Util.getDirectionTowards(hero.getLocation(), i.getLocation());
				}

				Direction direction = Util.getDirectionTowards(hero.getLocation(), i.getLocation());
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
			return new BreatheFireCommand(optimal, hero);
		} else if (pacmanFound) {
			return new BreatheFireCommand(pacmanDirection, hero);
		} else {
			Map<Direction, Integer> distances = new HashMap<Direction, Integer>();
			distances.put(Direction.NORTH, 0);
			distances.put(Direction.SOUTH, 0);
			distances.put(Direction.EAST, 0);
			distances.put(Direction.WEST, 0);
			int totalDistance = 0;
			int numItems = 0;

			for (Item i : surroundings.keySet()) {
				int distance = hero.getLocation().getDistance(i.getLocation());
				Direction direction = Util.getDirectionTowards(hero.getLocation(), i.getLocation());
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
			if (travelDistance > hero.getViewRange()) {
				travelDistance = hero.getViewRange();
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
			Location finalLoc = hero.getLocation();

			if (optimalPrime == null) {
				Double distRatio = (double) (distance1 / (distance1 + distance2));
				if (direction1.equals(Direction.WEST) || direction1.equals(Direction.EAST)) {
					Double xVal = travelDistance * distRatio;
					if (hero.getLocation().getX() + xVal.intValue() > world.getWidth()
							&& direction1.equals(Direction.EAST)) {
						x = world.getWidth() - hero.getLocation().getX();
					} else if (hero.getLocation().getX() - xVal.intValue() < 0 && direction1.equals(Direction.WEST)) {
						x = hero.getLocation().getX();
					} else {
						x = xVal.intValue();
					}
					if (hero.getLocation().getY() + (travelDistance - x) > world.getHeight()
							&& direction2.equals(Direction.SOUTH)) {
						y = world.getHeight() - hero.getLocation().getY();
					} else if (hero.getLocation().getY() - (travelDistance - x) < 0
							&& direction2.equals(Direction.NORTH)) {
						y = hero.getLocation().getY();
					} else {
						y = travelDistance - x;
					}
				} else {
					Direction temp = direction2;
					direction2 = direction1;
					direction1 = temp;
					Double yVal = travelDistance * distRatio;
					if (hero.getLocation().getY() + yVal.intValue() > world.getHeight()
							&& direction1.equals(Direction.SOUTH)) {
						y = world.getHeight() - hero.getLocation().getY();
					} else if (hero.getLocation().getY() - yVal.intValue() < 0 && direction2.equals(Direction.NORTH)) {
						y = hero.getLocation().getY();
					} else {
						y = yVal.intValue();
					}
					if (hero.getLocation().getX() + (travelDistance - y) > world.getWidth()
							&& direction2.equals(Direction.EAST)) {
						x = world.getWidth() - hero.getLocation().getX();
					} else
						if (hero.getLocation().getX() - (travelDistance - y) < 0 && direction2.equals(Direction.WEST)) {
						x = hero.getLocation().getX();
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

			if (Util.isValidLocation(world, finalLoc) && this.isLocationEmpty(world, hero, finalLoc)) {
				return new MoveCommand(hero, finalLoc);
			} else {
				//FIX CASTING
				return new MoveCommand(hero, Util.getRandomEmptyAdjacentLocation((World) world, hero.getLocation()));
			}
		}
	}

}
