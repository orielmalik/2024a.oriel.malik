package demo.interfaces;

import java.util.Map;

import reactor.core.publisher.Mono;

public interface MyUpdateMethod {
	public Mono<Void> updateCounselorTargetObjects(String targetObjectId, Map<String, Object> details);
}
