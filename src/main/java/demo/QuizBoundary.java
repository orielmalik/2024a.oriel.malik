package demo;

import java.util.Date;

public class QuizBoundary {
	private String quizId;
	private Date createdTimestamp;

	public QuizBoundary() {

	}

	public QuizBoundary(QuizEntity entity) {

	}

	public QuizBoundary(String quizId, Date createdTimestamp) {
		super();
		this.quizId = quizId;
		this.createdTimestamp = createdTimestamp;
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

	public QuizEntity toEntity() {
		QuizEntity entity = new QuizEntity();

		return entity;
	}

}
