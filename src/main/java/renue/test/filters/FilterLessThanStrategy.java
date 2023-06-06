package renue.test.filters;

public class FilterLessThanStrategy implements FilterStrategy{
    private final int indexOfColumn;
    private final String rightOperand;

    private FilterLessThanStrategy(int indexOfColumn, String rightOperand){
        this.indexOfColumn = indexOfColumn;
        this.rightOperand = rightOperand;
    }

    public static FilterStrategy createInstance(String filter){
        String[] operands = filter.split("<");
        return new FilterLessThanStrategy(Integer.parseInt(operands[0]) - 1, operands[1]);
    }

    @Override
    public boolean acceptFilter(String element) {
        return Double.parseDouble(element) < Double.parseDouble(rightOperand);
    }

    public int getIndexOfColumn() {
        return indexOfColumn;
    }

    public String getRightOperand() {
        return rightOperand;
    }
}
