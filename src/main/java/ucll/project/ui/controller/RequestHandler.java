package ucll.project.ui.controller;

import ucll.project.domain.user.*;
import ucll.project.model.CommentService;
import ucll.project.model.LikeService;
import ucll.project.model.StarService;
import ucll.project.model.StarServiceInterface;
import ucll.project.ui.controller.ControllerException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class RequestHandler {
    private String command;
    private UserService userService;
    private StarService starService;
    private LikeService likeService;
    private CommentService commentService;

    private void setStarService(StarService starService) {
        this.starService = starService;
    }

    public void setCommentService() {
        this.commentService = new CommentService();
    }

    private void setLikeService() {
        this.likeService = new LikeService();
    }

    public LikeService getLikeService() {
        return likeService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public RequestHandler(String command, UserService userService, StarService starService){
        setCommand(command);
        setUserService(userService);
        setStarService(starService);
        setLikeService();
        setCommentService();
    }

    private void setCommand(String command) {
        if (command == null || command.trim().isEmpty()){
            throw new ControllerException("Command is empty");
        }
        this.command = command;
    }

    private void setUserService(UserService userService){
        if (userService == null){
            throw new ControllerException("User service cannot be null.");
        }
        this.userService = userService;
    }

    public UserService getUserService(){ return userService; }

    public abstract void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    public String getCommand() {
        return command;
    }

    void forwardRequest(String destination, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request, response);
    }

    public StarServiceInterface getStarService() {
        return starService;
    }
}
