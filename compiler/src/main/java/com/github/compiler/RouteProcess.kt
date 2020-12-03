package com.github.compiler

import com.github.annotation.Route
import com.google.auto.common.SuperficialValidation
import com.google.auto.service.AutoService
import com.squareup.javapoet.*
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic.Kind.WARNING

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.github.annotation.Route")
@AutoService(Processor::class)
class RouteProcess : AbstractProcessor() {
    private val types: Types by lazy {
        processingEnv.typeUtils
    }
    private val elementUtils: Elements by lazy {
        processingEnv.elementUtils
    }

    companion object {
        private val ACTIVITY = "android.app.Activity"
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment
    ): Boolean {
        val routes = mutableListOf<TypeElement>()
        val annotatedElements = roundEnv.getElementsAnnotatedWith(Route::class.java)

        if (annotatedElements.isEmpty()) return false

        for (element in annotatedElements) {
            if (!invalid(element as TypeElement)) continue
            routes.add(element)
        }
        if (routes.isEmpty()) {
            processingEnv.messager.printMessage(WARNING, "Route is never used!")
            return false
        }
        processingEnv.messager.printMessage(WARNING, "Routes size ${routes.size}")

        val packageName = processingEnv.options["AROUTER_MODULE_NAME"]
//        processingEnv.messager.printMessage(WARNING, processingEnv.options["AROUTER_MODULE_NAME"])

        val cla = ClassName.get("java.util", "HashMap")

        val target = FieldSpec.builder(cla, "mRoutes")
            .initializer(
                CodeBlock.of(
                    "new HashMap<\$L, \$L>()",
                    String::class.java.simpleName,
                    String::class.java.simpleName
                )
            )
            .addModifiers(Modifier.PRIVATE, Modifier.FINAL).build()

        val constructorBuilder = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)

        routes.forEach {
            val value = it.getAnnotation(Route::class.java).value
            constructorBuilder.addStatement(
                "this.\$L.put(\"\$L\", \"\$L\")", target.name,
                value, it.qualifiedName
            )
        }

        val methodSpec = MethodSpec.methodBuilder("get")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .returns(Object::class.java).addParameter(String::class.java, "value", Modifier.FINAL)
            .addStatement("return ${target.name}.get(\$L)", "value")
            .build()
        var clazz = TypeSpec.classBuilder("RouteBinding")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addField(target)
            .addMethod(constructorBuilder.build())
            .addMethod(methodSpec)
            .build()
        var javaFile = JavaFile.builder(
            packageName,
            clazz
        ).build()

        try {
            javaFile.writeTo(processingEnv.filer)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return true
    }

    private fun invalid(element: TypeElement): Boolean {
        if (!SuperficialValidation.validateElement(element)) return false
        if (element.qualifiedName.toString().startsWith("android.")) return false

        val modifiers = element.modifiers
        if (modifiers.contains(Modifier.ABSTRACT)
            || modifiers.contains(Modifier.PRIVATE)
            || modifiers.contains(Modifier.STATIC)
        ) return false

        if (!types.isSubtype(element.asType(), elementUtils.getTypeElement(ACTIVITY).asType())) {
            return false
        }
        return true
    }
}