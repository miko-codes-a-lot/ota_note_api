package com.ota.api.note.models.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteForm {
    private long id;

    @NotEmpty(message = "Title is required")
    @Size(min = 3, message = "Title must be at least {min} characters long.")
    @Size(max = 60, message = "Title must not exceed {max} characters long.")
    private String title;


    @NotEmpty(message = "Body is required")
    @Size(min = 3, message = "Body must be at least {min} characters long.")
    @Size(max = 255, message = "Body must not exceed {max} characters long.")
    private String body;
}
