package ucll.project.ui.controller;

import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.Star;
import ucll.project.model.StarService;
import ucll.project.model.StarServiceInterface;
import ucll.project.model.Tag;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowProfile extends RequestHandler {
    public ShowProfile(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.setAttribute("error", "You need more rights to view this page");
            request.getRequestDispatcher("Controller?command=ShowLogIn").forward(request, response);
        } else {
            User sessionUser = (User) request.getSession().getAttribute("user");
            List<Star> given = getStarService().getAllByUsername(sessionUser.getUserName(),"receiver");
            int amountStars = sessionUser.getStars();
            request.setAttribute("amountStars",amountStars);
            //System.out.println(sessionUser.getUserName());
            //System.out.println(given.size());
            //System.out.println(given);
            request.setAttribute("given",given);
            List<Star> sent = getStarService().getAllByUsername(sessionUser.getUserName(),"sender");
            request.setAttribute("sent",sent);
            int level = getStarService().getLevel(sessionUser.getUserName(),"#excel");
            request.setAttribute("level",level);
            request.getRequestDispatcher("UserInfo.jsp").forward(request, response);
        }
    }
}
