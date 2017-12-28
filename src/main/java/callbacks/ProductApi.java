package callbacks;

import entities.Product;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.MultipartConfigElement;
import java.util.List;

public class ProductApi {
    private EntityManagerFactory emf;
    public ProductApi(EntityManagerFactory emf){
        this.emf = emf;
    }

    public String addProduct(Request req, Response res) {
        EntityManager em = emf.createEntityManager();
        try {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(""));

            Integer unitsOnStock = Integer.parseInt(req.queryParams("unitsOnStock"));
            String name = req.queryParams("name");
            em.getTransaction().begin();

            Product product = getProductByName(name, em);
            if(product == null)
                product = new Product(name, 0);

            product.setUnitsOnStock(product.getUnitsOnStock()+unitsOnStock);

            em.persist(product);
            em.getTransaction().commit();

            res.redirect("/");
            return "";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    private Product getProductByName(String name, EntityManager em){
        List<Product> p = em.createQuery(
                "SELECT p FROM Product p WHERE p.productName LIKE :name")
                .setParameter("name", name)
                .getResultList();
        if(p.size() == 0)
            return null;
        return p.get(0);
    }

}
