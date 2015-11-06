package ca.ubc.ece.cpen221.mp4.ai;

import java.nio.file.DirectoryIteratorException;
import java.util.*;

import com.sun.org.apache.bcel.internal.generic.LLOAD;

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
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {

	public RabbitAI() {
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {

		Set<Item> surroundings = world.searchSurroundings(animal);
		Map<Location, Double> foxAvgs;
		Map<Location, Double> grassAvgs;
		Location nextMoveLocation;
		boolean foxFound = false;
		boolean grassFound = false;
		double closestFoxDist = 0;
		Item nextEat = null;
		int minFoxCoolDown = 0;

		// Create an arraylist which contains the four different possibilities
		// for the rabbit to move to. Only add the the location if it is
		// empty.
		ArrayList<Location> locations = new ArrayList<Location>();
		Location westLoc = new Location(animal.getLocation(), Direction.WEST);
		if (isLocationEmpty(world, animal, westLoc))
			locations.add(westLoc);
		Location eastLoc = new Location(animal.getLocation(), Direction.EAST);
		if (isLocationEmpty(world, animal, eastLoc))
			locations.add(eastLoc);
		Location northLoc = new Location(animal.getLocation(), Direction.NORTH);
		if (isLocationEmpty(world, animal, northLoc))
			locations.add(northLoc);
		Location southLoc = new Location(animal.getLocation(), Direction.SOUTH);
		if (isLocationEmpty(world, animal, southLoc))
			locations.add(southLoc);

		for (Item item : surroundings) {
			if (item.getName().equals("Fox")) {
				Fox fox = (Fox) item;
				if (minFoxCoolDown == 0 || fox.getCoolDownPeriod() < minFoxCoolDown)
					minFoxCoolDown = fox.getCoolDownPeriod();
				if (closestFoxDist == 0.0 || item.getLocation().getDistance(animal.getLocation()) < closestFoxDist)
					closestFoxDist = item.getLocation().getDistance(animal.getLocation());
				foxFound = true;
			}
			if (item.getName().equals("grass")) {
				grassFound = true;
				if (item.getLocation().getDistance(animal.getLocation()) == 1) {
					nextEat = item;
				}
			}
			int[] asdf = new int[4];
			Arrays.sort(asdf);
		}

		if (!Util.isLocationEmpty((World) world, northLoc) && !Util.isLocationEmpty((World) world, southLoc)
				&& !Util.isLocationEmpty((World) world, eastLoc) && !Util.isLocationEmpty((World) world, westLoc)) {
			return new WaitCommand();
		}

		if (foxFound) {
			foxAvgs = getAverageDists(locations, surroundings, "fox");
			nextMoveLocation = getMaxDistLocation(foxAvgs);
			if (nextMoveLocation != null && minFoxCoolDown <= 1 || closestFoxDist < 2)
				return new MoveCommand(animal, nextMoveLocation);
			if (animal.getEnergy() >= animal.getMaxEnergy() - 10)
				return new BreedCommand(animal, nextMoveLocation);
		}
		if (grassFound) {

			grassAvgs = getAverageDists(locations, surroundings, "grass");
			nextMoveLocation = getMinDistLocation(grassAvgs);

			if (animal.getEnergy() >= animal.getMaxEnergy() - 10)
				return new BreedCommand(animal, nextMoveLocation);
			if (nextEat != null && animal.getEnergy() <= 45)
				return new EatCommand(animal, nextEat);
			else if (nextMoveLocation != null)
				return new MoveCommand(animal, nextMoveLocation);
			else if (animal.getEnergy() >= animal.getMinimumBreedingEnergy()) {
				return new BreedCommand(animal, nextMoveLocation);
			}
		}
		Direction dir = Util.getRandomDirection();
		nextMoveLocation = new Location(animal.getLocation(), dir);
		if (Util.isValidLocation(world, nextMoveLocation) && this.isLocationEmpty(world, animal, nextMoveLocation))
			return new MoveCommand(animal, nextMoveLocation);
		return new WaitCommand();
	}

	private Map<Location, Double> getAverageDists(ArrayList<Location> locations, Set<Item> surroundings, String name) {

		Map<Location, Double> averages = new HashMap<Location, Double>();

		for (Location l : locations) {
			averages.put(l, -1.0);
		}

		int count = 0;
		for (Item item : surroundings) {
			if (item.getName().equals(name)) {
				count++;
				for (Location l : averages.keySet()) {
					averages.replace(l, (averages.get(l) + (double) item.getLocation().getDistance(l)));
				}
			}
		}

		for (Location l : averages.keySet()) {
			averages.replace(l, averages.get(l) / (double) count);
		}

		return averages;
	}

	private Location getMaxDistLocation(Map<Location, Double> locations) {

		Map.Entry<Location, Double> max = null;
		if (locations.isEmpty()) {
			return null;
		}
		for (Map.Entry<Location, Double> distEntry : locations.entrySet()) {
			if (max == null || distEntry.getValue() > max.getValue()) {
				max = distEntry;
			}
		}
		if (max.getKey() != null)
			return max.getKey();
		return null;
	}

	private Location getMinDistLocation(Map<Location, Double> locations) {

		Map.Entry<Location, Double> min = null;
		if (locations.isEmpty()) {
			return null;
		}
		for (Map.Entry<Location, Double> distEntry : locations.entrySet()) {
			if (min == null || distEntry.getValue() < min.getValue()) {
				min = distEntry;
			}
		}
		if (min.getKey() != null)
			return min.getKey();

		return null;
	}
}