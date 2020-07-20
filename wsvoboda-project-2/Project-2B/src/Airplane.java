import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * An airplane, a type of passive tower.
 */
public class Airplane extends GameObject {

    private static final String IMAGE_FILE = "res/images/airsupport.png";
    private static final int SPAWN_OFFSET = 32;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final double SPEED = 3;
    private static boolean spawnHorizontal = true;
    private final ShadowDefend game;
    private final Random rand;
    private Vector2 movementVector;
    private double frameCount;
    private int droppedExplosives;
    private int dropTime;
    private boolean startedDropping;

    /**
     * Create a new Airplane.
     *
     * @param game The instance of the game associated with this Airplane
     * @param position The position to initially place the Airplane
     */
    public Airplane(ShadowDefend game, Point position) {
        super(position, IMAGE_FILE);
        this.game = game;
        // If the Airplane is to spawn horizontally it will stay at the same y-coordinate as where it was placed
        if (spawnHorizontal) {
            movementVector = new Vector2(1, 0);
            super.setPosition(new Point(-SPAWN_OFFSET, position.y));
            super.setAngle(Math.PI / 2);
            spawnHorizontal = false;
        }
        else {
            // If the Airplane is to spawn vertically it will stay at the same x-coordinate as where it was placed
            movementVector = new Vector2(0, 1);
            super.setPosition(new Point(position.x, -SPAWN_OFFSET));
            super.setAngle(Math.PI);
            spawnHorizontal = true;
        }
        this.rand = new Random();
        this.droppedExplosives = 0;
        this.dropTime = getDropTime();
        this.frameCount = 0;
        this.startedDropping = false;
        this.game.attach(this);
    }

    /* Calculate a random drop time between 1 and 2 seconds (inclusive). */
    private int getDropTime() {
        return rand.nextInt(2000 - 1000 + 1) + 1000;
    }

    /**
     * Update the state of the Airplane. The Airplane travels straight until it leaves the edge of the game window.
     * The Airplane chooses a random drop time between 1-2 seconds (inclusive) and drops an Explosive when this time is
     * reached. The Airplane is only removed from the game once all its dropped explosives have detonated and it has
     * left the window. The Airplane cannot drop any explosives when it is outside the map.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        // Remove the Airplane when it is outside the map and each dropped Explosive has detonated
        if (outsideMap(getCenter()) && droppedExplosives == 0 && startedDropping) {
            this.game.detach(this);
            return;
        }
        // Increase the frame counter by the current timescale
        frameCount += ShadowDefend.getTimescale();
        // If the Airplane is able to, drop an explosive
        if (frameCount / ShadowDefend.getFPS() * MILLISECONDS_PER_SECOND >= dropTime && !outsideMap(getCenter())) {
            new Explosive(game, getCenter(), this);
            droppedExplosives++;
            frameCount = 0;
            dropTime = getDropTime();
            startedDropping = true;
        }
        // Move at the specified speed
        // We do this by taking the Airplane's movement vector, and multiplying it
        // by its speed (accounting for the timescale)
        super.move(movementVector.mul(SPEED * ShadowDefend.getTimescale()));
        super.update(input);
    }

    /**
     * Decrease the number of the Airplane's tracked Explosives by one.
     */
    public void reduceDroppedExplosives() {
        droppedExplosives--;
    }

    /**
     * Reset the direction the Airplane will fly. Intended for use during Level transitions.
     */
    public static void reset() {
        spawnHorizontal = true;
    }

    /* Return if the Airplane is outside of the game window. */
    private boolean outsideMap(Point position) {
        return (position.x < 0 || position.x > ShadowDefend.getWidth() || position.y < 0
                || position.y > ShadowDefend.getHeight());
    }
}
