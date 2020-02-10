package app.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FormFieldPattern> formFieldsPattern = new ArrayList<>();

    public ReportType() {}

    public ReportType(String name) {
        this.name = name;
    }

    public ReportType(String name, List<FormFieldPattern> formFieldsPattern) {
        this.name = name;
        this.formFieldsPattern = formFieldsPattern;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FormFieldPattern> getFormFieldsPattern() {
        return formFieldsPattern;
    }

    public void setFormFieldsPattern(List<FormFieldPattern> formFieldsPattern) {
        this.formFieldsPattern = formFieldsPattern;
    }

    public void addFormFieldToPattern(FormFieldPattern formField) {
        formFieldsPattern.add(formField);
    }
}
