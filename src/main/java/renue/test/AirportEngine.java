package renue.test;

import renue.test.exceptions.EmptyPolandSequenceException;
import renue.test.exceptions.IllegalComparisonException;
import renue.test.filters.FilterStrategy;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class AirportEngine {

    private final List<String> polandSequence;
    private final List<FilterStrategy> filterStrategies;
    private static final String OPERATIONS = "|&";

    private AirportEngine(List<String> polandSequence, List<FilterStrategy> filterStrategies) {
        this.polandSequence = polandSequence;
        this.filterStrategies = filterStrategies;
    }

    public static AirportEngine createInstance(Parser parser){
        return new AirportEngine(parser.getPolandSequence(), FilterStrategyMaker.getFilters(parser.getFilters()));
    }

    public boolean isFit(String str){
        if (polandSequence.isEmpty() && filterStrategies.isEmpty()) {
            return true;
        }
        String[] strList = str.replace("\"", "").split(",");
        Stack<Boolean> finalResult = new Stack<>();
        try {
            for (String s : polandSequence) {
                if (!OPERATIONS.contains(s)) {
                    FilterStrategy tempFilterStrategy = filterStrategies.get(Integer.parseInt(s));
                    finalResult.push(tempFilterStrategy.acceptFilter(strList[tempFilterStrategy.getIndexOfColumn()]));
                } else {
                    finalResult.push(logisticOperation(finalResult.pop(), finalResult.pop(), s));
                }
            }
        } catch (EmptyStackException e){
            throw new EmptyPolandSequenceException("В ходе обработки таблицы возникла ошибка последовательности операторов");
        } catch (NumberFormatException e){
            throw new IllegalComparisonException("В ходе обработки таблицы возникла ошибка сравнения строк на операции > или <");
        }
        return finalResult.pop();

    }

    public boolean logisticOperation(boolean a, boolean b, String op){
        switch (op){
            case("&"):
                return a && b;
            case("|"):
                return a || b;
        }
        return false;
    }

}
