package gui.handler;

import gui.MainFrame;
import settings.HandlerCode;
import java.awt.event.ActionEvent;

public class MenuEditHandler extends Handler {

    public MenuEditHandler(MainFrame frame) {
        super(frame);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        FunctionsHandler functionsHandler = frame.getRightPanel().getTableData().getFunctionsHandler();
        switch (actionEvent.getActionCommand()) {
            case HandlerCode.MENU_EDIT_ADD: {
                functionsHandler.add();
                break;
            }
            case HandlerCode.MENU_EDIT_EDIT: {
                functionsHandler.edit();
                break;
            }
            case HandlerCode.MENU_EDIT_DELETE: {
                functionsHandler.delete();
            }
        }

        super.actionPerformed(actionEvent);
    }
}
