public class Token {
    private final TokenType tokenType;
    private Operation operation;
    private float value;

    public Token(float value) {
        this.value = value;
        tokenType = TokenType.VALUE;
    }

    public Token(Operation operation) {
        this.operation = operation;
        tokenType = TokenType.OPERATION;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Operation getOperation() {
        return operation;
    }

    public float getValue() {
        return value;
    }
}
