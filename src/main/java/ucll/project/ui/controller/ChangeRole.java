package ucll.project.ui.controller;

import ucll.project.domain.user.Role;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ChangeRole extends RequestHandler {
    public ChangeRole(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role");
        String userName = request.getParameter("user");
        ArrayList<String> errors = new ArrayList<>();
        String confirm = "";
        if(role != null && !role.trim().isEmpty() && userName != null && !userName.trim().isEmpty()){
            Role role1 = Role.valueOf(role.toUpperCase());
            User user = getUserService().get(userName);
            user.setRole(role1);
            getUserService().update(user);
            if(role1 == Role.USER){
                confirm = "Role set to USER";
            }else if (role1 == Role.SUPER){
                confirm = "Role set to SUPER";
            } else {
                confirm = "Role set to ADMIN";
            }
        }else {
            errors.add("Role not given");
        }
        request.setAttribute("confirm",confirm);
        request.setAttribute("errorsRoleChange",errors);
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }
}
