import bagel.util.Point;

import java.util.List;

/**
 * A regular slicer.
 */
public class RegularSlicer extends Slicer {

    private static final String IMAGE_FILE = "res/images/slicer.png";
    private static final double SPEED = 2;
    private static final int HEALTH = 1;
    private static final int REWARD = 2;
    private static final int PENALTY = 1;

    /**
     * Create a new Regular Slicer.
     *
     * @param game The instance of the game associated with this Slicer
     * @param polyline The polyline that the Slicer must traverse (must have at least 1 point)
     */
    public RegularSlicer(ShadowDefend game, List<Point> polyline) {
        super(game, polyline, IMAGE_FILE, SPEED, HEALTH, REWARD, PENALTY);
    }

    /* A regular slicer has no death effect. */
    @Override
    protected void deathEffect() {
        return;
    }
}
