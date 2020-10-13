package chat.onmap.positionserver;

import chat.onmap.positionserver.model.Position;
import chat.onmap.positionserver.repository.ArrayListPositionRepository;
import chat.onmap.positionserver.repository.LinkedMapPositionRepository;
import chat.onmap.positionserver.repository.PositionRepository;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import org.apache.commons.collections4.map.LinkedMap;
import org.junit.jupiter.api.Test;

public class Qwerty {

    @Test
    public void dfghj() {

        var p1 = new Position(UUID.randomUUID(), 11, 11, LocalDateTime.now());
        var p2 = new Position(UUID.randomUUID(), 22, 22, LocalDateTime.now());
        var p3 = new Position(UUID.randomUUID(), 33, 33, LocalDateTime.now());
        var p4 = new Position(UUID.randomUUID(), 44, 44, LocalDateTime.now());
        var p5 = new Position(UUID.randomUUID(), 55, 55, LocalDateTime.now());


    }


    class PositionDateComparator implements Comparator<Position> {

        @Override
        public int compare(Position p1, Position p2) {
            return 0;
        }
    }

}
