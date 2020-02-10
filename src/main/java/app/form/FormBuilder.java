package app.form;

import app.entity.FormFieldPattern;
import app.entity.ReportType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class FormBuilder {
    private ReportType reportType;
    private List<ObjectProperty> formValue = new ArrayList<>();

    public FormBuilder(ReportType reportType) {
        this.reportType = reportType;
    }

    public Pane getForm() {
        Pane formPane = new Pane();
        VBox formHolder = new VBox();

        for(FormFieldPattern item : reportType.getFormFieldsPattern()) {
            Pane formItemPane = new Pane();
            formItemPane.setTranslateX(20);

            Label label = new Label();

            label.setText(item.getDescription());

            switch (item.getFormFieldType()){
                case StandardInput: {
                    TextField textField = new TextField();
                    textField.setTranslateY(20);
                    formItemPane.setPrefHeight(50);
                    ObjectProperty property = new SimpleObjectProperty<>();
                    formValue.add(property);
                    textField.textProperty().bindBidirectional(property);

                    formItemPane.getChildren().add(textField);
                }break;

                case TextArea: {
                    TextArea textArea = new TextArea();
                    textArea.setTranslateY(20);
                    formItemPane.setPrefHeight(210);
                    ObjectProperty property = new SimpleObjectProperty<>();
                    formValue.add(property);
                    textArea.textProperty().bindBidirectional(property);
                    formItemPane.getChildren().add(textArea);
                }break;
            }
            formItemPane.getChildren().add(label);
            formHolder.getChildren().add(formItemPane);
        }
        formPane.getChildren().add(formHolder);
        return formPane;
    }

    public List<String> getFormData() {
        List<String> valueList = new ArrayList<>();
        for(ObjectProperty item : formValue) {
            valueList.add(item.getValue().toString());
        }

        return valueList;
    }
}
