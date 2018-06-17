package model;

public class ItemMaster {

	private String itemid;		// 商品ID
	private String itemname;	// 商品名
	private long price;			// 単価
	private long stocks;		// 在庫数
	private String description;	// 商品説明



	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getStocks() {
		return stocks;
	}

	public void setStocks(long stocks) {
		this.stocks = stocks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
