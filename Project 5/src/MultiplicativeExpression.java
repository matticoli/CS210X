public class MultiplicativeExpression extends AbstractCompoundExpression {
    @Override
    public Expression deepCopy() {
        final CompoundExpression e = new MultiplicativeExpression();
        deepCopyChildren(e);
        return e;
    }

    @Override
    public void flatten() {//TODO Document this
        children.forEach((child) -> {
            child.flatten();
            if (child instanceof MultiplicativeExpression) {
                ((MultiplicativeExpression) child).getChildren().forEach( (grandchild) -> {
                    grandchild.setParent(this);
                    addSubexpression(grandchild);
                });
            }
        });
    }

    @Override
    public String convertToString(int indentLevel) {
        String s = getIndentString(indentLevel)+"*"+"\n";
        return s+super.convertToString(indentLevel);
    }
}
