package chat.onmap.positionserver.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PointDto {

    public final UUID uuid;
    public final float lat;
    public final float lon;
}
