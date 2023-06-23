import java.util.HashMap;

/**
 * Slouzi pro komunikace s datovymi struktury ktere chrani
 * vsechna data aplikace
 */
public class Model {

    /**Hlavni zasobnik vsech minci*/
    private HashMap<Integer, Integer> allCoinsStack;
    /**Minci vlozene uzivatelem hned*/
    private HashMap<Integer, Integer> newCoinsStack;
    /**Tridene mince pro vraceni uzivateli*/
    private HashMap<Integer, Integer> changeCoinsStack;
    /**Vsichni dostupne zbozi*/
    private HashMap<Integer, Product> allProducts;
    /**Druhy nazvu zbozi*/
    private String[] productsNames = {"Bageta", "Tycinka Snikers", "Tycinka Kitkat", "Tycinka Knopers", "Napoj Pepsi"};
    /**Ceny zbozi*/
    private int [] productPrices = {55, 25, 25, 25, 20};

    public Model(){

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
            allProducts.put(i, new Product(i, productsNames[i], 10, productPrices[i]));
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
                return;
            }
        }

        newCoinsStack.put(coin, 1);
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
