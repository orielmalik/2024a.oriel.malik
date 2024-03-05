package demo.services;

import demo.boundries.MiniAppCommandBoundary;
import demo.entities.ObjectEntity;
import demo.interfaces.GeneralCommand;
import demo.interfaces.ObjectCrud;
import reactor.core.publisher.Flux;

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
		case("request-sortByviewsOffers"):
			//switch case options:1)sort by seen people views long, 

			break;
		case("request-offers"):
			//2)offers("id1:alias",)->
			//findByIdAndAlias->objectentity.getobjectdeatils.get("id:alias-true")->save this object ->-send note(string) "HEY I WANT TO MEET MEET YOU"

		default:
			return null;
		}
		return null;
	}

	public MiniAppCommandBoundary getMiniAppCommandBoundary() {
		return miniAppCommandBoundary;
	}

	public void setMiniAppCommandBoundary(MiniAppCommandBoundary miniAppCommandBoundary) {
		this.miniAppCommandBoundary = miniAppCommandBoundary;
	}

}
