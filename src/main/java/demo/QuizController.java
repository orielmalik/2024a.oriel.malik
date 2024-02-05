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
@RequestMapping(path = { "/superapp/objects" })
public class QuizController {

	private QuizService quizeService;

	public QuizController(QuizService quizeService) {
		super();
		this.quizeService = quizeService;
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<QuizBoundary> create(@RequestBody QuizBoundary message) {
		return this.quizeService.create(message);
	}

	@GetMapping(path = { "/${spring.application.name}/{id}" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<QuizBoundary> getQuiz(@PathVariable("id") String id) {
		return this.quizeService.getQuiz(id);
	}

	@GetMapping(produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	public Flux<QuizBoundary> getAllQuizs() {
		return this.quizeService.getAllQuizs();
	}

	@PutMapping(path = { "/${spring.application.name}/{id}" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<Void> updateMessage(@PathVariable("id") String id, @RequestBody QuizBoundary update) {
		return this.quizeService.updateQuiz(id, update);
	}
}
