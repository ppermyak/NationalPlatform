import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FullRoute {
    private int qtyCities;
    private final Map<String, List<Integer>> mapQtyRoutes = new HashMap<>();
    private final Map<String, List<String>> mapRoutes = new HashMap<>();
    private String start;
    private String finish;

    void fillMapRoutes(String cityFrom, String cityTo) {
        List<String> listRoutes;
        if (mapRoutes.containsKey(cityFrom)) {
            listRoutes = mapRoutes.get(cityFrom);
        } else {
            listRoutes = new ArrayList<>();
        }
        listRoutes.add(cityTo);
        mapRoutes.put(cityFrom, listRoutes);
    }

    void fillMapQtyRoutes(String cityFrom, String cityTo) {
        List<Integer> listQtyTo = new ArrayList<>();
        if (mapQtyRoutes.containsKey(cityFrom)) {
            listQtyTo.add(mapQtyRoutes.get(cityFrom).get(0));
            listQtyTo.add(mapQtyRoutes.get(cityFrom).get(1) + 1);
            mapQtyRoutes.put(cityFrom, listQtyTo);
        } else {
            listQtyTo.add(0);
            listQtyTo.add(1);
            mapQtyRoutes.put(cityFrom, listQtyTo);
        }

        List<Integer> listQtyFrom = new ArrayList<>();
        if (mapQtyRoutes.containsKey(cityTo)) {
            listQtyFrom.add(mapQtyRoutes.get(cityTo).get(0) + 1);
            listQtyFrom.add(mapQtyRoutes.get(cityTo).get(1));
            mapQtyRoutes.put(cityTo, listQtyFrom);
        } else {
            listQtyFrom.add(1);
            listQtyFrom.add(0);
            mapQtyRoutes.put(cityTo, listQtyFrom);
        }
    }

    void findStartCity() {
        for (Map.Entry<String, List<Integer>> map : mapQtyRoutes.entrySet()) {
            if (map.getValue().get(0) == 0) {
                start = map.getKey();
                break;
            }
        }
    }

    void findFinishCity() {
        for (Map.Entry<String, List<Integer>> map : mapQtyRoutes.entrySet()) {
            if (map.getValue().get(1) == 0) {
                finish = map.getKey();
                break;
            }
        }
    }

    void searchRoute(String currCity, String track, int qtyVisited) {
        List<String> listRoutes = mapRoutes.get(currCity);

        if (qtyVisited == qtyCities && currCity.equals(finish)) {
            System.out.println(track);
            return;
        }

        if (listRoutes == null)
            return;

        boolean[] visited = new boolean[listRoutes.size()];

        for (int i = 0; i < listRoutes.size(); i++)
            visited[i] = false;

        for (int i = 0; i < listRoutes.size(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                searchRoute(listRoutes.get(i), track + " " + listRoutes.get(i), qtyVisited + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        FullRoute fullRoute = new FullRoute();

        for (int i = 0; i < n; i++) {
            String s = reader.readLine();
            String cityFrom = s.split(" ")[0];
            String cityTo = s.split(" ")[1];
            fullRoute.fillMapRoutes(cityFrom, cityTo);
            fullRoute.fillMapQtyRoutes(cityFrom, cityTo);
        }

        fullRoute.qtyCities = fullRoute.mapQtyRoutes.size();
        fullRoute.findStartCity();
        fullRoute.findFinishCity();
        fullRoute.searchRoute(fullRoute.start, fullRoute.start, 1);
    }
}