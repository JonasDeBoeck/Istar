package ucll.project.ui.controller;

import ucll.project.domain.user.UserService;
import ucll.project.model.StarService;
import ucll.project.model.StarServiceInterface;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private UserService service;
    private HandlerFactory handlerFactory;
    private StarService starService;

    @Override
    public void init() throws ServletException {
        super.init();
        handlerFactory = new HandlerFactory();
        service = new UserService();
        starService = new StarService();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String command = request.getParameter("command");
            if (command == null || command.trim().isEmpty()) {
                command = "ShowLogIn";
            }
            RequestHandler handler = handlerFactory.getHandler(command, service, starService);
            handler.handleRequest(request, response);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

}

