package com.example;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by kenan on 17/7/13.
 */

public class BindViewElement {
    private VariableElement variableElement;
    private int mredId;

    public BindViewElement(Element element)throws IllegalArgumentException{
        if (element.getKind()!= ElementKind.FIELD) {
            throw new IllegalArgumentException("only fields can be annotated with"+BindView.class.getSimpleName());
        }
        variableElement=(VariableElement)element;

        BindView bindView=variableElement.getAnnotation(BindView.class);
        mredId=bindView.value();
        if(mredId<0){
            throw  new IllegalArgumentException("view id must valid"+BindView.class.getSimpleName());
        }
    }

    public Name getFieldName(){
        return variableElement.getSimpleName();
    }

    public int getMredId(){
        return mredId;
    }

    public TypeMirror getType(){
        return  variableElement.asType();
    }
}
