import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.LinkedList;

public abstract class AbstractCompoundExpression implements CompoundExpression {

    /**
     * Linkedlist of sub expressions
     */
    protected LinkedList<Expression> children;

    /**
     * Parent expression of this expression (null for root)
     */
    protected CompoundExpression parent;

    /**
     * GUI Node corresponding to this expression
     */
    private HBox node;

    /**
     * Default constructor, initializes LinkedList
     */
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

    /**
     * returns a list of the children of this expression
     *
     * @return children
     */
    private LinkedList<Expression> getChildren() {
        return this.children;
    }

    @Override
    public void setParent(CompoundExpression parent) {
        this.parent = parent;
    }

    @Override
    abstract public Expression deepCopy();

    /**
     *  Deep copy each child and set parent to new copy of this ParentheticalExpression
     *  @param parentCopy deep copy of the original expression's parent to add each subexpression to
     */
    void deepCopyChildren(CompoundExpression parentCopy) {
        children.forEach((child) -> {
            Expression childCopy = child.deepCopy();
            childCopy.setParent(parentCopy);
            parentCopy.addSubexpression(childCopy);
        });
    }

    @Override
    public Node getNode() {
        return getNode(false);
    }

    @Override
    public Node getNode(boolean ghost) {
        if(this.node != null) {
            return this.node;
        } else {

            HBox hBox = new HBox();
            // For each child of this node
            if (this instanceof ParentheticalExpression) {
                hBox.getChildren().add(new Label("("));
                for (Expression e : children) {
                    hBox.getChildren().add(e.getNode(ghost));
                }
                hBox.getChildren().add(new Label(")"));
                if(ghost) {
                    ((Label)hBox.getChildren().get(0)).setOpacity(0.5);
                    ((Label)hBox.getChildren().get(hBox.getChildren().size() - 1)).setOpacity(0.5);
                }
            } else if (this instanceof MultiplicativeExpression) {
                for (Expression e : children) {
                    hBox.getChildren().add(e.getNode(ghost));
                    Label l = new Label(e != children.getLast() ? "*" : "");
                    if(ghost) {
                        l.setOpacity(0.5);
                    }
                    hBox.getChildren().add(l);
                }
            } else if (this instanceof AdditiveExpression) {
                for (Expression e : children) {
                    hBox.getChildren().add(e.getNode(ghost));
                    Label l = new Label(e != children.getLast() ? "+" : "");
                    if(ghost) {
                        l.setOpacity(0.5);
                    }
                    hBox.getChildren().add(l);
                }
            } else {
                for (Expression e : children) {
                    hBox.getChildren().add(e.getNode(ghost));
                }
            }
            this.node = hBox;
            return hBox;
        }
    }

    @Override
    public void flatten() {
        for (int i = 0; i < children.size(); i++) {
            Expression exp = children.get(i);
            //Hack because random null children due to bad parsing.
            if (exp == null) {
                continue;
            }
            exp.flatten();
            if (exp.getClass() == this.getClass()) {
                this.children.remove(exp);
                for (int k = i; k <= ((AbstractCompoundExpression) exp).children.size(); k++)
                    children.add(k, ((AbstractCompoundExpression) exp).children.get(k - i));
            }

        }

    }


    @Override
    public String convertToString(int indentLevel) {
        String s = "";
        for (Expression child : this.children) s += child.convertToString(indentLevel + 1);
        return s;
    }

    /**
     * Returns a string representation of expression tree starting at this (sub)expression
     *
     * @param indentLevel current indent level
     * @return string representation of expression tree starting at this (sub)expression
     */
    static String getIndentString(int indentLevel) {
        String s = "";
        for (int i = 0; i < indentLevel; i++) {
            s += "\t";
        }
        return s;
    }
}
