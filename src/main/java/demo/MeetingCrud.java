package demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MeetingCrud extends ReactiveMongoRepository<MeetingEntity, String> {

}
