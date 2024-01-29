package demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
	public Mono<MessageBoundary> create (
		MessageBoundary message);
	
	public Mono<MessageBoundary> getMessage(
		String id);
	
	public Flux<MessageBoundary> getAllMessages();
	
	public Mono<Void> deleteAllMessages();
			
	public Mono<Void> updateMessage(
		String id, MessageBoundary update);
}
