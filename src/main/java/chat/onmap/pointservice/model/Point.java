package chat.onmap.pointservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private LatLon location;
    private LocalDateTime lastUpdate;
    private String name;


    @PrePersist
    @PreUpdate
    protected void prePersist() {
        lastUpdate = LocalDateTime.now();
    }


}
