package ucll.project.ui.controller;

import ucll.project.domain.user.InvalidLogin;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class LogIn extends RequestHandler {
    private boolean updated;
    //TODO Betere check opdat account bestaat of niet
    //TODO onthoud mijn account knop voordat je account opslaagt in sessie?
    //TODO email inlog niet case sensitive
    public LogIn (String command, UserService userService, StarService starService) {super (command, userService, starService);}

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String passwoord = request.getParameter("password");
        String email = request.getParameter("email");
        try {
            User loggedIn = getUserService().login(email, passwoord);
            request.getSession().setAttribute("user", loggedIn);
            response.sendRedirect("Controller?command=ShowOverview");
            updateStars(request);
        } catch (InvalidLogin e) {
            request.setAttribute("error", e.getMessage());
            if (!e.getMessage().equals("An account with this email doesn't exist")) {
                request.setAttribute("email", email);
            }
            request.getRequestDispatcher("Controller?command=ShowLogIn").forward(request, response);
        }
    }

    private void updateStars(HttpServletRequest request) {
        UserService service = new UserService();
        User sessionuser = (User)  request.getSession().getAttribute("user");
        this.updated = sessionuser.getUpdated();
        LocalDate todaydate = LocalDate.now();
        int today = todaydate.getDayOfMonth();
        if (today == 1 && !updated){
            System.out.println("updated");
            service.updateStars(3);
            sessionuser.setUpdated(true);
        }else if (today == 2){
            sessionuser.setUpdated(false);
            List<User> users = getUserService().getUsers();
            for (User user:users){
                getUserService().update(user);
            }
        }
    }
}
