package chat.onmap.positionserver.controller;

import chat.onmap.positionserver.dto.PositionDto;
import chat.onmap.positionserver.mapper.PositionMapper;
import chat.onmap.positionserver.services.PositionService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class PositionController {

    private final PositionService positionService;
    private final PositionMapper mapper = Mappers.getMapper(PositionMapper.class);

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }


    @GetMapping("/all-positions")
    public List<PositionDto> getAll() {
        log.info("all position request");
        return mapper.map(positionService.getAll());
    }

}
