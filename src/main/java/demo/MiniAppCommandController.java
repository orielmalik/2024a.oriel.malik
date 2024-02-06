package demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MiniAppCommandController {
	
	//private MiniAppCommandService miniAppCommandService;

	public MiniAppCommandController() {
	}
	
	@PostMapping(path = { "/superapp/miniapp/{miniappname}" }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	 public Flux<Object> invokeMiniApp(@PathVariable("miniappname") String miniAppName,
	            @RequestBody MIniAppCommandBoundary miniAppCommand) {
	        if (miniAppName.equals("miniapp")) {
	            Map<String, Object> output = new HashMap<>();
	            output.put("result", "success");
	            output.put("message", "Sample MiniApp command invoked successfully.");
	            output.put("command", miniAppCommand);
	            return Flux.just(output);
	        }
	        Map<String, Object> errorOutput = new HashMap<>();
	        errorOutput.put("result", "error");
	        errorOutput.put("message", "MiniApp not found.");
	        return Flux.just(errorOutput);
	}

}
