package com.example.HR.enums;

public enum TaskStatus {
    TODO,            // Yeni yaradılıb, hələ başlanmayıb
    IN_PROGRESS,     // Hal-hazırda işlənir
    BLOCKED,         // Maneə var, davam edə bilmir
    REVIEW,          // Kod/iş yoxlamadadır
    DONE,            // Tamamlanıb
    ARCHIVED
}
