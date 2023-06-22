import java.util.HashMap;

/**
 * Chrani vsechna potrebna data automatu
 */
public class Model {

    private HashMap<Integer, Coin> coins;
    private HashMap<Integer, Integer> allCoinsStack;
    private HashMap<Integer, Integer> newCoinsStack;
    private HashMap<Integer, Integer> changeCoinsStack;
    private HashMap<Integer, Product> allProducts;
    private String[] productsNames = {"Bageta", "Tycinka Snikers", "Tycinka Kitkat", "Tycinka Knopers", "Napoj Pepsi"};
    private int [] productPrices = {55, 25, 25, 25, 20};

    public Model(){

        coins = new HashMap<>();
        coins.put(1, Coin.JEDNA);
        coins.put(2, Coin.DVA);
        coins.put(5, Coin.PET);
        coins.put(10, Coin.DESET);
        coins.put(20, Coin.DVACET);

        allCoinsStack = new HashMap<>();
        newCoinsStack = new HashMap<>();
        changeCoinsStack = new HashMap<>();

        fillChangeCoinStack();
        generateProducts();
    }

    public void emptyAllCoinsStack(){
        allCoinsStack = new HashMap<>();
    }

    /**
     * Generuje vsichni zbozi pri restartu i pri plneni obsluhou
     */
    public void generateProducts(){
        allProducts = new HashMap<>();
        for(int i = 0; i < productsNames.length; i++){
            allProducts.put(i, new Product(i, productsNames[i], 1, productPrices[i]));
        }
    }

    /**
     * Slouzi pro vlozeni nove minci do zasobniku
     * minci vlozenych uzivatelem
     * @param coin
     */
    public void addNewCoin(int coin){

        if(newCoinsStack.get(coin) != null){
            if(newCoinsStack.get(coin) != 0){
                newCoinsStack.put(coin, newCoinsStack.get(coin) + 1);
            }
        }
        else{
            newCoinsStack.put(coin, 1);
        }
    }

    /**
     * Slouzi pro zaplneni zasobniku
     * s minci pro vraceni zpatky
     */
    public void fillChangeCoinStack(){
        changeCoinsStack.put(1, 10);
        changeCoinsStack.put(2, 10);
        changeCoinsStack.put(5, 10);
        changeCoinsStack.put(10, 10);
        changeCoinsStack.put(20, 10);
    }


    public Product getProduct(int id){
        return allProducts.get(id);
    }

    public HashMap<Integer, Product> getAllProducts(){
        return allProducts;
    }

    public HashMap<Integer, Integer> getChangeCoinsStack(){
        return changeCoinsStack;
    }

    public HashMap<Integer, Integer> getNewCoins(){
        return newCoinsStack;
    }

    public HashMap<Integer, Integer> getAllCoinsStack(){
        return allCoinsStack;
    }


}
