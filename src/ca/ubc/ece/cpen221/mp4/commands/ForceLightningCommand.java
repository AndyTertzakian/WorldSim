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

	public ForceLightningCommand(ArenaHero hero) {

		this.livingItem = hero;
		this.target = target;

	}

	@Override
	public void execute(World world) throws InvalidCommandException {
	}

}
