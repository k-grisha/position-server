package chat.onmap.positionserver;

import chat.onmap.positionserver.dto.PointDto;
import chat.onmap.positionserver.model.LatLon;
import chat.onmap.positionserver.model.Point;
import chat.onmap.positionserver.repository.PointRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PositionServerApplicationTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        pointRepository.deleteAll();
    }

    @Test
    void getOnePoint() throws Exception {
        pointRepository.save(Point.builder()
                .location(new LatLon(1, 2))
                .build());
        pointRepository.save(Point.builder()
                .location(new LatLon(1, 2))
                .build());
        var point = pointRepository.save(Point.builder()
                .location(new LatLon(1, 2))
                .build());
        var json = mvc.perform(get("/api/v1/point").param("num", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(json, new TypeReference<List<PointDto>>() {
        });

        assertThat(result)
                .containsExactly(new PointDto(point.getUuid(),
                        point.getLocation().getLat().floatValue(), point.getLocation().getLon().floatValue()));
    }

    @Test
    void getAllPointsOrder() throws Exception {
        var point1 = pointRepository.save(Point.builder()
                .location(new LatLon(1, 2))
                .build());
        var point2 = pointRepository.save(Point.builder()
                .location(new LatLon(1, 2))
                .build());
        var point3 = pointRepository.save(Point.builder()
                .location(new LatLon(1, 2))
                .build());
        var json = mvc.perform(get("/api/v1/point"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(json, new TypeReference<List<PointDto>>() {
        });

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).uuid).isEqualTo(point3.getUuid());
        assertThat(result.get(1).uuid).isEqualTo(point2.getUuid());
        assertThat(result.get(2).uuid).isEqualTo(point1.getUuid());
    }


}
