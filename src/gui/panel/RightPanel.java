package gui.panel;

import gui.EnableEditDelete;
import gui.MainFrame;
import gui.Refresh;
import gui.table.TableData;
import gui.toolbar.AbstractToolbar;
import settings.Style;
import settings.Text;

import javax.swing.*;

abstract public class RightPanel extends AbstractPanel {

    protected TableData td;

    private String title;
    private ImageIcon icon;
    private JPanel[] panels;

    public RightPanel(MainFrame frame, TableData td, String title, ImageIcon icon, JPanel[] panels) {
        super(frame);
        this.td = td;
        this.title = title;
        this.icon = icon;
        this.panels = panels;
        init();

    }

    public RightPanel(MainFrame frame, TableData td, String title, ImageIcon icon, AbstractToolbar tb) {
        this(frame, td, title, icon, new JPanel[]{tb});
    }

    public RightPanel(MainFrame frame, TableData td, String title, ImageIcon icon) {
        this(frame, td, title, icon, new JPanel[]{});
    }

    protected void setPanels(JPanel[] panels) {
        this.panels = panels;
    }

    @Override
    public void refresh() {
        super.refresh();
        if (td != null) td.refresh();
        for (JPanel panel : panels) {
            if (panel instanceof Refresh) ((Refresh) panel).refresh();
        }
    }

    private void enableEditDelete() {
        for (JPanel panel : panels) {
            if (panel instanceof EnableEditDelete) ((EnableEditDelete) panel).setEnableEditDelete(false);
        }
        frame.getMenu().setEnableEditDelete(false);

        if (td != null) {
            if (td.getSelectedRow() != -1) {
                for (JPanel panel : panels) {
                    if (panel instanceof EnableEditDelete) ((EnableEditDelete) panel).setEnableEditDelete(true);
                }
                frame.getMenu().setEnableEditDelete(true);
            }
        }
    }

    @Override
    protected final void init() {
        enableEditDelete();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel header = new JLabel(Text.get(title));
        header.setFont(Style.FONT_LABEL_HEADER);
        header.setIcon(icon);
        header.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        add(header);

        if (panels.length == 0) add(Box.createVerticalStrut(Style.PADDING_PANEL_EMPTY));

        for (JPanel panel : panels) {
            add(panel);
            add(Box.createVerticalStrut(Style.PADDING_PANEL));
        }

        if (td != null) {
            JScrollPane scrollPane = new JScrollPane(td);
            add(scrollPane);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            ListSelectionModel selectionModel = td.getSelectionModel();
            selectionModel.addListSelectionListener(listSelectionEvent -> enableEditDelete());
        }
    }

    public TableData getTableData() {
        return td;
    }
}
