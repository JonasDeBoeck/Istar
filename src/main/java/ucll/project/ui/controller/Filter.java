package ucll.project.ui.controller;

import ucll.project.domain.user.UserService;
import ucll.project.model.Star;
import ucll.project.model.StarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Filter extends RequestHandler {

    public Filter(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filter = request.getParameter("filter");
        filter = filter.replace(" ","");
        System.out.println(filter);
        List<Star> filtered = getStarService().getAllByUsername(filter.trim(),"receiver");
        if (filtered == null || filtered.isEmpty()){
            System.out.println("tagname");
            filtered = getStarService().getAllByTagname(filter.trim());
        }
        if (filtered == null || filtered.isEmpty()){
            List<String> errors = new ArrayList<>();
            errors.add("your filter didn't have any results");
            request.setAttribute("errors",errors);
        }
        System.out.println(filtered.size());
        System.out.println(filtered);
        request.setAttribute("stars",filtered);
        request.getRequestDispatcher("overview.jsp").forward(request,response);
    }
}
