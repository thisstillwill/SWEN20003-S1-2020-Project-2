/**
 * Generates Wave Events based on given information.
 */
public class WaveEventFactory {

    private final ShadowDefend game;

    /**
     * Create a new Wave Event Factory.
     *
     * @param game The instance of the game associated with this Wave Event Factory
     */
    public WaveEventFactory(ShadowDefend game) {
        this.game = game;
    }

    /**
     * Create a new Wave Event based on the given event type.
     *
     * @param eventInfo An array of Strings containing a particular Wave Event's information
     * @return The new Wave Event
     */
    public WaveEvent getWaveEvent(String[] eventInfo) {
        if (eventInfo == null)
            return null;
        if (eventInfo[1].equalsIgnoreCase("delay"))
            return new DelayEvent(eventInfo);
        else if (eventInfo[1].equalsIgnoreCase("spawn"))
            return new SpawnEvent(game, eventInfo);
        else return null;
    }
}
