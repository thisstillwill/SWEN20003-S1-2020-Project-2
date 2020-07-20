import bagel.util.Point;

import java.util.List;

/**
 * A mega slicer.
 */
public class MegaSlicer extends Slicer {

    private static final String IMAGE_FILE = "res/images/megaslicer.png";
    private static final double SPEED = 1.5;
    private static final int HEALTH = 2;
    private static final int REWARD = 10;
    private static final int PENALTY = 4;
    private static final int CHILDREN = 2;

    /**
     * Create a new Mega Slicer.
     *
     * @param game The instance of the game associated with this Slicer
     * @param polyline The polyline that the Slicer must traverse (must have at least 1 point)
     */
    public MegaSlicer(ShadowDefend game, List<Point> polyline) {
        super(game, polyline, IMAGE_FILE, SPEED, HEALTH, REWARD, PENALTY);
    }

    /**
     * Spawn each of the mega slicer's children at its current position.
     */
    @Override
    protected void deathEffect() {
        for (int i = 0; i < CHILDREN; i++) {
            slicerFactory.getSlicer(polyline, "superslicer").setPosition(getCenter(), targetPointIndex);
        }
    }
}
