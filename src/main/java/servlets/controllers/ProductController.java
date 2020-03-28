package servlets.controllers;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import entities.Product;
import services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/api/products")
public class ProductController extends HttpServlet {

    ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.readAll();
        String json = new Gson().toJson(products);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String price = req.getParameter("price");

        Optional<String> errorMassage = priceValidation(price);
        if(errorMassage.isPresent()){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(errorMassage.get());
            return;
        }

        Product product = new Product.Builder()
                .withName(name)
                .withDescription(description)
                .withPrice(Float.parseFloat(price))
                .build();
        productService.create(product);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Optional<String> priceValidation(String price) {
        if (Strings.isNullOrEmpty(price)) {
            return Optional.of("Price can't be empty");
        }
        try {
            float parsedPrice = Float.parseFloat(price);
            return parsedPrice > 0 ? Optional.empty() : Optional.of("Price can't less then zero");
        } catch (NumberFormatException e) {
            return Optional.of("Price should be numeric");
        }

    }
}
