package servlets;

import daos.UserDao;
import entities.User;
import entities.UserRole;
import org.apache.commons.lang3.ObjectUtils;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (ObjectUtils.allNotNull(firstName, lastName, email, password)
                && !userService.readByEmail(email).isPresent()) {
            userService.create(new User(firstName, lastName, email, UserRole.USER.toString(), password));
            req.setAttribute("userEmail", email);
            req.getRequestDispatcher("cabinet.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }
}
