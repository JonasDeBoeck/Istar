package ucll.project.ui.controller;

import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Comment extends RequestHandler {
    public Comment (String command, UserService userService, StarService starService) {super(command, userService, starService);}

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int starId = Integer.parseInt(request.getParameter("starId"));
        System.out.println(starId);
        String message = request.getParameter("comment");
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("session:" + user.getUserName());
        getCommentService().createComment(starId, message, user.getUserName());
        response.sendRedirect("Controller?command=ShowOverview");
    }
}
