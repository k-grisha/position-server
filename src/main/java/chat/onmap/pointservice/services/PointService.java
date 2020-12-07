package chat.onmap.pointservice.services;

import chat.onmap.pointservice.model.LatLon;
import chat.onmap.pointservice.model.Point;
import chat.onmap.pointservice.repository.PointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${chat.onmap.point_service.outdated_threshold:10000}")
    private long outdatedThreshold;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;

        berlinerUuid = this.pointRepository.save(Point.builder()
                .uuid(UUID.randomUUID())
                .location(new LatLon(52.536229, 13.436820))
                .build()).getUuid();
//        this.pointRepository.save(Point.builder()
//                .uuid(UUID.randomUUID())
//                .location(new LatLon(52.535324, 13.438687))
//                .build());
//        this.pointRepository.save(Point.builder()
//                .uuid(UUID.randomUUID())
//                .location(new LatLon(52.514863, 13.434657))
//                .build());
//        this.pointRepository.save(Point.builder()
//                .uuid(UUID.randomUUID())
//                .location(new LatLon(52.219534, 13.413757))
//                .build());
//        this.pointRepository.save(Point.builder()
//                .uuid(UUID.randomUUID())
//                .location(new LatLon(57.195511, 13.348374))
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
    public Point updatePoint(UUID uuid, LatLon latLon) {
        Optional<Point> point = pointRepository.findById(uuid);
        return point
                .map(p -> {
                    p.setLocation(latLon);
                    return p;
                })
                .orElseGet(() -> pointRepository.save(Point.builder().uuid(uuid).location(latLon).build()));
    }

    private Integer validQuantity(Integer quantity) {
        return quantity == null || quantity < 1 || quantity > MAX_QUANTITY_OF_POINTS ? DEFAULT_QUANTITY_OF_POINTS : quantity;
    }


    @Scheduled(fixedRate = 5000)
    @Transactional
    public void deleteOutdated(){
        pointRepository.deleteAllByLastUpdateBefore(LocalDateTime.now().minusSeconds(outdatedThreshold));
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
