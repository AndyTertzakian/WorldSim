package ca.ubc.ece.cpen221.mp4.commands;

import java.util.Set;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;

public class ForceLightningCommand implements Command {

	private ArenaHero livingItem;
	private Item target;

	public ForceLightningCommand(ArenaHero hero, Item target) {

		this.livingItem = hero;
		this.target = target;

	}

	@Override
	public void execute(World world) throws InvalidCommandException {
		Set<Item> targets = world.searchSurroundings(livingItem);
		Item target = null;
		Direction direction = Util.getDirectionTowards(livingItem.getLocation(), target.getLocation());
		int distance;
		int worldY = world.getHeight();
		int worldX = world.getWidth();
		Location loc = livingItem.getLocation();

		for (Item i : targets) {
			if (Util.getDirectionTowards(livingItem.getLocation(), i.getLocation()).equals(direction)
					&& !i.getName().equals("DarthVader")) {
				i.loseEnergy(Integer.MAX_VALUE);
				target = i;
				break;
			}
		}
		
		if(direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
			distance = Math.abs(livingItem.getLocation().getY() - target.getLocation().getY());
		}else {
			
		}

		
		
	}

}
