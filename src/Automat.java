import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Automat {

    private WindowView windowView;
    private Model database;
    private States actualState;
    private int selectedItem;

    /**
     * Konstruktor
     * @param database
     */
    public Automat(Model database){
        this.database = database;
        this.windowView = new WindowView(database);
        refillMachine();
    }

    public void refillMachine(){
        actualState = States.START;
        windowView.startStateView();
        database.fillChangeCoinStack();
        database.generateProducts();
        selectedItem = -1;
    }

    /**
     * Zpracovava vstup pro stav START
     * @param command
     */
    public void startStateController(String command){

        switch (command.charAt(2)){
            case 'M':
                sumHandlingController(command);
                break;
            case 'Z':
                productValueHandling(command);
                break;
            case 'I':
                itemsInfo();
                break;
            case 'S':
                break;
            case 'V':
                break;

        }
    }

    public void selectedGoodController(String command){
        switch (command.charAt(2)){
            case 'O':
                break;
            case 'V':
                break;
        }

    }


    private void itemsInfo(){
        HashMap<Integer, Product> products = database.getAllProducts();
        List<Integer> keys = new ArrayList<Integer>(products.keySet());
        String output = "";

        for(int i = 0; i < keys.size(); i++){
            output += i + " " + products.get(keys.get(i)).getName() + " Pocet:" + products.get(keys.get(i)).getCount() +
                    " Kusu, Cena:" + products.get(keys.get(i)).getPrice() + "Kc \n";
        }
        windowView.goodsInfoView(output);
    }

    /**
     * Zpracovava prikaz vyberu zbozi
     *
     * @param command prikaz uzivatele
     */
    private void productValueHandling(String command){

        if(command.length() == 5){
            char index = command.charAt(4);
            if(Character.isDigit(index)){
                int indexNumber = Character.getNumericValue(index);
                if(indexNumber <= 4){

                    actualState = States.GOOD_IS_SELECTED;
                    windowView.goodIsSelected(database.getProduct(indexNumber).getName());
                    selectedItem = indexNumber;

                }else{
                    windowView.badProductIndex();
                }

            }else{
                windowView.badProductIndex();
            }
        }else{
            windowView.badCommand();
        }
    }

    /**
     * Prodlouzeni s vybranym zbozim
     */
    private void contWithGood(){
        int userAmount = amountInUserStorage();
        if(selectedItem != -1){

            if(productAvailController(selectedItem)){
                if(saleController(selectedItem)){
                    int changeAmount = userAmount - database.getAllProducts().get(selectedItem).getPrice();

                    if(canChange(changeAmount)){
                        actualState = States.DELIVERY_OF_PRODUCT;
                        //vypis pro vydej produktu
                        removeProduct(selectedItem);
                        changeMoneyController(selectedItem);

                    }else{
                        windowView.notAnoughtChangeMon();
                        refundMoneyController();
                        actualState = States.START;
                    }

                }else {
                    windowView.notEnoughtMoney();
                    actualState = States.WALLET;
                    windowView.walletState(String.valueOf(userAmount));
                }
            }else {
                windowView.productIsNotAvail();
                actualState = States.START;
                windowView.startStateView();
            }
        }else{
            actualState = States.START;
        }
    }

    /**
     * TODO treba se otestovat mozna normalne nefunguje
     * @param index
     */
    public void changeMoneyController(int index){

        Product product = database.getProduct(index);
        int costOfItem = product.getPrice();
        int userAmount = amountInUserStorage();
        int changedAmount = userAmount - costOfItem;
        int [] coinsTemp = new int[5];
        HashMap<Integer, Integer> coinChangeStack = database.getChangeCoinsStack();
        List<Integer> keys = new ArrayList<Integer>(coinChangeStack.keySet());
        String output = "";

        for(int i = 0; i < keys.size(); i++){
            int key = keys.get(i);

            while(coinChangeStack.get(key) > 0 && changedAmount >= key){
                changedAmount -= key;
                coinsTemp[i]++;
            }

        }

        for(int i = 0; i < coinsTemp.length; i++){
            int key = keys.get(i);
            output += "Mince: " + key + " Kc " + coinsTemp[i] + " Kusu\n";
        }

    }

    /**
     * Odstranuje zbozi pri prodeji
     * @param index
     */
    private void removeProduct(int index){
        HashMap<Integer, Product> products = database.getAllProducts();
        Product product = products.get(index);
        if(product != null){
            if(product.getCount() > 0){
                product.setCount(product.getCount() - 1);
            }
        }

    }

    /**
     * Slouzi pro vraceni penez ze vstupniho zasobniku
     *
     */
    public void refundMoneyController(){
        String changeDesc = "";

        HashMap<Integer, Integer> userMoney = database.getNewCoins();
        List<Integer> keys = new ArrayList<Integer>(userMoney.keySet());

        for(int i = 0; i < keys.size(); i++){
            int key = keys.get(i);
            int coinCount = userMoney.get(key);
            changeDesc += "Mince: " + key + " Kc " + coinCount + " Kusu\n";
            userMoney.put(key, 0);
        }
        windowView.coinReturn(changeDesc);
    }

    /**
     * Slouzi pro kontrolu zda automat muze
     * vratit potrebnou castku
     * @param amount
     * @return
     */
    public boolean canChange(int amount){
        HashMap<Integer, Integer> changeCoins = (HashMap<Integer, Integer>) database.getChangeCoinsStack().clone();
        List<Integer> keys = new ArrayList<Integer>(changeCoins.keySet());

        for(int i = 0; i < keys.size(); i++){
            int key = keys.get(i);

            while(changeCoins.get(key) > 0 && amount >= key){
                amount -= key;
            }
        }
        return amount == 0;
    }

    /**
     * Kontroluje, zda uzivatelskich penez je dost
     * pro nakup zbozi
     * @param index
     * @return true, pokud castka je dostatecna
     */
    public boolean saleController(int index){
        HashMap<Integer, Product> products = database.getAllProducts();

        if(amountInUserStorage() > products.get(index).getPrice()){
            return true;
        }
        return false;
    }

    /**
     * Kontroluje pritomnost zbozi
     * @param index
     * @return
     */
    public boolean productAvailController(int index){
        HashMap<Integer, Product> products = database.getAllProducts();

        if(products.get(index).getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Zpracovava prikaz vlozeni minci do automatu
     * @param command
     */
    private void sumHandlingController(String command){

        if(command.length() == 5){
            char sum = command.charAt(4);
            if(Character.isDigit(sum)){
                int sumNumber = Character.getNumericValue(sum);

                if(coinCheck(sumNumber)){

                    database.addNewCoin(sumNumber);
                    actualState = States.WALLET;
                    windowView.walletState(Integer.toString(amountInUserStorage()));

                }else{
                    windowView.badAmount();
                }
            }else{
                windowView.badAmount();
            }
        }else{

            windowView.badCommand();
        }
    }

    /**
     * Kontrola zda mince je jednou z dostupnych minci
     * @param coin
     * @return
     */
    private boolean coinCheck(int coin){
        int[] allAmounts = {1, 2, 5, 10, 20};

        for(int i = 0; i < allAmounts.length; i++){
            if(allAmounts[i] == coin){
                return true;
            }
        }
        return false;
    }


    /**
     * Zpracovava uzivatelsky vstup
     * @param input
     */
    public void processInput(String input){
        if(input.length() > 2 && input.length() < 6){
            if(input.charAt(0) == 'A' && input.charAt(1) == '-'){
                switch (actualState){
                    case START:
                        startStateController(input);
                    case WALLET:
                        break;
                    case GOOD_IS_SELECTED:
                        break;
                    case CHANGE_RETURN_ERR:
                        break;
                    case CHANGE_RETURN_PROPER:
                        break;
                    case DELIVERY_OF_PRODUCT:
                        break;
                }

            }else{
                windowView.badCommand();
            }
        }

        else {
            windowView.badCommand();
        }
    }

    /**
     * Vraci castku vsech minci vlozenych
     * uzivatelem
     * @return
     */
    public int amountInUserStorage(){
        int amount = 0;

        HashMap<Integer, Integer> usersMoney = database.getNewCoins();
        List<Integer> keys = new ArrayList<Integer>(usersMoney.keySet());

        for(int i = 0; i < keys.size(); i++){
            int key = keys.get(i);
            int coinValue = usersMoney.get(key);

            amount += (key * coinValue);

        }

        return amount;
    }

}
