package servlets;

import entities.User;
import org.apache.commons.lang3.ObjectUtils;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (!ObjectUtils.allNotNull(email, password)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<User> user = userService.readByEmail(email, password);
        if (user.isPresent()) {
            HttpSession session = req.getSession(true);
            session.setAttribute("userFirstName", user.get().getFirstName());
            session.setAttribute("userLastName", user.get().getLastName());
            session.setAttribute("userId", user.get().getId());
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
