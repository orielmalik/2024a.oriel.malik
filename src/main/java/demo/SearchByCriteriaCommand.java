package demo;

import org.springframework.stereotype.Component;

import demo.boundries.MiniAppCommandBoundary;
import demo.entities.ObjectEntity;
import demo.interfaces.GeneralCommand;
import demo.interfaces.ObjectCrud;
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
		case ("search-ByUserName"):
			String userName = (String) miniAppCommandBoundary.getCommandAttributes().get("UserName");
			return this.objectcrud.findAllByActiveIsTrueAndAliasLike(userName);

		/*
		 * case ("searchByAge"): // TODO format Date and calc age LocalDate d =
		 * (LocalDate) m.getCommandAttributes().get("BirthDate"); int age =
		 * Period.between(d, LocalDate.now()).getYears(); return
		 * this.objectcrud.findByAgeGreaterThan(age);
		 */

		case ("sortPopular"):
			//

		default:
			return this.objectcrud.findAll();

		}

	}
}
