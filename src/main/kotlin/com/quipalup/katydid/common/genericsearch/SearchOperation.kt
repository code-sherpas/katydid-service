package com.quipalup.katydid.common.genericsearch

sealed class SearchOperation {
    sealed class ManyValuesSearchOperation : SearchOperation() {
        object In : ManyValuesSearchOperation()
        object NotIn : ManyValuesSearchOperation()
    }

    sealed class OneValueSearchOperation : SearchOperation() {
        object GreaterThan : OneValueSearchOperation()
        object LessThan : OneValueSearchOperation()
        object GreaterThanOrEqual : OneValueSearchOperation()
        object LessThanOrEqual : OneValueSearchOperation()
        object Equal : OneValueSearchOperation()
        object NotEqual : OneValueSearchOperation()
        object Match : OneValueSearchOperation()
        object MatchStart : OneValueSearchOperation()
        object MatchEnd : OneValueSearchOperation()
        object Like : OneValueSearchOperation()
    }

    sealed class UnarySearchOperation : SearchOperation() {
        object IsNull : UnarySearchOperation()
        object IsNotNull : UnarySearchOperation()
        object IsTrue : UnarySearchOperation()
        object IsFalse : UnarySearchOperation()
    }
}
