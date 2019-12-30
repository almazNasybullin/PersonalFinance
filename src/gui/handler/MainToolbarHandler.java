package gui.handler;

import gui.MainFrame;
import gui.panel.*;
import settings.HandlerCode;

import java.awt.event.ActionEvent;

public class MainToolbarHandler extends MenuViewHandler {

    public MainToolbarHandler(MainFrame frame) {
        super(frame);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case HandlerCode.TOOLBAR_OVERVIEW: {
                showOverviewPanel();
                break;
            }
            case HandlerCode.TOOLBAR_ACCOUNTS: {
                showAccountPanel();
                break;
            }
            case HandlerCode.TOOLBAR_ARTICLES: {
                showArticlePanel();
                break;
            }
            case HandlerCode.TOOLBAR_TRANSACTIONS: {
                showTransactionPanel();
                break;
            }
            case HandlerCode.TOOLBAR_TRANSFERS: {
                showTransferPanel();
                break;
            }
            case HandlerCode.TOOLBAR_CURRENCIES: {
                showCurrencyPanel();
                break;
            }
            case HandlerCode.TOOLBAR_STATISTICS: {
                showStatisticsPanel();
                break;
            }
        }

        super.actionPerformed(actionEvent);
    }



}
