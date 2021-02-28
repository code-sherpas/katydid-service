package com.quipalup.katydid.common.genericsearch

data class SearchRequest<F>(val pageQuery: PageQuery, val filters: List<Filter<F>>, val sortingList: List<Sorting<F>>)
