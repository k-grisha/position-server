package chat.onmap.pointservice.mapper;

import chat.onmap.pointservice.dto.LatLonDto;
import chat.onmap.pointservice.dto.PointDto;
import chat.onmap.pointservice.model.LatLon;
import chat.onmap.pointservice.model.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface PointMapper {

    @Mappings({
            @Mapping(target = "lat", source = "location.lat", qualifiedByName = "locationDoubleToInt"),
            @Mapping(target = "lon", source = "location.lon", qualifiedByName = "locationDoubleToInt")
    })
    PointDto map(Point model);

    @Mappings({
            @Mapping(target = "lat", source = "lat", qualifiedByName = "locationIntToDouble"),
            @Mapping(target = "lon", source = "lon", qualifiedByName = "locationIntToDouble")
    })
    LatLon map(LatLonDto dto);

    List<PointDto> map(List<Point> model);

    @Named("locationIntToDouble")
    static double locationIntToDouble(int pos) {
        return pos / 1000000.;
    }

    @Named("locationDoubleToInt")
    static int locationDoubleToInt(double pos) {
        return (int) (pos * 1000000);
    }

}
