package category;

import java.util.ArrayList;

import qAndA.Question;

public class Category {

	private String name = "";
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	public Category(String name) {
		this.name = name;
	
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	
	public String toString(){
		return name;
	}
	
	public void addQuestion(Question q) {
		for(Question qs : questions) {
			if(qs.equals(q)) {
				return;
			}
		}
		questions.add(q);
	}
	
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
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}

}
