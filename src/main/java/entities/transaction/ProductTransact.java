package entities.transaction;

import entities.Product;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class ProductTransact {

    private ProductTransactId pk = new ProductTransactId();
    private int quantity;

    public ProductTransact() {}

    @EmbeddedId
    public ProductTransactId getPk() {
        return pk;
    }

    public void setPk(ProductTransactId pk){
        this.pk = pk;
    }

    @Transient
    public Product getProduct(){
        return getPk().getProduct();
    }

    public void setProduct(Product p){
        getPk().setProduct(p);
    }

    @Transient
    public Transact getTransact(){
        return getPk().getTransact();
    }

    public void setTransact(Transact t){
        getPk().setTransact(t);
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return quantity;
    }

}
