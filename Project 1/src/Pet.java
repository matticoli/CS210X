public class Pet extends LivingEntity implements Ownable {
    public Pet(String name, Image image) {
        super(name, image);
    }

    private Person owner;

    @Override
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public Person getOwner() {
        return owner;
    }
}
