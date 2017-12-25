package Survey;

public class Question {
	
	private int id;
	private String quesiton;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuesiton() {
		return quesiton;
	}
	public void setQuesiton(String quesiton) {
		this.quesiton = quesiton;
	}
	public Question(int id, String quesiton) {

		this.id = id;
		this.quesiton = quesiton;
	}
	
	
}
