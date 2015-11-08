package ca.ubc.ece.cpen221.mp4.items.VideoGameHeroes;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Pacman implements ArenaHero {
	private static final int INITIAL_ENERGY = 25;
	private static final int MAX_ENERGY = 30;
	private static final int STRENGTH = 1000;
	private static final int VIEW_RANGE = 1;
	private static final int MIN_BREEDING_ENERGY = 10;
	private static final int COOLDOWN = 1;
	private static ImageIcon pacmanImage = Util.loadImage("pacmanOpen.gif");

	private final AI ai;

	private Location location;
	private int energy;
	private boolean isOpen;

	/**
	 * Create a new {@link Pacman} with an {@link AI} at
	 * <code>initialLocation</code>. The <code> initialLocation </code> must be
	 * valid and empty
	 *
	 * @param charizardAI
	 *            the AI designed for pacman
	 * @param initialLocation
	 *            the location where this pacman will be created
	 */
	public Pacman(AI pacmanAI, Location initialLocation) {
		this.ai = pacmanAI;
		this.location = initialLocation;

		this.energy = INITIAL_ENERGY;
		this.isOpen = true;
	}

	public void updatePicture() {
		if (isOpen)
			pacmanImage = Util.loadImage("pacmanClose.gif");
		else
			pacmanImage = Util.loadImage("pacmanOpen.gif");
		this.isOpen = !this.isOpen;
	}

	@Override
	public int getEnergy() {
		return energy;
	}

	@Override
	public LivingItem breed() {
		Pacman child = new Pacman(ai, location);
		child.energy = energy / 2;
		this.energy = energy / 2;
		return child;
	}

	@Override
	public void eat(Food food) {
		energy = Math.min(MAX_ENERGY, energy + food.getPlantCalories() + food.getMeatCalories());
	}

	@Override
	public void moveTo(Location targetLocation) {
		location = targetLocation;
	}

	@Override
	public int getMovingRange() {
		return VIEW_RANGE;
	}

	@Override
	public ImageIcon getImage() {
		return pacmanImage;
	}

	@Override
	public String getName() {
		return "Pacman";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public void loseEnergy(int energy) {
		this.energy -= energy;
	}

	@Override
	public boolean isDead() {
		return energy <= 0;
	}

	@Override
	public int getPlantCalories() {
		return 0;
	}

	@Override
	public int getMeatCalories() {
		return energy;
	}

	@Override
	public int getCoolDownPeriod() {
		return COOLDOWN;
	}

	@Override
	public Command getNextAction(World world) {
		Command nextAction = ai.getNextAction(world, this);
		this.energy--; // Loses 1 energy regardless of action.
		updatePicture();
		return nextAction;
	}

	@Override
	public int getMaxEnergy() {
		return MAX_ENERGY;
	}

	@Override
	public int getViewRange() {
		return VIEW_RANGE;
	}

	@Override
	public int getMinimumBreedingEnergy() {
		return MIN_BREEDING_ENERGY;
	}

}