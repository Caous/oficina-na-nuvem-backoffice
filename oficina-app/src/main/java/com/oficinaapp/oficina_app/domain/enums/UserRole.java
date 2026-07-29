package com.oficinaapp.oficina_app.domain.enums;

/**
 * Identifies which area of the product an account may reach.
 */
public enum UserRole {

    /** Vehicle owner who hires the workshop. */
    CUSTOMER,

    /** Person who owns the workshop and manages its team. */
    WORKSHOP_OWNER,

    /** Person hired by a workshop owner to operate the workshop. */
    WORKSHOP_EMPLOYEE
}
