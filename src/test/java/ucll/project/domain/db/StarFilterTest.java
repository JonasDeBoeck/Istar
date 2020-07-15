package ucll.project.domain.db;

import org.junit.Before;
import org.junit.Test;
import ucll.project.model.Star;
import ucll.project.model.StarService;

import java.util.ArrayList;
import java.util.List;

public class StarFilterTest {

    private StarService starDB;
    @Before
    public void setup(){
        starDB = new StarService();
    }

//    @Test
//    public void testTagFilter(){
//        List<Star> tagStars = new ArrayList<>();
//        tagStars = starDB.getAllByTagname("#idk");
//        System.out.println(tagStars.size());
//    }

    /*@Test
    public void testUsernameFilter(){
        List<Star> tagStars = new ArrayList<>();
        tagStars = starDB.getAllByUsername("test","sender");
        System.out.println(tagStars.size());
    }*/
}
