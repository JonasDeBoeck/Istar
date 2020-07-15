package ucll.project.domain.user;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * This is a sample unit test, write your own!
 */
public class SampleUserTest {

    @Test
    public void CreateUserTest() {

        User user = new User(
                "username",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN, 3, LocalDate.of(2000,1,18)
        );

        assertEquals(user.getUserName(), "username");
        assertEquals(user.getFirstName(), "firstName");
        assertEquals(user.getLastName(), "lastName");
        assertEquals(user.getEmail(), "email@example.com");
        assertEquals(user.getGender(), Gender.FEMALE);
        assertEquals(user.getRole(), Role.ADMIN);
        assertEquals(user.getBirthdate(),LocalDate.of(2000,1,18));
    }

    @Test(expected = IllegalArgumentException.class)
    public void CreateUserWithWrongEmailTest(){
        User user = new User(
                "username",
                "firstName",
                "lastName",
                "emailexample.com",
                Gender.FEMALE,
                Role.ADMIN,
                3,LocalDate.of(2000,1,18)
        );
    }

//    @Test
//    public void testUsername(){
//        UserRepositoryDb db = new UserRepositoryDb();
//        User user = db.get("TestTest");
//        System.out.println(user);
//    }

    @Test
    public void testUpdateStars(){
        UserService service = new UserService();
        service.updateStars(5);
    }
}
