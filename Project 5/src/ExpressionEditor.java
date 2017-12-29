import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;

public class ExpressionEditor extends Application {

    /**
     * Root expression as parsed from user input
     */
    static Expression rootExpression;
    /**
     * Currently focused expression (root or sub of root)
     */
    static Expression focusedExpression;
    /**
     * Pane containing expression Nodes
     */
    static Pane expressionPane = new Pane();

    /**
     * Ghost node (ghostExpression's getNode() ) for dragging
     */
    static Node ghostNode;
    /**
     * deepCopy of focusedExpression for dragging
     */
    static Expression ghostExpression;

    /**
     * Launches JavaFX App
     * @param args Command line args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Size of the GUI
     */
    private static final int WINDOW_WIDTH = 1080, WINDOW_HEIGHT = 250;

    /**
     * Initial expression shown in the textbox
     */
    private static final String EXAMPLE_EXPRESSION = "2*x+3*y+4*z+(7*6+z)";

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
                expressionPane.setStyle("-fx-font:36 \"Consolas\";");
                node.setLayoutX(WINDOW_WIDTH / 4);
                node.setLayoutY(WINDOW_HEIGHT / 4);
                node.applyCss();
                node.toFront();
                expressionPane.getChildren().add(node);
                // If the parsed expression is a CompoundExpression, then register some callbacks
                if (expression instanceof CompoundExpression) {
                    ((Pane) expression.getNode()).setBorder(Expression.NO_BORDER);
                    final MouseEventHandler eventHandler = new MouseEventHandler((CompoundExpression) expression);
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

        MouseEventHandler(CompoundExpression rootExpression_) {
            rootExpression = rootExpression_;
            focusedExpression = rootExpression;

        }

        public void handle(MouseEvent event) {
            Point2D mouseCoords = new Point2D(event.getX(), event.getY());

            // If mouse release and wasn't just dragging, handle focus change
            if (event.getEventType() == MouseEvent.MOUSE_RELEASED && ghostExpression == null) {
                // If currently focused on a LiteralExpression, focus will be reset
                if(!(focusedExpression instanceof AbstractCompoundExpression)) {
                    focusedExpression.setFocused(false);
                    ((HBox) focusedExpression.getNode()).setBorder(Expression.NO_BORDER);
                    focusedExpression = rootExpression;
                    rootExpression.setFocused(true);
                    return;
                }

                // Create list of expressions to check for click (from focus up in tree)
                LinkedList<Expression> toCheck = new LinkedList<>();
                {
                    Expression cursor = focusedExpression;
                    while(cursor != null) {
                        toCheck.addAll(((AbstractCompoundExpression) cursor).children);
                        cursor = focusedExpression.getParent();
                    }
                }

                // Check what expression was clicked
                for (Expression e : toCheck) {
                    // Get bounds of expression node
                    Bounds b = e.getNode().localToScene(e.getNode().getBoundsInLocal());

                    // If mouse click within bounds of node
                    if (b.contains(mouseCoords.getX()+0,
                            mouseCoords.getY()+25)) {
                        // If focused node is clicked (from deepest level up), reset focus and return
                        if(e.isFocused()) {
                            e.setFocused(false);
                            ((HBox) e.getNode()).setBorder(Expression.NO_BORDER);
                            focusedExpression = rootExpression;
                            rootExpression.setFocused(true);
                            return;
                        }

                        // Otherwise we want to focus this node
                        focusedExpression.setFocused(false);
                        ((HBox) focusedExpression.getNode()).setBorder(Expression.NO_BORDER);

                        e.setFocused(true);
                        ((HBox) e.getNode()).setBorder(Expression.BLUE_BORDER);
                        focusedExpression = e;
                        return;
                    } else {
                        // If this node is not focused and shouldn't be, make sure it doesn't appear as such
                        e.setFocused(false);
                        ((HBox) e.getNode()).setBorder(Expression.NO_BORDER);
                    }
                }
                // If no node was clicked, reset focus
                focusedExpression.setFocused(false);
                ((HBox) focusedExpression.getNode()).setBorder(Expression.NO_BORDER);
                focusedExpression = rootExpression;
                rootExpression.setFocused(true);

            // If drag, handle movement
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                // Create the ghost node if this is the first drag handle
                if(ghostExpression == null) {
                    ghostExpression = focusedExpression.deepCopy();
                    ghostNode = ghostExpression.getNode(true);
                    expressionPane.getChildren().add(ghostNode);
                }
                // Move ghost node to mouse location
                ghostNode.relocate(mouseCoords.getX(), mouseCoords.getY());
            // If released and just dragging, handle drop
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                // On drop, get rid of the ghost node
                expressionPane.getChildren().remove(ghostNode);
                ghostNode = null;
                ghostExpression = null;
            }
        }
    }
}
