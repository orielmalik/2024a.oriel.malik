package demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.boundries.MiniAppCommandBoundary;
import demo.services.MiniAppCommandServiceImplementation;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = {"/superapp/miniapp"})	
public class MiniAppCommandController {
	
	private MiniAppCommandServiceImplementation commandService;

	public MiniAppCommandController(MiniAppCommandServiceImplementation commandService) {
		this.commandService = commandService;
	}
	
	@PostMapping(path = { "/{miniAppName}" }, consumes = {MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	 public Flux<MiniAppCommandBoundary> invokeMiniApp(@PathVariable("miniAppName") String miniAppName, @RequestBody MiniAppCommandBoundary command){
	           return commandService.invoke(command, miniAppName);
	}

}
