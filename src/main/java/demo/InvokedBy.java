package demo;

import demo.entities.UserId;

public class InvokedBy{

    private UserId userId;

    public InvokedBy() {
        this.userId=new UserId();
    }
    public InvokedBy(UserId userId) {
        this.userId=userId;
    }


    
    
    public UserId getUserId() {
        return new UserId(userId.getSuperapp(), userId.getEmail());
    }

    public void setUserId(UserId userId) {
        this.userId = new UserId(userId.getSuperapp(),userId.getEmail());
    }
}