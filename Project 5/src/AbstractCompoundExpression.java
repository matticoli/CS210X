import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

public abstract class AbstractCompoundExpression implements CompoundExpression{

    protected LinkedList<Expression> children;
    protected CompoundExpression parent;

    AbstractCompoundExpression() {
        this.children = new LinkedList<>();
    }

    @Override
    public void addSubexpression(Expression subexpression) {
        children.add(subexpression);
    }

    @Override
    public CompoundExpression getParent() {
        return this.parent;
    }

    public LinkedList<Expression> getChildren() {
        return this.children;
    }

    @Override
    public void setParent(CompoundExpression parent) {
        this.parent = parent;
    }

    @Override
    abstract public Expression deepCopy();

    public CompoundExpression deepCopyChildren(CompoundExpression parentCopy) {
        // Deep copy each child and set parent to new copy of this ParentheticalExpression
        children.forEach((child) -> {
            Expression childCopy = child.deepCopy();
            childCopy.setParent(parentCopy);
            parentCopy.addSubexpression(childCopy);
        });
        return parentCopy;
    }

    @Override
     public void flatten(){
        for (int i = 0; i<children.size(); i++){
            Expression exp = children.get(i);
            exp.flatten();
            if(exp.getClass() == this.getClass()){
                this.children.remove(exp);
                for(int k = i; k<=((AbstractCompoundExpression) exp).children.size();k++)
                    children.add(k,((AbstractCompoundExpression) exp).children.get(k-i));
            }

        }

    }


    @Override
    public String convertToString(int indentLevel) {
        String s = "";
        for(Expression child : this.children) {
            s += child.convertToString(indentLevel + 1);
        }
        return s;
    }

    public static String getIndentString(int indentLevel) {
        String s = "";
        for(int i = 0; i<indentLevel; i++) {
            s+="\t";
        }
        return s;
    }
}
