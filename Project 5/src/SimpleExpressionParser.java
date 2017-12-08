
/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 * E := A | X
 * A := A+M | M
 * M := M*M | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */
public class SimpleExpressionParser implements ExpressionParser {
	/*
	 * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
         * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * @param str the string to parse into an expression tree
	 * @param withJavaFXControls you can just ignore this variable for R1
	 * @return the Expression object representing the parsed expression tree
	 */
	public Expression parse (String str, boolean withJavaFXControls) throws ExpressionParseException {
		// Remove spaces -- this simplifies the parsing logic
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			// If we couldn't parse the string, then raise an error
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		// Flatten the expression before returning
		expression.flatten();
		return expression;
	}
	
	protected Expression parseExpression (String str) {
		try {
			Expression e;
			if(isParentheticalExpression(str)) {
			// Parenthetical Expression
				e = new ParentheticalExpression();
				((ParentheticalExpression) e).addSubexpression(parseExpression(str.substring(1, str.length() - 1)));
				return e;
			} else if(isMultiplicativeExpression(str)) {
				// Multiplicative
				e = new MultiplicativeExpression();
				((MultiplicativeExpression) e).addSubexpression(
						parseExpression(str.substring(0, str.indexOf("*"))));
				((MultiplicativeExpression) e).addSubexpression(
						parseExpression(str.substring(1 + str.indexOf("*"))));
				return e;
			} else if (isAdditiveExpression(str)) {
				// Additive
				e = new AdditiveExpression();
				((AdditiveExpression) e).addSubexpression(
						parseExpression(str.substring(0, str.indexOf("+"))));
				((AdditiveExpression) e).addSubexpression(
						parseExpression(str.substring(1 + str.indexOf("+"))));
				return e;
			}

			// If nothing has been returned yet, the expression is either a literal or invalid
			return new LiteralExpression(str);

		} catch(Exception e) {
			e.printStackTrace();
			System.err.println(str+"\n The above expression is invalid");
			// Error parsing expression, return null.
			return null;
		} catch (AssertionError e) {
			e.printStackTrace();
			System.err.println(str+"\n The above expression is invalid");
			// Error parsing expression, return null.
			return null;
		}
	}

	/**
	 * Determines whether str is parenthetical expression (
	 * 
	 * @param str Expression to check
	 * @return True if str is parenthetical, else false.
	 */
	protected boolean isParentheticalExpression(String str) {
		return str.startsWith("(") && str.endsWith(")");
	}

	protected boolean isMultiplicativeExpression(String str) {
		if(!str.contains("*")) {
			// No multiplication sign = not multiplicative
			return false;
		} else if(!str.contains("(") && !str.contains("+")) {
			// No addition or parens = multiplicative
			return true;
		}else if(str.contains("+") && !str.contains("(")) {
			// Contains + and no parens = additive
		} else if(str.contains("(") && !str.contains("+") && !isParentheticalExpression(str)) {
			// Contains parens and no +, but isn't parenthetical = multiplicative or invalid
			return true;
		}

		// Parens and addition present
		int firstOpenParen = str.indexOf("(");
		int firstAdd = str.indexOf("+");
		int firstMult = str.indexOf("*");
		// None of these should be -1 at this point
		assert firstOpenParen !=-1;
		assert firstAdd !=-1;
		assert firstMult !=-1;

		if(firstAdd < firstOpenParen) {
			// If there is addition before the first open paren, the expression is additive
			return false;
		}

		// If the following assertion fails, the expression is invalid.
		// There may be a literal adjecent to a paren expression, which we are considering invalid
		assert firstMult < firstOpenParen;

		// There is * and nothing else before the first open paren, must be multiplicative
		return true;
	}

	protected boolean isAdditiveExpression(String str) {
		if(!str.contains("+")) {
			// No addition, must be literal or invalid
			return false;
		} else if(!str.contains("(") || (str.indexOf("+") < str.indexOf("("))) {
			// Addition takes place outside of first parenthetical expression, if there is one
			return true;
		} else {
			return false;
		}
	}
}
