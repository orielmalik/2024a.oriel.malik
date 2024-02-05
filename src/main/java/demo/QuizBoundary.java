package demo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class QuizBoundary {
	private String quizId;
	private Date createdTimestamp;
	private Map<String, String> questions;
	private Map<String, List<String>> optionalAnswers;
	private String notes;

	public QuizBoundary() {
		this.createdTimestamp = new Date();
	}

	public QuizBoundary(QuizEntity entity) {
		this.setQuizId(entity.getQuizId());
		this.setCreatedTimestamp(entity.getCreatedTimestamp());
		this.setNotes(entity.getNotes());
		this.setQuestions(entity.getQuestions());
		this.setOptionalAnswers(entity.getOptionalAnswers());
	}

	public QuizBoundary(Map<String, String> questions, Map<String, List<String>> optionalAnswers, String notes) {
		this();
		this.questions = questions;
		this.optionalAnswers = optionalAnswers;
		this.notes = notes;
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

	public QuizEntity toEntity() {
		QuizEntity entity = new QuizEntity();

		entity.setCreatedTimestamp(this.getCreatedTimestamp());
		entity.setQuizId(this.getQuizId());
		entity.setNotes(this.getNotes());
		entity.setQuestions(this.getQuestions());
		entity.setOptionalAnswers(this.getOptionalAnswers());

		return entity;
	}

	@Override
	public String toString() {
		return "QuizBoundary [quizId=" + quizId + ", createdTimestamp=" + createdTimestamp + ", questions=" + questions
				+ ", optionalAnswers=" + optionalAnswers + ", notes=" + notes + "]";
	}

}
