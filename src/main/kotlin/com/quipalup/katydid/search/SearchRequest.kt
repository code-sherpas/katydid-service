package com.quipalup.katydid.search

data class SearchRequest<F>(val pageQuery: PageQuery, val filters: List<Filter<F>>, val sortingList: List<Sorting<F>>)
