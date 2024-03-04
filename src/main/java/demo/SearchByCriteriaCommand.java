package demo;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.springframework.stereotype.Component;

import demo.boundries.MiniAppCommandBoundary;
import demo.interfaces.GeneralCommand;
import demo.interfaces.ObjectCrud;
import demo.interfaces.UserCrud;

@Component("SEARCHByCriteria ")
public class SearchByCriteriaCommand implements GeneralCommand {

	private ObjectCrud objectcrud;
	// private UserCrud usercrud;

	public SearchByCriteriaCommand(ObjectCrud objectcrud) {
		this.objectcrud = objectcrud;
	}

	@Override
	public Object execute(MiniAppCommandBoundary m) {
		// TODO Auto-generated method stub
		String specificCommand = (String) m.getCommand();// Null-return exception
		switch (specificCommand) {
		case ("searchByUserName"):
			String userName = (String) m.getCommandAttributes().get("UserName");
			return this.objectcrud.findAllByActiveIsTrueAndAliasLike(userName);
			
		case ("searchByAge"):
			// TODO format Date and calc age
			LocalDate d = (LocalDate) m.getCommandAttributes().get("BirthDate");
			int age = Period.between(d, LocalDate.now()).getYears();
			return this.objectcrud.findfindByAgeGreaterThan(age);
			
			
		case("sortPopular"):
			//
			
		default:
			return this.objectcrud.findAll();

		}

	}
}
