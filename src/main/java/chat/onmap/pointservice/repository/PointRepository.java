package chat.onmap.pointservice.repository;

import chat.onmap.pointservice.model.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface PointRepository extends JpaRepository<Point, UUID> {

    @Query("SELECT p FROM Point p WHERE p.location.lat BETWEEN :southWestLat AND :northEastLat " +
            "      AND p.location.lon  BETWEEN :southWestLon AND :northEastLon")
    List<Point> findVisible(@Param("southWestLat") Double southWestLat,
                            @Param("southWestLon") Double southWestLon,
                            @Param("northEastLat") Double northEastLat,
                            @Param("northEastLon") Double northEastLon,
                            Pageable pageable);

    void deleteAllByLastUpdateBefore(LocalDateTime localDateTime);

}
