import java.util.ArrayList;

/**
 * A wave in the game.
 */
public class Wave {

    private static final int COMPLETION_BONUS = 150;
    private static final int BONUS_MULTIPLIER = 100;
    private final ShadowDefend game;
    private final ArrayList<WaveEvent> waveEvents;
    private int waveID;
    private int currentEventIndex;
    private boolean started;
    private boolean finished;

    /**
     * Create a new Wave.
     *
     * @param game The instance of the game associated with this Wave
     * @param waveID Int representing the number of the Wave
     */
    public Wave(ShadowDefend game, int waveID) {
        this.game = game;
        this.waveEvents = new ArrayList<>();
        this.waveID = waveID;
        this.currentEventIndex = 0;
        this.started = false;
        this.finished = false;
    }

    /**
     * Add a new Wave Event to the Wave.
     *
     * @param newEvent The Wave Event to be added
     */
    public void addWaveEvent(WaveEvent newEvent) {
        waveEvents.add(newEvent);
    }

    /**
     * Update the current state of the Wave.
     */
    public void update() {
        if (!started || finished) {
            return;
        }
        // The Wave is finished when the last Wave Event finishes
        if (currentEventIndex == waveEvents.size() - 1 && waveEvents.get(currentEventIndex).isFinished()) {
            if (!game.slicersInPlay()) {
                finished = true;
                game.addMoney(COMPLETION_BONUS + waveID * BONUS_MULTIPLIER);
                return;
            }
        }
        else if (waveEvents.get(currentEventIndex).isFinished()) {
            // If the current Wave Event is finished move to the next one
            currentEventIndex++;
        }
        waveEvents.get(currentEventIndex).update(); // Update the current Wave Event
    }

    /**
     * Start the Wave.
     */
    public void start() {
        started = true;
    }

    /**
     * Return the number of the Wave.
     *
     * @return Int representing the Wave number
     */
    public int getWaveID() {
        return waveID;
    }

    /**
     * Return if the Wave has been completed.
     *
     * @return Boolean representing if the Wave is finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Return if the Wave has started yet.
     *
     * @return Boolean representing if the Wave has started.
     */
    public boolean hasStarted() {
        return started;
    }
}
