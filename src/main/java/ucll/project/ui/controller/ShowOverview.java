package ucll.project.ui.controller;

import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.Star;
import ucll.project.model.StarService;
import ucll.project.model.StarServiceInterface;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowOverview extends RequestHandler {
    public ShowOverview(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.setAttribute("error", "You have to login to reach the next page.");
            request.getRequestDispatcher("Controller?command=ShowLogIn").forward(request, response);
        } else {
            List<Star> starsGiven = getStarService().getAll();
            User user = (User) request.getSession().getAttribute("user");
            List<Integer> gegevenStars = getLikeService().getStarsByUserLikes(user.getUserName());
            request.setAttribute("likers", gegevenStars);
            request.setAttribute("stars",starsGiven);
            request.getRequestDispatcher("overview.jsp").forward(request, response);
        }
    }
}
