package chat.onmap.positionserver.mapper;

import chat.onmap.positionserver.dto.LatLonDto;
import chat.onmap.positionserver.dto.PointDto;
import chat.onmap.positionserver.model.LatLon;
import chat.onmap.positionserver.model.Point;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.hamcrest.number.IsCloseTo.closeTo;

class PointMapperTest {

    private final PointMapper mapper = Mappers.getMapper(PointMapper.class);

    @Test
    public void pointToDtoTest() {
        Point point = Point.builder().location(new LatLon(52.530443, 13.408755)).build();
        PointDto pointDto = mapper.map(point);
        assertThat(pointDto.uuid).isEqualTo(point.getUuid());
        assertThat(pointDto.lat).isEqualTo((int) (point.getLocation().getLat() * 1000000));
        assertThat(pointDto.lon).isEqualTo((int) (point.getLocation().getLon() * 1000000));
    }

    @Test
    public void dtoToLatLonTest() {
        LatLonDto latLonDto = new LatLonDto(52530443, 13408755);
        LatLon latLon = mapper.map(latLonDto);
        assertThat(latLon.getLat()).isEqualTo(latLonDto.lat / 1000000., withPrecision(0.000001));
        assertThat(latLon.getLon()).isEqualTo(latLonDto.lon / 1000000., withPrecision(0.000001));

    }
}