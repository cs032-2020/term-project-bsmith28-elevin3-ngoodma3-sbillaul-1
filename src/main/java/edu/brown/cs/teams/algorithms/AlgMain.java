package src.main.java.edu.brown.cs.teams.algorithms;

import src.main.java.edu.brown.cs.teams.io.CommandException;
import src.main.java.edu.brown.cs.teams.database.RecipeDatabase;
import src.main.java.edu.brown.cs.teams.kdtree.KDTree;
import src.main.java.edu.brown.cs.teams.recipe.MinimalRecipe;

import java.sql.SQLException;
import java.util.List;

/**
 * Class to store the information relevant to the algorithms being run.
 */
public class AlgMain {
  private static KDTree<MinimalRecipe> tree;
  private static List<MinimalRecipe> recipeList;
  private static RecipeDatabase db;

  /**
   * Constructor for AlgMain.
   *
   * @throws SQLException
   * @throws CommandException
   * @throws ClassNotFoundException
   */
  public AlgMain() {
    AlgMain.tree = new KDTree<>(300);
  }

  /**
   * Getter method for the KDTree
   *
   * @return the kdtree
   */
  public static KDTree<MinimalRecipe> getTree() {
    return tree;
  }

  /**
   * Getter method for the recipe list.
   *
   * @return a list of MinimalRecipes
   */
  public static List<MinimalRecipe> getRecipeList() {
    return recipeList;
  }

  /**
   * Getter method for the db proxy.
   *
   * @return the db
   */
  public static RecipeDatabase getDb() {
    return db;
  }

  /**
   * Setter method for the kd tree
   * @param tree
   */
  public static void setTree(KDTree<MinimalRecipe> tree) {
    AlgMain.tree = tree;
  }

  /**
   * Setter method for the recipe list
   * @param recipeList
   */
  public static void setRecipeList(List<MinimalRecipe> recipeList) {
    AlgMain.recipeList = recipeList;
  }

  /**
   * Setter method for the database querier
   * @param db
   */
  public static void setDb(RecipeDatabase db) {
    AlgMain.db = db;
  }
}