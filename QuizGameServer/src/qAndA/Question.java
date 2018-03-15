package qAndA;

import java.util.ArrayList;

public class Question {
	private String question = "";
	private static int counter = 1;
	private int id = 0;
	private ArrayList<Answer> answers = new ArrayList<Answer>();

	public Question(String q){
		this.question = q;
		this.id = counter;
		counter++;
	}
	
	public Question getQuestion(){
		return this;
	}
	
	public void setQuestion(String q){
		this.question = q;
	}
	
	public String getName() {
		return question;
	}
	
	public void addAnswer(Answer answer){
		answers.add(answer);
	}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<Answer> getAnswers(){
		return answers;
	}

}
