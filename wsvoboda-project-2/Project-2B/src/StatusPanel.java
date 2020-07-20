import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.EnumMap;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * The status panel.
 */
public class StatusPanel {

    private static StatusPanel _instance = null;

    // The panel background
    private static final Image background = new Image("res/images/statuspanel.png");
    // The font used for drawing panel information
    private static final Font font = new Font("res/fonts/DejaVuSans-Bold.ttf", 16);
    private static final DrawOptions GREEN = new DrawOptions().setBlendColour(Colour.GREEN);
    private static final double Y_POSITION = ShadowDefend.getHeight() - background.getHeight();
    private static final double FONT_Y_POSITION = 760;
    private final ShadowDefend game;
    private final Rectangle rect;

    /**
     * Each possible status of the game and their relative priorities.
     */
    public enum Status {
        WINNER,
        PLACING,
        WAVE_IN_PROGRESS,
        AWAITING_START
    }

    // Mapping of each status to string
    private EnumMap<Status, String> statusMap;
    // Queue to track statuses
    private PriorityQueue<Status> statusQueue;

    /* Instantiate the StatusPanel. */
    private StatusPanel(ShadowDefend game) {
        this.game = game;
        this.rect = background.getBoundingBox();
        this.rect.moveTo(new Point(0, Y_POSITION));
        this.statusMap = new EnumMap<>(Status.class);
        this.statusMap.put(Status.WINNER, "Winner!");
        this.statusMap.put(Status.PLACING, "Placing");
        this.statusMap.put(Status.WAVE_IN_PROGRESS, "Wave In Progress");
        this.statusMap.put(Status.AWAITING_START, "Awaiting Start");
        this.statusQueue = new PriorityQueue<>();
        this.statusQueue.add(Status.AWAITING_START);
    }

    /**
     * Instantiate the StatusPanel if necessary and return it.
     *
     * @param game The instance of the game associated with the Status Panel
     * @return The instance of the StatusPanel
     */
    public static StatusPanel getInstance(ShadowDefend game) {
        if (_instance == null)
            _instance = new StatusPanel(game);
        return _instance;
    }

    /**
     * Set a new game status.
     *
     * @param status The Status code to add
     */
    public void setStatus (Status status) {
        if (!statusQueue.contains(status))
            statusQueue.add(status);
    }

    /**
     * Remove a status from the Status Panel.
     *
     * @param status The Status to be removed
     */
    public void removeStatus(Status status) {
        statusQueue.remove(status);
    }

    /**
     * Reset the panel by removing all statuses unless a tower is currently being placed.
     */
    public void reset() {
        statusQueue.removeAll(statusQueue.stream().filter(s -> s != Status.PLACING).collect(Collectors.toList()));
        statusQueue.add(Status.AWAITING_START);
    }

    /**
     * Draw the StatusPanel.
     */
    public void update(){
        // Get the current timescale
        int currentTimescale = ShadowDefend.getTimescale();
        // Draw the panel background at the bottom of the game window
        background.drawFromTopLeft(0, Y_POSITION);
        // Draw the current wave number
        font.drawString(String.format("Wave: %d", game.getCurrentWaveID()), 5, FONT_Y_POSITION);
        // Draw the current timescale; if it is greater than 1 render it green
        DrawOptions drawOptions = new DrawOptions();
        if (currentTimescale > 1)
            drawOptions = GREEN;
        font.drawString(String.format("Time Scale: %d.0", currentTimescale), 220, FONT_Y_POSITION, drawOptions);
        // Draw the current game status
        font.drawString(String.format("Status: %s", statusMap.get(statusQueue.peek())), 450, FONT_Y_POSITION);
        // Draw the number of remaining lives
        font.drawString(String.format("Lives: %d", game.getLives()), 930, FONT_Y_POSITION);
    }

    /**
     * Return the bounding box of the Status Panel.
     *
     * @return Rectangle representing the panel's bounding box.
     */
    public Rectangle getRect() {
        return new Rectangle(rect);
    }
}
