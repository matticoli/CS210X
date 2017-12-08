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
    public Expression deepCopy() {
        return new LiteralExpression(value);
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
