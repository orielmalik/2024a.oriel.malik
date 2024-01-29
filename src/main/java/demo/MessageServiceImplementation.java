package demo;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageServiceImplementation 
	implements MessageService {

	private MessageCrud messageCrud;

	public MessageServiceImplementation(MessageCrud messageCrud) {
		super();
		this.messageCrud = messageCrud;
	}

	@Override
	public Mono<MessageBoundary> create(MessageBoundary message) {
		message.setId(UUID.randomUUID().toString());
		message.setCreatedTimestamp(new Date());
		return this.messageCrud
			.save(message.toEntity()) //Mono<MessageEntity>
			.map(entity->new MessageBoundary(entity))
			.log();
	}

	@Override
	public Mono<MessageBoundary> getMessage(
			String id) {
		return this.messageCrud
			.findById(id)
			.map(entity->new MessageBoundary(entity))
			.log();
	}

	@Override
	public Flux<MessageBoundary> getAllMessages() {
		return this.messageCrud
			.findAll()
			.map(entity->new MessageBoundary(entity))
			.log();
	}

	@Override
	public Mono<Void> deleteAllMessages() {
		return this.messageCrud
			.deleteAll()
			.log();
	}

	@Override
	public Mono<Void> updateMessage(
			String id, MessageBoundary update) {
		return this.messageCrud
			.findById(id)
			.map(entity->{
				entity.setMessage(update.getMessage());
				return entity;
			})
			.flatMap(this.messageCrud::save)
			//.map(entity->new MessageBoundary(entity))
			.map(MessageBoundary::new)
			.log()
			.then();
	}

}
