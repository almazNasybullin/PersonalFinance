package gui.dialog;

import exception.ModelException;
import gui.MainFrame;
import model.Article;
import model.Common;
import settings.Style;

import javax.swing.*;

public class ArticleAddEditDialog extends AddEditDialog {

    public ArticleAddEditDialog(MainFrame frame) {
        super(frame);
    }

    @Override
    protected void init() {
        components.put("LABEL_TITLE", new JTextField());

        icons.put("LABEL_TITLE", Style.ICON_TITLE);

    }

    @Override
    protected void setValues() {
        Article article = (Article) common;
        values.put("LABEL_TITLE", article.getTitle());
    }

    @Override
    public Common getCommonFromForm() throws ModelException {
        String title = ((JTextField) components.get("LABEL_TITLE")).getText();
        return new Article(title);
    }
}
