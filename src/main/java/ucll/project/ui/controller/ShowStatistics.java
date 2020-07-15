package ucll.project.ui.controller;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.model.Star;
import ucll.project.model.StarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShowStatistics extends RequestHandler {
    public ShowStatistics(String command, UserService userService, StarService starService) {super(command, userService, starService);}

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int totaltags =0;
        request.setAttribute("users", sentAndReceiverPerEmployee());
        request.setAttribute("tags", getTagMap());
        Map<String,Integer> result = ((StarService)getStarService()).getTagsMap();
        for (Map.Entry<String,Integer> entry:result.entrySet()){
            totaltags+= entry.getValue();
        }
        request.setAttribute("totaltags",totaltags);
        request.getRequestDispatcher("statistics.jsp").forward(request, response);
    }

    private String sentAndReceiverPerEmployee() {
        Map<User, Map<String, Integer>> result = ((StarService) getStarService()).getSentAndReceivedPerEmployee();
        JSONObject finaljson = new JSONObject();
        for (Map.Entry<User, Map<String, Integer>> entry : result.entrySet()) {
            JSONObject string = new JSONObject();

            //String idk = new Gson().toJson(entry.getValue());
            try {
                //string.put("usename", entry.getKey().getUserName());
                JSONObject stats = new JSONObject(entry.getValue());
                string.put("stats"+ entry.getKey().getUserName(), stats);
                finaljson.put(entry.getKey().getUserName(),string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //System.out.println(string.toString());
            //System.out.println(entry.getKey().toJsonString() + idk);
        }
        System.out.println(finaljson.toString());
        return finaljson.toString();
    }

    public String getTagMap(){
        Map<String,Integer> result = ((StarService)getStarService()).getTagsMap();
        JSONObject obj = new JSONObject(result);
        return obj.toString();
    }
}