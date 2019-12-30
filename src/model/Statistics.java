package model;

import saveload.SaveData;

import java.util.HashMap;
import java.util.List;

public class Statistics extends Common {

    /**
     * @method getBalanceCurrency getting balance in this currency
     * @param currency is current currency
     * @return balance in current currency
     */
    public static double getBalanceCurrency(Currency currency){
        SaveData sd = SaveData.getInstance();
        double amount = 0;
        for (Account account: sd.getAccounts()){
            if (currency.equals(account.getCurrency())) amount += account.getAmount();
        }

        return  amount;
    }

    /**
     * @method getBalance getting all balance from all currencies
     * @param currency is current currency
     * @return all balance
     */
    public static double getBalance(Currency currency) {
        SaveData sd = SaveData.getInstance();
        double amount = 0;
        for (Account account: sd.getAccounts()){
            amount += account.getAmount() * account.getCurrency().getRateByCurrency(currency);
        }
        return amount;
    }

    //return profit
    public static HashMap<String, Double> getDataForChartOnIncomeArticles(){
        return getDataForChartOnArticles(true);
    }

    //return consumption
    public static HashMap<String, Double> getDataForChartOnExpArticles(){
        return getDataForChartOnArticles(false);
    }

    /**
     * @method getDataForChartOnArticles
     * @param income detecting true means profit or false - consumption
     * @return data
     */
    private static HashMap<String, Double> getDataForChartOnArticles(boolean income) {
        List<Transaction> transactions = SaveData.getInstance().getFilterTransactions();
        HashMap<String, Double> data = new HashMap<>();
        for (Transaction t: transactions) {
            if ((income && t.getAmount() > 0) || (!income && t.getAmount() < 0)) {
                String key = t.getArticle().getTitle();
                double sum = 0;
                double amount = t.getAmount();
                if (!income) amount *= -1;
                if (data.containsKey(key)) sum = data.get(key);
                sum += amount * t.getAccount().getCurrency().getRateByCurrency(SaveData.getInstance().getBaseCurrency());
                data.put(key, round(sum));
            }
        }
        return data;
    }

    /**
     * @method round
     * @return round for 2 symbols
     */
    private static double round(double s){
        return  (double) Math.round(s * 100) / 100;
    }

}
