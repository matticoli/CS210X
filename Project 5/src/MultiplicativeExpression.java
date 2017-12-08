import java.util.LinkedList;

public class MultiplicativeExpression extends AbstractCompoundExpression {
    @Override
    public Expression deepCopy() {
        final CompoundExpression e = new MultiplicativeExpression();
        deepCopyChildren(e);
        return e;
    }



    @Override
    public String convertToString(int indentLevel) {
        String s = getIndentString(indentLevel)+"*\n";
        return s+super.convertToString(indentLevel);
    }
}
