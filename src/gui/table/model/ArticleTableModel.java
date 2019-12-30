package gui.table.model;

import model.Article;
import saveload.SaveData;


public class ArticleTableModel extends MainTableModel {

    private static final int TITLE = 0;

    public ArticleTableModel(String[] columns) {
        super(SaveData.getInstance().getArticles(), columns);
    }


    @Override
    protected void updateData() {
        data = SaveData.getInstance().getArticles();
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (data.isEmpty()) return null;
        Article article = (Article) data.get(row);
        return article.getTitle();
    }
}
