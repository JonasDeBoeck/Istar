package ucll.project.ui.controller;

import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MailReceive extends RequestHandler {
    public MailReceive(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String answer = request.getParameter("answer");
        String userName = request.getParameter("user");
        User user = getUserService().get(userName);
        if(answer.equalsIgnoreCase("no")){
            user.setReceivingEmails(false);
        }else {
            user.setReceivingEmails(true);
        }
        getUserService().update(user);
        request.setAttribute("confirm","Mails received updated");
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }
}
