package chat.onmap.positionserver.repository;

import chat.onmap.positionserver.model.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LinkedMapPositionRepository  {

    Map<UUID, Point> positions = Collections.synchronizedMap( new LinkedHashMap<>());


    public List<Point> findAll() {
        return new ArrayList<>(positions.values());
    }


    public void saveOrUpdate(Point point) {
    }


    public List<Point> findLast(int num) {
        List<UUID> allKeys = new ArrayList<>(positions.keySet());
        allKeys = allKeys.subList(allKeys.size() - num, allKeys.size());
        Collections.reverse(allKeys);
        return allKeys.stream()
            .map(k -> positions.get(k))
            .collect(Collectors.toList());
    }
}
