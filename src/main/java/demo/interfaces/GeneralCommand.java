package demo.interfaces;

import demo.boundries.MiniAppCommandBoundary;
import demo.entities.ObjectEntity;
import reactor.core.publisher.Flux;

public interface GeneralCommand {

	// Flux<ObjectEntity> 
	public Flux<ObjectEntity> execute();
}
