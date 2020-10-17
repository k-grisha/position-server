package chat.onmap.pointservice.repository;

import chat.onmap.pointservice.model.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LinkedMapPositionRepository  {

    Map<UUID, Point> points = Collections.synchronizedMap( new LinkedHashMap<>());


    public List<Point> findAll() {
        return new ArrayList<>(points.values());
    }


    public void saveOrUpdate(Point point) {
    }


    public List<Point> findLast(int num) {
        List<UUID> allKeys = new ArrayList<>(points.keySet());
        allKeys = allKeys.subList(allKeys.size() - num, allKeys.size());
        Collections.reverse(allKeys);
        return allKeys.stream()
            .map(k -> points.get(k))
            .collect(Collectors.toList());
    }
}
