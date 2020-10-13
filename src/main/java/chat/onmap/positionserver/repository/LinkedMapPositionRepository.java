package chat.onmap.positionserver.repository;

import chat.onmap.positionserver.model.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LinkedMapPositionRepository implements PositionRepository {

    Map<UUID, Position> positions = Collections.synchronizedMap( new LinkedHashMap<>());

    @Override
    public List<Position> findAll() {
        return new ArrayList<>(positions.values());
    }

    @Override
    public void saveOrUpdate(Position position) {
        positions.remove(position.getId());
        positions.put(position.getId(), position);
    }

    @Override
    public List<Position> findLast(int num) {
        List<UUID> allKeys = new ArrayList<>(positions.keySet());
        allKeys = allKeys.subList(allKeys.size() - num, allKeys.size());
        Collections.reverse(allKeys);
        return allKeys.stream()
            .map(k -> positions.get(k))
            .collect(Collectors.toList());
    }
}
