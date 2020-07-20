import bagel.util.Point;

import java.util.List;

/**
 * Generates Slicers based on given information.
 */
public class SlicerFactory {

    private final ShadowDefend game;

    /**
     * Create a new Slicer Factory.
     *
     * @param game The instance of the game associated with this Slicer Factory.
     */
    public SlicerFactory(ShadowDefend game) {
        this.game = game;
    }

    /**
     * Create a new Slicer based on the given Slicer type.
     *
     * @param polyline The polyline that the Slicer must traverse (must have at least 1 point)
     * @param slicerType The type of Slicer to be generated
     * @return The new Slicer
     */
    public Slicer getSlicer(List<Point> polyline, String slicerType) {
        if (polyline == null || slicerType == null)
            return null;
        if (slicerType.equalsIgnoreCase("slicer"))
            return new RegularSlicer(game, polyline);
        else if (slicerType.equalsIgnoreCase("superslicer"))
            return new SuperSlicer(game, polyline);
        else if (slicerType.equalsIgnoreCase("megaslicer"))
            return new MegaSlicer(game, polyline);
        else if (slicerType.equalsIgnoreCase("apexslicer"))
            return new ApexSlicer(game, polyline);
        else return null;
    }
}
