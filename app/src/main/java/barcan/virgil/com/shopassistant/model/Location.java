package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 28.11.2015.
 */
public class Location {

    private String locationID;
    private Double latitude;
    private Double longitude;

    private String countryName;
    private String cityName;
    private String streetName;
    private String streetNo;
    private String ZIP;

    public Location() {
        initMemberData();
    }

    public Location(Double latitude, Double longitude) {
        initMemberData();

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Location location) {
        this.locationID = new String(location.getLocationID());
        this.latitude = new Double(location.getLatitude());
        this.longitude = new Double(location.getLongitude());

        this.countryName = new String(location.getCountryName());
        this.cityName = new String(location.getCityName());
        this.streetName = new String(location.getStreetName());
        this.streetNo = new String(location.getStreetNo());
        this.ZIP = new String(location.getZIP());
    }

    private void initMemberData() {
        this.locationID = "";
        this.latitude = 0.0;
        this.longitude = 0.0;

        this.countryName = "";
        this.cityName = "";
        this.streetName = "";
        this.streetNo = "";
        this.ZIP = "";
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFullAddress() {
        return this.streetName + ", " + this.streetNo + ", " + this.cityName + ", " + this.countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationID='" + locationID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", countryName='" + countryName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNo='" + streetNo + '\'' +
                ", ZIP='" + ZIP + '\'' +
                '}';
    }
}
