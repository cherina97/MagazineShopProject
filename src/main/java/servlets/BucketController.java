package servlets;

import com.google.gson.Gson;
import entities.Bucket;
import entities.Product;
import services.BucketService;
import services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/api/buckets")
public class BucketController extends HttpServlet {
    private BucketService bucketService = BucketService.getInstance();
    private ProductService productService = ProductService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");
        int userId = (int) req.getSession().getAttribute("userId");

        bucketService.create(new Bucket.Builder()
                .withUserId(userId)
                .withProductId(Integer.parseInt(productId))
                .withDate(new Date())
                .build());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = (int) req.getSession().getAttribute("userId");

        List<Bucket> allByUserId = bucketService.getAllByUserId(userId);
        Set<Integer> productIds = allByUserId.stream().map(Bucket::getProduct_id).collect(Collectors.toSet());
        List<Product> products = productService.readAllByIds(productIds);

        String json = new Gson().toJson(products);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("productId");
        int userId = (int) req.getSession().getAttribute("userId");

        bucketService.deleteBucketByUserAndProductIds(productId, userId);
    }
}


