public class AdditiveExpression extends AbstractCompoundExpression {
    @Override
    public Expression deepCopy() {
        final CompoundExpression e = new AdditiveExpression();
        deepCopyChildren(e);
        return e;
    }

    @Override
    public void flatten() {//TODO Document this
        children.forEach((child) -> {
            child.flatten();
            if (child instanceof AdditiveExpression) {
                ((AdditiveExpression) child).getChildren().forEach( (grandchild) -> {
                    grandchild.setParent(this);
                    addSubexpression(grandchild);
                });
            }
        });
    }

    @Override
    public String convertToString(int indentLevel) {
        String s = getIndentString(indentLevel)+"+"+"\n";
        return s+super.convertToString(indentLevel);
    }
}
