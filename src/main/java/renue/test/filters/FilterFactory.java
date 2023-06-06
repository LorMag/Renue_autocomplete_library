package renue.test.filters;

public class FilterFactory {

    public static FilterStrategy createFilterStrategy(String filter){
        if (filter.matches("^\\d+>.+$")){
            return FilterGreaterThanStrategy.createInstance(filter);
        }
        if (filter.matches("^\\d+<.+$")){
            return FilterLessThanStrategy.createInstance(filter);
        }
        if (filter.matches("^\\d+=.+$")){
            return FilterEqualsStrategy.createInstance(filter);
        }
        if (filter.matches("^\\d+!.+$")){
            return FilterNotEqualsStrategy.createInstance(filter);
        }
        return null;
    }
}
