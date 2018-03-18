package qAndA;

import java.util.ArrayList;

/**
 * This class represents a question. 
 * This class would be implemented as a database table in a full version of the system. 
 *
 */
public class Question {
	private String question = "";
	private static int counter = 1;
	private int id = 0;
	private ArrayList<Answer> answers = new ArrayList<Answer>();

	/**
	 * Constructor
	 * @param q The question as a string
	 */
	public Question(String q){
		this.question = q;
		this.id = counter;
		counter++;
	}
	
	/**
	 * Get a question-object
	 * @return a question-object
	 */
	public Question getQuestion(){
		return this;
	}
	
	/**
	 * Set a question-object
	 * @param q The question as a string
	 */
	public void setQuestion(String q){
		this.question = q;
	}
	
	/**
	 * Get the name of the question
	 * @return The question as a string
	 */
	public String getName() {
		return question;
	}
	
	/**
	 * Adds an answer to a question to the arraylist of Answer-objects
	 * @param answer The answer-object to be added
	 */
	public void addAnswer(Answer answer){
		answers.add(answer);
	}
	
	/**
	 * Get the id of question
	 * @return the id as an int of the question-object
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get all the answers to a question
	 * @return all answer-objects as an arraylist
	 */
	public ArrayList<Answer> getAnswers(){
		return answers;
	}

}
