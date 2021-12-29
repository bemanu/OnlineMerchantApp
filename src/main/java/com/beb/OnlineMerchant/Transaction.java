package com.beb.OnlineMerchant;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Currency;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Transaction {
   @Id
   @GeneratedValue
   private UUID id;

   @NotNull
   @Column(name = "date")
   private Date transactionDate;

   @NotNull
   @Column(name = "status")
   private TransactionStatus transactionStatus;

   @NotNull
   @Column(name = "amount")
   private Long amount;

   @NotNull
   @Column(name = "currency")
   private Currency transactionCurrency;

   @Column(name = "description")
   private String description;


    public UUID getId() {
        return id;
    }


    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Currency getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(Currency transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTransactionDate(), that.getTransactionDate()) && getTransactionStatus() == that.getTransactionStatus() && Objects.equals(getAmount(), that.getAmount()) && Objects.equals(getTransactionCurrency(), that.getTransactionCurrency()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTransactionDate(), getTransactionStatus(), getAmount(), getTransactionCurrency(), getDescription());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", transactionStatus=" + transactionStatus +
                ", amount=" + amount +
                ", transactionCurrency=" + transactionCurrency +
                ", description='" + description + '\'' +
                '}';
    }
}
