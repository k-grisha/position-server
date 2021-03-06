package chat.onmap.pointservice;

import chat.onmap.pointservice.dto.PointDto;
import chat.onmap.pointservice.model.LatLon;
import chat.onmap.pointservice.model.Point;
import chat.onmap.pointservice.repository.PointRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .uuid(UUID.randomUUID())
                .location(new LatLon(1, 2))
                .build());
        pointRepository.save(Point.builder()
                .uuid(UUID.randomUUID())
                .location(new LatLon(1, 2))
                .build());
        var point = pointRepository.save(Point.builder()
                .uuid(UUID.randomUUID())
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
                        (int) (point.getLocation().getLat() * 1000000),
                        (int) (point.getLocation().getLon() * 1000000)));
    }

    @Test
    void getPointsOrdered() throws Exception {
        var point1 = pointRepository.save(Point.builder()
                .uuid(UUID.randomUUID())
                .location(new LatLon(1, 2))
                .build());
        var point2 = pointRepository.save(Point.builder()
                .uuid(UUID.randomUUID())
                .location(new LatLon(1, 2))
                .build());
        var point3 = pointRepository.save(Point.builder()
                .uuid(UUID.randomUUID())
                .location(new LatLon(1, 2))
                .build());
        var json = mvc.perform(get("/api/v1/point").param("num", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(json, new TypeReference<List<PointDto>>() {
        });

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).uuid).isEqualTo(point3.getUuid());
        assertThat(result.get(1).uuid).isEqualTo(point2.getUuid());
    }

    @Test
    void savePoint_success() throws Exception {
        var uuid = UUID.randomUUID();
        mvc.perform(post("/api/v1/point/{uuid}", uuid.toString()).content("{\"lat\":11,\"lon\":22}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var point = pointRepository.findById(uuid);
        assertThat(point).isPresent();
    }

    @Test
    void savePoint_validationFail() throws Exception {
        mvc.perform(post("/api/v1/point/{uuid}", UUID.randomUUID().toString()).content("{\"lat\":11}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
        mvc.perform(post("/api/v1/point/{uuid}", UUID.randomUUID().toString()).content("{\"lat\":11,\"lon\":189000000}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
        mvc.perform(post("/api/v1/point/{uuid}", UUID.randomUUID().toString()).content("{\"lat\":11,\"lon\":-189000000}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTest() throws Exception {
        var point = pointRepository.save(Point.builder()
                .uuid(UUID.randomUUID())
                .location(new LatLon(1, 2))
                .build());
        mvc.perform(post("/api/v1/point/{uuid}", point.getUuid().toString()).content("{\"lat\": 33000000,\"lon\": " +
                "44000000}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        var pointUpdated = pointRepository.findById(point.getUuid());
        assertThat(pointUpdated).isPresent();
        assertThat(pointUpdated.get().getLocation().getLat()).isEqualTo(33);
        assertThat(pointUpdated.get().getLocation().getLon()).isEqualTo(44);

    }

}
