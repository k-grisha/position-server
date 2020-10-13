package chat.onmap.positionserver.mapper;

import chat.onmap.positionserver.dto.PositionDto;
import chat.onmap.positionserver.model.Position;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper //(componentModel = "spring")
public interface PositionMapper {

    PositionDto map(Position model);

    List<PositionDto> map(List<Position> model);

}
