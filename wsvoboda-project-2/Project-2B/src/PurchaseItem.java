import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Colour;
import bagel.util.Point;

/**
 * A purchase item.
 */
public abstract class PurchaseItem extends GameObject {

    private static final Font fontPrice = new Font("res/fonts/DejaVuSans-Bold.ttf", 22);
    private static final DrawOptions GREEN = new DrawOptions().setBlendColour(Colour.GREEN);
    private static final DrawOptions RED = new DrawOptions().setBlendColour(Colour.RED);
    private static final int FONT_X_OFFSET = 32;
    private static final int FONT_Y_OFFSET = 50;
    private static PurchaseItem selectedItem = null;
    private static boolean placing = false;
    protected static double Y_POSITION = BuyPanel.getHeight() / 2 - 10;
    protected static final int INITIAL_OFFSET = 64;
    protected static int ITEM_OFFSET = 120;
    private final Image sprite;
    private final int price;
    protected final ShadowDefend game;

    /**
     * Create a new Purchase Item.
     *
     * @param position The Purchase Item's position
     * @param imageFile The Purchase Item's sprite
     * @param price The price of the tower
     */
    public PurchaseItem(ShadowDefend game, Point position, String imageFile, int price) {
        super(position, imageFile);
        this.game = game;
        this.sprite = new Image(imageFile);
        this.price = price;
    }

    /**
     * If a panel item is left clicked (and the player has the funds to purchase it), a copy of the panel
     * item image is rendered at the user’s cursor so that they can have a visual indicator of where
     * the tower is to be placed. If the tower cannot be placed where the user’s cursor is, then the visual indicator
     * is not be rendered when hovering over that spot. Clicking again while a panel item is selected creates
     * a new tower at the chosen location and deducts the cost of the tower from the player’s money. Right clicking
     * while a purchase item is selected should de-select the tower.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        super.update(input);

        DrawOptions drawOptions;
        if (game.getMoney() >= price) {
            drawOptions = GREEN;
        }
        else drawOptions = RED;
        fontPrice.drawString(String.format("$%d", price), getCenter().x - FONT_X_OFFSET,
                getCenter().y + FONT_Y_OFFSET, drawOptions);

        Point mousePosition = input.getMousePosition();
        // If the player first left clicks the Purchase Item enable tower placement if they have sufficient funds
        if (!placing && input.wasPressed(MouseButtons.LEFT) && getRect().intersects(mousePosition)
                && game.getMoney() >= price) {
            placing = true;
            selectedItem = this;
            game.getStatusPanel().setStatus(StatusPanel.Status.PLACING);
        }
        else if (placing) {
            // Do not allow invalid placement or placement of an unselected Purchase Item
            if (!canPlace(mousePosition) || selectedItem != this) return;

            // Draw the Image of the placing Purchase Item
            sprite.draw(mousePosition.x, mousePosition.y);

            // If the player left clicks again place a new tower
            if (input.wasPressed(MouseButtons.LEFT)) {
                placing = false;
                game.purchase(price);
                placeTower(game, mousePosition);
                selectedItem = null;
                game.getStatusPanel().removeStatus(StatusPanel.Status.PLACING);
            }
            else if (input.wasPressed(MouseButtons.RIGHT)) {
                // If the player clicks the middle mouse button deselect the tower
                placing = false;
                game.getStatusPanel().removeStatus(StatusPanel.Status.PLACING);
            }
        }
    }

    /* Check if a tower can be placed at the given position. By default, a tower cannot be placed on a coordinate if the
    center of the tower to place intersects with a panel, the bounding box of another tower, or with a blocked tile. */
    protected boolean canPlace(Point position) {
        if (game.intersectsPanels(position)) return false;
        else if (game.getActiveTowers().stream().anyMatch(o -> o.getRect().intersects(position))) return false;
        else if (position.x < 0 || position.x > ShadowDefend.getWidth() || position.y < 0
                || position.y > ShadowDefend.getHeight()) return false;
        else return !game.getCurrentMap().hasProperty((int) position.x, (int) position.y, "blocked");
    }

    /* Place a tower at the specified position. */
    protected abstract void placeTower(ShadowDefend game, Point position);

    /**
     * Cancels any item placement.
     */
    public static void reset() {
        placing = false;
        selectedItem = null;
    }
}
