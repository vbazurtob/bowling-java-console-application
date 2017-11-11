package org.vbazurtob.jobsity.bowlingapp;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author voltaire
 * Interface solely created to illustrate the possibility of creating custom qualifiers to be 
 * use for resolve autowiring conlflicts in Spring
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface CompleteListOfScores {

}
