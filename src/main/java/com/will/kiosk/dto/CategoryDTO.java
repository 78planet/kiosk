package com.will.kiosk.dto;

import java.time.LocalDateTime;

public record CategoryDTO(
        int categoryId,
        String categoryName,
        String description
) {
}
