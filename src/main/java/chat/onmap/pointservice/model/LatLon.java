package chat.onmap.pointservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@Embeddable
public class LatLon {
    /// The latitude in degrees between -90.0 and 90.0, both inclusive.
    private Double lat;

    /// The longitude in degrees between -180.0 (inclusive) and 180.0 (exclusive).
    private Double lon;

    public LatLon(double lat, double lon) {
        this.lat = (lat < -90.0 ? -90.0 : (Math.min(90.0, lat)));
        this.lon = (lon + 180.0) % 360.0 - 180.0;
    }
}
