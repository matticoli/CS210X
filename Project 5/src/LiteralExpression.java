public class LiteralExpression implements Expression {

    /**
     * Parent expression in the expression tree
     */
    protected CompoundExpression parent;
    /**
     * The numerical value that the LiteralExpression represents
     */
    protected String value;

    /**
     * Creates new LiteralExpression with given int value
     *
     * @param value The numerical value that the LiteralExpression represents
     */
    public LiteralExpression(String value) {
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
    public Expression deepCopy() {
        return new LiteralExpression(value);
    }

    @Override
    public void flatten() {
        // Literal is already flat
        return;
    }

    @Override
    public String convertToString(int indentLevel) {
        String s = "";
        for(int i = 0; i<indentLevel; i++) {
            s+="\t";
        }
        return s+value;
    }
}
