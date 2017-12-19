package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 10/26/2017.
 */

public class PaymentModel {
   public String id, title, dueDate, amount;
    public String invoice_id;
    public String type;

    public PaymentModel(String id, String dueDate, String title, String amount) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.amount = amount;
    }

    public PaymentModel() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
