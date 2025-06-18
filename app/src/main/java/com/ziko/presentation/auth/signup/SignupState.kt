package com.ziko.presentation.auth.signup

/**
 * Represents the different states of the sign-up process.
 * Used by the [SignUpViewModel] to communicate UI status changes.
 */
sealed class SignUpState {

    /**
     * The default state before any action has been taken.
     */
    data object Idle : SignUpState() // Initial idle state, nothing in progress

    /**
     * Indicates that a sign-up operation is currently ongoing.
     */
    data object Loading : SignUpState() // Actively submitting sign-up request

    /**
     * Represents a successful sign-up operation.
     */
    data object Success : SignUpState() // Sign-up completed successfully

    /**
     * Represents a failure in the sign-up process.
     *
     * @property message Describes the reason for failure.
     */
    data class Error(val message: String) : SignUpState() // Sign-up failed with message
}
