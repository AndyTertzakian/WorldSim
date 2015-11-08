package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreatheFireCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class VaderAI extends AbstractAI {

	public VaderAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaHero hero) {
		Set<Item> visible = world.searchSurroundings(hero);
		
		
		
		
		return null;
	}

}
