package demo;
public class TargetObject {

    private ObjectId objectId;

    public TargetObject(){

    }

    public TargetObject(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return new ObjectId(objectId.getSuperapp(), objectId.getId());
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = new ObjectId(objectId.getSuperapp(), objectId.getId());
    }
}
