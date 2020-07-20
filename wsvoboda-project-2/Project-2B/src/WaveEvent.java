/**
 * An event in a Wave.
 */
public abstract class WaveEvent {

    private static final int MILLISECONDS_PER_SECOND = 1000;
    private final String eventType;
    private final int waveID;
    private final int delay;
    protected double frameCount;
    protected boolean finished;

    /**
     * Create a new Wave Event.
     *
     * @param eventType String representing the type of event
     * @param waveID The particular Wave the event corresponds to
     * @param delay The delay in milliseconds for event behavior
     * @param frameCount The starting frame count to use for timing
     */
    public WaveEvent(String eventType, int waveID, int delay, double frameCount) {
        this.eventType = eventType;
        this.waveID = waveID;
        this.delay = delay;
        this.frameCount = frameCount;
        finished = false;
    }

    /**
     * Update the current state of the Wave Event.
     */
    public void update() {
        if (finished) {
            return;
        }
        // Increase the frame counter by the current timescale
        frameCount += ShadowDefend.getTimescale();
        // Check if it is time to activate the Wave Event's behavior
        if (frameCount / ShadowDefend.getFPS() * MILLISECONDS_PER_SECOND >= delay)
            activateBehavior();
    }

    /* Trigger the behavior associated with the Wave Event. */
    protected abstract void activateBehavior();

    /**
     * Return if the Wave Event has ended.
     *
     * @return Boolean representing if the Wave Event is finished
     */
    public boolean isFinished() {
        return finished;
    }
}
