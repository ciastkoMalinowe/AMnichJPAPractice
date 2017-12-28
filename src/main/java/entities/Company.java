package entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int companyId;
    private String companyName;


    private String street;

    private String zipCode;

    private String city;

    public Company(){}

    public Company(String name, String street, String zipCode, String city){
        this.companyName = name;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }
    public String getCompanyName() {
        return companyName;
    }
}
