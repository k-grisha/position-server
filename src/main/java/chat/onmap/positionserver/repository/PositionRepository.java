package chat.onmap.positionserver.repository;

import chat.onmap.positionserver.model.Position;
import java.util.List;

public interface PositionRepository {

    List<Position> findAll();

    void saveOrUpdate(Position position);

    List<Position> findLast(int num);
}
