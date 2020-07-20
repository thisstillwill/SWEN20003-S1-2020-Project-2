/**
 * A spawn event.
 */
public class SpawnEvent extends WaveEvent {

    private static final double STARTING_FRAMECOUNT = Integer.MAX_VALUE;
    private final ShadowDefend game;
    private final SlicerFactory slicerFactory;
    private final String slicerType;
    private final int maxSlicers;
    private int spawnedSlicers;

    /**
     * Create a new Spawn Event.
     *
     * @param game The instance of the game associated with this Spawn Event
     * @param eventInfo Array representing information about the event
     */
    public SpawnEvent(ShadowDefend game, String[] eventInfo) {
        super(eventInfo[1], Integer.parseInt(eventInfo[0]), Integer.parseInt(eventInfo[4]), STARTING_FRAMECOUNT);
        this.game = game;
        slicerFactory = new SlicerFactory(game);
        slicerType = eventInfo[3];
        maxSlicers = Integer.parseInt(eventInfo[2]);
        spawnedSlicers = 0;
    }

    /**
     * Spawn a new Slicer when the delay threshold is reached and reset the frame counter. When all Slicers are spawned,
     * mark the Spawn Event as having finished.
     */
    @Override
    protected void activateBehavior() {
        if (spawnedSlicers == maxSlicers) {
            super.finished = true;
        }
        else {
            slicerFactory.getSlicer(game.getCurrentPolyline(), slicerType);
            spawnedSlicers++;
            super.frameCount = 0;
        }
    }
}
