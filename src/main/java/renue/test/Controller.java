package renue.test;

import java.util.List;

public class Controller {


    private final Service service = new Service();

    public void parseFilter(String filterExpression){
        service.parseExpression(filterExpression);
    }

    public List<String> getRows(String name){
        return service.getRows(name);
    }

    public void identifyAirports(){
        service.identifyAirports();
    }
}
