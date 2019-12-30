package gui.dialog;

import exception.ModelException;
import gui.MainFrame;
import model.Account;
import model.Common;
import model.Currency;
import settings.Format;
import settings.Settings;
import settings.Style;
import settings.Text;

import javax.swing.*;

public class CurrencyAddEditDialog extends AddEditDialog {

    public CurrencyAddEditDialog(MainFrame frame) {
        super(frame);
    }

    @Override
    protected void init() {
        components.put("LABEL_TITLE", new JTextField());
        components.put("LABEL_CODE", new JComboBox<>(Settings.CURRENCIES_CODES));
        components.put("LABEL_RATE", new JTextField());
        components.put("LABEL_ON", new JComboBox<>(new String[]{Text.get("YES"), Text.get("NO")}));
        components.put("LABEL_BASE", new JCheckBox());

        icons.put("LABEL_TITLE", Style.ICON_TITLE);
        icons.put("LABEL_CODE", Style.ICON_CODE);
        icons.put("LABEL_RATE", Style.ICON_RATE);
        icons.put("LABEL_ON", Style.ICON_ON);
        icons.put("LABEL_BASE", Style.ICON_BASE);

        values.put("LABEL_RATE", Format.amount(1));

    }

    @Override
    protected void setValues() {
        Currency currency = (Currency) common;
        JCheckBox checkBox = new JCheckBox();
        values.put("LABEL_TITLE", currency.getTitle());
        values.put("LABEL_CODE", currency.getCode());
        values.put("LABEL_RATE", currency.getRate());
        if (currency.isOn()) values.put("LABEL_ON", Text.get("YES"));
        else values.put("LABEL_ON", Text.get("NO"));
        values.put("LABEL_BASE", currency.isBase()); //под вопросом
        if (currency.isBase()) {
            checkBox.setSelected(true);
            checkBox.setEnabled(false);
            components.put("LABEL_BASE", checkBox);
        }
    }

    @Override
    public Common getCommonFromForm() throws ModelException {
        try{
            String title = ((JTextField) components.get("LABEL_TITLE")).getText();
            String code = (String) ((JComboBox) components.get("LABEL_CODE")).getSelectedItem();
            String rate = ((JTextField) components.get("LABEL_TITLE")).getText();
            boolean isOn = false;
            boolean isBase = ((JCheckBox) components.get("LABEL_BASE")).isSelected();
            if (((JComboBox) (components.get("LABEL_ON"))).getSelectedItem().equals(Text.get("YES"))) isOn = true;
            if (!isBase && common != null && ((Currency) common).isBase()) throw new ModelException(ModelException.NO_BASE_CURRENCY);
            return new Currency(title, code, Format.fromAmountToNumber(rate), isOn, isBase);
        } catch (NumberFormatException e) {
            throw new ModelException(ModelException.AMOUNT_FORMAT);
        }
    }
}
