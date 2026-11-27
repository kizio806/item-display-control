package com.kizio.itemdisplaycontrol.common.architecture;

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
}
