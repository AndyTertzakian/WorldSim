package ca.ubc.ece.cpen221.mp4.commands;

import java.util.Set;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Fire;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class BreatheFireCommand implements Command {
	private Direction xDirection;
	private Direction yDirection1;
	private Direction yDirection2;
	private int range = 5;
	private ArenaHero livingItem;

	public BreatheFireCommand(Direction direction, ArenaHero animal) {
		this.xDirection = direction;
		this.livingItem = animal;
		if (xDirection.equals(Direction.NORTH) || xDirection.equals(Direction.SOUTH)) {
			yDirection1 = Direction.EAST;
			yDirection2 = Direction.WEST;
		} else {
			yDirection1 = Direction.SOUTH;
			yDirection2 = Direction.NORTH;
		}
	}

	@Override
	public void execute(World world) throws InvalidCommandException {
		Set<Item> targets = world.searchSurroundings(livingItem);

		for (Item i : targets) {
			if (Util.getDirectionTowards(livingItem.getLocation(), i.getLocation()).equals(xDirection)
					&& livingItem.getLocation().getDistance(i.getLocation()) <= range
					&& !i.getName().equals("Charizard")) {
				i.loseEnergy(Integer.MAX_VALUE);
			}
		}

		Location xLoc = livingItem.getLocation();
		Location yLoc1;
		Location yLoc2;

		for (int x = 0; x < 3; x++) {
			world.addItem(new Fire(new Location(xLoc, xDirection)));
			xLoc = new Location(xLoc, xDirection);
			yLoc1 = xLoc;
			yLoc2 = xLoc;

			for (int y = 0; y < x; y++) {
				world.addItem(new Fire(new Location(yLoc1, yDirection1)));
				world.addItem(new Fire(new Location(yLoc2, yDirection2)));
				yLoc1 = new Location(yLoc1, yDirection1);
				yLoc2 = new Location(yLoc2, yDirection2);
			}
		}
	}

}
