package gui.handler;

import gui.MainFrame;
import gui.dialog.AddEditDialog;
import gui.dialog.ConfirmDialog;
import gui.table.TableData;
import gui.table.model.MainTableModel;
import model.Common;
import saveload.SaveData;
import settings.HandlerCode;

import javax.swing.*;
import java.awt.event.*;
import java.util.IllegalFormatCodePointException;

public class FunctionsHandler extends Handler implements MouseListener, KeyListener {

    private final AddEditDialog dialog;

    public FunctionsHandler(MainFrame frame, AddEditDialog dialog) {
        super(frame);
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case HandlerCode.ADD :
                add();
                break;
            case HandlerCode.EDIT :
                edit();
                break;
            case HandlerCode.DELETE :
                delete();
                break;
        }
        super.actionPerformed(actionEvent);
    }

    public void delete() {
        Common c = getSelectedCommon();
        if (c != null) {
            int result = ConfirmDialog.show(frame, "CONFIRM_DELETE_TEXT", "CONFIRM_DELETE_TITLE");
            if (result == JOptionPane.YES_OPTION) {
                SaveData.getInstance().remove(c);
            }
        }
    }

    public void edit() {
        showAddEditDialog(getSelectedCommon());
    }

    public void add() {
        showAddEditDialog(null);
    }

    private Common getSelectedCommon() {
        TableData tableData = frame.getRightPanel().getTableData();
        Common c = ((MainTableModel) tableData.getModel()).getCommonByRow(tableData.getSelectedRow());
        return c;
    }

    private void showAddEditDialog(Common c) {
        dialog.setCommon(c);
        dialog.showDialog();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) delete();
        frame.refresh();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof TableData) {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseEvent.BUTTON1)
                showAddEditDialog(getSelectedCommon());
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof TableData) {
            if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                TableData tableData = frame.getRightPanel().getTableData();
                int row = tableData.rowAtPoint(mouseEvent.getPoint());
                tableData.setRowSelectionInterval(row, row);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
