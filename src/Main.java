import exception.ModelException;
import gui.MainFrame;
import model.*;
import saveload.SaveData;
import settings.Settings;
import settings.Text;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Almaz Nasybullin
 */

public class Main {
    public static void main(String[] args) throws Exception {
        init();
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
//        SaveData sd = SaveData.getInstance();
//        System.out.println(sd);
//        testModel();
    }

    private static void testModel() throws ModelException {
        Currency c1 = new Currency("Рубль", "RUB", 1, true, true);
        Currency c2 = new Currency("Доллар", "USD", 65, true, false);
        Currency c3 = new Currency("Евро", "EUR", 75, true, false);
        Currency c4 = new Currency("Франки", "GBR", 82, false, false);

        Account account1 = new Account("Кошелек", c1, 1000);
        Account account2 = new Account("Кошелек Visa", c2, 10);
        Account account3 = new Account("Кошелек VisaEUR", c3, 10);

        Article article1 = new Article("Продукты");
        Article article2 = new Article("ЖКХ");
        Article article3 = new Article("Зарплата");
        Article article4 = new Article("Рестораны");
        Article article5 = new Article("Проценты по депозиту");

        ArrayList<Currency> currencies = new ArrayList<>();
        currencies.add(c1);
        currencies.add(c2);
        currencies.add(c3);
        currencies.add(c4);

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        ArrayList<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);
        articles.add(article4);
        articles.add(article5);

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(account2, article3, 30000));
        transactions.add(new Transaction(account2, article1, -2000, "На праздник"));
        transactions.add(new Transaction(account3, article5, 600));
        transactions.add(new Transaction(account1, article2, -2500, "Квартира", new Date()));
        transactions.add(new Transaction(account3, article3, 25000, new Date(new Date().getTime() - (long) 86400000 * 30)));

        for (int i = 0; i < 50; i++) {
            Article tempArticle;
            Account tempAccount;
            if (Math.random() < 0.5) tempArticle = article1;
            else tempArticle = article4;
            if (Math.random() < 0.2) tempAccount = account1;
            else tempAccount = account2;
            double tempAmount = Math.round(Math.random() * (-1000));
            Date tempDate = new Date((long) (new Date().getTime() - (long) 86400000 * 30 * Math.random()));
            transactions.add(new Transaction(tempAccount, tempArticle, tempAmount, tempDate));
        }

        ArrayList<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer(account2, account1, 25000, 25000));
        transfers.add(new Transfer(account2, account3, 2500, 2500));

        for (Account account: accounts){
            account.setAmountFromTransactionsAndTransfers(transactions, transfers);
        }

        SaveData sd = SaveData.getInstance();
        sd.setArticles(articles);
        sd.setAccounts(accounts);
        sd.setCurrencies(currencies);
        sd.setTransactions(transactions);
        sd.setTransfers(transfers);
        sd.save();
//        sd.load();
        System.out.println(sd);
    }

    /**
     * Init
     */
    private static void init(){
        try {
            Settings.init();
            Text.init();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Settings.FONT_ROBOTO_LIGHT));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
