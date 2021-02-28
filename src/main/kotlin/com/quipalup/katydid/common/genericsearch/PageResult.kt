package com.quipalup.katydid.common.genericsearch

data class PageResult<R : Any>(val total: Long, val matches: List<R>)
