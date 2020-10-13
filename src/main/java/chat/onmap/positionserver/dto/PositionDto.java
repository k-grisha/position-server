package chat.onmap.positionserver.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class PositionDto {

    public final UUID id;
    public final long lat;
    public final long lon;
}
