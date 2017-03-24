package io.vertx;

public class Account {
    private String id;
    private String label;
    private String number;
    private String type;
    private String IBAN;
    private String SWIFT;
    private String bank_id;

    private Balance balance;
    private Owner owner;

    public Account(String id,
                   String label,
                   String number,
                   String type,
                   String IBAN,
                   String SWIFT,
                   String bank_id,
                   Balance balance,
                   Owner owner) {
        this.id = id;
        this.label = label;
        this.number = number;
        this.type = type;
        this.IBAN = IBAN;
        this.SWIFT = SWIFT;
        this.bank_id = bank_id;
        this.balance = balance;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getSWIFT() {
        return SWIFT;
    }

    public void setSWIFT(String SWIFT) {
        this.SWIFT = SWIFT;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
