package category;

import java.util.ArrayList;

import qAndA.Question;

/**
 * This class represents a category. 
 * This class would be implemented as a database table in a full version of the system. 
 *
 */
public class Category {

	private String name = "";
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	/**
	 * Constructor
	 * @param name The name of the category
	 */
	public Category(String name) {
		this.name = name;
	
	}

	/**
	 * Gets the name of a category as a string. 
	 * @return name of category
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets a name of a category as a string. 
	 * @param name name of category
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	public String toString(){
		return name;
	}
	
	/**
	 * Adds a question-object to the arraylist of Questions. 
	 * @param q The question-object to be added
	 */
	public void addQuestion(Question q) {
		for(Question qs : questions) {
			if(qs.equals(q)) {
				return;
			}
		}
		questions.add(q);
	}
	
	/**
	 * Removes a question object from the arraylist of Questions.
	 * @param q The question-object to be removed
	 */
	public void removeQuestion(Question q) {
		Question temp = new Question("");
		for(Question qs : questions) {
			if(qs.getId() == q.getId()) {
				temp = qs;
				break;
			}
		}
		questions.remove(temp);
	}
	
	/**
	 * Gets the question-objects in the arraylist of Questions.
	 * @return all questions in the arraylist of Questions. 
	 */
	public ArrayList<Question> getQuestions() {
		return questions;
	}

}
