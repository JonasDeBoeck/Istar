package ucll.project.ui.controller;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AdminSearchUser extends RequestHandler {
    public AdminSearchUser(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        ArrayList<String> errors = new ArrayList<>();
        if(username != null && !username.trim().isEmpty()){
            User user = getUserService().get(username.trim());
            if(user!=null){
                request.setAttribute("userFound",user);
            }else {
                errors.add("User does not exist");
            }
        }else {
            errors.add("No username given");
        }
        request.setAttribute("errorsSearch",errors);
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }
}
