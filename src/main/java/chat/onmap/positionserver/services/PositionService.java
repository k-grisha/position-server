package chat.onmap.positionserver.services;

import chat.onmap.positionserver.model.Position;
import chat.onmap.positionserver.repository.PositionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PositionService {

    private final PositionRepository positionRepository;
    private final Position berliner;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
        berliner = Position.builder()
            .id(UUID.randomUUID())
            .lat(52536251)
            .lon(13436820)
            .lastUpdate(LocalDateTime.now())
            .build();
        this.positionRepository.saveOrUpdate(Position.builder()
            .id(UUID.randomUUID())
            .lat(47069980)
            .lon(2378352)
            .lastUpdate(LocalDateTime.now())
            .build());
        this.positionRepository.saveOrUpdate(Position.builder()
            .id(UUID.randomUUID())
            .lat(-1316095)
            .lon(36796962)
            .lastUpdate(LocalDateTime.now())
            .build());
        this.positionRepository.saveOrUpdate(Position.builder()
            .id(UUID.randomUUID())
            .lat(38647236)
            .lon(-90306432)
            .lastUpdate(LocalDateTime.now())
            .build());
    }

    public List<Position> getAll() {
        return positionRepository.findAll();
    }

    private final int LON_PER_METER = 15;

    @Scheduled(fixedRate = 1000)
    public void updateBerliner() {
        long newLon = berliner.getLon() + LON_PER_METER;
        if (newLon > 179999999) {
            newLon = -179999999;
        }
        berliner.setLon(newLon);
        positionRepository.saveOrUpdate(berliner);
    }

}
