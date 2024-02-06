package demo;

public class CommandId {
    private String superapp;
    private String miniApp;
    private String CommandId;

    public CommandId() {
    }

    public CommandId(String superapp, String miniApp, String inCommandId)
    {
        this.superapp = superapp;
        this.miniApp = miniApp;
        this.setCommandId(inCommandId);
    }

    public String getSuperapp() {
        return superapp;
    }
    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }
    public String getMiniApp() {
        return miniApp;
    }
    public void setMiniApp(String miniApp) {
        this.miniApp = miniApp;
    }

	public String getCommandId() {
		return CommandId;
	}

	public void setCommandId(String commandId) {
		CommandId = commandId;
	}
 

}
