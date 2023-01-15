package cz.petrchatrny.sopc.entity.item;

public abstract class Item implements IItem, Comparable<Item> {
    private final String name;
    private final String image;
    private int count;
    private final ItemType type;
    private final int comparingOrder;
    private final String BASE_PATH = "images/items/";

    protected Item(String name, String image, int count, ItemType type, int comparingOrder) {
        this.name = name;
        this.image = BASE_PATH + image;
        this.count = count;
        this.type = type;
        this.comparingOrder = comparingOrder;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increaseCount() {
        this.count++;
    }

    public ItemType getType() {
        return type;
    }

    public int getComparingOrder() {
        return comparingOrder;
    }

    @Override
    public String toString() {
        return name + " " + count;
    }

    @Override
    public int compareTo(Item item) {
        if (comparingOrder == item.getComparingOrder()) {
            return name.compareTo(item.getName());
        } else if (comparingOrder > item.getComparingOrder()) {
            return 1;
        } else {
            return -1;
        }
    }
}
