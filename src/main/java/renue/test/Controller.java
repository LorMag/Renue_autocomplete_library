package renue.test;

public class Controller {

    private View view;
    private Parser parser = new Parser();

    public Controller(View view){
        this.view = view;
    }

    public void parseFilter(String filterExpression){
        parser.parseExpression(filterExpression);
    }

}
