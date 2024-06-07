package com.yosuz.flatapp.data.rules

object Validator {
    fun validateName(name: String): ValidationResult {
        return ValidationResult(
            (!name.isNullOrEmpty() && name.length >= 2)
        )
    }
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (!email.isNullOrEmpty())
        )
    }
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >= 6)
        )
    }
    data class ValidationResult(
        val status: Boolean = false
    )
}