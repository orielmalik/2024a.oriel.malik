package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/hello"})
public class HelloController {
	private Demo demo;
	
	@Autowired
	public void setDemo(Demo demo) {
		this.demo = demo;
	}
	
	@GetMapping(
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<MessageBoundary> hello (){
		return Mono.just(
			new MessageBoundary(
				this.demo
					.getAfekaDemoMessage() + "?!"));
	}
}
