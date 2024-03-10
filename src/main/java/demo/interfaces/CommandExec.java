package demo.interfaces;

import demo.boundries.MiniAppCommandBoundary;
import reactor.core.publisher.Flux;

public interface CommandExec {

	public Flux<MiniAppCommandBoundary> execute(MiniAppCommandBoundary input);
}
