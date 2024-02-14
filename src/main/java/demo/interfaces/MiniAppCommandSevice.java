package demo.interfaces;

import demo.boundries.MiniAppCommandBoundary;
import reactor.core.publisher.Flux;

public interface MiniAppCommandSevice {
	public Flux<MiniAppCommandBoundary> invoke(MiniAppCommandBoundary command, String miniAppName);
}
