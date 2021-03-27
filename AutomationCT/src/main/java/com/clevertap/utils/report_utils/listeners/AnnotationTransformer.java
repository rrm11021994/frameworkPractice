package com.clevertap.utils.report_utils.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class aClass, Constructor constructor, Method method)
    {
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();

        if (retry == null)	{
            annotation.setRetryAnalyzer(Retry.class);
        }
    }
}
