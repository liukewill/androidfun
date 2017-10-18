package com.example;

import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by kenan on 17/7/12.
 * ButterKnife
 *
 * 1.inject java module  注解 @BindView 申明注解
 * 2.inject-compiler  java modlue 注解processor 注解处理器，生成代码
 * 3.inject-api android module 注解api目录，ViewInject
 */
@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {
    private Filer mFiler;//文件相关
    private Elements mElementUtils;//元素相关辅助类
    private Messager mMessage;//日志
    private Map<String,AnnotatedJavaSource> sourceMap;

    /**
     * * init()方法会被注解处理工具调用，并输入ProcessingEnviroment参数。
     * ProcessingEnviroment提供很多有用的工具类Elements, Types 和 Filer
     * @param processingEnv 提供给 processor 用来访问工具框架的环境
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessage = processingEnv.getMessager();
        sourceMap=new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        sourceMap.clear();
        try {
            processBindView(roundEnv);


            for (AnnotatedJavaSource annotatedJavaSource : sourceMap.values()) {
                annotatedJavaSource.generateFile().writeTo(mFiler);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations=new HashSet<>();
        annotations.add(BindView.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    private void processBindView(RoundEnvironment roundEnvironment){
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            AnnotatedJavaSource annotatedJavaSource=getAnnotationSource(element);
            BindViewElement bindElement=new BindViewElement(element);//bindview对象
            annotatedJavaSource.addField(bindElement);
        }
    }

    private AnnotatedJavaSource getAnnotationSource(Element element){
        TypeElement typeElement=(TypeElement) element.getEnclosingElement();
        String fullName=typeElement.getQualifiedName().toString();
        AnnotatedJavaSource annotatedJavaSource=sourceMap.get(fullName);
        if(annotatedJavaSource==null){
            annotatedJavaSource=new AnnotatedJavaSource(typeElement,mElementUtils);
            sourceMap.put(fullName,annotatedJavaSource);
        }
        return annotatedJavaSource;
    }
}
