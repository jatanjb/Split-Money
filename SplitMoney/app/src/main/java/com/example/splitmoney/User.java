package com.example.splitmoney;

import java.util.ArrayList;

public class User {
    private int userId;
    private String useremailTxt, usernameTxt, userexpenseTxt;
    ArrayList<Integer> selectedItems = new ArrayList<>();

    public User(int userId, String useremailTxt, String usernameTxt, String userexpenseTxt, ArrayList<Integer> selectedItems){
        this.userId = userId;
        this.useremailTxt = useremailTxt;
        this.usernameTxt = usernameTxt;
        this.userexpenseTxt = userexpenseTxt;
        this.selectedItems = selectedItems;
    }

    public int getUserId(){
        return userId;
    }

    public String getUseremailTxt() {
        return useremailTxt;
    }

    public String getUsernameTxt() {
        return usernameTxt;
    }

    public String getUserExpenseTxt(){ return userexpenseTxt; }

    public ArrayList<Integer> getSelectedItems(){ return selectedItems; }
}
