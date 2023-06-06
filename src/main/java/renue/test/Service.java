package renue.test;

import java.util.List;

public class Service {

    private final Parser parser = new Parser();
    private final AirportSearcher airportSearcher = new AirportSearcher();

    public void parseExpression(String filterExpression){
        parser.parseExpression(filterExpression);
    }

    public List<String> getRows(String name){
        airportSearcher.setAirportEngine(AirportEngine.createInstance(parser));
        return airportSearcher.getRows(name);
    }

    public void identifyAirports(){
        airportSearcher.identifyAirports();
    }


}
