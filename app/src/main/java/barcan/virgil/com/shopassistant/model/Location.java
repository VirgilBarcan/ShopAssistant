package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 28.11.2015.
 */
public class Location {

    private Double latitude;
    private Double longitude;

    private String fullAddress;
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

    private void initMemberData() {
        this.latitude = 0.0;
        this.longitude = 0.0;

        this.countryName = "";
        this.cityName = "";
        this.streetName = "";
        this.streetNo = "";
        this.ZIP = "";
        this.fullAddress = "";
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
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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
}
