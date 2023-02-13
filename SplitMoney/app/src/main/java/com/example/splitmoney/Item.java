package com.example.splitmoney;

public class Item {
    private int userId;
    private int itemId;
    private String itemAmount;
    private String itemDesc;

    public Item(int userId, int itemId, String itemAmount, String itemDesc){
        this.userId = userId;
        this.itemId = itemId;
        this.itemAmount = itemAmount;
        this.itemDesc = itemDesc;
    }

    public int getUserId(){ return userId; }

    public int getItemId() { return itemId; }

    public String getItemAmount(){
        return itemAmount;
    }

    public String getItemDesc() {
        return itemDesc;
    }
}
