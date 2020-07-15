//
//package ucll.project.domain.db;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import ucll.project.domain.user.*;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DatabaseTest {
//    UserRepositoryDb db;
//    List<User> userList;
//    User user;
//    @Before
//    public void setup(){
//        db = new UserRepositoryDb();
//        userList = new ArrayList<>();
//        user = new User("rafael1","rafael","backx","rafaelbackx@live.be", Gender.MALE, Role.ADMIN,3, LocalDate.of(2000,1,18));
//    }
//
//    @Test
//    public void addUser(){
//        System.out.println("added");
//        List<User> list = db.getAll();
//        int previous = list.size();
//        add();
//        list = db.getAll();
//        int after = list.size();
//        Assert.assertEquals(previous+=1,after);
//    }
//
//    @Test
//    public void removeUser(){
//        add();
//        List<User> list = db.getAll();
//        int previous = list.size();
//        userList.add(user);
//        db.delete(user);
//        list = db.getAll();
//        int after = list.size();
//        Assert.assertEquals(previous-=1,after);
//    }
//
//    @Test
//    public void updateUser(){
//        add();
//        User user1 = new User("rafael2","rafael","backx","rafaelbackx@live.be", Gender.MALE, Role.USER,3,LocalDate.of(2000,1,18));
//        List<User> users = db.getAll();
//        for (User user:users){
//            if (user.getUserId() == user1.getUserId()){
//                Assert.assertEquals(user.getRole().toString(), Role.USER.toString());
//            }
//        }
//        db.update(user);
//    }
//
//    public void add(){
//        userList.add(user);
//        db.createUser(user,"password");
//    }
//
//    @After
//    public void quit(){
//        for (User user:userList){
//            db.delete(user);
//        }
//    }
//}
//
