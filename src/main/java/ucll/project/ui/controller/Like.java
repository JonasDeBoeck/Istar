package ucll.project.ui.controller;

import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Like extends RequestHandler {
    public Like (String command, UserService userService, StarService starService) {super(command, userService, starService);}

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int starId = Integer.parseInt(request.getParameter("sId"));
        String username = request.getParameter("username");
        if (getLikeService().doesLikeExist(starId, username)) {
            getLikeService().removeLike(starId, username);
            request.getRequestDispatcher("Controller?command=ShowOverview").forward(request, response);
        } else {
            getLikeService().createLikes(starId, username);
            request.getRequestDispatcher("Controller?command=ShowOverview").forward(request, response);
        }
    }
}
