package renue.test;

import org.apache.commons.lang3.StringUtils;
import renue.test.exceptions.IncorrectParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {

    private static final String symbols = "(&|)";

    private List<String> polandSequence;
    private List<String> filters;

    public List<String> getPolandSequence() {
        return polandSequence;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void parseExpression(String filterExpression){
        polandSequence = new ArrayList<>();
        filters = new ArrayList<>();
        if (!isCorrectBracersSequence(filterExpression)){
            throw new IncorrectParseException("Неправильная скобочная последовательность");
        }
        filterExpression = prepareFilterExpression(filterExpression);
        filterExpression = simplifyFilterExpression(filterExpression);
        generatePolandSequence(filterExpression);
    }

    private String prepareFilterExpression(String filterExpression){
        return filterExpression.replace(" ", "").replace("||", "|").replace("<>", "!");
    }

    private String simplifyFilterExpression(String filterExpression){
        StringBuilder resultString = new StringBuilder();
        StringBuilder subString = new StringBuilder();
        for(Character c: filterExpression.toCharArray()){
            if (!symbols.contains(c.toString())){
                subString.append(c);
                continue;
            }
            if (subString.length() == 0){
                resultString.append(c);
                continue;
            }
            if (!isCorrect(subString.toString())) {
                throw new IncorrectParseException("Ошибка при анализе входного фильтра");
            }

            String filter = encoder(subString); // кодирует часть с column
            filters.add(filter);
            resultString.append(filters.size() - 1).append(c);
            subString.delete(0, subString.length());
        }

        if (subString.length() != 0){
            if (!isCorrect(subString.toString())) {
                throw new IncorrectParseException("Ошибка при анализе входного фильтра");
            }
            filters.add(encoder(subString));// кодирует часть с column
            resultString.append(filters.size() - 1);
        }
        return resultString.toString();
    }

    private String encoder(StringBuilder subString){
        String tempSubstring = subString.toString();
        String temp = StringUtils.substringBetween(subString.toString(),"column[", "]");
        subString.delete(0, subString.length());
        subString.append(temp).append(tempSubstring.substring(tempSubstring.indexOf("]") + 1));
        return subString.toString();
    }

    public static boolean isCorrect(String str){
        boolean moreLessMatch = str.matches("^column\\[(1[0-4]|[1-9])\\]\\s*[><]\\s*(-?\\d+|-?\\d+\\.\\d+)$");
        boolean equalsMatch = str.matches("^column\\[(1[0-4]|[1-9])\\]\\s*[!=]\\s*((-?\\d+|-?\\d+\\.\\d+)|'([A-Za-z]+)')$");
        return moreLessMatch || equalsMatch;
    }

    public static boolean isCorrectBracersSequence(String str){
        Stack<Character> bracers = new Stack<>();
        for (Character c : str.toCharArray()){
            if(c == '('){
                bracers.push(c);
            } else if (bracers.isEmpty() && c == ')'){
                return false;
            } else if (c == ')') {
                bracers.pop();
            }
        }
        return bracers.isEmpty();
    }

    private void generatePolandSequence(String filterExpression){
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < filterExpression.length(); i++) {
            char tempChar = filterExpression.charAt(i);
            if (tempChar == '(') {
                stack.push(tempChar);
            } else if (tempChar == '|'){
                while (!stack.isEmpty()) {
                    if (stack.peek() == '&' || stack.peek() == '|') {
                        polandSequence.add(String.valueOf(stack.pop()));
                    } else {
                        break;
                    }
                }
                stack.push(tempChar);
            }else if (tempChar == '&') {
                while (!stack.isEmpty()) {
                    if (stack.peek() == '&') {
                        polandSequence.add(String.valueOf(stack.pop()));
                    } else {
                        break;
                    }
                }
                stack.push(tempChar);
            } else if (tempChar == ')') {
                while (!stack.isEmpty()) {
                    if (stack.peek() == '(') {
                        stack.pop();
                        break;
                    } else {
                        polandSequence.add(String.valueOf(stack.pop()));
                    }
                }
            } else {
                polandSequence.add(String.valueOf(tempChar));
            }
        }
        while (!stack.isEmpty()){
            polandSequence.add(String.valueOf(stack.pop()));
        }
    }

}
