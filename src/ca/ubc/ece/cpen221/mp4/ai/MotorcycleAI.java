package ca.ubc.ece.cpen221.mp4.ai;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.CrashCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.vehicles.ArenaVehicle;

public class MotorcycleAI extends AbstractAI {

	private Direction direction;
	private boolean speedUp;
	private int turnCounter;

	public MotorcycleAI() {
		this.direction = Util.getRandomDirection();
		this.speedUp = true;
		this.turnCounter = 0;
	}

	/**
	 * @param world
	 *            The ArenaWorld in which the ArenaAnimal given this ai lives in
	 * @param ArenaVehicle
	 *            The ArenaVehicle which is using this ai
	 * 
	 * @return command The Command which is chosen based on the decided
	 *         attributes of a Motorcycle. In this case, Motorcycles change
	 *         direction every 12 turns. They must slow down before turning.
	 */
	@Override
	public Command getNextAction(ArenaWorld world, ArenaVehicle vehicle) {
		Iterable<Item> surroundings = world.searchSurroundings(vehicle);
		Location location = vehicle.getLocation();
		int counter = 0;
		Location nextLocation = new Location(location, direction);

		turnCounter++;

		while (Util.isValidLocation(world, nextLocation)) {
			counter++;
			nextLocation = new Location(nextLocation, direction);
		}

		if (counter <= vehicle.getDistanceToCrash() || turnCounter > 12) {
			speedUp = false;
		} else {
			speedUp = true;
		}

		if ((!speedUp && vehicle.getSpeed() >= vehicle.getTurningSpeed())) {
			if (vehicle.getLocation().getX() < world.getWidth() / 2
					&& vehicle.getLocation().getY() < world.getHeight() / 2) {
				direction = Direction.EAST;
			} else if (vehicle.getLocation().getX() > world.getWidth() / 2
					&& vehicle.getLocation().getY() < world.getHeight() / 2) {
				direction = Direction.SOUTH;
			} else if (vehicle.getLocation().getX() < world.getWidth() / 2
					&& vehicle.getLocation().getY() > world.getHeight() / 2) {
				direction = Direction.NORTH;
			} else if (vehicle.getLocation().getX() >= world.getWidth() / 2
					&& vehicle.getLocation().getY() >= world.getHeight() / 2) {
				direction = Direction.WEST;
			}
			this.turnCounter = 0;
		}

		for (Item item : surroundings) {
			Location loc = new Location(location, direction);
			if (item.getLocation().equals(loc) && item.getStrength() < vehicle.getStrength()) {
				item.loseEnergy(Integer.MAX_VALUE);
			} else if (item.getLocation().equals(loc) && item.getStrength() == vehicle.getStrength()) {
				item.loseEnergy(Integer.MAX_VALUE);
				return new CrashCommand(vehicle);
			} else if (item.getLocation().equals(loc) && item.getStrength() >= vehicle.getStrength()) {
				return new CrashCommand(vehicle);
			}

		}

		if (!Util.isValidLocation(world, new Location(location, direction))
				&& vehicle.getSpeed() < vehicle.getTurningSpeed()) {
			return new CrashCommand(vehicle);
		}

		vehicle.updateSpeed(speedUp);
		return new MoveCommand(vehicle, new Location(location, direction));

	}

}
