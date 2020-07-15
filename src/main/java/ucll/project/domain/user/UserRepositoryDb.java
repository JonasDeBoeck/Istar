package ucll.project.domain.user;

import ucll.project.db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDb implements UserRepository {

    @Override
    public void createUser(User user, String password) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"award-team8\".user (username, firstname, lastname, email, gender, role, password,stars,birthdate,level,image,receivingemails, updated) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            user.hashAndSetPassword(password);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getGender().toString());
            stmt.setString(6, user.getRole().toString());
            stmt.setString(7, user.getHashedPassword());
            stmt.setInt(8, user.getStars());
            stmt.setDate(9,java.sql.Date.valueOf(user.getBirthdate()));
            stmt.setString(10,user.getLevel());
            stmt.setNull(11, Types.NULL);
            stmt.setBoolean(12,user.isReceivingEmails());
            stmt.setBoolean(13,user.getUpdated());
            if (stmt.executeUpdate() == 0) {
                throw new RuntimeException("Failed to create user");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                generatedKeys.next();
                user.setUserId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(int id) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team8\".user WHERE id = ?"))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return userFromResult(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStars(int stars) {
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement("update \"award-team8\".user set stars = ? where id>0")){
            statement.setInt(1,stars);
            if (statement.executeUpdate() == 0){
                throw new IllegalArgumentException("unable to update stars");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User get(String userName) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team8\".user WHERE username ILIKE ?"))
        {
            stmt.setString(1, userName +"%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return userFromResult(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM \"award-team8\".user"))
        {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(userFromResult(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User loginUser(String email, String password) throws InvalidLogin {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team8\".user WHERE email = ?"))
        {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new InvalidLogin("An account with this email doesn't exist");
                }

                User user = userFromResult(rs);
                if (!user.isValidPassword(password)) {
                    throw new InvalidLogin("Invalid email and password combination");
                }

                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE \"award-team8\".user SET " +
                     "username = ?, firstname = ?, lastname = ?, email = ?, gender = ?, role = ?, password = ? , stars = ?,birthdate =  ?, level = ?,receivingemails =?, updated=?" +
                     "WHERE id = ? "))
        {
            //int i = stmtSetUser(stmt, 1, user);
            stmt.setString(1,user.getUserName());
            stmt.setString(2,user.getFirstName());
            stmt.setString(3,user.getLastName());
            stmt.setString(4,user.getEmail());
            stmt.setString(6,user.getRole().toString());
            stmt.setString(5,user.getGender().toString());
            stmt.setString(7,user.getHashedPassword());
            stmt.setInt(8,user.getStars());
            stmt.setDate(9,java.sql.Date.valueOf(user.getBirthdate()));
            stmt.setString(10,user.getLevel());
            stmt.setBoolean(11,user.isReceivingEmails());
            stmt.setBoolean(12,user.getUpdated());
            stmt.setInt(13,user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM \"award-team8\".user WHERE id = ?"))
        {
            stmt.setInt(1, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUsersWhoWantsEmails () {
        List<User> users = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team8\".user WHERE receivingemails = ?")) {
            stmt.setBoolean(1, true);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                users.add(userFromResult(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private static User userFromResult(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("id"));
        user.setUserName(rs.getString("username"));
        user.setFirstName(rs.getString("firstname"));
        user.setLastName(rs.getString("lastname"));
        user.setEmail(rs.getString("email"));
        user.setGender(Gender.valueOf(rs.getString("gender")));
        user.setRole(Role.valueOf(rs.getString("role")));
        user.setHashedPassword(rs.getString("password"));
        user.setStars(rs.getInt("stars"));
        user.setBirthdate(rs.getDate("birthdate").toLocalDate());
        user.setReceivingEmails(rs.getBoolean("receivingemails"));
        user.setUpdated(rs.getBoolean("updated"));
        return user;
    }

    private static int stmtSetUser(PreparedStatement stmt, int i, User user) throws SQLException {
        stmt.setString(i++, user.getUserName());
        stmt.setString(i++, user.getFirstName());
        stmt.setString(i++, user.getLastName());
        stmt.setString(i++, user.getEmail());
        stmt.setString(i++, user.getGender().toString());
        stmt.setString(i++, user.getRole().toString());
        stmt.setString(i++, user.getHashedPassword());
        stmt.setInt(i++, user.getStars());
        stmt.setDate(i++,java.sql.Date.valueOf(user.getBirthdate()));
        stmt.setString(i++,user.getLevel());
        stmt.setBoolean(i++,user.isReceivingEmails());
        stmt.setNull(i++, Types.NULL);
        stmt.setBoolean(i++,user.getUpdated());
        return i;
    }
}