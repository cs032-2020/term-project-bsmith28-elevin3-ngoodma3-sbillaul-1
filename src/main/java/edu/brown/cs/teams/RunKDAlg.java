package src.main.java.edu.brown.cs.teams;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.main.java.edu.brown.cs.teams.algorithms.AlgMain;
import src.main.java.edu.brown.cs.teams.io.Command;
import src.main.java.edu.brown.cs.teams.io.CommandException;
import src.main.java.edu.brown.cs.teams.io.REPL;
import src.main.java.edu.brown.cs.teams.recipe.MinimalRecipe;
import src.main.java.edu.brown.cs.teams.state.Config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class RunKDAlg implements Command {
  @Override
  public String runCommand(String[] command) throws CommandException {
    if (command.length < 3) {
      throw new CommandException("ERROR: Must enter in at least two ingredients");
    }
    try {
      Gson gson = new Gson();
      FileReader reader = new FileReader("data/ingredient_vectors.json");
      JSONParser parser = new JSONParser();
      JSONObject object = (JSONObject) parser.parse(reader);
      double[][] embeddings = new double[command.length - 1][300];
      for (int i = 1; i < command.length; i++) {
        String ingredient = REPL.removeQuotes(command[i]);
        double[] embedding = gson.fromJson(object.get(ingredient).toString(), double[].class);
        embeddings[i-1] = embedding;
      }
      double[] queryEmbedding = Config.arrayAdd(embeddings);
      List<MinimalRecipe> neighbors = AlgMain.getTree().getNeighbors(10, queryEmbedding);
      String result = "";
      for (MinimalRecipe recipe : neighbors) {
        System.out.println(AlgMain.getDb().getRecipe(recipe.getId()));
      }
      return neighbors.toString();
    }catch (IOException | ParseException e) {
      throw new CommandException(e.getMessage());
    }
  }
}
