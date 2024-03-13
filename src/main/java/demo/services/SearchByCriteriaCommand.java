package demo.services;

import demo.boundries.MiniAppCommandBoundary;
import demo.entities.ObjectEntity;
import demo.exception.BadRequest400;
import demo.interfaces.GeneralCommand;
import demo.interfaces.ObjectCrud;
import jdk.javadoc.doclet.Taglet.Location;
import reactor.core.publisher.Flux;

public class SearchByCriteriaCommand implements GeneralCommand {

	private ObjectCrud objectcrud;
	// private UserCrud usercrud;
	private MiniAppCommandBoundary miniAppCommandBoundary;

	public SearchByCriteriaCommand(ObjectCrud objectcrud, MiniAppCommandBoundary mi) {
		this.objectcrud = objectcrud;
		this.miniAppCommandBoundary = mi;
	}

	@Override
	public Flux<ObjectEntity> execute() {
		// TODO Auto-generated method stub
		String specificCommand = (String) miniAppCommandBoundary.getCommand();// Null-return exception
		switch (specificCommand) {
		case ("search-ByUserNameLike"):
			String userName = (String) miniAppCommandBoundary.getCommandAttributes().get("UserName");
			if (userName == null) {
				return Flux.error(new BadRequest400("UserName field does not exist"));
			}
			return this.objectcrud.findAllByActiveIsTrueAndAliasLike(userName);

		case ("search-ByAge"):
			long birthdate = (long) miniAppCommandBoundary.getCommandAttributes().get("birthdate");
			return this.objectcrud.findByBirthdateGreaterThan(birthdate);

		case ("search-Bygender"):
			boolean gender = (boolean) miniAppCommandBoundary.getCommandAttributes().get("gender");
			return this.objectcrud.findByGenderNot(gender);

		case ("search-Bylocation"):
			String location = (String) miniAppCommandBoundary.getCommandAttributes().get("location");
			return this.objectcrud.findByLocation(location);

		case ("search-ByPopular"):
			String a = (String) miniAppCommandBoundary.getCommandAttributes().get("UserName");
			return this.objectcrud.findByAliasOrderByViewscountDesc(a);

		default:
			return this.objectcrud.findAll();

		}

	}
}
