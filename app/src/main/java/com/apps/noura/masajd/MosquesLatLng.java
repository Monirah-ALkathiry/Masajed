package com.apps.noura.masajd;

/**
 * Created by Monirah on 11/12/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MosquesLatLng {

    @SerializedName("Mosque_Name")
    @Expose
    private String mosqueName;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("City_Village")
    @Expose
    private String cityVillage;
    @SerializedName("District")
    @Expose
    private String district;
    @SerializedName("Street_Name")
    @Expose
    private String streetName;
    @SerializedName("Mosque_Catogery")
    @Expose
    private String mosqueCatogery;
    @SerializedName("Mosque_Status")
    @Expose
    private String mosqueStatus;
    @SerializedName("Parking")
    @Expose
    private String parking;
    @SerializedName("Asphalt_Road")
    @Expose
    private String asphaltRoad;
    @SerializedName("Constructing_Date")
    @Expose
    private String constructingDate;
    @SerializedName("Observer_Name")
    @Expose
    private String observerName;
    @SerializedName("Construction_Type")
    @Expose
    private String constructionType;
    @SerializedName("Easting")
    @Expose
    private String easting;
    @SerializedName("Northing")
    @Expose
    private String northing;
    @SerializedName("Zone_")
    @Expose
    private String zone;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;

    /**
     * No args constructor for use in serialization
     *
     * @param icons
     * @param cityVillage
     * @param s
     * @param latitude
     * @param longitude
     */
    public MosquesLatLng(int icons, String cityVillage, String s, String latitude, String longitude) {
    }

    /**
     *
     * @param region
     * @param mosqueName
     * @param parking
     * @param constructionType
     * @param streetName
     * @param code
     * @param easting
     * @param asphaltRoad
     * @param constructingDate
     * @param cityVillage
     * @param observerName
     * @param longitude
     * @param mosqueCatogery
     * @param latitude
     * @param district
     * @param northing
     * @param zone
     * @param mosqueStatus
     */
    public MosquesLatLng(String mosqueName, String code, String region, String cityVillage, String district, String streetName, String mosqueCatogery, String mosqueStatus, String parking, String asphaltRoad, String constructingDate, String observerName, String constructionType, String easting, String northing, String zone, String latitude, String longitude) {
        super();
        this.mosqueName = mosqueName;
        this.code = code;
        this.region = region;
        this.cityVillage = cityVillage;
        this.district = district;
        this.streetName = streetName;
        this.mosqueCatogery = mosqueCatogery;
        this.mosqueStatus = mosqueStatus;
        this.parking = parking;
        this.asphaltRoad = asphaltRoad;
        this.constructingDate = constructingDate;
        this.observerName = observerName;
        this.constructionType = constructionType;
        this.easting = easting;
        this.northing = northing;
        this.zone = zone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getMosqueName() {
        return mosqueName;
    }

    public void setMosqueName(String mosqueName) {
        this.mosqueName = mosqueName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCityVillage() {
        return cityVillage;
    }

    public void setCityVillage(String cityVillage) {
        this.cityVillage = cityVillage;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getMosqueCatogery() {
        return mosqueCatogery;
    }

    public void setMosqueCatogery(String mosqueCatogery) {
        this.mosqueCatogery = mosqueCatogery;
    }

    public String getMosqueStatus() {
        return mosqueStatus;
    }

    public void setMosqueStatus(String mosqueStatus) {
        this.mosqueStatus = mosqueStatus;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getAsphaltRoad() {
        return asphaltRoad;
    }

    public void setAsphaltRoad(String asphaltRoad) {
        this.asphaltRoad = asphaltRoad;
    }

    public String getConstructingDate() {
        return constructingDate;
    }

    public void setConstructingDate(String constructingDate) {
        this.constructingDate = constructingDate;
    }

    public String getObserverName() {
        return observerName;
    }

    public void setObserverName(String observerName) {
        this.observerName = observerName;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    public String getEasting() {
        return easting;
    }

    public void setEasting(String easting) {
        this.easting = easting;
    }

    public String getNorthing() {
        return northing;
    }

    public void setNorthing(String northing) {
        this.northing = northing;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "MosquesLatLng{" +
                "mosqueName='" + mosqueName + '\'' +
                ", code='" + code + '\'' +
                ", region='" + region + '\'' +
                ", cityVillage='" + cityVillage + '\'' +
                ", district='" + district + '\'' +
                ", streetName='" + streetName + '\'' +
                ", mosqueCatogery='" + mosqueCatogery + '\'' +
                ", mosqueStatus='" + mosqueStatus + '\'' +
                ", parking='" + parking + '\'' +
                ", asphaltRoad='" + asphaltRoad + '\'' +
                ", constructingDate='" + constructingDate + '\'' +
                ", observerName='" + observerName + '\'' +
                ", constructionType='" + constructionType + '\'' +
                ", easting='" + easting + '\'' +
                ", northing='" + northing + '\'' +
                ", zone='" + zone + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}