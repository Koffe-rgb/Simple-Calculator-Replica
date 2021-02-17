import java.util.ArrayList;
import java.util.List;

public class Expr {
    private final Lexer lexer;
    private Token curToken;

    private Expr(String expression) {
        lexer = new Lexer(expression);
        getNext();
    }

    private void getNext() {
        try {
            curToken = lexer.getNextToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void accept(Operation operation) {
        if (curToken == null ||
                curToken.getTokenType() != TokenType.OPERATION ||
                curToken.getOperation() != operation)
        {
            try {
                throw new Exception("expected another op");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getNext();
    }

    private boolean isOperation(List<Operation> operations) {
        if (curToken == null || curToken.getTokenType() != TokenType.OPERATION)
            return false;
        for (Operation operation : operations) {
            if (curToken.getOperation() == operation)
                return true;
        }
        return false;
    }

    private float simpleExpression() {
        float left = term();
        while (isOperation(new ArrayList<Operation>() {{
            add(Operation.PLUS);
            add(Operation.MINUS);
        }})) {
            Operation operation = curToken.getOperation();
            getNext();
            float right = term();

            switch (operation) {
                case PLUS: left = left + right; break;
                case MINUS: left = left - right; break;
                default:
                    try {
                        throw new Exception("bad addictive operation");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return left;
    }

    private float term() {
        float left = factor();
        while (isOperation(new ArrayList<Operation>() {{
            add(Operation.MULTIPLICATION);
            add(Operation.DIVISION);
        }})) {
            Operation operation = curToken.getOperation();
            getNext();
            float right = factor();

            switch (operation) {
                case MULTIPLICATION: left = left * right; break;
                case DIVISION: left = left / right; break;
                default:
                    try {
                        throw new Exception("bad multiplication operation");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return left;
    }

    private float factor() {
        if (curToken != null && curToken.getTokenType() == TokenType.OPERATION) {
            accept(Operation.LEFT_BRACKET);
            float left = simpleExpression();
            accept(Operation.RIGHT_BRACKET);
            return left;
        }
        if (curToken == null) {
            try {
                throw new Exception("Token expected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        float result = curToken.getValue();
        getNext();
        return result;
    }

    public static float evaluate(String expression) {
        Expr expr = new Expr(expression);
        return expr.simpleExpression();
    }
}
