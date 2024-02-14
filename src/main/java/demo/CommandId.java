package demo;

public class CommandId {
    private String superapp;
    private String miniapp;
    private String id;

    public CommandId() {
    }

    public CommandId(String superapp, String miniapp, String id)
    {
        this.setSuperapp(superapp);
        this.setMiniapp(miniapp);
        this.setId(id);
    }

    public String getSuperapp() {
        return superapp;
    }
    public CommandId setSuperapp(String superapp) {
        this.superapp = superapp;
        return this;
    }

	public String getMiniapp() {
		return miniapp;
	}

	public CommandId setMiniapp(String miniapp) {
		this.miniapp = miniapp;
		return this;
	}

	public String getId() {
		return id;
	}

	public CommandId setId(String id) {
		this.id = id;
		return this;
	}
 
    

}
