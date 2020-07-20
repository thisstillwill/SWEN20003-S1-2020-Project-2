import bagel.util.Point;

import java.util.List;

/**
 * An apex slicer.
 */
public class ApexSlicer extends Slicer {

    private static final String IMAGE_FILE = "res/images/apexslicer.png";
    private static final double SPEED = 0.75;
    private static final int HEALTH = 25;
    private static final int REWARD = 150;
    private static final int PENALTY = 16;
    private static final int CHILDREN = 4;


    /**
     * Create a new Apex Slicer.
     *
     * @param game The instance of the game associated with this Slicer
     * @param polyline The polyline that the Slicer must traverse (must have at least 1 point)
     */
    public ApexSlicer(ShadowDefend game, List<Point> polyline) {
        super(game, polyline, IMAGE_FILE, SPEED, HEALTH, REWARD, PENALTY);
    }

    /**
     * Spawn each of the Apex Slicer's children at its current position.
     */
    @Override
    protected void deathEffect() {
        for (int i = 0; i < CHILDREN; i++) {
            slicerFactory.getSlicer(polyline, "megaslicer").setPosition(getCenter(), targetPointIndex);
        }
    }
}