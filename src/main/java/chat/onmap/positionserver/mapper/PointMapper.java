package chat.onmap.positionserver.mapper;

import chat.onmap.positionserver.dto.PointDto;
import chat.onmap.positionserver.model.Point;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper //(componentModel = "spring")
public interface PointMapper {

    @Mappings({
            @Mapping(target="lat", source="location.lat"),
            @Mapping(target="lon", source="location.lon")
    })
    PointDto map(Point model);

    List<PointDto> map(List<Point> model);

}
