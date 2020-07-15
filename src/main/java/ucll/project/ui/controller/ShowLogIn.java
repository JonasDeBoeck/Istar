package ucll.project.ui.controller;

import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;
import ucll.project.model.StarServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowLogIn extends RequestHandler {
    public ShowLogIn(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.getRequestDispatcher("logIn.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Controller?command=ShowOverview").forward(request, response);
        }
    }
}
