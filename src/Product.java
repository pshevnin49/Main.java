public class Product {

    private int id;
    private String name;
    private int count;
    private int price;

    /**
     * Trida reprezentuje zbozi
     * @param id
     * @param name
     * @param count
     * @param price
     */
    public Product(int id, String name, int count, int price){
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
