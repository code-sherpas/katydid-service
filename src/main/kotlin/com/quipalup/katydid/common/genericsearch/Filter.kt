package com.quipalup.katydid.common.genericsearch

abstract class Filter<F> {
    abstract val operation: SearchOperation
    abstract val field: F
}

data class OneValueFilter<F, T>(
    override val operation: SearchOperation.OneValueSearchOperation,
    override val field: F,
    val value: T
) : Filter<F>()

data class ManyValuesFilter<F, T>(
    override val operation: SearchOperation.ManyValuesSearchOperation,
    override val field: F,
    val values: List<T>
) : Filter<F>()

data class UnaryFilter<F>(override val operation: SearchOperation.UnarySearchOperation, override val field: F) :
        Filter<F>()