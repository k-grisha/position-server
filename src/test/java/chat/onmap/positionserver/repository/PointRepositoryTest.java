package chat.onmap.positionserver.repository;

import chat.onmap.positionserver.model.LatLon;
import chat.onmap.positionserver.model.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PointRepositoryTest {

    @Autowired
    private PointRepository pointRepository;

    @Test
    public void testSavePoint() {
        var point = pointRepository.save(Point.builder()
                .location(new LatLon(1, 2))
                .build());
        assertThat(point.getUuid()).isNotNull();
        assertThat(point.getLastUpdate()).isNotNull();
    }

}