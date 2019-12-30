package gui.panel;

import gui.MainFrame;
import gui.dialog.ArticleAddEditDialog;
import gui.handler.FunctionsHandler;
import gui.table.ArticleTableData;
import gui.toolbar.FunctionsToolbar;
import settings.Style;


public class ArticlePanel extends RightPanel {

    public ArticlePanel(MainFrame frame) {
        super(frame, new ArticleTableData(new FunctionsHandler(frame, new ArticleAddEditDialog(frame))), "ARTICLES", Style.ICON_PANEL_ARTICLE, new FunctionsToolbar(new FunctionsHandler(frame, new ArticleAddEditDialog(frame))));
    }
}
