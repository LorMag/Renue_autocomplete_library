package renue.test;

import renue.test.filters.FilterFactory;
import renue.test.filters.FilterStrategy;

import java.util.ArrayList;
import java.util.List;

public class FilterStrategyMaker {

    public static List<FilterStrategy> getFilters(List<String> filters){

        List<FilterStrategy> filtersStrategy = new ArrayList<>();
        for(String s : filters){
            filtersStrategy.add(FilterFactory.createFilterStrategy(s));
        }
        return filtersStrategy;
    }
}
