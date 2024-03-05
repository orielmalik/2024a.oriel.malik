package demo.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.springframework.stereotype.Component;

import demo.boundries.MiniAppCommandBoundary;
import demo.entities.ObjectEntity;
import demo.exception.BadRequest400;
import demo.interfaces.GeneralCommand;
import demo.interfaces.ObjectCrud;
import demo.interfaces.UserCrud;
import reactor.core.publisher.Flux;

public class SearchByCriteriaCommand implements GeneralCommand {

	private ObjectCrud objectcrud;
	// private UserCrud usercrud;
private MiniAppCommandBoundary miniAppCommandBoundary;
	public SearchByCriteriaCommand(ObjectCrud objectcrud,MiniAppCommandBoundary mi) {
		this.objectcrud = objectcrud;
		this.miniAppCommandBoundary=mi;
	}

	@Override
	public Flux<ObjectEntity> execute() {
		// TODO Auto-generated method stub
		String specificCommand = (String) miniAppCommandBoundary.getCommand();// Null-return exception
		switch (specificCommand) {
		case ("search-ByUserName"):
			
			String userName = (String) miniAppCommandBoundary.getCommandAttributes().get("UserName");
		if(userName==null)
		{
			return Flux.error(new BadRequest400("UserName field does not exist"));
		}
			return this.objectcrud.findAllByActiveIsTrueAndAliasLike(userName);
			
	/*	case ("search-ByAge"): 
			// TODO format Date and calc age
			LocalDate d = (LocalDate) m.getCommandAttributes().get("BirthDate");
			int age = Period.between(d, LocalDate.now()).getYears();
			return this.objectcrud.findByAgeGreaterThan(age);
			Moshe: Case SearchByAge,SortPopular-count methods which checks objectDeatils keys
			*/
			
		case("sort-Popular"):
			//
			
		default:
			return this.objectcrud.findAll();

		}

	}
}
