package renue.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AirportSearcher {
    private final List<Airport> airports = new ArrayList<>();
    private static final String PATH = "airports.csv";

    private AirportEngine airportEngine;
    public List<String> getRows(String name) {
        return searchAirports(name);
    }

    public void setAirportEngine(AirportEngine airportEngine) {
        this.airportEngine = airportEngine;
    }

    public void identifyAirports(){
        String row;
        String[] arguments;
        try(RandomAccessFile raf = new RandomAccessFile(PATH, "r")) {
            while (raf.getFilePointer() != raf.length()) {
                Airport airport = new Airport();
                airport.setPointer(raf.getFilePointer());
                row = new String(raf.readLine().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                arguments = row.replace("\"", "").split(",");
                airport.setName(arguments[1].toLowerCase());
                airports.add(airport);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        Collections.sort(airports);

    }

    private ArrayList<String> searchAirports(String startAirport){
        int leftIndex = leftBinarySearch(startAirport);
        ArrayList<String> list = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(PATH, "r")) {
            for (int i = leftIndex; i < airports.size(); i++) {
                if (!airports.get(i).getName().startsWith(startAirport)) {
                    return list;
                }
                raf.seek(airports.get(i).getPointer());

                String strTemp = raf.readLine();

                if (airportEngine.isFit(strTemp)){
                    list.add(strTemp);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    private int leftBinarySearch(String startAirport) {
        int left = 0;
        int right = airports.size() - 1;
        int mid;
        while (left < right) {
            mid = (left + right) / 2;
            if (airports.get(mid).getName().substring(0, startAirport.length()).compareTo(startAirport) >= 0)
                right = mid;
            else
                left = mid + 1;
        }
        return left;
    }

}
