package renue.test.filters;

public interface FilterStrategy {

    boolean acceptFilter(String element);
    String getRightOperand();
    int getIndexOfColumn();
}
