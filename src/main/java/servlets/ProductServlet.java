package servlets;

import entities.Product;
import services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import com.google.common.base.Strings;

@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {
    ProductService productService = ProductService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String purchasePrice = req.getParameter("purchasePrice");

        Optional<String> errorMassage = priceValidation(purchasePrice);
        if(errorMassage.isPresent()){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(errorMassage.get());
            return;
        }
//todo builder
        Product product = productService.create(
                new Product(name, description, Float.parseFloat(purchasePrice)));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Optional<String> priceValidation(String purchasePrice) {
        if(Strings.isNullOrEmpty(purchasePrice)){
            return Optional.of("Price can`t be empty");
        }
        float parsedPrice = Float.parseFloat(purchasePrice);
        return parsedPrice > 0 ? Optional.empty() : Optional.of("Price can't less then zero");
    }
}
