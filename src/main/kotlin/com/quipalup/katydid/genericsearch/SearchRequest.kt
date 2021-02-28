package com.quipalup.katydid.genericsearch

data class SearchRequest<F>(val pageQuery: PageQuery, val filters: List<Filter<F>>, val sortingList: List<Sorting<F>>)
