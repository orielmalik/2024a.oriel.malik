package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Quiz")
public class QuizEntity {

	@Id
	private String quizId;
	private Date createdTimestamp;
	private Map<String,String> questions;
	private Map<String,List<String>> optionalAnswers;
	private String notes;
	
	public QuizEntity() {
		this.questions = new HashMap<>();
		this.optionalAnswers = new HashMap<>();
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Map<String, String> getQuestions() {
		return questions;
	}

	public void setQuestions(Map<String, String> questions) {
		this.questions = questions;
	}

	public Map<String, List<String>> getOptionalAnswers() {
		return optionalAnswers;
	}

	public void setOptionalAnswers(Map<String, List<String>> optionalAnswers) {
		this.optionalAnswers = optionalAnswers;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "QuizEntity [quizId=" + quizId + ", createdTimestamp=" + createdTimestamp + ", questions=" + questions
				+ ", optionalAnswers=" + optionalAnswers + ", notes=" + notes + "]";
	}

}
