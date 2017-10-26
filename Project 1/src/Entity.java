public abstract class Entity {
    private String name;
    private Image image;

    public Entity(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Entity)) {
            return false;
        } else {
            return ((Entity) o).getName().equals(this.getName());
        }
    }
}
