package cz.cvut.fel.rsp.travelandwork.dto;

import javax.persistence.Basic;
import javax.validation.constraints.NotNull;

public class AddressDto {

    @Basic(optional = false)
    @NotNull(message = "City cannot be blank")
    private String city;

    @Basic(optional = false)
    @NotNull(message = "Street cannot be blank")
    private String street;

    @Basic(optional = false)
    private int houseNumber;

    @Basic(optional = false)
    @NotNull(message = "ZIP code cannot be blank")
    private String zipCode;

    @Basic(optional = false)
    @NotNull(message = "Country cannot be blank")
    private String country;

    private UserDto user;


    public AddressDto(@NotNull(message = "City cannot be blank") String city, @NotNull(message = "Street cannot be blank") String street,
                      int houseNumber, @NotNull(message = "ZIP code cannot be blank") String zipCode,
                      @NotNull(message = "Country cannot be blank") String country, UserDto user) {

        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.country = country;
        this.user = user;
    }


    public AddressDto() {
    }


    public String getCity() {

        return city;
    }


    public void setCity(String city) {

        this.city = city;
    }


    public String getStreet() {

        return street;
    }


    public void setStreet(String street) {

        this.street = street;
    }


    public int getHouseNumber() {

        return houseNumber;
    }


    public void setHouseNumber(int houseNumber) {

        this.houseNumber = houseNumber;
    }


    public String getZipCode() {

        return zipCode;
    }


    public void setZipCode(String zipCode) {

        this.zipCode = zipCode;
    }


    public String getCountry() {

        return country;
    }


    public void setCountry(String country) {

        this.country = country;
    }


    public UserDto getUser() {

        return user;
    }


    public void setUser(UserDto user) {

        this.user = user;
    }
}