package processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

import project.test.annotation.Builder;
import processor.args.BuilderArgs;
import processor.args.ConstructorArgs;


/**
 * Created by Ali Esa Assadi on 01/05/2018.
 */
@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    private final boolean EXIT_PROCESSING = true;

    private Filer filer;
    private Messager messager;
    private String mPackageName = "processor";


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        /** Annotated Element **/
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Builder.class)) {

            String className = annotatedElement.getSimpleName() + "_Builder";

            //only for CLASS
            if (!ValidationUtils.isKindOfClass(annotatedElement)) return EXIT_PROCESSING;// Exit processing

            BuilderArgs builderArgs = generateSetMethods(annotatedElement, className);
            ArrayList<FieldSpec> fieldList = builderArgs.getFieldList();
            ArrayList<MethodSpec> methodList = builderArgs.getMethodList();


            ConstructorArgs constructorArgs = getConstructorArgs(annotatedElement,fieldList);
            StringBuilder fieldsName = constructorArgs.getFieldsName();
            boolean isConstructorHaveAllParams = constructorArgs.isConstructorHaveAllParams();

            if (!isConstructorHaveAllParams) {
                error(annotatedElement, "you must have at least a one constructor with all the argument !!");
                return EXIT_PROCESSING;
            }

            //build method
            MethodSpec method = MethodSpec.methodBuilder("build")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ClassName.get(annotatedElement.asType()))
                    .addStatement("return new " + annotatedElement.getSimpleName() + "(" + fieldsName + ")")
                    .build();

            //Constructor
            MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build();

            TypeSpec classBuilder = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addFields(fieldList)
                    .addMethod(constructor)
                    .addMethods(methodList)
                    .addMethod(method)
                    .build();

            JavaFile javaFile = JavaFile.builder(mPackageName, classBuilder).build();

            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                error(annotatedElement, e.getMessage(), e);
                return true;
            }


        }

        return true;
    }

    private ConstructorArgs getConstructorArgs(Element annotatedElement, ArrayList<FieldSpec> fieldList) {

        StringBuilder fieldsName = new StringBuilder();
        boolean isConstructorHaveAllParams = false;

        for (Element enclosedElement : annotatedElement.getEnclosedElements()) {

            //CONSTRUCTOR
            if (enclosedElement.getKind().equals(ElementKind.CONSTRUCTOR)) {
                //Represents a method, constructor, or initializer (static or instance) of a class or interface,
                // including annotation type elements.
                ExecutableElement executableElement = (ExecutableElement) enclosedElement; // constructor

                List<? extends VariableElement> parameters = executableElement.getParameters();

                if (parameters.size() == fieldList.size()) {
                    isConstructorHaveAllParams = true;


                    for (VariableElement param : parameters) {

                        if (fieldsName.length() > 0) {
                            fieldsName.append(",");
                        }

                        fieldsName.append(param.getSimpleName());
                    }


                }


            }
        }

        return new ConstructorArgs(fieldsName, isConstructorHaveAllParams);
    }


    private BuilderArgs generateSetMethods(Element annotatedElement, String className) {

        ArrayList<MethodSpec> methodList = new ArrayList<>();
        ArrayList<FieldSpec> fieldList = new ArrayList<>();


        for (Element enclosedElement : annotatedElement.getEnclosedElements()) {

            //FIELD
            if (enclosedElement.getKind().equals(ElementKind.FIELD)) {

                String fieldName = enclosedElement.getSimpleName().toString(); //name
                TypeName fieldType = TypeName.get(enclosedElement.asType()); // String

                //field List
                FieldSpec field = FieldSpec.builder(fieldType, fieldName, Modifier.PRIVATE).build();
                fieldList.add(field);

                //set methods
                MethodSpec method = MethodSpec.methodBuilder("set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1))
                        .addModifiers(Modifier.PUBLIC)
                        .returns(ClassName.get(mPackageName, className))
                        .addParameter(fieldType, fieldName)
                        .addStatement("this." + fieldName + " = " + fieldName)
                        .addStatement("return this")
                        .build();

                methodList.add(method);
            }
        }

        return new BuilderArgs(fieldList, methodList);
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Builder.class.getCanonicalName());
    }
}
