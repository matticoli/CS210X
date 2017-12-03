import java.util.LinkedList;

public class AbstractCompoundExpression implements CompoundExpression{

    protected LinkedList<Expression> children;
    protected CompoundExpression parent;

    @Override
    public void addSubexpression(Expression subexpression) {
        children.add(subexpression);
    }

    @Override
    public CompoundExpression getParent() {
        return this.parent;
    }

    @Override
    public void setParent(CompoundExpression parent) {
        this.parent = parent;
    }

    @Override
    public Expression deepCopy() {
        CompoundExpression copy = new AbstractCompoundExpression();
        for(Expression child : children ) {
            copy.addSubexpression(child.deepCopy());
            child.setParent(copy);
        }
    }

    @Override
    public void flatten() {

    }

    @Override
    public String convertToString(int indentLevel) {
        String childrenString = "";
        for(Expression child : this.children) {
            return childrenString + child.convertToString(indentLevel + 1) + "\n";
        }
    }
}
