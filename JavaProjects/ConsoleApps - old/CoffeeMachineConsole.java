/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package github;
    import java.util.Scanner;
/**
 *
 * @author wkris
 */
public class CoffeeMachineConsole {


// alap változók
    //italok szükséges hozzávalói
    //a kávégép tartalma

    static final int EWATER = 250;
    static final int EMONEY = 4;
    static final int ECOFFEEBEANS = 16;
    static final int LWATER = 350;
    static final int LMONEY = 7;
    static final int LMILK = 75;
    static final int LCOFFEEBEANS = 20;
    static final int CWATER = 200;
    static final int CMILK = 100;
    static final int CCOFFEEBEANS = 12;
    static final int CMONEY = 6;
    static int CURRENTWATER = 400;
    static int CURRENTMILK = 540;
    static int CURRENTCOFFEEBEANS = 120;
    static int CURRENTMONEY = 550;
    static int DISCUPS = 9;
    static int ADDTWATER = 0;
    static int ADDMILK = 0;
    static int ADDCOFFEEBEANS = 0;
    static int ADDDISCUPS = 0;
    
    //szükséges poharak számítása
    public static int ForCups(int HowManyCups, int Ingredient) {
        int totalAmount = HowManyCups * Ingredient;
        return totalAmount;
    }

    public static boolean CanMakeCoffee(int HowManyCups) {

        return true;
    }
    //kiírja a kávégép jelenlegi tartalékait
    public static void writeCurrAmount(int water, int milk, int cooffeB, int disc, int money) {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(cooffeB + " of coffee beans");
        System.out.println(disc + " of disposable cups");
        System.out.println(money + " of money");
    }
    // vétel, levonja az aktuális tartalékokból az adott típus árát
    public static void buy(int water, int milk, int cooffeB, int disc, int money) {
        if (water <= CURRENTWATER && milk <= CURRENTMILK && cooffeB <= CURRENTCOFFEEBEANS && disc <= DISCUPS) {
            CURRENTCOFFEEBEANS = CURRENTCOFFEEBEANS - cooffeB;
            CURRENTWATER = CURRENTWATER - water;
            CURRENTMILK = CURRENTMILK - milk;
            DISCUPS = DISCUPS - disc;
            CURRENTMONEY = CURRENTMONEY + money;
            System.out.println("I have enough resources, making you a coffee!");
        } else {
            if (CURRENTWATER < water) {
                System.out.println("Sorry, not enough water!");
            } else if (CURRENTMILK < milk) {
                System.out.println("Sorry, not enough milk!");
            } else if (CURRENTCOFFEEBEANS < cooffeB) {
                System.out.println("Sorry, not enough coffee beans!");
            } else if (DISCUPS < disc) {
                System.out.println("Sorry, not enough disposable cups!");
            }
        }

    }
    // újratöltés
    public static void fill(int water, int milk, int cooffeB, int disc) {

        CURRENTCOFFEEBEANS = CURRENTCOFFEEBEANS + cooffeB;
        CURRENTWATER = CURRENTWATER + water;
        CURRENTMILK = CURRENTMILK + milk;
        DISCUPS = DISCUPS + disc;
         writeCurrAmount(CURRENTWATER, CURRENTMILK, CURRENTCOFFEEBEANS, DISCUPS, CURRENTMONEY);

    }
    // pénz kivétel
    public static void take(int money) {
        CURRENTMONEY = CURRENTMONEY - money;
    }

    public static void main(String[] args) {
        boolean exit = false;
        //kezdésnél kiírja az tartalékokat
        writeCurrAmount(CURRENTWATER, CURRENTMILK, CURRENTCOFFEEBEANS, DISCUPS, CURRENTMONEY);
        do {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            Scanner sc = new Scanner(System.in);
            String action = sc.nextLine();
            // megadjuk a kívánt tevékenységet
            switch (action) {
                case "buy":
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                    // káve típusok ára
                    System.out.println("The espresso, the coffee machine needs 250 ml of water and 16 g of coffee beans. It costs $4.\nThe latte, the coffee machine needs 350 ml of water, 75 ml of milk, and 20 g of coffee beans. It costs $7.\nThe cappuccino, the coffee machine needs 200 ml of water, 100 ml of milk, and 12 g of coffee. It costs $6.");
                    sc = new Scanner(System.in);
                    try{
                    int coffeType = sc.nextInt();
                    // megadjuk a kávé típust
                     switch (coffeType) {
                        case 1:
                            buy(EWATER, 0, ECOFFEEBEANS, 1, EMONEY);
                            break;
                        case 2:
                            buy(LWATER, LMILK, LCOFFEEBEANS, 1, LMONEY);
                            break;
                        case 3:
                            buy(CWATER, CMILK, CCOFFEEBEANS, 1, CMONEY);
                            break;
                        default:
                            break;
                    }}
                    // rossz érték megadása esetén visszalép
                    catch(Exception e){
                        String back=sc.nextLine();
                        switch (back) {
                        case "back":
                            break;
                    }
                    }
                    break;
                    // feltöltés
                case "fill":
                    System.out.println("Write how many ml of water do you want to add: ");
                    sc = new Scanner(System.in);
                    ADDTWATER = sc.nextInt();
                    System.out.println("Write how many ml of milk do you want to add: ");
                    sc = new Scanner(System.in);
                    ADDMILK = sc.nextInt();
                    System.out.println("Write how many grams of coffee beans do you want to add: ");
                    sc = new Scanner(System.in);
                    ADDCOFFEEBEANS = sc.nextInt();
                    System.out.println("Write how many disposable cups of coffee do you want to add: ");
                    sc = new Scanner(System.in);
                    ADDDISCUPS = sc.nextInt();
                    fill(ADDTWATER, ADDMILK, ADDCOFFEEBEANS, ADDDISCUPS);
                    break;
                    // pénz kivétel
                case "take":
                    System.out.println("I gave you $" + CURRENTMONEY);
                    take(CURRENTMONEY);
                    // jelenlegi tartalékok kiírása
                case "remaining":
                    writeCurrAmount(CURRENTWATER, CURRENTMILK, CURRENTCOFFEEBEANS, DISCUPS, CURRENTMONEY);
                    break;
                    // kilépés
                case "exit":
                    exit = true;
                    break;

            }
        } while (exit == false);
    }

}

