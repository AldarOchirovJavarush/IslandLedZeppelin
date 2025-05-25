package com.javarush.island.ochirov.organism;

import com.javarush.island.ochirov.configs.utils.DefaultOrganismConfigFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisteredOrganism {
    DefaultOrganismConfigFactory.OrganismType value();
}
