package com.ecommerce.auth.user.utils;

public class UserUtils {

    public static String cleanDocumentFormat(String document) {
        if (document == null) {
            return null;
        }
        return document.replaceAll("[^\\d]", "");
    }

    public static String formatDocument(String document) {

        if (document == null || document.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }

        return String.format("%s.%s.%s-%s",
                document.substring(0, 3),
                document.substring(3, 6),
                document.substring(6, 9),
                document.substring(9, 11)
        );
    }
}
