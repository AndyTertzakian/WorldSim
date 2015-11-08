package ca.ubc.ece.cpen221.mp4;

import javax.swing.SwingUtilities;

import ca.ubc.ece.cpen221.mp4.ai.*;
import ca.ubc.ece.cpen221.mp4.items.Gardener;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.Merlin;
import ca.ubc.ece.cpen221.mp4.items.Rain;
import ca.ubc.ece.cpen221.mp4.items.Water;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes.*;
import ca.ubc.ece.cpen221.mp4.staff.WorldImpl;
import ca.ubc.ece.cpen221.mp4.staff.WorldUI;

/**
 * The Main class initialize a world with some {@link Grass}, {@link Rabbit}s,
 * {@link Fox}es, {@link Gnat}s, {@link Gardener}, etc.
 *
 * You may modify or add Items/Actors to the World.
 *
 */
public class Main {

	static final int X_DIM = 40;
	static final int Y_DIM = 40;
	static final int SPACES_PER_GRASS = 7;
	static final int INITIAL_GRASS = X_DIM * Y_DIM / SPACES_PER_GRASS;
	static final int INITIAL_WATER = INITIAL_GRASS / 2;
	static final int INITIAL_GNATS = INITIAL_GRASS / 4;
	static final int INITIAL_RABBITS = INITIAL_GRASS / 4;
	static final int INITIAL_FOXES = INITIAL_GRASS / 32;
	static final int INITIAL_TIGERS = INITIAL_GRASS / 32;
	static final int INITIAL_BEARS = INITIAL_GRASS / 40;
	static final int INITIAL_HYENAS = INITIAL_GRASS / 32;
	static final int INITIAL_PLATYPUSSES = INITIAL_GRASS / 20;
	static final int INITIAL_ALBATROSS = INITIAL_GRASS / 50;
	static final int INITIAL_CHARIZARD = 1;
	static final int INITIAL_PACMAN = 1;
	static final int INITIAL_VADER = 1;
	static final int INITIAL_CARS = INITIAL_GRASS / 100;
	static final int INITIAL_TRUCKS = INITIAL_GRASS / 150;
	static final int INITIAL_MOTORCYCLES = INITIAL_GRASS / 64;
	static final int INITIAL_MANS = INITIAL_GRASS / 150;
	static final int INITIAL_WOMANS = INITIAL_GRASS / 100;
	static final int INITIAL_HUNTERS = INITIAL_GRASS / 150;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main().createAndShowWorld();
			}
		});
	}

	public void createAndShowWorld() {
		World world = new WorldImpl(X_DIM, Y_DIM);
		initialize(world);
		new WorldUI(world).show();
	}

	public void initialize(World world) {
		addGrass(world);
		addWater(world);
		world.addActor(new Gardener());
		world.addActor(new Rain());
		world.addActor(new Merlin());

		//addVader(world);
		addCharizard(world);
		addGnats(world);
		addRabbits(world);
		addFoxes(world);
		addPlatypusses(world);
		addAlbatross(world);	
		addPacman(world);
	}

	private void addGrass(World world) {
		for (int i = 0; i < INITIAL_GRASS; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			world.addItem(new Grass(loc));
		}
	}
	
	private void addWater(World world){
		for(int i = 0; i < INITIAL_WATER; i++){
			Location loc = Util.getRandomEmptyLocation(world);
			world.addItem(new Water(loc));
		}
	}

	private void addGnats(World world) {
		for (int i = 0; i < INITIAL_GNATS; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			Gnat gnat = new Gnat(loc);
			world.addItem(gnat);
			world.addActor(gnat);
		}
	}

	private void addFoxes(World world) {
		FoxAI foxAI = new FoxAI();
		for (int i = 0; i < INITIAL_FOXES; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			Fox fox = new Fox(foxAI, loc);
			world.addItem(fox);
			world.addActor(fox);
		}
	}
	
	private void addAlbatross(World world){
		AlbatrossAI albatrossAI = new AlbatrossAI();
		for(int i = 0; i < INITIAL_ALBATROSS; i++){
			Location loc = Util.getRandomEmptyLocation(world);
			Albatross albatross = new Albatross(albatrossAI, loc);
			world.addItem(albatross);
			world.addActor(albatross);
		}
	}

	private void addRabbits(World world) {
		RabbitAI rabbitAI = new RabbitAI();
		for (int i = 0; i < INITIAL_RABBITS; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			Rabbit rabbit = new Rabbit(rabbitAI, loc);
			world.addItem(rabbit);
			world.addActor(rabbit);
		}
	}
	
	private void addPlatypusses(World world){
		PlatypusAI platypusAI = new PlatypusAI();
		for(int i = 0; i < INITIAL_PLATYPUSSES; i++){
			Location loc = Util.getRandomEmptyLocation(world);
			Platypus platypus = new Platypus(platypusAI, loc);
			world.addItem(platypus);
			world.addActor(platypus);
		}
	}
	
	private void addCharizard(World world){
		CharizardAI charizardAI = new CharizardAI();
		for(int i = 0; i < INITIAL_CHARIZARD; i++){
			Location loc = Util.getRandomEmptyLocation(world);
			Charizard charizard = new Charizard(charizardAI, loc);
			world.addItem(charizard);
			world.addActor(charizard);
		}
	}
	
	private void addPacman(World world){
		PacmanAI pacmanAI = new PacmanAI();
		for(int i = 0; i < INITIAL_PACMAN; i++){
			Location loc = Util.getRandomEmptyLocation(world);
			Pacman pacman = new Pacman(pacmanAI, loc);
			world.addItem(pacman);
			world.addActor(pacman);
		}
	}
	
	private void addVader(World world){
		VaderAI vaderAI = new VaderAI();
		for(int i = 0; i < INITIAL_VADER; i++){
			Location loc = Util.getRandomEmptyLocation(world);
			DarthVader darthVader = new DarthVader(vaderAI, loc);
			world.addItem(darthVader);
			world.addActor(darthVader);
		}
	}
}