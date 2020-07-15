package ucll.project.ui.controller;

import ucll.project.domain.user.Gender;
import ucll.project.domain.user.Role;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;
import ucll.project.model.StarServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddUser extends RequestHandler {
    public AddUser(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();
        Role role1 = Role.USER;
        User userToAdd = new User();
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String birthdate = request.getParameter("birthdate");
        if(username != null && !username.trim().isEmpty() && email!=null && !email.trim().isEmpty() && password != null && !password.trim().isEmpty()
        && birthdate != null && !birthdate.trim().isEmpty()){
            String[] date = birthdate.split("-");
            int year = Integer.parseInt(date[0].trim());
            int month = Integer.parseInt(date[1].trim());
            int day = Integer.parseInt(date[2].trim());
            LocalDate birthDate = LocalDate.of(year,month,day);
            if(role != null && !role.isEmpty()) {
                role1 = Role.valueOf(role.trim().toUpperCase());
            }
            Gender gender1 = Gender.valueOf(gender.trim().toUpperCase());
            try {
                userToAdd = new User(username.trim(),firstname.trim(),lastname.trim(),email.trim(),gender1,role1,3,birthDate);
            }catch (IllegalArgumentException e){
                errors.add(e.getMessage());
            }
            try {
                getUserService().addUsser(userToAdd,password);
            } catch (IllegalArgumentException e){
                errors.add(e.getMessage());
            }
        } else {
            errors.add("Not every necessary field was filled in.");
            request.setAttribute("usernamePrevious",username.trim());
            request.setAttribute("firstnamePrevious",firstname.trim());
            request.setAttribute("lastnamePrevious",lastname.trim());
            request.setAttribute("emailPrevious", email.trim());
            request.setAttribute("genderPrevious",gender.trim());
            request.setAttribute("rolePrevious",role.trim());
            request.setAttribute("passwordPrevious",password.trim());
            request.setAttribute("birthdayPrevious",birthdate.trim());
            request.setAttribute("errors",errors);
        }

        request.setAttribute("melding","Succesfully added user!");
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }
}
