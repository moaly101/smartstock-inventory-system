package org.example.smartstocksystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Komplett neue Datei – ersetzt UserDTO
// Java Record: kein Boilerplate, unveränderlich
public record RegisterRequest(

        @NotBlank(message = "Benutzername darf nicht leer sein")
        String username,

        @NotBlank(message = "Passwort darf nicht leer sein")
        @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen lang sein")
        String password

) {}
// Kein Konstruktor, keine Getter, kein equals/hashCode nötig

