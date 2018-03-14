package controller;

import java.util.ArrayList;

import category.Category;
import qAndA.Question;

public class QuestionController {
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	public QuestionController(){
		questions.add(new Question("Geometri", "Är en cirkel rund?"));
		questions.add(new Question("Geometri", "Är en kub rund?"));
		questions.add(new Question("Algebra", "1 + 1 = ?"));
		questions.add(new Question("Algebra", "3 + 6 = ?"));
	}
	
	
	public ArrayList<String> getQuestionBasedOnCategory(String category) {
		ArrayList<String> filteredList = new ArrayList<String>();
		for (Question q : questions) {
			if (q.getCategory().equals(category)) {
				filteredList.add(q.getQuestion());
			}
		}
		return filteredList;
	}
	
	
	
}
