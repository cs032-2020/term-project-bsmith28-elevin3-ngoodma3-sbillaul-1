package edu.brown.cs.teams.GUI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.brown.cs.teams.login.AccountUser;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;
import java.util.List;

//can have more objects for different types of handlers
public class GuiHandlers {

    //Handles a user login. Takes user data from the Google User and adds to the database if possible.
    private static class userLoginHandler implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            QueryParamsMap qm = request.queryMap();
            String uid = qm.value("uid");
            String name = qm.value("firstName");
            String pfp = qm.value("profilePicture");
            AccountUser user = new AccountUser(uid, name, pfp);

            JsonObject responseJSON = new JsonObject();
            responseJSON.addProperty("uid", uid);
            responseJSON.addProperty("name", name);
            responseJSON.addProperty("profilePicture", pfp);

            try {
                StubAlgMain.getDB().addNewUser(user);
                responseJSON.addProperty("newUser", true);
            } catch (SQLException e) {
                responseJSON.addProperty("newUser", false);
                List<Integer> recipeIDs = StubAlgMain.getDB().getFavorites(uid);
            }

            return responseJSON.toString();
        }
    }

    //handles a request to the favorites page. Queries db for the user's favorited recipes.
    private static class favoritesHandler implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            QueryParamsMap qm = request.queryMap();
            String uid = qm.value("uid");

            List<Integer> recipeIDs = StubAlgMain.getDB().getFavorites(uid);
            JsonArray responseJSON = new JsonArray();
            for (Integer curID : recipeIDs) {

                //this is where the json array is created
                JsonObject obj = StubAlgMain.getDB().getRecipeContentFromID(Integer.toString(curID));
                if (obj == null) {
                    throw new IllegalArgumentException("ERROR in favoritesHandler:  recipe doesn't exist");
                }
                responseJSON.add(obj);
            }
            return responseJSON.toString();
        }
    }
}
