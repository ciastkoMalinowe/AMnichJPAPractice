package callbacks;

import entities.Customer;
import entities.transaction.Transact;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.MultipartConfigElement;
import java.util.List;

public class CustomerApi {

    private EntityManagerFactory emf;
    public CustomerApi(EntityManagerFactory emf){
        this.emf = emf;
    }

    public String addCustmer(Request req, Response res) {
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(""));

        Transact t = req.session().attribute("transaction");
        EntityManager em = req.session().attribute("entityManager");
        String company = req.queryParams("company");
        String street = req.queryParams("street");
        String zip = req.queryParams("zip");
        String city = req.queryParams("city");
        Customer customer = getCustomerByName(company, em);
        if(customer == null){
            customer = new Customer(company, street, zip, city);
            em.persist(customer);
        }
        customer.addTransaction(t);
        em.persist(t);
        em.getTransaction().commit();
        em.close();
        res.redirect("/");
        return "";
    }

    private Customer getCustomerByName(String name, EntityManager em){
        List<Customer> c = em.createQuery(
                "SELECT c FROM Customer c WHERE c.companyName LIKE :name")
                .setParameter("name", name)
                .getResultList();
        if(c.size() == 0)
            return null;
        return c.get(0);
    }
}
