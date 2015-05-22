public class Item {
	private String name;

	private double price;

	private double quantity;

	public Item(String name, double price, double quantity) {
		this.name = name;

		this.price = price;

		this.quantity = quantity;
	}

	public void setQuantity(double newQuantity) {
		quantity = newQuantity;
	}

	public double getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}
}