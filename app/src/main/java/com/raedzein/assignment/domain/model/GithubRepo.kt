package com.raedzein.assignment.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raedzein.assignment.data.CustomErrorResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Raed Zein
 * created on Thursday, 24 March, 2022
 */
@JsonClass(generateAdapter = true)
data class GithubRepoList(
    @field:Json(name = "incomplete_results") val resultIncomplete: Boolean = true,
    @field:Json(name = "total_count") val total: Int = 0,
    @field:Json(name = "items") val items: List<GithubRepo> = emptyList(),
)

@JsonClass(generateAdapter = true)
@Entity
data class GithubRepo(
    @PrimaryKey(autoGenerate = false) @field:Json(name = "id") val id: Long = 0,
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "language") val language: String? = null,
    @field:Json(name = "description") val description: String? = null,
    @field:Json(name = "stargazers_count") val starsCount: Int? = null,
    @field:Json(name = "forks_count") val forks: Int = 0,
    @Embedded @field:Json(name = "owner") val owner: RepoOwner = RepoOwner(),
    var favourited: Boolean = false
)

@JsonClass(generateAdapter = true)
data class RepoOwner(
    @field:Json(name = "login") val username: String? = null,
    @field:Json(name = "avatar_url") val avatarUrl: String? = null
)

@JsonClass(generateAdapter = true)
data class GithubErrorResponse(
    @field:Json(name = "message") val message: String? = null,
    @field:Json(name = "errors") val githubErrors: List<GithubErrorObject> = listOf()
): CustomErrorResponse() {
    override fun getError() = if (githubErrors.isNotEmpty()) githubErrors.joinToString(", ") { it.toString() }
    else message
}

@JsonClass(generateAdapter = true)
data class GithubErrorObject(
    @field:Json(name = "resource") val resource: String,
    @field:Json(name = "field") val field: String,
    @field:Json(name = "code") val code: String
) {
    override fun toString(): String {
        return "Resource: $resource, field:$field, code:$code"
    }
}
