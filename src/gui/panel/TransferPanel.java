package gui.panel;

import gui.MainFrame;
import gui.dialog.TransferAddEditDialog;
import gui.handler.FunctionsHandler;
import gui.table.TransferTableData;
import gui.toolbar.FunctionsToolbar;
import settings.Style;

import javax.swing.*;


public class TransferPanel extends RightPanel {

    public TransferPanel(MainFrame frame) {
        super(frame, new TransferTableData(new FunctionsHandler(frame, new TransferAddEditDialog(frame))), "TRANSFERS", Style.ICON_PANEL_TRANSFERS, new JPanel[] {new FunctionsToolbar(new FunctionsHandler(frame, new TransferAddEditDialog(frame))), new FilterPanel(frame)});
    }
}
