import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AdditiveExpression extends AbstractCompoundExpression {
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
        final CompoundExpression e = new AdditiveExpression();
        deepCopyChildren(e);
        return e;
    }


    @Override
    public String convertToString(int indentLevel) {
        String s = getIndentString(indentLevel)+"+\n";
        return s+super.convertToString(indentLevel);
    }
}
