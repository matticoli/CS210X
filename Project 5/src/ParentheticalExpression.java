import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ParentheticalExpression extends AbstractCompoundExpression {
    boolean focused;

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
        final CompoundExpression e = new ParentheticalExpression();
        deepCopyChildren(e);
        return e;
    }

    @Override
    public Node getNode() {
        HBox boxyMcBoxFace = new HBox();
        boxyMcBoxFace.getChildren().add(new Label("("));
        for(Expression e : children) {
            boxyMcBoxFace.getChildren().add(e.getNode());

        }
        boxyMcBoxFace.getChildren().add(new Label(")"));
        return boxyMcBoxFace;
    }

    @Override
    public String convertToString(int indentLevel) {
        String s = getIndentString(indentLevel)+"()\n";
        return s+super.convertToString(indentLevel);
    }

}
