package chat.onmap.positionserver.repository;

import chat.onmap.positionserver.model.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

//@Repository
public class ArrayListPositionRepository implements PositionRepository {

    private final ArrayList<Position> positions = new ArrayList<>();
    private final HashMap<UUID, Integer> indexes = new HashMap<>();

    public List<Position> findAll() {
        return new ArrayList<>(positions);
    }

    public void saveOrUpdate(Position position) {
        Integer index = indexes.get(position.getId());
        if (index != null) {
            positions.remove(index.intValue());
        }
        positions.add(position);
        indexes.put(position.getId(), positions.size() - 1);
    }

    public List<Position> findLast(int num) {
        if (num >= positions.size()) {
            return new ArrayList<>(positions);
        }
        return positions.subList(positions.size() - num , positions.size() );
    }

}
