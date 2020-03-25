package servlets;

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
import com.google.common.base.Strings;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {
    ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("id");
        Optional<Product> product = productService.read(Integer.parseInt(productId));

        request.setAttribute("productName", product.get().getName());
        request.setAttribute("productDescription", product.get().getDescription());
        request.setAttribute("productPrice", product.get().getPrice());
        request.setAttribute("productId", product.get().getId());

        request.getRequestDispatcher("showProductDetails.jsp").forward(request, response);
    }
}
