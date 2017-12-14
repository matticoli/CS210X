import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LiteralExpression implements Expression {

    /**
     * Parent expression in the expression tree
     */
    protected CompoundExpression parent;
    /**
     * The numerical value that the LiteralExpression represents
     */
    protected String value;
    boolean focused = false;
    private HBox node;


    /**
     * Creates new LiteralExpression with given int value
     *
     * @param value The numerical value that the LiteralExpression represents
     */
    LiteralExpression(String value) {
        this.value = value;
    }

    @Override
    public CompoundExpression getParent() {
        return parent;
    }

    @Override
    public void setParent(CompoundExpression parent) {
        this.parent = parent;
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public Expression deepCopy() {
        return new LiteralExpression(value);
    }

    @Override
    public Node getNode() {
        return getNode(false);
    }

    public Node getNode(boolean ghost) {
        if(node == null) {
            HBox box = new HBox();
            Label l = new Label(value);
            if(ghost) {
                l.setOpacity(0.5);
            }
            box.getChildren().add(l);
            node = box;
        }
        return node;
    }

    @Override
    public void flatten() {
        //literal is already flat
    }

    @Override
    public String convertToString(int indentLevel) {
        StringBuffer s = new StringBuffer();
        Expression.indent(s, indentLevel);
        return s.append(value).append("\n").toString();
    }
}
