package controller;

import java.util.ArrayList;

import category.Category;
import qAndA.Answer;
import qAndA.Question;
/**
 * This class manages the categories. 
 * This class would contain the database connection for the categories in a full implementation of the system.
 *
 */
public class CatController {

	private ArrayList<Category> category = new ArrayList<Category>();
	private ArrayList<Question> checkingdubs = new ArrayList<Question>();
	String cat = "";
	Question q;

	/**
	 * Constructor. Creates 4 categories when initialized and adds them to an arraylist of categories.  
	 */
	public CatController() {
		category.add(new Category("Geometri"));
		category.add(new Category("Algebra"));
		category.add(new Category("Procent"));
		category.add(new Category("Logaritmer"));
	}

	/**
	 * Checks if a category exists. 
	 * @param cat The name of the category to be checked
	 * @return the index of the category if category name exists as an int. If not, -1 is returned as an int.
	 */
	public int catExists(String cat) {
		for (Category c : category) {
			String cname = c.getName().toLowerCase();
			String cat2 = cat.toLowerCase();
			if (cname.equals(cat2)) {
				return category.indexOf(c);
			}
		}
		return -1;

	}

	/**
	 * Gets all the category names.
	 * @return category names as an arraylist of strings
	 */
	public ArrayList<String> getCategoryNames() {
		ArrayList<String> catNames = new ArrayList<String>();
		for (Category c : category) {
			catNames.add(c.getName());
		}
		return catNames;
	}

	/**
	 * Renames a specific category to a new name if that category exists. 
	 * @param cat the category that should be renamed as a string
	 * @param name the new name of the category as a string
	 * @return boolean, true if success, else false
	 */
	public boolean renameCategory(String cat, String name) {
		int index = catExists(cat);
		if (index != -1) {
			Category reCat = category.get(index);
			reCat.setName(name);
			return true;
		}
		return false;
	}

	/**
	 * Removes a category
	 * @param cat the name of the category to be removed as a string
	 * @return 1 as an integer if success, else -1
	 */
	public int removeCategory(String cat) {
		for (int i = 0; i < category.size(); i++) {

			if (category.get(i).getName().equals(cat)) {
				category.remove(i);
				return 1;

			}

		}
		return -1;
	}

	/**
	 * Adds and creates a new category. 
	 * @param cat the name of the new category to be added as a string
	 * @return 1 as an integer if success, else -1
	 */
	public int addCat(String cat) {
		System.out.println(cat);
		if (catExists(cat) == -1) {
			category.add(new Category(cat));
			return 1;
		}

		return -1;

	}
	
	/**
	 * Adds a question to a category.
	 * @param q the question-object to be added 
	 * @param name the name of the category as a string that the question should be added to
	 */
	public void addQToCat(Question q, String name) {
		for(Category c : category) {
			if(c.getName().equals(name)) {
				c.addQuestion(q);
			}
		}
	}
	
	/**
	 * Get name of category.
	 * @param name the name of the category as a string
	 * @return the category-object that matches the name if success, else null
	 */
	public Category getCat(String name) {
		for(Category c : category) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Get all the category-objects
	 * @return all categories as an arraylist
	 */
	public ArrayList<Category> getAllCats() {
		return category;
	}
	
	/**
	 * Gets all questions in all categories. Controls that question only appears once.
	 * @return all questions as an arraylist of question-objects.
	 */
	public ArrayList<Question> getAllQuestions(){
		ArrayList<Question> allQuestions = new ArrayList<Question>();	
		checkingdubs.clear();
		for(Category category : getAllCats()){
			
			allQuestions.addAll(category.getQuestions());
		}
		Question question = null;
		
		for(int i=0 ; i<allQuestions.size(); i++){   
			question = allQuestions.get(i);
			allQuestions.remove(i);
			if(!(allQuestions.contains(question))){
				allQuestions.add(question);
			}
		}
		
		return allQuestions;
	}
	
	/**
	 * Get answer with a specific id.
	 * @param id Id of answer as an int
	 * @return the answer-object if success, else null
	 */
	public Answer getAnswer(int id){
		for(Category category : getAllCats()){
			for(Question question : category.getQuestions()){
				for(Answer answer : question.getAnswers()){
					if(answer.getId() == id){
						return answer;
					}
				}
			}	
		}
		return null;
	}
	
	/**
	 * Get question with a specific id.
	 * @param id Id of question as an int
	 * @return the question-object if success, else null
	 */
	public Question getQuestion(int id){		
		for(Category category : getAllCats()){
			for(Question question : category.getQuestions()){
				if(question.getId() == id){
					return question;
				}
			}
		}
		return null;
	}
	/**
	 * Remove an answer with a specific id.
	 * @param id Id of answer as an int
	 * @return an integer, 1 if success, else -1
	 */
	public int removeAnswer(int id){
		for(Category category : getAllCats()){
			for(Question question : category.getQuestions()){
				for (int i = 0; i < question.getAnswers().size(); i++) {
					if (question.getAnswers().get(i).getId() == id) {
						System.out.println("RemovedAnswer: " + question.getAnswers().get(i).getName());
						System.out.println("Id answer " + question.getAnswers().get(i).getId());
						question.getAnswers().remove(i);
						return 1;
					}
				}
			}
		}
		return -1;
	}
	
	/**
	 * Changes an answer to a new answer.
	 * @param id Id of answer as an int
	 * @param newAnswer The new answer as a string
	 * @return an integer
	 */
	public int changeAnswer(int id, String newAnswer) {
		int returnValue = -1;
		
		Answer answer = getAnswer(id);
		if (answer != null) {
			System.out.println("Changed Question with id " + id + " from "
					+ answer.getName() + " to " + newAnswer);
			answer.setAnswer(newAnswer);
		}
		return returnValue;
	}

}
