package com.example.todoapp.ui.settings.model

import com.example.todoapp.R

enum class ThemeMode {
    LIGHT {
        override fun toStringResource(): Int = R.string.theme_light
    },
    DARK {
        override fun toStringResource(): Int = R.string.theme_dark
    },
    SYSTEM {
        override fun toStringResource(): Int = R.string.theme_system
    };

    abstract fun toStringResource(): Int
}
