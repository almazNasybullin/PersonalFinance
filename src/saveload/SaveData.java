package saveload;

import exception.ModelException;
import model.*;
import settings.RateCurrency;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public final class SaveData {

    private static SaveData instance;
    private List<Article> articles = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<Currency> currencies = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Transfer> transfers = new ArrayList<>();

    private Common oldCommon;
    private boolean isSaved = true;
    private final Filter filter;

    private SaveData() {
        load();
        this.filter = new Filter();
    }

    public void load() {
        SaveLoad.load(this);
        sort();
        for (Account a : accounts) {
            a.setAmountFromTransactionsAndTransfers(transactions, transfers);
        }
    }

    public void clear() {
        articles.clear();
        accounts.clear();
        transactions.clear();
        transfers.clear();
        currencies.clear();
    }

    private void sort(){
        this.articles.sort((Article t, Article  t1) -> t.getTitle().compareToIgnoreCase(t1.getTitle()));
        this.accounts.sort((Account t, Account  t1) -> t.getTitle().compareToIgnoreCase(t1.getTitle()));
        this.transactions.sort((Transaction t, Transaction  t1) -> t1.getDate().compareTo(t.getDate()));
        this.transfers.sort((Transfer t, Transfer  t1) -> t1.getDate().compareTo(t.getDate()));
        this.currencies.sort(new Comparator<Currency>() {
            @Override
            public int compare(Currency currency, Currency currency1) {
                if (currency.isBase()) return  -1;
                if (currency1.isBase()) return  1;
                if (currency.isOn() ^ currency1.isOn()){
                    if (currency.isOn()) return 1;
                    else return -1;
                }
                return currency.getTitle().compareToIgnoreCase(currency1.getTitle());
            }
        });
    }

    public void save(){
        SaveLoad.save(this);
        isSaved = true;
    }

    public boolean isSaved(){
        return isSaved;
    }

    public static SaveData getInstance(){
        if (instance == null) instance = new SaveData();
        return instance;
    }

    public Filter getFilter(){
        return filter;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setArticles(List<Article> articles) {
        if (articles != null) this.articles = articles;
    }

    public void setAccounts(List<Account> accounts) {
        if (accounts != null) this.accounts = accounts;
    }

    public void setCurrencies(List<Currency> currencies) {
        if (currencies != null) this.currencies = currencies;
    }

    public void setTransactions(List<Transaction> transactions) {
        if (transactions != null) this.transactions = transactions;
    }

    public void setTransfers(List<Transfer> transfers) {
        if (transfers != null) this.transfers = transfers;
    }

    public Currency getBaseCurrency() {
        for (Currency c: currencies) {
            if (c.isBase()) return c;
        }
        return new Currency();
    }

    public ArrayList<Currency> getEnableCurrencies() {
        ArrayList<Currency> list = new ArrayList<>();
        for (Currency c : currencies){
            if (c.isOn()) list.add(c);
        }
        return list;
    }

    public List<Transaction> getFilterTransactions() {
        ArrayList<Transaction> list = new ArrayList<>();
        for (Transaction t: transactions){
            if (filter.check(t.getDate())) list.add(t);
        }
        return list;
    }

    public List<Transaction> getTransactionsOnCount(int count) {
        return new ArrayList(transactions.subList(0, Math.min(count, transactions.size())));
    }

    public List<Transfer> getFilterTransfers() {
        ArrayList<Transfer> list = new ArrayList<>();
        for (Transfer t: transfers){
            if (filter.check(t.getDate())) list.add(t);
        }
        return list;
    }

    public Common getOldCommon() {
        return oldCommon;
    }

    public void add(Common c) throws ModelException {
        List ref = getRef(c);
        assert ref != null;
        if (ref.contains(c)) throw new ModelException(ModelException.IS_EXISTS);
        ref.add(c);
        c.postAdd(this);
        sort();
        isSaved = false;
    }

    public void edit(Common oldC, Common newC) throws ModelException {
        List ref = getRef(oldC);
        if (ref.contains(newC) && oldC != ref.get(ref.indexOf(newC))) throw new ModelException(ModelException.IS_EXISTS);
        ref.set(ref.indexOf(oldC), newC);
        oldCommon = oldC;
        newC.postEdit(this);
        sort();
        isSaved = false;
    }

    public void remove(Common c) {
        getRef(c).remove(c);
        c.postRemove(this);
        isSaved = false;
    }

    public void updateCurrency() throws Exception {
        HashMap<String, Double> rates = RateCurrency.getRates(getBaseCurrency());
        for (Currency c : currencies) {
            if (rates.get(c.getCode()) != null){
                c.setRate(rates.get(c.getCode()));
            }
        }
        for (Account account: accounts) {
            account.getCurrency().setRate(rates.get(account.getCurrency().getCode()));
        }
        isSaved = false;
    }

    private List getRef(Common c) {
        if (c instanceof Account) return accounts;
        else if (c instanceof Article) return articles;
        else if (c instanceof Transaction) return transactions;
        else if (c instanceof Transfer) return transfers;
        else if (c instanceof Currency) return currencies;
        return null;
    }

    @Override
    public String toString() {
        return "SaveData{" +
                "articles=" + articles +
                ", accounts=" + accounts +
                ", currencies=" + currencies +
                ", transactions=" + transactions +
                ", transfers=" + transfers +
                '}';
    }
}
