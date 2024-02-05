package demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuizCrud extends ReactiveMongoRepository<QuizEntity, String> {

}
