package com.ziko.core.common

/**
 * Represents a generic wrapper class to encapsulate the state of a resource or operation.
 *
 * This sealed class helps manage UI state in a type-safe and declarative way.
 * It is commonly used in ViewModels and UI layers to represent:
 * - Loading states
 * - Success with data
 * - Error with optional data and error message
 *
 * @param T The type of data being wrapped
 * @property data The data returned from a successful operation, or null if unavailable
 * @property message An optional error message for failure states
 *
 * Usage example:
 * ```
 * viewModelScope.launch {
 *     _uiState.value = Resource.Loading()
 *     try {
 *         val result = repository.fetchData()
 *         _uiState.value = Resource.Success(result)
 *     } catch (e: Exception) {
 *         _uiState.value = Resource.Error("Failed to fetch data")
 *     }
 * }
 * ```
 *
 * @see Success
 * @see Error
 * @see Loading
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {

    /**
     * Represents a successful operation with [data] returned.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Represents a failed operation with an optional [data] and a [message] describing the failure.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Represents an ongoing operation, typically used to show loading indicators.
     */
    class Loading<T> : Resource<T>()
}
