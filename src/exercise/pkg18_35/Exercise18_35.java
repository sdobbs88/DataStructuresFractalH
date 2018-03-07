
/**
 * Class: CSCI 2410 Data Structures and Algorithms 
 * Instructor: Y. Daniel Liang
 * Description: Uses recursion to draw a tree with upper case H's. 
 * Due: 08/22/2016
 *
 * @author Shaun C. Dobbs
 * @version 1.0
 *
 * I pledge by honor that I have completed the programming assignment
 * independently. I have not copied the code from a student or any source. I
 * have not given my code to any student. 
 * Sign here: Shaun C. Dobbs
 */
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Exercise18_35 extends Application {

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        HPane hPane = new HPane();

        Button btUp = new Button("+");
        Button btDown = new Button("-");

        btUp.setOnAction(e -> {
            hPane.setOrder(1);

            if (hPane.getOrder() > 0) {
                btDown.setDisable(false);
            } else if (hPane.getOrder() <= 0) {
                btDown.setDisable(true);
            }

            if (hPane.getOrder() >= 8) {
                btUp.setDisable(true);
            } else {
                btUp.setDisable(false);
            }
        });

        //Down/decrease button initially disabled.
        //Because the program starts at the triangle being 0.
        btDown.setDisable(true);

        btDown.setOnAction(e -> {
            hPane.setOrder(-1);
            //Add if statement to enable button if !=0

            if (hPane.getOrder() > 0) {
                btDown.setDisable(false);
            } else if (hPane.getOrder() <= 0) {
                btDown.setDisable(true);
            }

            if (hPane.getOrder() >= 8) {
                btUp.setDisable(true);
            } else {
                btUp.setDisable(false);
            }
        });

        // Pane to hold graphic and buttons
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btUp, btDown);
        hBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(hPane);
        borderPane.setBottom(hBox);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 200, 210);
        primaryStage.setTitle("Fractal H's"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        hPane.widthProperty().addListener(e -> hPane.paint());
        hPane.heightProperty().addListener(e -> hPane.paint());
        hPane.paint();
    }

    /**
     * Pane for displaying Fractal H's
     */
    static class HPane extends Pane {

        private int order = 0;

        /**
         * Set a new order
         */
        public void setOrder(int order) {
            //Now += order since it changes everytime the up or down button is pressed
            this.order += order;
            paint();
        }

        public int getOrder() {
            return order;
        }

        HPane() {
        }

        protected void paint() {
            // Select a point and length in proportion to the panel size

            Point2D p = new Point2D(getWidth() / 2, getHeight() / 2);
            double length = (Math.min(getWidth(), getHeight())) / 4;

            this.getChildren().clear(); // Clear the pane before redisplay

            displayHTree(order, p, length);
        }

        private void displayHTree(int order, Point2D p, double length) {

            Line line = new Line((p.getX() - length), p.getY(), (p.getX() + length), p.getY());
            Line lineLeft = new Line((p.getX() - length), p.getY() + length, (p.getX() - length), p.getY() - length);
            Line lineRight = new Line((p.getX() + length), p.getY() + length, (p.getX() + length), p.getY() - length);

            if (order == 0) {
                this.getChildren().addAll(line, lineLeft, lineRight);
            } else {
                //Get the endpoint of each veritcal piece

                double newLength = length / 2;

                Point2D pTL = new Point2D(lineLeft.getEndX(), lineLeft.getEndY());
                Point2D pBL = new Point2D(lineLeft.getStartX(), lineLeft.getStartY());
                Point2D pTR = new Point2D(lineRight.getEndX(), lineRight.getEndY());
                Point2D pBR = new Point2D(lineRight.getStartX(), lineRight.getStartY());
                
                this.getChildren().addAll(line, lineLeft, lineRight);

                // Recursively display the new H pieces
                displayHTree(order - 1, pTL, newLength);
                displayHTree(order - 1, pBL, newLength);
                displayHTree(order - 1, pTR, newLength);
                displayHTree(order - 1, pBR, newLength);

            }
        }
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
