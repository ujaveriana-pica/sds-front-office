package co.edu.javeriana.pica.front.controller.dto;

import co.edu.javeriana.pica.front.entity.Form;
import lombok.Data;

import java.util.Map;

@Data
public class FormRequest {
    private Map<String, String> data;
    private String type;
    private String owner;

    public Form toForm() {
        Form formEntity = new Form();
        formEntity.setData(data);
        formEntity.setType(type);
        formEntity.setOwner(owner);
        return formEntity;
    }
}
