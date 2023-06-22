public class WindowView {

    private Model database;

    public WindowView(Model database){
        this.database = database;
    }

    /**
     * Slouzi pro vypis pocatecniho stavu pri startu
     * aplikace
     */
    public void startStateView(){
        System.out.println();
        System.out.println("Aktualni stav: start");
        System.out.println("Dostupne aktivity:");
        System.out.println("A-M=N pro vlozeni mince, kde N je mena mince");
        System.out.println("A-Z=N pro vyber zbozi, kde N je index zbozi");
        System.out.println("A-I pro vypis info o dostupnem zbozi");
        System.out.println("A-S Servisni tlacitko pro naplneni automatu zbozim a odber minci");
        System.out.println("A-V Tlacitko <STORNO>");
    }

    /**
     * Slouzi pro vypis moznych prikazu
     * pri vybranem zbozi
     * @param item
     */
    public void goodIsSelected(String item){
        System.out.println("Je vybrano: " + item);
        System.out.println("Aktualni stav: item is selected");
        System.out.println("Dostupne aktivity:");
        System.out.println("A-O potvrdit vyber zbozi");
        System.out.println("A-V Tlacitko <STORNO>");
    }

    public void walletState(String amount){
        System.out.println();
        System.out.println("Aktualni stav: wallet");
        System.out.println("Jste vlozil " + amount + " Kc");
        System.out.println("Dostupne aktivity:");
        System.out.println("A-M=N pro vlozeni mince, kde N je mena mince");
        System.out.println("A-Z=N pro vyber zbozi, kde N je index zbozi");
        System.out.println("A-I pro vypis info o dostupnem zbozi");
        System.out.println("A-V Tlacitko <STORNO>");
    }


    /**
     * Slouzi pro vypis aktualne dostupneho zbozi, a poctu.
     * Prochazi po baze dat
     */
    public void goodsInfoView(String info){
        System.out.println("Dostupne zbozi: ");
        System.out.println(info);
    }

    /**
     * Slouzi pro vypis v pripade nedostatku penez
     */
    public void notEnoughtMoney(){
        System.out.println("Mate malo penez");
    }

    /**
     * Vypis pri nedostatku poctu zbozi
     */
    public void productIsNotAvail(){
        System.out.println("Omlouvame se, ale zbozi momentalne neni k dispozici");
    }

    public void itemDeliveryState(){
        System.out.println();
        System.out.println("Aktualni stav: delivery of item");
    }

    public void changeFromUserStack(){
        System.out.println();
        System.out.println("Aktualni stav: money refund");
    }

    /**
     * Vypis vydej zbozi
     * @param goodsName
     */
    public void issuanceOfGoods(String goodsName){
        System.out.println("Vydano zbozi: " + goodsName);
    }

    /**
     * Vypis v pripade zadani spatneho prikazu
     */
    public void badCommand(){
        System.out.println("Spatny format prikazu");
        System.out.println("Prikaz musi byt ve formatu: A-<_> ");
    }

    /**
     * Vypis v pripade zadani spatne castky
     */
    public void badAmount(){
        System.out.println("Spatny format castky, vyberte prosim jednu z dostupnych minci: (1, 2, 5, 10, 20)");
    }

    public void badProductIndex(){
        System.out.println("Spatny index zbozi, zbozi s takovym indexem neexistuje");
    }

    public void notAnoughtChangeMon(){
        System.out.println("Omlouvame se, ale automat nema penize pro vraceni");
    }

    /**
     * Vypis po stisknuti servisniho tlacitka
     * @param allCoins
     */
    public void serviceButtonView(String allCoins){
        System.out.println("Stisknuto tlacitko SERVICE, zbozi je aktualizovano");
        System.out.println("Ze hlavniho zasobniku jsou vylozene mince:");
        if(allCoins.length() > 2){
            System.out.println(allCoins);
        }else {
            System.out.println("Zadne mince");
        }

    }

    /**
     * Vraceni minci do zasobniku minci
     */
    public void coinReturn(String changeMoney){
        System.out.println("Vracene mince: ");
        System.out.println(changeMoney);
    }

}
