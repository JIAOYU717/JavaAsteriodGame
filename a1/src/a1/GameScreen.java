package a1;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class GameScreen {
    private Pane gameLayout;
    private Polygon ship;
    private Stage primaryStage;

    public GameScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initialize();
    }

    private void initialize() {
        gameLayout = new Pane();
        ship = createShip();

        gameLayout.getChildren().add(ship);
        Scene gameScene = new Scene(gameLayout, 800, 600);

        gameScene.setOnKeyPressed(this::handleKeyPress);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Asteroids Game");
        primaryStage.show();
    }

    private Polygon createShip() {
        Polygon ship = new Polygon(0, -10, -10, 10, 10, 10);
        ship.setLayoutX(400);
        ship.setLayoutY(300);
        return ship;
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                ship.getTransforms().add(new Rotate(5, 0, 0));
                break;
            case LEFT:
                ship.getTransforms().add(new Rotate(-5, 0, 0));
                break;
            case UP:
                // Apply thrust
                break;
            case SPACE:
                // Fire a bullet
                break;
            case H:
                // Hyperspace jump
                break;
            default:
                break;
        }
    }
}
