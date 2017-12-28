import callbacks.CustomerApi;
import callbacks.ProductApi;
import callbacks.TransactionApi;
import entities.Customer;
import entities.Supplier;
import entities.Product;

import static spark.Spark.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class Main {

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.
                createEntityManagerFactory("myDatabaseConfig");

        addSomeProducts(emf);

        TransactionApi transactionApi = new TransactionApi(emf);
        CustomerApi customerApi = new CustomerApi(emf);
        ProductApi productApi = new ProductApi(emf);

        staticFiles.location("/public");

        post("/api/customer", customerApi::addCustmer);

        post("/api/task", productApi::addProduct);


        get("/list", transactionApi::listAll);

        post("/trans", transactionApi::createTransaction);

    }
    private static void addSomeProducts(EntityManagerFactory emf) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Supplier supplier1 = new Supplier("Dostawca","Ulica","00-000","Miasto");
        supplier1.setBankAccountNumber("1234567890");
        Customer customer1 = new Customer("Klient", "Inna Ulica", "11-111", "Inne Miasto");
        customer1.setDiscount(10);
        Supplier supplier2 = new Supplier("Kolejny Dostawca","Aleja","22-222","Większe Miasto");
        supplier2.setBankAccountNumber("0987654321");
        Customer customer2 = new Customer("Klient", "Dłuższa Ulica", "00-000", "Miasto");
        customer2.setDiscount(12);
        Product product1 = new Product("product_1",100);
        Product product2 = new Product ("product_2", 100);
        Product product3 = new Product("product_3", 100);
        supplier1.addProduct(product1);
        supplier2.addProduct(product2);
        supplier2.addProduct(product3);
        em.persist(supplier1);
        em.persist(supplier2);
        em.persist(customer1);
        em.persist(customer2);
        etx.commit();
        em.close();

    }

}
