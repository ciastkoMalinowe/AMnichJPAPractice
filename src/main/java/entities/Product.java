package entities;

import entities.transaction.ProductTransact;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;
    private String productName;
    private int unitsOnStock;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name="Supplier_fk")
    private Supplier isSuppliedBy;

    @OneToMany(mappedBy = "pk.product", cascade = {CascadeType.ALL})
    private Set<ProductTransact> productTransacts;

    public Product(){ }

    public Product(String name, int unitsOnStock){
        this.productName = name;
        this.unitsOnStock = unitsOnStock;
        this.productTransacts = new HashSet<>();
    }

    public void setSupplier(Supplier supplier){
        this.isSuppliedBy = supplier;
    }

    public Supplier getSupplier() {
        return isSuppliedBy;
    }

    public String getProductName() {
        return productName;
    }

    public int getUnitsOnStock(){
        return unitsOnStock;
    }

    public void setUnitsOnStock(int unitsOnStock){
        this.unitsOnStock = unitsOnStock;
    }

    public Set<ProductTransact> getProductTransacts() {
        return productTransacts;
    }

    public void setProductTransacts(Set<ProductTransact> t){
        productTransacts = t;
    }

    public int getId(){
        return productId;
    }
}
