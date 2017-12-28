package callbacks;

import entities.Product;
import entities.transaction.ProductTransact;
import entities.transaction.Transact;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.MultipartConfigElement;
import java.util.List;

public class TransactionApi {

    private EntityManagerFactory emf;
    public TransactionApi(EntityManagerFactory emf){
        this.emf = emf;
    }

    public String listAll(Request req, Response res){
        EntityManager em = emf.createEntityManager();
        try {
            List<Product> products = em.createQuery("FROM Product").getResultList();

            StringBuilder builder = new StringBuilder();

            builder.append("<form method=\"post\" action=\"/trans\" enctype=\"multipart/form-data\">");

            builder.append("<style>\n" +
                    "table {\n" +
                    "    border-collapse: collapse;\n" +
                    "    width: 50%;\n" +
                    "}" +
                    "td, th {\n" +
                    "    border: 1px solid #dddddd;\n" +
                    "    text-align: left;\n" +
                    "    padding: 8px;\n" +
                    "}\n" +
                    "</style>");


            builder.append("<table><tr><th>Product</th><th>Units</th><th>UnitsOnStock</th></tr>\n");
            for (Product p : products) {
                builder.append("<tr><td>" + "<input type=\"checkbox\" onclick=\"var input = document.getElementById('"+
                        p.getProductName()+"'); " +
                        "if(this.checked){ " +
                        "input.disabled = false; " +
                        "input.focus();}" +
                        "else{input.disabled=true;}\"/> " +
                        p.getProductName() +
                        "</td><td><input type =\"text\" id=\""+p.getProductName() +
                        "\" name=\""+p.getProductName()+
                        "\" disabled=\"disabled\"/>" +
                        "</td><td>" + p.getUnitsOnStock() + "</td></tr>\n");
            }
            builder.append("</table>\n" +
                    "<br>" +
                    "<input type=\"submit\" value=\"Order!\"\\> " +
                    "</form>");

            return builder.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public String createTransaction(Request req, Response res){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Transact t = new Transact();
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(""));

        for(String param: req.queryParams()){
            if(req.queryParams(param) != null) {
                System.out.println(param);
                List<Product> p = em.createQuery(
                        "SELECT p FROM Product p WHERE p.productName LIKE :name")
                        .setParameter("name", param)
                        .getResultList();

                ProductTransact pT = new ProductTransact();
                pT.setProduct(p.get(0));
                pT.setQuantity(Integer.parseInt(req.queryParams(param)));
                pT.setTransact(t);
                t.getProductTransacts().add(pT);
            }
        }

        if(t.isValid()) {
            req.session(true);
            req.session().attribute("transaction", t);
            req.session().attribute("entityManager", em);
            res.redirect("addCustomer.html");
            return "";
        }
        else{
            List<Product> products = em.createQuery("FROM Product").getResultList();

            StringBuilder builder = new StringBuilder();

            builder.append("Choosen quantity not available");

            builder.append("<form method=\"post\" action=\"/trans\" enctype=\"multipart/form-data\">");

            builder.append("<style>\n" +
                    "table {\n" +
                    "    border-collapse: collapse;\n" +
                    "    width: 50%;\n" +
                    "}" +
                    "td, th {\n" +
                    "    border: 1px solid #dddddd;\n" +
                    "    text-align: left;\n" +
                    "    padding: 8px;\n" +
                    "}\n" +
                    "</style>");


            builder.append("<table><tr><th>Product</th><th>Units</th><th>UnitsOnStock</th></tr>\n");
            for(ProductTransact pt : t.getProductTransacts()){
                Product p = pt.getProduct();
                if(p.getUnitsOnStock() < pt.getQuantity()){
                    builder.append("<tr><td>" + "<input type=\"checkbox\" onclick=\"var input = document.getElementById('"+
                            p.getProductName()+"'); " +
                            "if(this.checked){ " +
                            "input.disabled = false; " +
                            "input.focus();}" +
                            "else{input.disabled=true;}\"/> " +
                            p.getProductName() +
                            "</td>"+"<td><input type =\"text\" id=\""+p.getProductName() +
                            "\" name=\""+p.getProductName()+
                            "\" disabled=\"disabled\"/>" +
                            "</td><td>" + p.getUnitsOnStock() + "</td></tr>\n");
                }
                else{
                    builder.append("<tr><td>" + "<input type=\"checkbox\" checked/> " +
                            p.getProductName() +
                            "</td>"+"<td><input type =\"text\" id=\""+p.getProductName() +
                            "\" name=\""+p.getProductName()+ "\" value=\""+pt.getQuantity()+"\">" +
                            "</td><td>" + p.getUnitsOnStock() + "</td></tr>\n");

                }
            }
            builder.append("</table>\n" +
                    "<br>" +
                    "<input type=\"submit\" value=\"Order!\"\\> " +
                    "</form>");
            return builder.toString();
        }
    }
}
