package demo.services;

import demo.boundries.MiniAppCommandBoundary;
import demo.entities.ObjectEntity;
import demo.interfaces.GeneralCommand;
import demo.interfaces.ObjectCrud;
import reactor.core.publisher.Flux;

public class ShopCommand  implements GeneralCommand{

	private ObjectCrud objectcrud;
	// private UserCrud usercrud;
private MiniAppCommandBoundary miniAppCommandBoundary;

	public ShopCommand(ObjectCrud objectcrud,MiniAppCommandBoundary mi) {
		this.objectcrud = objectcrud;
		this.miniAppCommandBoundary=mi;
	}
	@Override
	public Flux<ObjectEntity> execute() {
		// TODO Auto-generated method stub
		String command=(String)miniAppCommandBoundary.getCommand();
		double price=(double)miniAppCommandBoundary.getCommandAttributes().get("price");
		switch(command) {
		case("shop-priceAsc"):
		return this.objectcrud.findByTypeOrderByPriceAsc("product",price);
		case("shop-priceDesc"):
			return this.objectcrud.findByTypeOrderByPriceDesc("product",price);
		default:
			return null;
		}
		
	}

}
