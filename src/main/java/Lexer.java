public class Lexer {
    private int position;
    private final String[] parsed;

    public Lexer(String expression) {
        parsed = expression.split(" ");
        position = 0;
    }

    public Token getNextToken() throws Exception {
        if (position >= parsed.length)
            return null;

        String curStr = parsed[position++];
        if (Character.isDigit(curStr.charAt(0)))
            return new Token(Float.parseFloat(curStr));

        switch (curStr.charAt(0)) {
            case '(':
                return new Token(Operation.LEFT_BRACKET);
            case ')':
                return new Token(Operation.RIGHT_BRACKET);
            case '+':
                return new Token(Operation.PLUS);
            case '-':
                return new Token(Operation.MINUS);
            case '*':
                return new Token(Operation.MULTIPLICATION);
            case '/':
                return new Token(Operation.DIVISION);
            default:
                throw new Exception("bad symbol");
        }
    }
}
