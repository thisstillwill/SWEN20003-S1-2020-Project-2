import bagel.Font;
import bagel.Image;
import bagel.util.Rectangle;

/**
 * The buy panel.
 */
public class BuyPanel {

    private static BuyPanel _instance = null;

    // The panel background
    private static final Image background = new Image("res/images/buypanel.png");
    // The font used for drawing key bind information
    private static final Font fontKeybinds = new Font("res/fonts/DejaVuSans-Bold.ttf", 12);
    // The font used for drawing key bind information
    private static final Font fontMoney = new Font("res/fonts/DejaVuSans-Bold.ttf", 48);
    // Key bind information
    private static final String keybinds =
            "Key Binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale";
    private final ShadowDefend game;
    private final Rectangle rect;

    /* Instantiate the BuyPanel with the given instance of the game. */
    private BuyPanel(ShadowDefend game) {
        this.game = game;
         rect = background.getBoundingBox();
    }

    /**
     * Instantiate the BuyPanel if necessary and return it.
     *
     * @param game The instance of the game associated with the Status Panel
     * @return The instance of the BuyPanel
     */
    public static BuyPanel getInstance(ShadowDefend game) {
        if (_instance == null)
            _instance = new BuyPanel(game);
        return _instance;
    }

    /**
     * Draw the BuyPanel.
     */
    public void update() {
        // Draw the panel background at the top of the game window
        background.drawFromTopLeft(0, 0);
        // Draw the key bind information in the middle of the panel
        fontKeybinds.drawString(keybinds, 450, 20);
        // Draw the player's money
        fontMoney.drawString(String.format("$%d", game.getMoney()), ShadowDefend.getWidth() - 200, 65);
    }

    /**
     * Return the bounding box of the Buy Panel.
     *
     * @return Rectangle representing the panel's bounding box.
     */
    public Rectangle getRect() {
        return new Rectangle(rect);
    }

    /**
     * Return the height of the Buy Panel.
     *
     * @return Double representing the height of the panel in pixels
     */
    public static double getHeight() {
        return background.getHeight();
    }
}
