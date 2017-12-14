import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExpressionEditor extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    static AbstractCompoundExpression expression;
    static Pane expressionPane = new Pane();

    /**
     * Size of the GUI
     */
    private static final int WINDOW_WIDTH = 1080, WINDOW_HEIGHT = 250;
    /**
     * Initial expression shown in the textbox
     */
    private static final String EXAMPLE_EXPRESSION = "2*x+3*y+4*z+(7+6*z)";
    /**
     * Parser used for parsing expressions.
     */
    private final ExpressionParser expressionParser = new SimpleExpressionParser();
    //private static final String EXAMPLE_EXPRESSION = "a+b+c";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expression Editor");
        // Add the textbox and Parser button
        final Pane queryPane = new HBox();
        final TextField textField = new TextField(EXAMPLE_EXPRESSION);
        final Button button = new Button("Parse");
        queryPane.getChildren().add(textField);
        // Add the callback to handle when the Parse button is pressed
        button.setOnMouseClicked(e -> {
            // Try to parse the expression
            try {
                // Success! Add the expression's Node to the expressionPane
                final Expression expression = expressionParser.parse(textField.getText(), true);
                expressionPane.getChildren().clear();
                Node node = expression.getNode();
                expressionPane.setStyle("-fx-font:36 \"Arial\";");
                node.setLayoutX(WINDOW_WIDTH / 4);
                node.setLayoutY(WINDOW_HEIGHT / 4);
                node.applyCss();
                node.toFront();
                expressionPane.getChildren().add(node);
                // If the parsed expression is a CompoundExpression, then register some callbacks
                if (expression instanceof CompoundExpression) {
                    ((Pane) expression.getNode()).setBorder(Expression.NO_BORDER);
                    final MouseEventHandler eventHandler = new MouseEventHandler(expressionPane, (CompoundExpression) expression);
                    expressionPane.setOnMousePressed(eventHandler);
                    expressionPane.setOnMouseDragged(eventHandler);
                    expressionPane.setOnMouseReleased(eventHandler);
                }
            } catch (ExpressionParseException epe) {
                // If we can't parse the expression, then mark it in red
                textField.setStyle("-fx-text-fill: red");
            }
        });
        queryPane.getChildren().add(button);
        // Reset the color to black whenever the user presses a key
        textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));
        final BorderPane root = new BorderPane();
        root.setTop(queryPane);
        root.setCenter(expressionPane);
        expressionPane.applyCss();
        expressionPane.layout();
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }

    /**
     * Mouse event handler for the entire pane that constitutes the ExpressionEditor
     */
    private static class MouseEventHandler implements EventHandler<MouseEvent> {

        MouseEventHandler(Pane pane_, CompoundExpression rootExpression_) {
            expression = (AbstractCompoundExpression) rootExpression_;

        }

        public void handle(MouseEvent event) {
            //System.out.println(rootExp.convertToString(0));
            /**
             * Current Issues:
             * No repaints. or whatever javafx does
             * No focus change on mouse click due to bounding box issues. 
             */
            expressionPane.requestLayout();
            Point2D mouseCoords = new Point2D(event.getX(), event.getY());
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("Mouse Event at " + mouseCoords.toString());
                for (Expression e : expression.children) {
                    expressionPane.layout();
                    System.out.println(e.getNode().getBoundsInParent());
                    if (e.getNode().contains(mouseCoords)) {
                        e.setFocused(true);
                        e.getNode().setStyle("-fx-font:36 \"Comic Sans MS\";");
                        e.getNode().setStyle("-fx-fill: RED");
                    } else {
                        e.setFocused(false);
                        e.getNode().setStyle("-fx-font:36 \"Times\";");
                        e.getNode().setStyle("-fx-fill: BLACK");
                    }
                }
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            }
            System.out.println(expression.getNode().getBoundsInParent());
        }
    }
}
