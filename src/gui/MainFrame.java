package gui;

import gui.dialog.*;
import gui.handler.MainToolbarHandler;
import gui.handler.MainWindowHandler;
import gui.menu.MainMenu;
import gui.panel.*;
import gui.toolbar.FunctionsToolbar;
import gui.toolbar.MainToolbar;
import saveload.SaveData;
import settings.Style;
import settings.Text;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements Refresh {

    private final GridBagConstraints constraints;
    private final MainMenu mb;
    private final LeftPanel leftPanel;
    private RightPanel rightPanel;
    private final MainToolbar toolbar;
//    private final FunctionsToolbar functionsToolbar;

    public MainFrame() {
        super(Text.get("PROGRAM_NAME"));

//        CurrencyAddEditDialog temp = new CurrencyAddEditDialog(this);
//        temp.setCommon(SaveData.getInstance().getBaseCurrency());
//        temp.showDialog();

//        setResizable(false);
        setIconImage(Style.ICON_MAIN.getImage());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        mb = new MainMenu(this);
        setJMenuBar(mb);


        setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;

        //add toolbar
        toolbar = new MainToolbar(new MainToolbarHandler(this));
        add(toolbar, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTH;


        //add left panel
        leftPanel = new LeftPanel(this);
        add(leftPanel, constraints);

        setRightPanel(new OverviewPanel(this));

        pack();
        setLocationRelativeTo(null);

        addWindowListener(new MainWindowHandler());
    }

    public void setRightPanel(RightPanel panel) {
        if (rightPanel != null) remove(rightPanel);
        constraints.gridy = 1;
        constraints.gridx = 1;
        rightPanel = panel;
        panel.setBorder(Style.BORDER_PANEL);
        add(rightPanel, constraints);
        pack();
    }

    @Override
    public void refresh() {
        SwingUtilities.updateComponentTreeUI(this);
        mb.refresh();
        leftPanel.refresh();
        rightPanel.refresh();
        pack();
    }

    public MainMenu getMenu() {
        return mb;
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }
}
