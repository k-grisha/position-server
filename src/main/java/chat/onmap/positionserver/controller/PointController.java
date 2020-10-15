package chat.onmap.positionserver.controller;

import chat.onmap.positionserver.dto.PointDto;
import chat.onmap.positionserver.mapper.PointMapper;
import chat.onmap.positionserver.model.LatLon;
import chat.onmap.positionserver.services.PointService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class PointController {

    private final PointService pointService;
    private final PointMapper mapper = Mappers.getMapper(PointMapper.class);

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/point")
    public List<PointDto> getPoints(@RequestParam(required = false) Float swLat,
                                    @RequestParam(required = false) Float swLon,
                                    @RequestParam(required = false) Float neLat,
                                    @RequestParam(required = false) Float neLon,
                                    @RequestParam(required = false) Integer num) {
        var southWest = swLat == null || swLon == null ? null : new LatLon(swLat, swLon);
        var northEast = neLat == null || neLon == null ? null : new LatLon(neLat, neLon);
        var points = mapper.map(pointService.getPoints(southWest, northEast, num));
        log.info("{} points returned", points.size());
        return points;
    }

}
