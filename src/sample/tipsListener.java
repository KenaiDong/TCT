package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class tipsListener<T> implements ChangeListener<T> {

    public Label tips;

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {

    }


}
