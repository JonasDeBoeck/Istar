package ucll.project.ui.controller;

import ucll.project.domain.user.Role;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;
import ucll.project.model.StarServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAdmin extends RequestHandler {
    public ShowAdmin(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            request.setAttribute("error", "You don't have the necessary rights to view this page");
            request.getRequestDispatcher("Controller?command=ShowLogIn").forward(request, response);
        } else {
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        }
    }
}
