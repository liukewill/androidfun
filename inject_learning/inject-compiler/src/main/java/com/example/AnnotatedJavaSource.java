package com.example;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by kenan on 17/7/13.
 * 组装java 代码
 */

public class AnnotatedJavaSource {

    private TypeElement mTypeElement;//类
    private ArrayList<BindViewElement> mBindElement;//成员变量
    private Elements elements;

    public AnnotatedJavaSource(TypeElement mTypeElement, Elements elements) {
        this.mTypeElement = mTypeElement;
        this.elements = elements;
        mBindElement=new ArrayList<>();
    }

    public String getFullClassName(){
        return mTypeElement.getQualifiedName().toString();
    }

    public void addField(BindViewElement element){
        mBindElement.add(element);
    }

    public JavaFile generateFile(){
        //生成inject方法
        MethodSpec.Builder injectMethod=
                MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()),"host",Modifier.FINAL)
                .addParameter(TypeName.OBJECT,"source")
                .addParameter(ClassName.get("com.sina.inject_api","Provider"),"provider");

        //方法里添加语句
        for(BindViewElement field:mBindElement){
            injectMethod.addStatement("host.$N = ($T)(provider.findView(source,$L))",
            field.getFieldName(),
            field.getType(),
            field.getMredId());
        }

        injectMethod.addStatement("System.out.println($N)","\"lk\"");

        //生成类
        TypeSpec injectClass =TypeSpec.classBuilder(mTypeElement.getSimpleName()+"$ViewInject")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("com.sina.inject_api","Inject"),TypeName.get(mTypeElement.asType())))
                .addMethod(injectMethod.build())
                .build();

        String packageName= elements.getPackageOf(mTypeElement).getQualifiedName().toString();

        return JavaFile.builder(packageName,injectClass).build();
    }


}
