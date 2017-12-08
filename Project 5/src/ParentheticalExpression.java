import java.util.LinkedList;

public class ParentheticalExpression extends AbstractCompoundExpression {
    @Override
    public Expression deepCopy() {
        final CompoundExpression e = new ParentheticalExpression();
        deepCopyChildren(e);
        return e;
    }

    @Override
    public void flatten() {//TODO Document this
        new LinkedList<Expression>(children).forEach((child) -> {
            child.flatten();
            if (child instanceof ParentheticalExpression) {
                ((ParentheticalExpression) child).getChildren().forEach( (grandchild) -> {
                    grandchild.setParent(this);
                    addSubexpression(grandchild);
                });
            }
        });
    }

    @Override
    public String convertToString(int indentLevel) {
        String s = getIndentString(indentLevel)+"()\n";
        return s+super.convertToString(indentLevel);
    }
}
