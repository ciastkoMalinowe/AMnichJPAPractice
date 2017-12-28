package entities;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String street;
    private String zipCode;
    private String city;

    public Address(String street, String zipCode, String city){
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Address(){}

    public void setCity(String city){
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }
}
