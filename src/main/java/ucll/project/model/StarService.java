package ucll.project.model;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserRepositoryDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarService implements StarServiceInterface{

    public StarService(){

    }

    @Override
    public void createStar(Star star) {
        try(Connection conn = ConnectionPool.getConnection(); PreparedStatement stmt = conn.prepareStatement("insert into \"award-team8\".star (receiver,sender,message, date) values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            PreparedStatement tagstatement = conn.prepareStatement("insert into \"award-team8\".tags (starid,tag) values(?,?)")){
            stmt.setInt(1,star.getReceiver().getUserId());
            stmt.setInt(2,star.getSender().getUserId());
            stmt.setString(3,star.getMessage());
            stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            if (stmt.executeUpdate() == 0) {
                throw new RuntimeException("Failed to create star");
            }
            //get the serial id we just created
            try(ResultSet generatedkeys = stmt.getGeneratedKeys()){
                if (generatedkeys.next()){
                    star.setId(generatedkeys.getInt(1));
                }
            }
            if (star.getTags() != null && !star.getTags().isEmpty()) {
                for (Tag tag : star.getTags()) {

                    tagstatement.setInt(1, star.getId());
                    tagstatement.setString(2, tag.getMessage());
                    if (tagstatement.executeUpdate() == 0){
                        throw new IllegalArgumentException("unable to create tag" + tag);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Star get(int id){
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select id,receiver,sender,message, date from \"award-team8\".star where id =?")){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int starid = rs.getInt("id");
                    UserRepositoryDb userdb = new UserRepositoryDb();
                    int receiver = rs.getInt("receiver");
                    User userReceiver = userdb.getUser(receiver);
                    int sender = rs.getInt("sender");
                    User userSender = userdb.getUser(sender);
                    String message = rs.getString("message");
                    LocalDate datum = rs.getDate("date").toLocalDate();
                    Star star = new Star(userReceiver,userSender,message, datum);
                    star.setId(starid);
                    List<Comment> commentslist = getComments(starid,star,userSender);
                    List<Like> likelist = getLikes(starid,star,userSender);
                    List<Tag> taglist = getTags(starid,star);
                    star.setComments(commentslist);
                    star.setLikes(likelist);
                    star.setTags(taglist);
                    return star;
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Tag> getTags(int tags, Star star) {
        List<Tag> taglist = new ArrayList<>();
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select id,starid,tag from \"award-team8\".tags where starid = ?")){

            stmt.setInt(1,tags);
            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Tag tag = new Tag(star,rs.getString("tag"));
                    taglist.add(tag);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taglist;
    }

    private List<Like> getLikes(int likes, Star star, User userSender) {
        List<Like> likeslist = new ArrayList<>();
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select id,starid,liker from \"award-team8\".likes where starid = ?")){
            stmt.setInt(1,likes);
            try (ResultSet rs = stmt.executeQuery()){
                UserRepositoryDb userdb = new UserRepositoryDb();
                while(rs.next()){
                    Like like = new Like(star,userdb.getUser(rs.getInt("liker")));
                    likeslist.add(like);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likeslist;
    }

    private List<Comment> getComments(int comments,Star star,User sender) {
        List<Comment> commentslist = new ArrayList<>();
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select id,starid,message,commenter from \"award-team8\".comments where starid = ?")){
            stmt.setInt(1,comments);
            try (ResultSet rs = stmt.executeQuery()){
                UserRepositoryDb service = new UserRepositoryDb();
                while(rs.next()){
                    User user = service.getUser(rs.getInt("commenter"));
                    Comment comment = new Comment(star,rs.getString("message"),user);
                    commentslist.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentslist;
    }

    @Override
    public List<Star> getAll() {
        List<Star> stars = new ArrayList<>();
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select id,receiver,sender,message, date from \"award-team8\".star order by id desc ")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int starid = rs.getInt("id");
                    UserRepositoryDb userdb = new UserRepositoryDb();
                    int receiver = rs.getInt("receiver");
                    User userReceiver = userdb.getUser(receiver);
                    int sender = rs.getInt("sender");
                    User userSender = userdb.getUser(sender);
                    String message = rs.getString("message");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    Star star = new Star(userReceiver,userSender,message, date);
                    star.setId(starid);
                    List<Comment> commentslist = getComments(starid,star,userSender);
                    List<Like> likelist = getLikes(starid,star,userSender);
                    List<Tag> taglist = getTags(starid,star);
                    star.setComments(commentslist);
                    star.setLikes(likelist);
                    star.setTags(taglist);
                    stars.add(star);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return stars;
    }

    @Override
    public void update(Star star) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE \"award-team8\".star SET " +
                     "receiver = ?, sender = ?, message = ? WHERE id = ? "))
        {
            stmt.setInt(1,star.getReceiver().getUserId());
            stmt.setInt(2,star.getSender().getUserId());
            stmt.setString(3,star.getMessage());
            stmt.setInt(4,star.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM \"award-team8\".likes WHERE starid = ?");
             PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM \"award-team8\".comments WHERE starid = ?");
             PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM \"award-team8\".tags WHERE starid = ?");
             PreparedStatement stmt4 = conn.prepareStatement("DELETE FROM \"award-team8\".star WHERE id = ?");)
        {
            stmt.setInt(1, id);
            stmt2.setInt(1,id);
            stmt3.setInt(1,id);
            stmt4.setInt(1,id);
            stmt.executeUpdate();
            stmt2.executeUpdate();
            stmt3.executeUpdate();
            stmt4.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Star> getAllByTagname(String tagname){
        List<Star> stars = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement("select starid from \"award-team8\".tags where tag = ?")){
            statement.setString(1,tagname);
            List<Integer> ids = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                ids.add(rs.getInt("starid"));
            }
            for (Integer id: ids){
                  stars.add(get(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return stars;
    }

    public Star getReceived(int receivedid){
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select id,receiver,sender,message,date from \"award-team8\".star where receiver =?")){
            stmt.setInt(1,receivedid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int starid = rs.getInt("id");
                    UserRepositoryDb userdb = new UserRepositoryDb();
                    int receiver = rs.getInt("receiver");
                    User userReceiver = userdb.getUser(receiver);
                    int sender = rs.getInt("sender");
                    User userSender = userdb.getUser(sender);
                    String message = rs.getString("message");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    Star star = new Star(userReceiver,userSender,message,date);
                    star.setId(starid);
                    List<Comment> commentslist = getComments(starid,star,userSender);
                    List<Like> likelist = getLikes(starid,star,userSender);
                    List<Tag> taglist = getTags(starid,star);
                    star.setComments(commentslist);
                    star.setLikes(likelist);
                    star.setTags(taglist);
                    return star;
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Star> getAllByUsername(String username,String senderReceiver){
        List<Star> stars = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement("select id from \"award-team8\".user where username = ?");
            PreparedStatement statement1 = conn.prepareStatement("select id from \"award-team8\".star where " + senderReceiver + " = ?")){
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                statement1.setInt(1,id);
                ResultSet set = statement1.executeQuery();
                while (set.next()){
                    System.out.println( senderReceiver + ": " + set.getInt("id"));
                    stars.add(get(set.getInt("id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return stars;
    }

    public int getLevel(String username,String tagname){
        int levelint;
        try (Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("select count(tag) as level from \"award-team8\".tags tags inner join (select id from \"award-team8\".star \n" +
                    "where receiver = (select id from \"award-team8\".user\n" +
                    "where username = ?)) as stars on (tags.starid = stars.id)\n" +
                    "where tag = ?")) {
            statement.setString(1,username);
            statement.setString(2,tagname);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                levelint = rs.getInt("level");
                return levelint;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Star> getStarsForLastMonths(int months){
        List<Star> stars = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("select id from \"award-team8\".star where date >= date_trunc('month',now()) -  ?::interval ")){
            String querystring = months + " month";
            statement.setString(1,querystring);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                stars.add(get(set.getInt("id")));
            }
            return stars;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<User, Map<String,Integer>> getSentAndReceivedPerEmployee(){
        Map<User, Map<String, Integer>> result = new HashMap<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("select s.id as employee,\n" +
                    "(select count(*)\n" +
                    "from \"award-team8\".user left outer join \"award-team8\".star on (s.id = \"award-team8\".star.sender) \n" +
                    "where \"award-team8\".user.id = s.id) as sent,\n" +
                    "(select count(*) \n" +
                    "from \"award-team8\".user left outer join \"award-team8\".star on (s.id = \"award-team8\".star.receiver) \n" +
                    "where \"award-team8\".user.id = s.id) as received\n" +
                    "from \"award-team8\".user as s\n" +
                    "order by s.id")) {
            ResultSet set = statement.executeQuery();
            UserRepositoryDb userdb = new UserRepositoryDb();
            while (set.next()){
                Map<String,Integer> sentreceived = new HashMap<>();
                User employee = userdb.getUser(set.getInt("employee"));
                if (checkReceive(employee.getUserId()) && checkSend(employee.getUserId())) {
                    System.out.println(employee.getUserId());
                    int received = set.getInt("received");
                    int sent = set.getInt("sent");
                    sentreceived.put("sent", sent);
                    sentreceived.put("received", received);
                }else if (checkSend(employee.getUserId())){
                    System.out.println(employee.getUserId());
                    int sent = set.getInt("sent");
                    sentreceived.put("sent",sent);
                    sentreceived.put("received",0);
                }else if (checkReceive(employee.getUserId())){
                    System.out.println(employee.getUserId());
                    int received = set.getInt("received");
                    sentreceived.put("sent",0);
                    sentreceived.put("received",received);
                }
                result.put(employee,sentreceived);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean checkSend(int userId) {
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement statementSender = conn.prepareStatement("select * from \"award-team8\".star where sender = ?;")) {
            statementSender.setInt(1,userId);
            ResultSet set = statementSender.executeQuery();
            if (set.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkReceive(int userid){
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement statementSender = conn.prepareStatement("select * from \"award-team8\".star where receiver = ?;")){
                statementSender.setInt(1,userid);
                ResultSet set = statementSender.executeQuery();
                if (set.next()){
                    return true;
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    private boolean checkUser(int id) {
//        try (Connection conn = ConnectionPool.getConnection();
//             PreparedStatement statementReceiver = conn.prepareStatement("select * from \"award-team8\".star where receiver = ?;");
//             PreparedStatement statementSender = conn.prepareStatement("select * from \"award-team8\".star where receiver = ?;")) {
//            statementReceiver.setInt(1,id);
//            statementSender.setInt(1,id);
//            ResultSet setReceiver = statementReceiver.executeQuery();
//            ResultSet setSender = statementSender.executeQuery();
//            if (setReceiver.next()){
//                System.out.println(id);
//                return true;
//            }
//            if (setSender.next()){
//                System.out.println(id);
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

        public Map<String,Integer> getTagsMap(){
        Map<String,Integer> result = new HashMap<>();
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement("select tag,count(*) as total from \"award-team8\".tags group by (tag)")){
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.put(set.getString("tag"),set.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
