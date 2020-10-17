package chat.onmap.pointservice.services;

import chat.onmap.pointservice.model.LatLon;
import chat.onmap.pointservice.model.Point;
import chat.onmap.pointservice.repository.PointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PointService {

    private final PointRepository pointRepository;
    private final UUID berlinerUuid;
    private final int MAX_QUANTITY_OF_POINTS = 100;
    private final int DEFAULT_QUANTITY_OF_POINTS = 100;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;

        berlinerUuid = this.pointRepository.save(Point.builder()
                .location(new LatLon(51.536251, 13.436820))
                .lastUpdate(LocalDateTime.now())
                .build()).getUuid();
//        this.pointRepository.save(Point.builder()
//                .location(new LatLon(49, 13.436820))
//                .lastUpdate(LocalDateTime.now().minusHours(1))
//                .build());
//        this.pointRepository.save(Point.builder()
//                .location(new LatLon(48, 13.436820))
//                .lastUpdate(LocalDateTime.now().minusHours(2))
//                .build());
//        this.pointRepository.save(Point.builder()
//                .location(new LatLon(46, 13.436820))
//                .lastUpdate(LocalDateTime.now().minusHours(4))
//                .build());
//        this.pointRepository.save(Point.builder()
//                .location(new LatLon(47, 13.436820))
//                .lastUpdate(LocalDateTime.now().minusHours(3))
//                .build());
    }

    public List<Point> getPoints(final LatLon southWest, final LatLon northEast, final Integer quantity) {
        var pageRequest = PageRequest.of(0, validQuantity(quantity), Sort.by("lastUpdate").descending());
        if (southWest == null || northEast == null) {
            return pointRepository.findAll(pageRequest).getContent();
        }
        return pointRepository.findVisible(southWest.getLat(), southWest.getLon(), northEast.getLat(),
                northEast.getLon(), pageRequest);
    }

    @Transactional
    public Point savePoint(LatLon latLon) {
        return pointRepository.save(Point.builder().location(latLon).build());
    }

    @Transactional
    public Point updatePoint(UUID uuid, LatLon latLon) {
        Optional<Point> point = pointRepository.findById(uuid);
        return point
                .map(p -> {
                    p.setLocation(latLon);
                    return p;
                })
                .orElseGet(() -> pointRepository.save(Point.builder().location(latLon).build()));
    }

    private Integer validQuantity(Integer quantity) {
        return quantity == null || quantity < 1 || quantity > MAX_QUANTITY_OF_POINTS ? DEFAULT_QUANTITY_OF_POINTS : quantity;
    }


    @Scheduled(fixedRate = 3000)
    @Transactional
    public void updateBerliner() {
        final double LON_PER_METER = 0.000015;
//        Point berliner= pointRepository.getOne(berlinerUuid);
////        long newLon = berliner.getLon() + LON_PER_METER;
////        if (newLon > 179999999) {
////            newLon = -179999999;
////        }
//        berliner.setLon(new Random().nextInt());
//        positionRepository.save(berliner);

        Optional<Point> berliner = pointRepository.findById(berlinerUuid);
        berliner.ifPresent(p -> {
            double newLon = p.getLocation().getLon() + (LON_PER_METER * 10);
            if (newLon > 179.999999) {
                newLon = -179.999999;
            }
            p.getLocation().setLon(newLon);
        });

    }

}
