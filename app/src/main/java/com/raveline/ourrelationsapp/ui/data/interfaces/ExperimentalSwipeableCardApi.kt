package com.raveline.ourrelationsapp.ui.data.interfaces

/**
 * An annotation used to indicate that the annotated element is part of an experimental API that may change or be removed in the future.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalSwipeableCardApi