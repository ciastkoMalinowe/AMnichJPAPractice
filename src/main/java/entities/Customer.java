package entities;

import entities.transaction.Transact;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer extends Company {

    private int discount;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST})
    private Set<Transact> transactions = new HashSet<>();

    public Customer(){}

    public Customer(String name, String street, String zipCode, String city){
        super(name, street, zipCode, city);
        discount = 0;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount){
        this.discount = discount;
    }

    public Set<Transact> getTransactions(){
        return transactions;
    }

    public void addTransaction(Transact t) {
        this.transactions.add(t);
        t.setCustomer(this);
    }
}
