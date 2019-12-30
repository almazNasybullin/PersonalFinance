package model;

import saveload.SaveData;

abstract public class Common {

    public Common() {}

    // ComboBox - выпадаающий список
    public String getValueForComboBox(){
        return null;
    }

    public void postAdd(SaveData sd){}
    public void postEdit(SaveData sd){}
    public void postRemove(SaveData sd){}
}
