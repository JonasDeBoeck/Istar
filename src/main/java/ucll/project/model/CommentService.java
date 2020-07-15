package ucll.project.model;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentService {
    public CommentService() {}

    public void createComment (int starId, String message, String username) {
        StarService starService = new StarService();
        UserService userService = new UserService();
        Star star = starService.get(starId);
        User user = userService.get(username);
        System.out.println("sql :" + user.getUserName());
        try (Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO \"award-team8\".comments (starid, message, commenter) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, starId);
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
