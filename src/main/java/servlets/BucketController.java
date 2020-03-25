package servlets;

import entities.Bucket;
import services.BucketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/api/buckets")
public class BucketController extends HttpServlet {

    private BucketService bucketService = BucketService.getInstance();

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
}
