package gui.handler;

import gui.MainFileChooser;
import gui.MainFrame;
import gui.dialog.ConfirmDialog;
import gui.dialog.ErrorDialog;
import saveload.SaveData;
import settings.HandlerCode;
import settings.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MenuFileHandler extends Handler {

    private final MainFileChooser fileChooser;

    public MenuFileHandler(MainFrame frame) {
        super(frame);
        fileChooser = new MainFileChooser(frame);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case HandlerCode.MENU_FILE_NEW: {
                Settings.setFileSave(null);
                SaveData.getInstance().clear();
                break;
            }
            case HandlerCode.MENU_FILE_OPEN: {
                int result = fileChooser.open();
                if (result == JFileChooser.APPROVE_OPTION) {
                    Settings.setFileSave(fileChooser.getSelectedFile());
                    SaveData.getInstance().clear();
                    SaveData.getInstance().load();
                }
                break;
            }
            case HandlerCode.MENU_FILE_SAVE: {
                if (Settings.getFileSave() == null) {
                    int result = fileChooser.save();
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String path = fileChooser.getSelectedFile().getAbsolutePath();
                        String ext = path.substring(path.lastIndexOf(".") + 1);
                        if (ext.equals(Settings.SAVE_FILE_EXP)) Settings.setFileSave(new File(path));
                        else Settings.setFileSave(new File(path + "." + Settings.SAVE_FILE_EXP));
                    }
                }
                if (Settings.getFileSave() != null) SaveData.getInstance().save();
                break;
            }
            case HandlerCode.MENU_FILE_UPDATE_CURRENCIES: {
                try {
                    SaveData.getInstance().updateCurrency();
                } catch (Exception e) {
                    ErrorDialog.show(frame, "ERROR_UPDATE_CURRENCIES");
                }
                break;
            }
            case HandlerCode.MENU_FILE_EXIT: {
                if (SaveData.getInstance().isSaved()) System.exit(0);
                else {
                    int result = ConfirmDialog.show(frame, "CONFIRM_EXIT_TEXT", "CONFIRM_EXIT_TITLE");
                    if (result == JOptionPane.YES_OPTION) System.exit(0);
                }
                break;
            }
        }

        super.actionPerformed(actionEvent);
    }
}
