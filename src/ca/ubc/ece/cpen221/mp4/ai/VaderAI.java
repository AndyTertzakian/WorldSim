package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.*;
import ca.ubc.ece.cpen221.mp4.items.ForceLightning;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.ArenaHero;

public class VaderAI extends AbstractAI {

	private boolean timeToExcute;
	private int timer;

	public VaderAI() {
		this.timeToExcute = false;
		this.timer = 0;
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaHero hero) {
		Set<Item> visible = world.searchSurroundings(hero);
		Direction direction = Util.getRandomDirection();
		Location targetLocation = new Location(hero.getLocation(), direction);
		int attemptCount = 0;
		
		while (!this.isLocationEmpty(world, hero, targetLocation)) {
			direction = Util.getRandomDirection();
			targetLocation = new Location(hero.getLocation(), direction);
			attemptCount++;
			if(attemptCount > 5) {
				return new WaitCommand();
			}
		}

		this.timer++;
		
		if (this.timer <= 4) {
			return new MoveCommand(hero, targetLocation);		
		} else {
			this.timer = 0;
			return new ForceLightningCommand(hero);
		}

	}
}
