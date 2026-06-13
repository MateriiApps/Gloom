package dev.materii.gloom.core.model.paging

/**
 * A page of items.
 *
 * @param next The key used to request the next page
 * @param prev The key used to request the previous page
 * @param hasNext Whether or not this is the last page
 * @param hasPrev Whether or not this is the first page
 * @param totalCount Total number of items to page through
 * @param items Items in this page
 */
data class Page<T>(
    val next: String? = null,
    val prev: String? = null,
    val hasNext: Boolean? = null,
    val hasPrev: Boolean? = null,
    val totalCount: Int? = null,
    val items: List<T>
)