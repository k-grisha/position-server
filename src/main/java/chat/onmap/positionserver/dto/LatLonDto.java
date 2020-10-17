package chat.onmap.positionserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LatLonDto {

    @NotNull
    @Min(value = -90000000, message = "Latitude mast be between -90.0 and 90.0")
    @Max(value = 90000000, message = "Latitude mast be between -90.0 and 90.0")
    public final Integer lat;

    @NotNull
    @Min(value = -180000000, message = "Longitude mast be between -180.0 and 180.0")
    @Max(value = 180000000, message = "Longitude mast be between -180.0 and 180.0")
    public final Integer lon;

}
