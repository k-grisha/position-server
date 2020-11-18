package chat.onmap.pointservice.repository;

import chat.onmap.pointservice.model.LatLon;
import chat.onmap.pointservice.model.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PointRepositoryTest {

    @Autowired
    private PointRepository pointRepository;

    @Test
    public void testSavePoint() {
        var point = pointRepository.save(Point.builder()
                .uuid(UUID.randomUUID())
                .location(new LatLon(1, 2))
                .build());
        assertThat(point.getUuid()).isNotNull();
        assertThat(point.getLastUpdate()).isNotNull();
    }

}