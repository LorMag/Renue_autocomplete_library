package renue.test.filters;

public class FilterEqualsStrategy implements FilterStrategy{
    private final int indexOfColumn;
    private final String rightOperand;

    private FilterEqualsStrategy(int indexOfColumn, String rightOperand){
        this.indexOfColumn = indexOfColumn;
        this.rightOperand = rightOperand;
    }

    public static FilterStrategy createInstance(String filter){
        String[] operands = filter.split("=");
        if (operands[1].charAt(0) == '\''){
            operands[1] = operands[1].substring(1, operands[1].length() - 1);
        }
        return new FilterEqualsStrategy(Integer.parseInt(operands[0]) - 1, operands[1]);
    }

    @Override
    public boolean acceptFilter(String element) {
        return element.equals(rightOperand);
    }

    public int getIndexOfColumn() {
        return indexOfColumn;
    }

    public String getRightOperand() {
        return rightOperand;
    }
}
