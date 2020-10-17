package chat.onmap.pointservice.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PointDto {

    public final UUID uuid;
    public final int lat;
    public final int lon;
}
