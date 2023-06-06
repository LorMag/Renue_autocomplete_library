package org.example.Filters;

public interface FilterStrategy {

    boolean acceptFilter(String element);
    String getRightOperand();
    int getIndexOfColumn();
}
