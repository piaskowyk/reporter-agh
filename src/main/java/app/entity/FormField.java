package app.entity;

import app.form.FormFieldType;

import javax.persistence.*;

@Entity
public class FormField {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private FormFieldType formFieldType;
    private String content;

    @ManyToOne()
    @JoinColumn(name = "report")
    private Report report;

    public FormField() {}

    public FormField(FormFieldType formFieldType, String content) {
        this.formFieldType = formFieldType;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public FormFieldType getFormFieldType() {
        return formFieldType;
    }

    public void setFormFieldType(FormFieldType formFieldType) {
        this.formFieldType = formFieldType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
