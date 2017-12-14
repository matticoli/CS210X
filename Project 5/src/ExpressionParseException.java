/**
 * Exception thrown when a ExpressionParser fails to parse a specified string.
 */
class ExpressionParseException extends Exception {
    ExpressionParseException(String message) {
        super(message);
    }
}
