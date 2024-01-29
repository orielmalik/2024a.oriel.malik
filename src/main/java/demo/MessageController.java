package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(path = { "/messages" })
public class MessageController {
	private MessageService messageService;

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@PostMapping(
		produces = { MediaType.APPLICATION_JSON_VALUE },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Mono<MessageBoundary> create (
			@RequestBody MessageBoundary message){
		return this.messageService
			.create(message);
	}
	
	@GetMapping(
		path = {"/{id}"},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<MessageBoundary> getMessage(
			@PathVariable("id") String id){
		return this.messageService
			.getMessage(id);
	}
	
	@GetMapping(
		produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<MessageBoundary> getAllMessages(){
		return this.messageService
			.getAllMessages();
	}
	
	@DeleteMapping
	public Mono<Void> deleteAllMessages(){
		return this.messageService
			.deleteAllMessages();
	}
	
	@PutMapping(
		path = {"/{id}"},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<Void> updateMessage(
			@PathVariable("id") String id, 
			@RequestBody MessageBoundary update){
		return this.messageService
			.updateMessage(id, update);
	}
}
