package renue.test;

import renue.test.exceptions.EmptyPolandSequenceException;
import renue.test.exceptions.IllegalComparisonException;
import renue.test.exceptions.IncorrectParseException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class View {

    private final Controller controller = new Controller();


    public void showMenu(){
        Scanner scanner = new Scanner(System.in);
        controller.identifyAirports();
        while(true){
            System.out.println("Введите фильтр для поиска аэропорта:");
            String str = scanner.nextLine();
            if (str.equals("!quit"))
                break;
            try {
                controller.parseFilter(str);
            } catch (IncorrectParseException e){
                System.out.println(e.getMessage());
                continue;
            }
            System.out.println("Введите название аэропорта:");
            str = scanner.nextLine();
            if (str.equals("!quit"))
                break;
            long time;
            List<String> answer;
            try {
                time = System.currentTimeMillis();
                answer = controller.getRows(str.toLowerCase());
                time = System.currentTimeMillis() - time;
            } catch (EmptyPolandSequenceException | IllegalComparisonException e){
                System.out.println(e.getMessage());
                continue;
            }
            for (String s : answer) {
                String[] tempArrString = new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).split(",");
                System.out.println(tempArrString[1]+Arrays.toString(tempArrString));
            }
            System.out.println("Количество найденных строк: " + answer.size() + " ," +
                                " затраченное время на поиск: " + time + " мс");
        }
    }

}
