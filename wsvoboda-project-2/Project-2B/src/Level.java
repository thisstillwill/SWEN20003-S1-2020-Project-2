import bagel.Input;
import bagel.Keys;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * A level in the game.
 */
public class Level {

    private final ShadowDefend game;
    private final TiledMap map;
    private final List<Point> polyline;
    private final ArrayList<Wave> waves;
    private int currentWaveIndex;
    public boolean finished;

    /**
     * Create a new Level.
     *
     * @param game The instance of the game associated with this Level
     * @param mapFile The file name used by the Level
     */
    public Level(ShadowDefend game, String mapFile) {
        this.game = game;
        this.map = new TiledMap(mapFile);
        this.polyline = map.getAllPolylines().get(0);
        this.waves = new ArrayList<>();
        this.currentWaveIndex = 0;
        this.finished = false;
    }

    /**
     * Update the current state of the Level.
     *
     * @param input The current mouse/keyboard state
     */
    public void update(Input input) {
        if (finished) {
            return;
        }
        // The Level is finished when all Waves have finished
        if (currentWaveIndex == waves.size() - 1 && waves.get(currentWaveIndex).isFinished()) {
            finished = true;
            return;
        }
        else if (waves.get(currentWaveIndex).isFinished()) {
            // If the current Wave is finished move to the next one
            currentWaveIndex++;
            game.getStatusPanel().reset(); // Reset the status panel after each Wave
        }
        if (input.wasPressed(Keys.S) && !waves.get(currentWaveIndex).hasStarted()) {
            // Start the current Wave
            waves.get(currentWaveIndex).start();
            game.getStatusPanel().setStatus(StatusPanel.Status.WAVE_IN_PROGRESS);
        }
        game.setCurrentWaveID(waves.get(currentWaveIndex).getWaveID());
        waves.get(currentWaveIndex).update(); // Update the current Wave
    }

    /**
     * Add a Wave to the Level.
     *
     * @param newWave The Wave to be added
     */
    public void addWave(Wave newWave) {
        waves.add(newWave);
    }

    /**
     * Return the TiledMap used for the Level.
     *
     * @return TiledMap representing the map used for the Level
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Return the polyline used for the Level.
     *
     * @return List of Points representing the Level's polyline
     */
    public List<Point> getPolyline() {
        return polyline;
    }

    /**
     * Return if the Level is finished.
     *
     * @return Boolean representing the Level state
     */
    public boolean isFinished() {
        return finished;
    }
}
