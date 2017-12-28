package entities;

import entities.Company;
import entities.Product;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Supplier extends Company {

    @OneToMany(mappedBy = "isSuppliedBy", cascade = {CascadeType.PERSIST})
    private Set<Product> products;

    private String bankAccountNumber;
    public Supplier(){}

    public Supplier(String name, String street, String zipCode, String city){
        super(name, street, zipCode, city);
        products = new HashSet<Product>();
    }

    public void addProduct(Product p){
        products.add(p);
        p.setSupplier(this);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public String getBankAccountNumber(){
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber){
        this.bankAccountNumber = bankAccountNumber;
    }
}
