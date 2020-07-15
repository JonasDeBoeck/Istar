package ucll.project.domain.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserRepositoryDb;
import ucll.project.model.Star;
import ucll.project.model.StarService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StarDatabaseTest {
    private Star star;
    private User receiver;
    private User sender;
    private StarService starDB;

    @Before
    public void setup() {
        starDB = new StarService();
        UserRepositoryDb db = new UserRepositoryDb();
        receiver = db.getUser(2);
        sender = db.getUser(1);
        star = new Star(receiver, sender, "test star8", LocalDate.now());
    }

//    @Test
//    public void addStarTest(){
//        starDB.createStar(star);
//    }
//
//    @Test
//    public void getStar(){
//        Star star = starDB.get(4);
//        System.out.println(star);
//    }

//    @Test
//    public void getStarsForMont(){
//        List<Star> stars = starDB.getStarsForLastMonths(13);
//        System.out.println(stars);
//    }

    @Test
    public void testStats() {
        Map<User, Map<String, Integer>> result = starDB.getSentAndReceivedPerEmployee();
//        JSONObject string = new JSONObject();
//        JSONObject finaljson = new JSONObject();
//        for (Map.Entry<User, Map<String, Integer>> entry : result.entrySet()) {
//            try {
//                JSONObject stats = new JSONObject(entry.getValue());
//                string.put("stats", stats);
//                finaljson.put(entry.getKey().getUserName(),string);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(finaljson);
    }

//    @Test
//    public void getTagMap(){
//        Map<String,Integer> result = starDB.getTagsMap();
//        JSONObject obj = new JSONObject(result);
//        System.out.println(obj.toString());
//    }

//    @Test
//    public void tagListTest(){
//        StarService service = new StarService();
//        Star star = service.get(121);
//        System.out.println(star.getTagList());
//    }
//    @Test
//    public void getAllStars(){
//        List<Star> stars = starDB.getAll();
//        System.out.println(stars.size());
//    }

//    @Test
//    public void updateStar(){
//        Star star2 = new Star(receiver,sender,"test star2");
//        Star startoupdate = starDB.get(4);
//        star2.setId(startoupdate.getId());
//        starDB.update(star2);
//    }

//    @Test
//    public void removeStar(){
//        starDB.delete(3);
//    }
}
