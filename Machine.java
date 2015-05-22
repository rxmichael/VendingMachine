import java.util.Scanner;
import java.util.HashMap;
import java.util.TreeMap; 

public class Machine {

	private HashMap<String,Item> stock;

	private TreeMap<Double, Integer> change;

	private HashMap<Integer, String> menu;

	private double userMoney;


	public void loadChange(Double cat, Integer amount) {
		if (change.get(cat) != null) {
			int oldvalue = change.get(cat);
			change.put(cat, oldvalue + amount);
		}
		else
			change.put(cat, amount);
	}
	public void loadItems(String name,  Double price, Integer amount) {
		if (stock.get(name) != null) {
			Item current = stock.get(name);
			current.setQuantity(current.getQuantity() + amount);
		}
		else {
			Item current = new Item(name, price, amount);
			stock.put(name, current);
		}
	}

	public Machine() {
		stock = new HashMap<String, Item>();

		change = new TreeMap<Double, Integer>();

		menu = new HashMap<Integer, String>();

		userMoney = 0;
	}

	public double getMachineTotal() {
		double total = 0;
		for (Double val : change.keySet())
			total = total + (val * change.get(val));
		return total;
	}


	public static void main(String[] args) {
		

        Machine airportVending = new Machine();

        airportVending.menu.put(new Integer(1), "Snickers");
        airportVending.menu.put(new Integer(2), "KitKat");
        airportVending.menu.put(new Integer(3), "DietCoke");
        airportVending.menu.put(new Integer(4), "Sprite");
        airportVending.menu.put(new Integer(5),"Skittles");
        airportVending.menu.put(new Integer(6),"Starbust");
        airportVending.menu.put(new Integer(7),"Muffin");

        airportVending.loadChange(new Double(0.01), new Integer(100));
        airportVending.loadChange(new Double(0.02), new Integer(100));
        airportVending.loadChange(new Double(0.05), new Integer(100));
        airportVending.loadChange(new Double(0.1), new Integer(100));
        airportVending.loadChange(new Double(0.2), new Integer(100));
        airportVending.loadChange(new Double(0.5), new Integer(100));
        airportVending.loadChange(new Double(1.0), new Integer(100));
        airportVending.loadChange(new Double(2.0), new Integer(100));

        airportVending.loadItems("Snickers", 2.0, 3);
        airportVending.loadItems("KitKat", 3.20, 7);
        airportVending.loadItems("Skittles", 1.75, 5);
        airportVending.loadItems("Muffin", 4.0, 15);
        airportVending.loadItems("Starbust", 1.5, 5);
        airportVending.loadItems("KitKat", 2.15, 12);
        airportVending.loadItems("DietCoke", 1.75, 30);


        airportVending.runMachine();
        
    }
    public void buyItem(Integer sel, double money) {

    	Item current = this.stock.get(this.menu.get(sel));
    	double expected = current.getPrice();
    	if (money > expected) {
    		if (expected - money < this.getMachineTotal()) {
    			this.dispenseItem(current);
    			this.makeChange(money - expected);
                this.userMoney = (double) (money - expected);
    		}
    		else
    			System.out.println("Sorry ! Not enough change. Aborting operation");
    	}
    	else
    		System.out.println("Sorry, Insufficent funds ! Please insert  "+(expected - money)+" to buy this item");


    }

    public void dispenseItem(Item newItem) {
    	newItem.setQuantity(newItem.getQuantity()-1);
    	System.out.println("Please take the "+newItem.getName()+" from the drawer");
    }

    public void runMachine() {

    	Scanner input = new Scanner(System.in);
    	System.out.println("SELECTION MENU");
        for (Integer number : this.menu.keySet())
        {
        	String name = this.menu.get(number);
        	if(this.stock.get(name) != null)
        		System.out.println("Press "+ (int) number+" for "+ name + " price "+ this.stock.get(name).getPrice());
        }
        System.out.println("Please enter coins and bills followed by * to start selection: ");
        boolean exit = false;
        while (true)
        {
        	while (input.hasNext()) {

        		if (input.hasNextDouble())
        			this.userMoney = this.userMoney + input.nextDouble();
        		else if (input.next().equals("*"))
        			break;
        	}
            while (!exit) {

                System.out.println("Amount credited: "+this.userMoney);
                
                System.out.println("Please enter a selection, $ to add credit or EXIT to quit: ");

            	if(input.hasNextInt()) {
            		int selection = input.nextInt();
                    if (this.menu.containsKey(selection))
                        buyItem(selection, this.userMoney);
            		else
            			System.out.println("Enter a valid menu item");
            	}
                else {

                    String command = input.next();
                    if (command.equals("$")) {

                        System.out.println("Please enter coins and bills followed by * to add credit");
                        while (input.hasNext()) {
                            if (input.hasNextDouble())
                            this.userMoney = this.userMoney + input.nextDouble();
                            else if (input.next().equals("*"))
                            break;
                        }
                    }
                    else if (command.equals("EXIT")) {

                        System.out.println("Goodbye ! Have a nice day");
                        System.exit(0);
                    }
                }
            }

        }
    }

    public void makeChange(Double needed)
    {
        System.out.println("Change needed :"+needed);
    	double temp = needed;
    	for (Double value : this.change.descendingMap().keySet()) {

    		int numCoins = (int) (temp  / value.doubleValue());
    		temp = temp % value.doubleValue();
    		if (numCoins > 0) {
    			int old = this.change.get(value).intValue();
    			if (numCoins < old) {
    				this.change.put(value, new Integer(old - numCoins));
    				System.out.println("Please take your "+numCoins+" coin of "+ value);
    			}
    		}
    	}
    }
}