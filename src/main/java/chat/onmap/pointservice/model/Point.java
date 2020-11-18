package chat.onmap.pointservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @NotNull
    private UUID uuid;
    private LatLon location;
    private LocalDateTime lastUpdate;


    @PrePersist
    @PreUpdate
    protected void prePersist() {
        lastUpdate = LocalDateTime.now();
    }


}
