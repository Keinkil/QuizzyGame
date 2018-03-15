package qAndA;

public class Answer {
	private String answer = "";
	private static int counter = 1;
	private int id = 0;
	private boolean correctAnswer = false;
	
	public Answer(String a){
		this.answer = a;
		this.id = counter;
		counter++;
	}
	
	public Answer(String a, boolean correct){
		this.correctAnswer = correct;
		this.answer = a;
		this.id = counter;
		counter++;
	}
	
	public Answer getAnswer(){
		return this;
	}
	
	public void setAnswer(String a){
		this.answer = a;
	}
	
	public String getName() {
		return answer;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean getCorrectAnswer(){
		return correctAnswer;
	}


}
