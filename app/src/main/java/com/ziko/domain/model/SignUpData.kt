package com.ziko.domain.model

/**
 * Data class that holds user input information during the sign-up process.
 *
 * This model is used only within the domain/UI layer and is **not** sent directly to the API.
 * It allows you to:
 * - Perform validation on form fields.
 * - Track user inputs.
 * - Construct an API-ready [SignUpRequest] when needed.
 *
 * @property firstName The user's first name (required).
 * @property lastName The user's last name (required).
 * @property email The user's email address (used as username).
 * @property password The chosen password.
 * @property confirmPassword Used only for client-side validation, not sent to backend.
 */
data class SignUpData(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = ""
)
