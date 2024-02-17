package demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.boundries.ObjectBoundary;
import demo.interfaces.ObjectService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


	@RestController
	@RequestMapping(path = { "/superapp/objects/search" })
	public class SearchingObjectController {

		private ObjectService objectService;

		public SearchingObjectController(ObjectService objectService) {
			super();
			this.objectService = objectService;
		}	
		
		
		@GetMapping(path={"/byType/{type}"},
		produces = {MediaType.TEXT_EVENT_STREAM_VALUE})

		public Flux<ObjectBoundary> searchbyType( @RequestParam(name="type",required = false,defaultValue = "maayan")  String type,
                @RequestParam(name="superapp",required = false,defaultValue = "2024.otiel.malik") String userSuperapp,
                @RequestParam(name="email",required = false,defaultValue = "true") String userEmail) {
			
					return objectService.searchbyType(type);
 
                }
		@GetMapping(path= {"/byAlias/{alias}"},
				produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
		
		public Flux<ObjectBoundary> searchbyAlias(@RequestParam(name="alias",required=false,defaultValue="mc") String alias,
				@RequestParam(name="superapp",required = false,defaultValue = "2024.otiel.malik") String userSuperapp,
                @RequestParam(name="email",required = false,defaultValue = "true") String userEmail)
				{
			return objectService.searchbyAlias(alias);
			
		}
}
