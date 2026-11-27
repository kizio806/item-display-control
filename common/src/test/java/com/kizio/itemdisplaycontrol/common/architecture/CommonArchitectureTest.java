package com.kizio.itemdisplaycontrol.common.architecture;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class CommonArchitectureTest {

    private static final JavaClasses COMMON_CLASSES =
            new ClassFileImporter().importPackages("com.kizio.itemdisplaycontrol.common");

    @Test
    void commonMustNotDependOnLoadersOrMinecraft() {
        ArchRule rule = noClasses()
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("net.fabricmc..", "net.neoforged..", "net.minecraft..")
                .because("Common module must remain strictly loader-agnostic and Minecraft-free");

        rule.check(COMMON_CLASSES);
    }

    @Test
    void coreDomainPackagesMustNotCallStaticMethodsOnGlobalFacade() {
        ArchRule rule = noClasses()
                .that()
                .resideInAnyPackage("..engine..", "..config..", "..interaction..", "..math..")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .callMethodWhere(DescribedPredicate.describe(
                        "calls static method on ItemDisplayControl",
                        call -> call.getTargetOwner().isEquivalentTo(ItemDisplayControl.class)))
                .because("Core domain components must receive dependencies directly rather than calling static facade");

        rule.check(COMMON_CLASSES);
    }
}
