package demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MeetingService {
	public Mono<MeetingBoundary> create(MeetingBoundary meating);

	public Mono<MeetingBoundary> getMeeting(String id);

	public Flux<MeetingBoundary> getAllMeetings();

	public Mono<Void> updateMeeting(String id, MeetingBoundary update);
}
