package com.quipalup.katydid.genericsearch

data class PageResult<R : Any>(val total: Long, val matches: List<R>)
