package demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.boundries.ObjectBoundary;
import demo.interfaces.ObjectService;
import reactor.core.publisher.Flux;

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

		public Flux<ObjectBoundary> searchbyType( @RequestParam(name="type",required = false)  String type,
                @RequestParam(name="superapp",required = true) String userSuperapp,
                @RequestParam(name="email",required = true) String userEmail) {
			
					return objectService.searchbyType(type,userSuperapp, userEmail);
 
                }
		@GetMapping(path= {"/byAlias/{alias}"},
				produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
		
		public Flux<ObjectBoundary> searchbyAlias(@RequestParam(name="alias",required=false) String alias,
				@RequestParam(name="superapp",required = true) String userSuperapp,
                @RequestParam(name="email",required = true) String userEmail)
				{
			return objectService.searchbyAlias(alias,userSuperapp, userEmail);
		}
		
		@GetMapping(path= {"/byAliasPattern/{pattern}"},
				produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
		
		public Flux<ObjectBoundary> searchbyAliasPattern(@RequestParam(name="pattern",required=false) String pattern,
				@RequestParam(name="superapp",required = true) String userSuperapp,
                @RequestParam(name="email",required = true) String userEmail)
				{
			return objectService.searchbyAliasPattern(pattern,userSuperapp, userEmail);
		}
}
