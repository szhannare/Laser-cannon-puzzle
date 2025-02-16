package laserpuzzle.play.application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * Object to store the variables needed by the methods of {@code GameController}.
 */
public class CannonVariables {
    private boolean isCannon;
    private Circle empty;
    private StackPane square;
    private ImageView cannon;
    private Image activeCannon;
    private Image inactiveCannon;
    private int[] evenCannons;
    private int[] oddCannons;

    public CannonVariables(boolean isCannon, Circle empty, StackPane square, ImageView cannon, Image activeCannon, Image inactiveCannon, int[] evenCannons, int[] oddCannons) {
        this.isCannon = isCannon;
        this.empty = empty;
        this.square = square;
        this.cannon = cannon;
        this.activeCannon = activeCannon;
        this.inactiveCannon = inactiveCannon;
        this.evenCannons=evenCannons;
        this.oddCannons=oddCannons;
    }

    public void setIsCannon(boolean cannon) {
        isCannon = cannon;
    }

    public boolean isCannon() {
        return isCannon;
    }

    public Circle getEmpty() {
        return empty;
    }

    public StackPane getSquare() {
        return square;
    }

    public ImageView getCannon() {
        return cannon;
    }

    public Image getActiveCannon() {
        return activeCannon;
    }

    public Image getInactiveCannon() {
        return inactiveCannon;
    }

    public int[] getEvenCannons() {
        return evenCannons;
    }

    public int[] getOddCannons() {
        return oddCannons;
    }
}
