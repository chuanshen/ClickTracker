package com.quejue.tracker.compiler;

import com.google.auto.service.AutoService;
import com.quejue.tracker.annotations.DataId;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static javax.tools.Diagnostic.Kind.WARNING;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.quejue.tracker.annotations.DataId")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DataProcessor extends AbstractProcessor {
    private static final String PACKAGE_NAME = "com.quejue.clicktracker";
    private Messager messager;
    private Filer filer;
    private Elements elementsUtils;
    private HashMap<Integer, String> mEventMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        elementsUtils = processingEnv.getElementUtils();
    }
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (null != set && set.size() > 0) {
            try {
                parseDataIds(roundEnvironment.getElementsAnnotatedWith(DataId.class));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    private void parseDataIds(Set<? extends Element> elements) {
        ParameterizedTypeName interceptorMap = ParameterizedTypeName.get(Map.class, Integer.class, String.class);
        //build map params name
        ParameterSpec routeMapParam = ParameterSpec.builder(interceptorMap, "events").build();
        MethodSpec.Builder method = MethodSpec.methodBuilder("bindEvent")
                .addParameter(routeMapParam)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);

        for (Element e : elements) {
            DataId m = e.getAnnotation(DataId.class);
            int values[] = m.value();
            for (int value : values) {
                mEventMap.put(value, e.getSimpleName().toString());
            }
        }
        Iterator<Map.Entry<Integer, String>> iter = mEventMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, String> entry = iter.next();
            method.addStatement("events.put($L, $S)", entry.getKey(), entry.getValue());
        }

        TypeElement iBindEvent = elementsUtils.getTypeElement("com.quejue.tracker.internal.IBindEvent");
        ClassName routerClassName = null;
        if (iBindEvent != null) {
            routerClassName = ClassName.get(iBindEvent);
        }

        if (routerClassName != null) {
            TypeSpec type = TypeSpec.classBuilder("ClickTrackerEvents")
                    .addSuperinterface(routerClassName)
                    .addMethod(method.build())
                    .addJavadoc("DO NOT EDIT THIS FILE")
                    .build();

            try {
                JavaFile.builder(PACKAGE_NAME, type)
                        .build()
                        .writeTo(filer);

                printLog("write ClickTrackerEvents.java class file success");
            } catch (Exception e) {
                printLog(" write ClickTrackerEvents.java class file failed");
            }
        } else {
            printLog("iBindEvent == null");
        }
    }
    private void printLog(String msg) {
        if (msg != null) {
            messager.printMessage(WARNING, msg);
        }
    }
}
