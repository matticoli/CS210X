import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MultiplicativeExpression extends AbstractCompoundExpression {
    boolean focused = false;
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
        final CompoundExpression e = new MultiplicativeExpression();
        deepCopyChildren(e);
        return e;
    }

    @Override
    public Node getNode() {
        HBox boxyMcBoxFace = new HBox();
        for(int i = 0; i<children.size(); i++) {
            boxyMcBoxFace.getChildren().add(children.get(i).getNode());
            if((i<children.size()-1)){
                boxyMcBoxFace.getChildren().add(new Label("*"));
            }
        }
        return boxyMcBoxFace;
    }


    @Override
    public String convertToString(int indentLevel) {
        String s = getIndentString(indentLevel)+"*\n";
        return s+super.convertToString(indentLevel);
    }
}
