package qAndA;

/**
 * This class represents an answer.
 * This class would be implemented as a database table in a full version of the system. 
 *
 */
public class Answer {
	private String answer = "";
	private static int counter = 1;
	private int id = 0;
	private boolean correctAnswer = false;
	
	/**
	 * Constructor
	 * @param a The answer as a string
	 */
	public Answer(String a){
		this.answer = a;
		this.id = counter;
		counter++;
	}
	
	/**
	 * Constructor with two parameters
	 * @param a The correct answer as a string
	 * @param correct True if answer is correct
	 */
	public Answer(String a, boolean correct){
		this.correctAnswer = correct;
		this.answer = a;
		this.id = counter;
		counter++;
	}
	
	/**
	 * Get an answer
	 * @return The answer-object
	 */
	public Answer getAnswer(){
		return this;
	}
	
	/**
	 * Sets an answer
	 * @param a The answer to be set as a string
	 */
	public void setAnswer(String a){
		this.answer = a;
	}
	
	/**
	 * Get the name of the answer
	 * @return The name as a string
	 */
	public String getName() {
		return answer;
	}
	
	/**
	 * Get the id of the answer
	 * @return The id as an integer
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Check if the answer is correct or not
	 * @return True if answer is correct, else false
	 */
	public boolean getCorrectAnswer(){
		return correctAnswer;
	}


}
