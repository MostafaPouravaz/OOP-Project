package models;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Delivery {
    public Delivery(int source, int destination) throws IOException {
        this.source = source-1;
        this.destination = destination-1;
        map.floydWarshall();
    }

    private int source;
    private int destination;
    private final Map map = new Map("E:\\graph.txt");
    public ArrayList<String> shortestPath(){
        return new ArrayList<>(List.of(map.getShortestPath(source, destination).split(" -> ")));
    }
    public ArrayList<Integer> weightOfEdges(){
        ArrayList<String> path = shortestPath();
        ArrayList<Integer> path1 = new ArrayList<>();
        for (int i=0; i<path.size()-1; i++){
            path1.add((int) map.getShortestDistance(Integer.parseInt(path.get(i)),Integer.parseInt(path.get(i+1))));
        }
        return path1;
    }
    public Long shortestDistinction(){
        return map.getShortestDistance(source,destination);
    }

    public String getShortestPath(){
        return map.getShortestPath(source,destination);
    }

    public int whereIsNowDelivery(LocalDateTime startTime , LocalDateTime nowTime , int estimatedTime){
        Duration duration = Duration.between(startTime , nowTime);
        int passedTime = (int) duration.getSeconds();
        long w = this.shortestDistinction() * passedTime/estimatedTime ;
        int sum = 0 ;
        for(int i=0 ; i< this.shortestPath().size() ; i++){
            sum += Integer.parseInt(this.shortestPath().get(i));
           if(w < sum){
                return i-1 ;
            }
            else if(w == sum)
                return i ;
        }
        return 0;
    }

}
