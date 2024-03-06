package demo.utils;

import demo.InvokedBy;
import demo.TargetObject;
import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.UserBoundary;
import demo.entities.UserEntity;
import demo.interfaces.GeneralCommand;
import demo.interfaces.UserCrud;

public class Menu {

	private UserBoundary user;
	private TargetObject target;
	private InvokedBy invoker;//by id specific i find this invokedBy and do i
 private UserCrud usercrud;
	private MiniAppCommandBoundary m;
	private GeneralCommand generalCommand;
	public Menu(String id)
	{
		m=new MiniAppCommandBoundary();
	}
	
	
	
	public void OptionsMenu(String option)
	{//fill  other miniApp Command
		switch(option.toLowerCase())
		{
		case("search"):
			break;
		
		case("createmeeting"):
			break;
		case("joinattraction"):
			break;
		case("provideproduct"):
			break;
		}
	//	generalCommand.execute(m);
	}
	
	/*public boolean fillbyId(String id)//true if we succeded login to menu
	{
		UserEntity e=this.usercrud.findById(id).block();
		if(e==null)
		{
		return false;	
		}
		invoker.setUserId(e.get);
		
		return true;
	}
	public void fillMiniAppCommand(String command)
	{
		
	}*/
	
	
}
