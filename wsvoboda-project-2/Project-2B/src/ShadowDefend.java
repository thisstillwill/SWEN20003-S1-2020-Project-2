import bagel.AbstractGame;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * ShadowDefend, a tower defence game.
 *
 * Some code adapted from Rohyl Joshi's rohylj-project-1 at:
 * https://gitlab.eng.unimelb.edu.au/swen20003-S1-2020/rohylj/rohylj-project-1.git
 *
 * The start and end of copied code is identified with inline comments.
 *
 * @author William Svoboda
 */
public class ShadowDefend extends AbstractGame {

    // The title of the game
    public static final String TITLE = "ShadowDefend";
    // The files for the game's levels
    private static final String MAP1_FILE = "res/levels/1.tmx";
    private static final String MAP2_FILE = "res/levels/2.tmx";

    //------------------------------------------
    // START COPIED CODE (from rohylj-project-1)
    //------------------------------------------
    // Change to suit system specifications. This could be
    // dynamically determined but that is out of scope.
    private static final double FPS = 60;
    //------------------------------------------
    // END COPIED CODE
    //------------------------------------------

    // The width and height of the game window
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final int STARTING_LIVES = 25;
    private static final int STARTING_MONEY = 500;

    //------------------------------------------
    // START COPIED CODE (from rohylj-project-1)
    //------------------------------------------
    private static final int INITIAL_TIMESCALE = 1;
    // Timescale is made static because it is a universal property of the game and the specification
    // says everything in the game is affected by this
    private static int timescale = INITIAL_TIMESCALE;
    //------------------------------------------
    // END COPIED CODE
    //------------------------------------------

    // All GameObjects currently in play
    private final ArrayList<GameObject> entities;
    private final ArrayList<Level> levels;
    // The game's panels
    private final StatusPanel statusPanel;
    private final BuyPanel buyPanel;
    // The game's purchasable items
    private final ArrayList<PurchaseItem> purchaseItems;
    private int livesRemaining = STARTING_LIVES;
    private int money = STARTING_MONEY;
    private int currentLevelIndex;
    private int currentWaveID;
    private boolean finished;

    /**
     * Create a new instance of the ShadowDefend game.
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, TITLE);
        entities = new ArrayList<>();
        levels = new ArrayList<>();
        levels.add(new Level(this, MAP1_FILE));
        levels.add(new Level(this, MAP2_FILE));
        statusPanel = StatusPanel.getInstance(this);
        buyPanel = BuyPanel.getInstance(this);
        purchaseItems = new ArrayList<>();
        purchaseItems.add(new TankPurchaseItem(this));
        purchaseItems.add(new SuperTankPurchaseItem(this));
        purchaseItems.add(new AirplanePurchaseItem(this));
        currentLevelIndex = 0;
        finished = false;
        processWaveInfo();
    }

    /* For each Level in the game, process waves.txt to generate each Wave and its corresponding Wave Events. */
    private void processWaveInfo() {
        WaveEventFactory waveEventFactory = new WaveEventFactory(this);

        // For each Level in the game
        for (Level level : levels) {

            // Track current wave
            int currentWaveID = 0; // Guaranteed to not match any valid wave ID
            Wave currentWave = new Wave(this, currentWaveID); // Will be reassigned on first line processed

            // Read each line of waves.txt
            try {
                Scanner scanner = new Scanner(new File("res/levels/waves.txt"));
                while (scanner.hasNextLine()) {

                    // Split each line into an array containing the corresponding Wave Event information
                    String[] eventInfo = scanner.nextLine().split(",");

                    // Determine if it is time to move to the next wave
                    if (Integer.parseInt(eventInfo[0]) != currentWaveID) {
                        currentWaveID = Integer.parseInt(eventInfo[0]);
                        currentWave = new Wave(this, currentWaveID);
                        level.addWave(currentWave);
                    }

                    // Add the Wave Event represented by the line to the current Wave
                    currentWave.addWaveEvent(waveEventFactory.getWaveEvent(eventInfo));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The entry point for the game.
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) {
        new ShadowDefend().run();
    }

    /**
     * Return the width of the game's window
     *
     * @return The game window's width
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * Return the height of the game's window
     *
     * @return The game window's height
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * Return the game's intended frames/second.
     *
     * @return A double representing FPS
     */
    public static double getFPS() {
        return FPS;
    }

    /**
     * Attach a GameObject in play to the game.
     *
     * @param entity The GameObject to register
     */
    public void attach(GameObject entity) {
        entities.add(entity);
    }

    /**
     * Detach a GameObject in play from the game.
     *
     * @param entity The observer to unregister
     */
    public void detach(GameObject entity) {
        entities.remove(entity);
    }

    /**
     * Update each GameObject in play.
     *
     * @param input The current mouse/keyboard state
     */
    public void updateEntities(Input input) {
        // Iterate through entities in reverse to avoid concurrent modification
        for (int i = entities.size() - 1; i >= 0; i--) {
            entities.get(i).update(input);
        }
    }

    /**
     * Return the game's Status Panel instance.
     *
     * @return The instance of the Status Panel
     */
    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    /**
     * Return the player's current money.
     *
     * @return Int representing the player's money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Add to the player's money.
     *
     * @param reward The amount of money to add
     */
    public void addMoney(int reward) {
        money += reward;
    }

    /**
     * Subtract from the player's money.
     *
     * @param price The amount of money to remove
     */
    public void purchase(int price) {
        money -= price;
    }

    /**
     * Return the player's remaining lives.
     *
     * @return Int representing the player's lives
     */
    public int getLives() {
        return livesRemaining;
    }

    /**
     * Remove from the player's remaining lives.
     *
     * @param penalty The amount of lives to remove
     */
    public void reduceLives(int penalty) {
        livesRemaining -= penalty;
        if (livesRemaining < 0)
            livesRemaining = 0;
    }

    /**
     * Return the current Wave number.
     *
     * @return Int representing the current Wave's number
     */
    public int getCurrentWaveID() {
        return currentWaveID;
    }

    /**
     * Set the game's current Wave number
     *
     * @param waveID The number of the current Wave
     */
    public void setCurrentWaveID(int waveID) {
        currentWaveID = waveID;
    }

    /**
     * Return a list of all Active Towers in play.
     *
     * @return List of Active Towers
     */
    public List<ActiveTower> getActiveTowers() {
        return entities.stream().filter(o -> o instanceof ActiveTower).map(o -> (ActiveTower) o)
                .collect(Collectors.toList());
    }

    /**
     * Return a list of all Slicers in play.
     *
     * @return List of Slicers
     */
    public List<Slicer> getSlicers() {
        return entities.stream().filter(o -> o instanceof Slicer).map(o -> (Slicer) o).collect(Collectors.toList());
    }

    /**
     * Return the current map polyline used in the game.
     *
     * @return List of Points representing a polyline
     */
    public List<Point> getCurrentPolyline() {
        return levels.get(currentLevelIndex).getPolyline();
    }

    /**
     * Return the current Map used in the game.
     *
     * @return TiledMap representing a map
     */
    public TiledMap getCurrentMap() {
        return levels.get(currentLevelIndex).getMap();
    }

    /**
     * Return if there are any Slicers currently in play.
     *
     * @return Boolean representing if Slicers are in play
     */
    public boolean slicersInPlay() {
        return entities.stream().anyMatch(o -> o instanceof Slicer);
    }

    /**
     * Return if a given Point intersects with the game's panels.
     *
     * @param position The Point to test for intersection
     * @return Boolean representing if the given Point intersects
     */
    public boolean intersectsPanels(Point position) {
        return (statusPanel.getRect().intersects(position) || buyPanel.getRect().intersects(position));
    }

    //------------------------------------------
    // START COPIED CODE (from rohylj-project-1)
    //------------------------------------------
    /**
     * Return the current timescale.
     *
     * @return An int representing the timescale
     */
    public static int getTimescale() {
        return timescale;
    }

    /* Increase the timescale. */
    private void increaseTimescale() {
        timescale++;
    }

    /* Decrease the timescale without going below the base timescale. */
    private void decreaseTimescale() {
        if (timescale > INITIAL_TIMESCALE) {
            timescale--;
        }
    }
    //------------------------------------------
    // END COPIED CODE
    //------------------------------------------

    /**
     * Update the state of the game, potentially reading from input.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        // Exit the game if all lives are lost
        if (livesRemaining <= 0) {
            Window.close();
        }

        // Draw map from the top left of the window
        levels.get(currentLevelIndex).getMap().draw(0,0, 0, 0, WIDTH, HEIGHT);

        if (!finished) {
            // The game is finished when all Levels have finished
            if (currentLevelIndex == levels.size() - 1 && levels.get(currentLevelIndex).isFinished()) {
                finished = true;
                statusPanel.setStatus(StatusPanel.Status.WINNER);
            } else if (levels.get(currentLevelIndex).isFinished()) {
                // If the current Level is done move to the next Level
                currentLevelIndex++;
                reset(); // Reset the state of the game
            }

            // Update the current Level
            levels.get(currentLevelIndex).update(input);
        }

        // Handle key presses
        if (input.wasPressed(Keys.L)) {
            increaseTimescale();
        }

        if (input.wasPressed(Keys.K)) {
            decreaseTimescale();
        }

        // Update all GameObjects in play
        updateEntities(input);

        // Update the user interface
        updateInterface(input);
    }

    /* Reset the game if a level is completed */
    private void reset() {
        livesRemaining = STARTING_LIVES;
        money = STARTING_MONEY;
        statusPanel.reset();
        PurchaseItem.reset(); // Prevent invalid purchases on Level transitions
        Airplane.reset();
        entities.removeAll(entities);
    }

    /* Update the game's user interface in the order they are to be rendered. */
    private void updateInterface(Input input) {
        statusPanel.update();
        buyPanel.update();
        for (PurchaseItem purchaseItem : purchaseItems) {
            purchaseItem.update(input);
        }
    }
}
