package com.example.nationinfo.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Country implements Parcelable {
    private String continent;
    private String capital;
    private String languages;
    private long geonameId;
    private Double south;
    private String isoAlpha3;
    private Double north;
    private String fipsCode;
    private String population;
    private Double east;
    private String isoNumeric;
    private String areaInSqKm;
    private String countryCode;
    private Double west;
    private String countryName;
    private String postalCodeFormat;
    private String continentName;
    private String currencyCode;

    public Country(JSONObject data) throws JSONException {
        this.continent = data.getString("continent");
        this.capital = data.getString("capital");
        this.languages = data.getString("languages");
        this.geonameId = data.getLong("geonameId");
        this.south = data.getDouble("south");
        this.isoAlpha3 = data.getString("isoAlpha3");
        this.north = data.getDouble("north");
        this.fipsCode = data.getString("fipsCode");
        this.population = data.getString("population");
        this.east = data.getDouble("east");
        this.isoNumeric = data.getString("isoNumeric");
        this.areaInSqKm = data.getString("areaInSqKm");
        this.countryCode = data.getString("countryCode");
        this.west = data.getDouble("west");
        this.countryName = data.getString("countryName");
        this.postalCodeFormat = data.getString("postalCodeFormat");
        this.continentName = data.getString("continentName");
        this.currencyCode = data.getString("currencyCode");
    }

    public String getContinent() {
        return continent;
    }

    public String getCapital() {
        return capital;
    }

    public String getLanguages() {
        return languages;
    }

    public long getGeonameId() {
        return geonameId;
    }

    public Double getSouth() {
        return south;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public Double getNorth() {
        return north;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public String getPopulation() {
        return population;
    }

    public Double getEast() {
        return east;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Double getWest() {
        return west;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getPostalCodeFormat() {
        return postalCodeFormat;
    }

    public String getContinentName() {
        return continentName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    protected Country(Parcel in) {
        continent = in.readString();
        capital = in.readString();
        languages = in.readString();
        geonameId = in.readLong();
        south = in.readDouble();
        isoAlpha3 = in.readString();
        north = in.readDouble();
        fipsCode = in.readString();
        population = in.readString();
        east = in.readDouble();
        isoNumeric = in.readString();
        areaInSqKm = in.readString();
        countryCode = in.readString();
        west = in.readDouble();
        countryName = in.readString();
        postalCodeFormat = in.readString();
        continentName = in.readString();
        currencyCode = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(continent);
        parcel.writeString(capital);
        parcel.writeString(languages);
        parcel.writeLong(geonameId);
        parcel.writeDouble(south);
        parcel.writeString(isoAlpha3);
        parcel.writeDouble(north);
        parcel.writeString(fipsCode);
        parcel.writeString(population);
        parcel.writeDouble(east);
        parcel.writeString(isoNumeric);
        parcel.writeString(areaInSqKm);
        parcel.writeString(countryCode);
        parcel.writeDouble(west);
        parcel.writeString(countryName);
        parcel.writeString(postalCodeFormat);
        parcel.writeString(continentName);
        parcel.writeString(currencyCode);
    }

    @Override
    public String toString() {
        return "Country{" +
                "continent='" + continent + '\'' +
                ", capital='" + capital + '\'' +
                ", languages='" + languages + '\'' +
                ", geonameId=" + geonameId +
                ", south=" + south +
                ", isoAlpha3='" + isoAlpha3 + '\'' +
                ", north=" + north +
                ", fipsCode='" + fipsCode + '\'' +
                ", population='" + population + '\'' +
                ", east=" + east +
                ", isoNumeric='" + isoNumeric + '\'' +
                ", areaInSqKm='" + areaInSqKm + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", west=" + west +
                ", countryName='" + countryName + '\'' +
                ", postalCodeFormat='" + postalCodeFormat + '\'' +
                ", continentName='" + continentName + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
