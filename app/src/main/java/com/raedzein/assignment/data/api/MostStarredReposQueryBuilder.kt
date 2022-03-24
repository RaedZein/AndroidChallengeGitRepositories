package com.raedzein.assignment.data.api

import com.raedzein.assignment.utils.Constants

class MostStarredReposQueryBuilder : GitReposQueryBuilder() {

    override fun putQueryParams(queryParams: MutableMap<String, String>) {
        queryParams["order"] = Constants.DEFAULT_ORDER
        queryParams["per_page"] = Constants.DEFAULT_PER_PAGE.toString()
        queryParams["sort"] = Constants.DEFAULT_SORT_BY
    }

}
