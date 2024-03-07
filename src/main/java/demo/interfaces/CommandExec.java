package demo.interfaces;

import demo.boundries.MiniAppCommandBoundary;
import demo.entities.ObjectEntity;
import reactor.core.publisher.Flux;

public interface CommandExec {

	public Flux<MiniAppCommandBoundary> execute(MiniAppCommandBoundary input);
}
