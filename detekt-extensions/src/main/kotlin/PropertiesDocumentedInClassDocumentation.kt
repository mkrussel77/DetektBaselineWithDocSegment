package com.foo

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.kdoc.psi.impl.KDocSection
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.getChildrenOfType

class PropertiesDocumentedInClassDocumentation(config: Config = Config.empty) : Rule(config) {
    override val issue: Issue
        get() = Issue(
            this.javaClass.simpleName,
            Severity.Style,
            "Documenting properties as tag in class documentation is not allowed due to how they are rendered in Xcode when using frameworks",
            Debt.FIVE_MINS
        )

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)
        klass.docComment?.getChildrenOfType<KDocSection>()
            ?.filter { it.name == "property" }
            ?.forEach { docSection ->
                reportPropertyDoc(klass, docSection)
            }
    }

    private fun reportPropertyDoc(classDeclaration: KtClass, docSection: KDocSection) {
        val propertyName = "${classDeclaration.fqName.toString()}.${docSection.getSubjectName()}"

        report(
            CodeSmell(
                issue, Entity.from(docSection),
                "Documentation for $propertyName should not be in class documentation"
            )
        )
    }
}
