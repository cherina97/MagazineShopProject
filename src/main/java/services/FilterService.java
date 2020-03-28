package services;

import daos.BucketDao;
import entities.UserRole;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FilterService {

    private static FilterService filterService;

    public FilterService() {

    }

    public static FilterService getInstance(){
        if (filterService == null){
            filterService = new FilterService();
        }
        return filterService;
    }

    public void doFilterValidation(ServletRequest req,
                                   ServletResponse resp,
                                   FilterChain filterChain,
                                   List <UserRole> userRoles) throws ServletException, IOException {
        try {
            HttpSession session = ((HttpServletRequest) req).getSession();
            UserRole role = UserRole.valueOf((String) session.getAttribute("userRole"));

            if (userRoles.contains(role)){
                filterChain.doFilter(req, resp);
            } else {
                req.getRequestDispatcher("cabinet.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.getRequestDispatcher("cabinet.jsp").forward(req, resp);
        }




    }
}
