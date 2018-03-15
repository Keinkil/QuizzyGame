package controller;

import java.util.ArrayList;

import qAndA.Question;

public class QuestionController {
	private ArrayList<Question> questions = new ArrayList<Question>();

	// public ArrayList<Question> getQuestionBasedOnCategory(String category) {
	// ArrayList<Question> filteredList = new ArrayList<Question>();
	// for (Question q : questions) {
	// if (q.getCategory().equals(category)) {
	// filteredList.add(q.getQuestion());
	// }
	// }
	// return filteredList;
	// }

	public void addQuestion(Question q) {
		questions.add(q);
	}
	
	public Question getQ(int qid) {
		for(Question q : questions) {
			if (q.getId() == qid) {
				return q;
			}
		}
		return null;
	}

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
