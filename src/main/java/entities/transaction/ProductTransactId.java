package entities.transaction;

import entities.Product;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ProductTransactId implements Serializable{

    private Product product;
    private Transact transact;

    @ManyToOne
    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    public Transact getTransact(){
        return transact;
    }

    public void setTransact(Transact transact) {
        this.transact = transact;
    }
}
