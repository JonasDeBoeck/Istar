package ucll.project.ui.controller;

//import jdk.nashorn.internal.ir.RuntimeNode;
import ucll.project.domain.user.Role;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.Star;
import ucll.project.model.StarService;
import ucll.project.model.Tag;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class SendStar extends RequestHandler {
    private final String mail = "project1920team8@gmail.com";
    private final String password = "yqwilrhhslwzralz";

    public SendStar(String command, UserService userService, StarService starService) {
        super(command, userService, starService);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sender = (User) request.getSession().getAttribute("user");
        ArrayList<String> errors = new ArrayList<>();
        if (sender.getStars() > 0) {
            //Ster versturen
            Star star = new Star();
            setReceiver(star, (request.getParameter("receiver")), request, response, errors);
            setSender(star, sender, request, response, errors);
            setMessage(star, request.getParameter("message"), request, response, errors);
            setDate(star, LocalDate.now(), request, response, errors);

            if (errors.isEmpty()) {
                String[] tagStringArray = request.getParameterValues("tags");
                List<String> allTags = new ArrayList<>();
                for (int i = 0; i < tagStringArray.length; i++) {
                    allTags.add(tagStringArray[i]);
                }
                request.setAttribute("tagsPrevious", tagStringArray);
                List<Tag> tagsList = new ArrayList<>();
                for (String tag : allTags) {
                    tagsList.add(new Tag(star, tag));
                }
                star.setTags(tagsList);
                getStarService().createStar(star);
                if(sender.getRole() == Role.USER) {
                    sender.subtractStar();
                    getUserService().update(sender);
                }
                //Mail versturen
                User recipient = getUserService().get(request.getParameter("receiver"));
                String mailRecipient = recipient.getEmail();
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");

                List<User> users = getUserService().getUserWhoWantsEmails();

                Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mail, password);
                    }
                });

                try {
                    Message message1 = new MimeMessage(session);
                    message1.setFrom(new InternetAddress(mail));
                    Address[] addressen = InternetAddress.parse(mailRecipient);
                    message1.setRecipients(Message.RecipientType.TO, addressen);
                    message1.setSubject("You have received a star!");
                    message1.setText("Dear " + recipient.getFirstName() + ",\n We wanted to inform you that you received a star from " + sender.getFirstName() + " " + sender.getLastName() + "\n Visit the website to find more info about the received star.\nwww.team-8.projectweek.be ,you can log in using your work email address" + "\n Kind regards,\n\n Istar.");
                    Transport.send(message1);
                } catch (MessagingException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    Message message1 = new MimeMessage(session);
                    message1.setFrom(new InternetAddress(mail));
                    if (!users.isEmpty()) {
                        for (User user : users) {
                            if (!mailRecipient.equals(user.getEmail())) {
                                message1.addRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                            }
                        }
                        message1.setSubject("Someone has received a star.");
                        message1.setText("Dear " + ",\n We wanted to inform you that " + recipient.getFirstName() + " " + recipient.getLastName() + " received a star from " + sender.getFirstName() + " " + sender.getLastName() + "." + "\n Kind regards,\n\n Istar.");
                        Transport.send(message1);
                    }
                    response.sendRedirect("Controller?command=ShowOverview");
                } catch (MessagingException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                request.setAttribute("errors", errors);
                RequestHandler requestHandler = new ShowOverview("ShowOverview", getUserService(), (StarService) getStarService());
                requestHandler.handleRequest(request, response);
            }
        }
    }

    private void setReceiver(Star star, String receiver, HttpServletRequest request, HttpServletResponse response, ArrayList<String> errors) {
        try {
            star.setReceiver(receiver);
            request.setAttribute("receiverPrev", receiver);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
        }
    }

    private void setSender(Star star, User sender, HttpServletRequest request, HttpServletResponse response, ArrayList<String> errors) {
        try {
            star.setSender(sender);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
        }
    }

    private void setMessage(Star star, String message, HttpServletRequest request, HttpServletResponse response, ArrayList<String> errors) {
        try {
            star.setMessage(message);
            request.setAttribute("message", message);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
        }
    }

    private void setDate(Star star, LocalDate date, HttpServletRequest request, HttpServletResponse response, ArrayList<String> errors) {
        try {
            star.setDate(date);
            request.setAttribute("date", date);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
        }
    }
}
