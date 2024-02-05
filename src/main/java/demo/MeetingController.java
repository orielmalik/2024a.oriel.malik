package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = { "/superapp/object" })
public class MeetingController {
	private MeetingService meetingService;

	public MeetingController(MeetingService meetingService) {
		super();
		this.meetingService = meetingService;
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<MeetingBoundary> create(@RequestBody MeetingBoundary meeting) {
		return this.meetingService.create(meeting);
	}

	@GetMapping(path = { "/${spring.application.name}/{id}" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<MeetingBoundary> getMeeting(@PathVariable("id") String id) {
		return this.meetingService.getMeeting(id);
	}

	@GetMapping(produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	public Flux<MeetingBoundary> getAllMeetings() {
		return this.meetingService.getAllMeetings();
	}

	@PutMapping(path = { "/${spring.application.name}/{id}" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<Void> updateMeeting(@PathVariable("id") String id, @RequestBody MeetingBoundary update) {
		return this.meetingService.updateMeeting(id, update);
	}
}
