package chat.onmap.pointservice.services;

import chat.onmap.pointservice.model.LatLon;
import chat.onmap.pointservice.model.Point;
import chat.onmap.pointservice.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PointServiceTest {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PointService pointService;
    private List<Point> points;

    @BeforeEach
    public void before() {
        pointRepository.deleteAll();
        points = Arrays.asList(
                // Berliners
                Point.builder().location(new LatLon(52.530443, 13.408755)).uuid(UUID.randomUUID()).build(),
                Point.builder().location(new LatLon(52.530643, 13.408855)).uuid(UUID.randomUUID()).build(),
                Point.builder().location(new LatLon(52.530743, 13.408955)).uuid(UUID.randomUUID()).build(),
                // Other
                Point.builder().location(new LatLon(52.149851, 12.403229)).uuid(UUID.randomUUID()).build(),
                Point.builder().location(new LatLon(53.194280, 11.998717)).uuid(UUID.randomUUID()).build(),
                Point.builder().location(new LatLon(52.904201, 13.931979)).uuid(UUID.randomUUID()).build(),
                Point.builder().location(new LatLon(52.059942, 14.391751)).uuid(UUID.randomUUID()).build());
        pointRepository.saveAll(points);
    }

    @Test
    public void getAllPointsTest() {
        var response = pointService.getPoints(null, null, null);
        assertThat(response.size()).isEqualTo(points.size());
    }

    @Test
    public void getSpecificAmountPointsTest() {
        var response = pointService.getPoints(null, null, 2);
        assertThat(response.size()).isEqualTo(2);
    }

    @Test
    public void getVisiblePointsTest() {
        // Berlin boundary
        LatLon southWest = new LatLon(52.433194, 13.232152);
        LatLon northEast = new LatLon(52.608029, 13.541953);
        var response = pointService.getPoints(southWest, northEast, null);
        assertThat(response.size()).isEqualTo(3);
        response = pointService.getPoints(southWest, northEast, 2);
        assertThat(response.size()).isEqualTo(2);
    }

}