package ucll.project.model;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikeService {
    public LikeService() {}

    public boolean doesLikeExist (int starId, String username) {
        boolean exists = false;
        StarService starService = new StarService();
        UserService userService = new UserService();
        Star star = starService.get(starId);
        User user = userService.get(username);
        try (Connection connection = ConnectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"award-team8\".likes WHERE starid = ? AND liker = ?")) {
            preparedStatement.setInt(1, starId);
            preparedStatement.setInt(2, user.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exists;
    }

    public void removeLike (int starId, String username) {
        StarService starService = new StarService();
        UserService userService = new UserService();
        Star star = starService.get(starId);
        User user = userService.get(username);
        try (Connection connection = ConnectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"award-team8\".likes WHERE starid = ? AND liker = ?")) {
            preparedStatement.setInt(1, starId);
            preparedStatement.setInt(2, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createLikes (int starId, String username) {
        StarService starService = new StarService();
        UserService userService = new UserService();
        Star star = starService.get(starId);
        User user = userService.get(username);
        try (Connection connection = ConnectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO \"award-team8\".likes (starid, liker) VALUES (?, ?)")) {
            preparedStatement.setInt(1, starId);
            preparedStatement.setInt(2, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Integer> getStarsByUserLikes (String username) {
        UserService userService = new UserService();
        User user = userService.get(username);
        List<Integer> lijstStarIds = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT starid FROM\"award-team8\".likes WHERE liker = ?")){
            preparedStatement.setInt(1, user.getUserId());
            ResultSet ids = preparedStatement.executeQuery();
            while (ids.next()){
                lijstStarIds.add(ids.getInt("starid"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lijstStarIds;
    }
}
