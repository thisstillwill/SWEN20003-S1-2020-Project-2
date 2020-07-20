import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

/**
 * An explosive.
 */
public class Explosive extends GameObject {

    private static final String IMAGE_FILE = "res/images/explosive.png";
    private static final int ATTACK_RADIUS = 200;
    private static final int ATTACK_DAMAGE = 500;
    private static final int DELAY = 2;
    private final ShadowDefend game;
    private final Airplane airplane;
    private double frameCount;

    /**
     * Create a new Explosive.
     *
     * @param game The instance of the game associated with this Explosive
     * @param position The position to drop the Explosive
     * @param airplane The Airplane that dropped this Explosive
     */
    public Explosive(ShadowDefend game, Point position, Airplane airplane) {
        super(position, IMAGE_FILE);
        this.game = game;
        this.airplane = airplane;
        this.frameCount = 0;
        this.game.attach(this);
    }

    /**
     * An Explosive detonates after two seconds, and damages all Slicers within its attack radius before being removed
     * from the game.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        // Increase the frame counter by the current timescale
        frameCount += ShadowDefend.getTimescale();
        // When the delay is reached detonate the Explosive
        if (frameCount / ShadowDefend.getFPS() >= DELAY) {
            airplane.reduceDroppedExplosives();
            detonate();
            this.game.detach(this);
        }
        super.update(input);
    }

    /* For each Slicers in play, deal damage if it is within the Explosive's attack radius. */
    private void detonate() {
        List<Slicer> currentSlicers = game.getSlicers();

        //------------------------------------------
        // START COPIED CODE (from rohylj-project-1)
        // Vector math to calculate distance from
        // the target point to the current point
        // was adapted; Variable names changed to
        // suit context and addition of logic to
        // check attack radius and deal damage.
        //------------------------------------------

        // Obtain where we currently are
        Point currentPoint = getCenter();
        for (Slicer slicer : currentSlicers) {
            // Obtain where we are trying to target
            Point candidatePoint = slicer.getCenter();
            // Convert them to vectors to perform some very basic vector math
            Vector2 candidate = candidatePoint.asVector();
            Vector2 current = currentPoint.asVector();
            Vector2 distance = candidate.sub(current);
            // Distance we are (in pixels) away from our target point
            double magnitude = distance.length();
            // Deal damage to if distance is within radius
            if (magnitude <= ATTACK_RADIUS) {
                slicer.takeDamage(ATTACK_DAMAGE);
            }
        }
        //------------------------------------------
        // END COPIED CODE
        //------------------------------------------
    }
}
