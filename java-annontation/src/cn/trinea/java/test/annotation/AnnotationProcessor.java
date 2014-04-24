package cn.trinea.java.test.annotation;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({ "cn.trinea.java.test.annotation.MethodInfo" })
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment env) {
        
        HashMap<String, String> map = new HashMap<String, String>();
        for (TypeElement te : elements) {
            for (Element element : env.getElementsAnnotatedWith(te)) {
                MethodInfo methodInfo = element.getAnnotation(MethodInfo.class);
                element.getEnclosingElement().toString();
                System.out.println(methodInfo.author());
            }
        }
        return false;
    }

}
