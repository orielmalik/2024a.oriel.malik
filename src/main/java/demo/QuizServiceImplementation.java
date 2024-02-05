package demo;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class QuizServiceImplementation implements QuizService {

	private QuizCrud quizCrud;

	public QuizServiceImplementation(QuizCrud quizCrud) {
		super();
		this.quizCrud = quizCrud;
	}

	@Override
	public Mono<QuizBoundary> create(QuizBoundary quiz) {
		quiz.setQuizId(UUID.randomUUID().toString());
		quiz.setCreatedTimestamp(new Date());
		return this.quizCrud.save(quiz.toEntity()) // Mono<QuizEntity>
				.map(entity -> new QuizBoundary(entity)).log();
	}

	@Override
	public Mono<QuizBoundary> getQuiz(String id) {
		return this.quizCrud.findById(id).map(entity -> new QuizBoundary(entity)).log();
	}

	@Override
	public Flux<QuizBoundary> getAllQuizs() {
		return this.quizCrud.findAll().map(entity -> new QuizBoundary(entity)).log();
	}

	@Override
	public Mono<Void> updateQuiz(String id, QuizBoundary update) {
		return this.quizCrud.findById(id).map(entity -> {
			// TODO edit

			return entity;
		}).flatMap(this.quizCrud::save).map(QuizBoundary::new).log().then();
	}

}
