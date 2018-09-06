package com.epages.restdocs.openapi.sample;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.constraints.ValidatorConstraintResolver;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.util.StringUtils;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ConstrainedFields {

    private final ValidatorConstraintResolver validatorConstraintResolver = new ValidatorConstraintResolver();

    private final ConstraintDescriptions constraintDescriptions;

    private final Class<?> classHoldingConstraints;


    public ConstrainedFields(Class<?> input) {
        this.classHoldingConstraints = input;
        this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    /**
     * Create a field description with constraint descriptions for bean property with the same name
     * @param path json path of the field
     */
    public FieldDescriptor withPath(String path) {
        return applyAttributes(fieldWithPath(path));
    }

    public FieldDescriptor applyAttributes(FieldDescriptor descriptor) {
        return descriptor.attributes(constraintAttributes(descriptor.getPath()));
    }

    /**
     *
     * Create a field description with constraint descriptions for bean property with the same name
     * @param jsonPath json path of the field
     * @param beanPropertyName name of the property of the bean that is used to get the field constraint descriptions
     */
    public FieldDescriptor withMappedPath(String jsonPath, String beanPropertyName) {
        return fieldWithPath(jsonPath).attributes(constraintAttributes(beanPropertyName));
    }

    private Attributes.Attribute[] constraintAttributes(String beanPropertyName) {
        return new Attributes.Attribute[]{
                key("constraints").value(StringUtils
                        .collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(beanPropertyName), ". ")),
                key("validationConstraints")
                        .value(this.validatorConstraintResolver.resolveForProperty(beanPropertyName, classHoldingConstraints))
        };
    }
}
