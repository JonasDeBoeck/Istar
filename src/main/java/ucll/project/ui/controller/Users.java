package ucll.project.ui.controller;

import ucll.project.domain.user.UserRepository;
import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;
import ucll.project.model.StarServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Users extends RequestHandler {
    public Users(String command, UserService userService, StarService starService){
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.setAttribute("error", "You have to login to reach the next page.");
            request.getRequestDispatcher("Controller?command=ShowLogIn").forward(request, response);
        } else {
            request.setAttribute("users", this.getUserService().getUsers());
            request.getRequestDispatcher("users.jsp").forward(request, response);
        }
    }
}
