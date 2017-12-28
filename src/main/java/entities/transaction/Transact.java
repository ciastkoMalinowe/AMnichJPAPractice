package entities.transaction;

import entities.Customer;
import entities.Product;
import entities.transaction.ProductTransact;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Transact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionNumber;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "pk.transact", cascade = {CascadeType.ALL})
    private Set<ProductTransact> productTransacts = new HashSet<>(0);

    public Transact(){}

    public Transact(Set<ProductTransact> pT){
        productTransacts = pT;
    }


    public Set<ProductTransact> getProductTransacts() {
        return productTransacts;
    }

    public void setProductTransacts(Set<ProductTransact> pT){
        this.productTransacts = pT;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isValid(){
        for(ProductTransact pt: productTransacts){
            if(pt.getQuantity() > pt.getProduct().getUnitsOnStock())
                return false;
        }
        return true;
    }
}
