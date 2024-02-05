package demo;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MeetingServiceImplementation implements MeetingService {

	private MeetingCrud meetingCrud;

	@Override
	public Mono<MeetingBoundary> create(MeetingBoundary meating) {
		meating.setMeetingId(UUID.randomUUID().toString());
		meating.setCreatedTimestamp(new Date());
		return this.meetingCrud.save(meating.toEntity()).map(entity -> new MeetingBoundary(entity)).log();
	}

	@Override
	public Mono<MeetingBoundary> getMeeting(String id) {
		return this.meetingCrud.findById(id).map(entity -> new MeetingBoundary(entity)).log();
	}

	@Override
	public Flux<MeetingBoundary> getAllMeetings() {
		return this.meetingCrud.findAll().map(entity -> new MeetingBoundary(entity)).log();
	}

	@Override
	public Mono<Void> updateMeeting(String id, MeetingBoundary update) {
		return this.meetingCrud.findById(id).map(entity -> {
			entity.setType(update.getType());
			entity.setContent(update.getContent());

			return entity;
		}).flatMap(this.meetingCrud::save).map(MeetingBoundary::new).log().then();
	}

}
