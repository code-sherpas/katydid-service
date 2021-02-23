package com.quipalup.katydid.search

data class PageResult<R : Any>(val total: Long, val matches: List<R>)
