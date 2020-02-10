package app.entity;

import app.form.FormFieldType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FormFieldPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String description;
    private FormFieldType formFieldType;

    public FormFieldPattern() {}

    public FormFieldPattern(String description, FormFieldType formFieldType) {
        this.description = description;
        this.formFieldType = formFieldType;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FormFieldType getFormFieldType() {
        return formFieldType;
    }

    public void setFormFieldType(FormFieldType formFieldType) {
        this.formFieldType = formFieldType;
    }
}
