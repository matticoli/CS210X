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
    public String convertToString(int indentLevel) {
        String s = getIndentString(indentLevel) + "*\n";
        return s + super.convertToString(indentLevel);
    }
}
