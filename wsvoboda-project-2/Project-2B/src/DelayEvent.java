/**
 * A delay event.
 */
public class DelayEvent extends WaveEvent {

    private static final double STARTING_FRAMECOUNT = 0;

    /**
     * Create a new Delay Event.
     *
     * @param eventInfo Array representing information about the event
     */
    public DelayEvent(String[] eventInfo) {
        super(eventInfo[1], Integer.parseInt(eventInfo[0]), Integer.parseInt(eventInfo[2]), STARTING_FRAMECOUNT);
    }

    /**
     * Mark the Delay Event as having finished.
     */
    @Override
    protected void activateBehavior() {
        super.finished = true;
    }
}
