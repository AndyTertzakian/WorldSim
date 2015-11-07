package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class PacmanAI extends AbstractAI {
	private Direction direction;
	private Direction previousDirection = null;
	int foundFood;

	public PacmanAI() {
		this.direction = Util.getRandomDirection();
		foundFood = 0;
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		if (previousDirection != null
				&& !Util.isValidLocation(world, new Location(animal.getLocation(), previousDirection))) {
			Location loc = new Location(animal.getLocation(), previousDirection);
			previousDirection = null;
			return new MoveCommand(animal, loc);
		}
		
		if(foundFood > 7){
			direction = Util.getRandomDirection();
			foundFood = 0;
		}

		if (!Util.isValidLocation(world, new Location(animal.getLocation(), direction))) {
			direction = oppositeDir(direction);
			if(!Util.isValidLocation(world, new Location(animal.getLocation(), direction))){
				return new WaitCommand();
			}
		}

		Set<Item> surroundings = world.searchSurroundings(animal);

		for (Item i : surroundings) {
			if (Util.getDirectionTowards(animal.getLocation(), i.getLocation()).equals(direction)
					&& !i.equals(animal)) {
				previousDirection = direction;
				direction = Util.getRandomDirection();
				foundFood = 0;
				return new EatCommand(animal, i);
			}else{
				foundFood++;
			}
		}

		return new MoveCommand(animal, new Location(animal.getLocation(), direction));
	}

}
