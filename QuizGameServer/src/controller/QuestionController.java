package controller;

import java.util.ArrayList;
import qAndA.Question;


/**
 * This class manages the questions. 
 * This class would contain the database connection for the questions in a full implementation of the system.
 *
 */
public class QuestionController {
	private ArrayList<Question> questions = new ArrayList<Question>();

	/**
	 * Adds a question to an arraylist of questions
	 * @param q The question-object to be added
	 * @return The question-object that was added
	 */
	public Question addQuestion(Question q) {
		questions.add(q);
		return q;
	}
	
	/**
	 * Get a specific question
	 * @param qid The id as an integer of a question-object
	 * @return The question-object with the correct id if success, else null
	 */
	public Question getQ(int qid) {
		for(Question q : questions) {
			if (q.getId() == qid) {
				return q;
			}
		}
		return null;
	}

	/**
	 * Remove a question
	 * @param qid The id as an integer of a question-object
	 * @return An integer, 1 if success, else -1
	 */
	public int removeQuestion(int qid) {
		int returnvalue = -1;
		Question temp = new Question("");
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getId() == qid) {
				temp = questions.get(i);
				returnvalue = 1;
				break;
			}
		}
		questions.remove(temp);
		return returnvalue;
	}

	/**
	 * Change a specific question to a new question.
	 * @param id The id as an integer of the question being changed
	 * @param question The new question as a string
	 * @return An integer, 1 if success, else -1. 
	 */
	public int changeQuestion(int id, String question) {
		int returnValue = -1;
		Question temp = new Question("");
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getId() == id) {
				temp = questions.get(i);
				returnValue = 1;
				break;
			}
		}
		if (returnValue == 1) {
			
			System.out.println("Changed Question with id " + id + " from "
					+ temp.getName() + " to " + question);
			temp.setQuestion(question);
		}

		return returnValue;
	}


}
