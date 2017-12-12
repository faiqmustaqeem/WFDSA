package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 10/26/2017.
 */

public class PaymentModel {
   public String id, title, dueDate, amount;

    public PaymentModel(String id, String dueDate, String title, String amount) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.amount = amount;
    }

    public PaymentModel() {

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getAmount() {
        return amount;
    }
}
