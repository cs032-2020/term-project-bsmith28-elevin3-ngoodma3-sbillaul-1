package edu.brown.cs.teams.database;

import com.google.gson.JsonObject;
import edu.brown.cs.teams.login.AccountUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDatabase extends RecipeDatabase {

    //TODO: get rid of this and use RecipeDatabase's conn
    private Connection conn;

    /**
     * creates the user table. This table has three columns:
     *      uid (TEXT): the unique string identifying the user (primary key)
     *      name (TEXT): The user's first name. Is not unique
     *      profile (TEXT): The path to the user's profile image.
     *
     * @throws SQLException - if exception occurs while making table.
     */
    public void makeUserTable() throws SQLException {
        PreparedStatement prep = conn.prepareStatement("CREATE TABLE user("
                + "uid TEXT PRIMARY KEY,"
                + "name TEXT,"
                + "profile TEXT);");
        prep.executeUpdate();
    }

    /**
     * The favorite table is a junction table between recipes and users. It link every recipe
     * that is a favorite to each of the users that have it as a favorite.
     *
     * @throws SQLException
     */
    public void makeFavTable() throws SQLException {
        PreparedStatement prep = conn.prepareStatement("CREATE TABLE favorite("
                + "recipeId TEXT FOREIGN KEY REFERENCES recipe(id),"
                + "uid TEXT FOREIGN KEY REFERENCES user(uid);");
        prep.executeUpdate();
    }

    public void addNewUser(AccountUser user) throws SQLException {
        String uid = user.getUid();
        String name = user.getName();
        String profilePic = user.getProfile();

        PreparedStatement prep = conn.prepareStatement("INSERT INTO user VALUES (?, ?, ?);");
        prep.setString(1, uid);
        prep.setString(2, name);
        prep.setString(3, profilePic);
        prep.addBatch();
        prep.executeUpdate();
    }

    public List<Integer> getFavorites(String uid) throws SQLException{
        PreparedStatement prep = conn.prepareStatement("SELECT recipeId FROM favorite WHERE uid="
                + uid + ";");
        ResultSet res = prep.executeQuery();

        List<Integer> ret = new ArrayList<Integer>();
        while (res.next()) {
            ret.add(res.getInt(1));
        }
        return ret;
    }

    public JsonObject getRecipeFromID(String id) throws SQLException {
        String query = "SELECT * FROM recipe WHERE recipe.id=" + "id" + ";";
        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            JsonObject recipe = new JsonObject();
            recipe.addProperty("name", rs.getString(2));
            recipe.addProperty("author", rs.getString(3));
            recipe.addProperty("description", rs.getString(4));
            recipe.addProperty("ingredients", rs.getString(5));
            recipe.addProperty("time", rs.getString(7));
            recipe.addProperty("servings", rs.getString(8));
            recipe.addProperty("imageURL", rs.getString(9));
            return recipe;
        } else {
            return null;
        }
    }

}
