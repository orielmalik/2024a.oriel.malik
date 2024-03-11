package demo.services;

import org.springframework.web.bind.annotation.PathVariable;

import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.ObjectBoundary;
import demo.entities.ObjectEntity;
import demo.interfaces.GeneralCommand;
import demo.interfaces.ObjectCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RequestMeetingCommand implements GeneralCommand{
private MiniAppCommandBoundary miniAppCommandBoundary;
private ObjectCrud objectCrud;

public RequestMeetingCommand(MiniAppCommandBoundary m,ObjectCrud objectCrud)
{
	this.setMiniAppCommandBoundary(m);
	this.objectCrud=objectCrud;
}
	
	@Override
	public Flux<ObjectEntity> execute() {
		// TODO Maayan: objectDeatils("offers",string[]offers)
		//switch case options:1)sort by seen people views long, 
		//2)offers("id1:alias",)->findByIdAndAlias->save this object ->if want -send Note(string) "HEY I WANT TO MEET MEET YOU"
		switch(miniAppCommandBoundary.getCommand()) {
		//switch case options:1)sort by seen people views long,
		case("request-sortByviewsOffers"):
			
		break;
		
		case("request-sortByviewsOffers1"):
	break;}
		return null;
	}

	public MiniAppCommandBoundary getMiniAppCommandBoundary() {
		return miniAppCommandBoundary;
	}

	public void setMiniAppCommandBoundary(MiniAppCommandBoundary miniAppCommandBoundary) {
		this.miniAppCommandBoundary = miniAppCommandBoundary;
	}
}


