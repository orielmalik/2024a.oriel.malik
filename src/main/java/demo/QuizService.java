package demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface QuizService {
	public Mono<QuizBoundary> create(QuizBoundary message);

	public Mono<QuizBoundary> getQuiz(String id);

	public Flux<QuizBoundary> getAllQuizs();

	public Mono<Void> updateQuiz(String id, QuizBoundary update);
}
