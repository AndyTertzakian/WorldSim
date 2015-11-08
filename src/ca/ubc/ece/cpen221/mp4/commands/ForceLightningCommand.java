package ca.ubc.ece.cpen221.mp4.commands;

import java.util.*;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.ForceLightning;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;

public class ForceLightningCommand implements Command {

	private ArenaHero livingItem;

	public ForceLightningCommand(ArenaHero hero) {

		this.livingItem = hero;

	}

	@Override
	public void execute(World world) throws InvalidCommandException {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		Set<Item> surroundings = world.searchSurroundings(livingItem);
		directions.add(Direction.NORTH);
		directions.add(Direction.SOUTH);
		directions.add(Direction.EAST);
		directions.add(Direction.WEST);
		Location loc;
		
		for(Direction d : directions){
			loc = new Location(livingItem.getLocation(), d);
			while(Util.isLocationEmpty(world, loc) && Util.isValidLocation(world, loc)){
				world.addItem(new ForceLightning(loc, d));
				loc = new Location(loc, d);
			}
			
			for(Item i : surroundings){
				if(i.getLocation().equals(loc)){
					i.loseEnergy(Integer.MAX_VALUE);
				}
			}
		}
	}

}
