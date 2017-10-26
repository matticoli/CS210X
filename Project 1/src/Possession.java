public class Possession extends Entity implements Ownable {

    private float price;

    public Possession(String name, Image image, float price) {
        super(name, image);
        this.price = price;
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
