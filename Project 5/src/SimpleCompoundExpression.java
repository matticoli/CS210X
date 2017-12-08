public class SimpleCompoundExpression extends AbstractCompoundExpression {
    @Override
    public Expression deepCopy() {
        final CompoundExpression e = new SimpleCompoundExpression();
        deepCopyChildren(e);
        return e;
    }

    @Override
    public void flatten() {
        children.forEach((child) -> {
            child.flatten();
            if (child instanceof SimpleCompoundExpression) {
                ((SimpleCompoundExpression) child).getChildren().forEach( (grandchild) -> {
                    grandchild.setParent(this);
                    addSubexpression(grandchild);
                });
            }
        });
    }
}
