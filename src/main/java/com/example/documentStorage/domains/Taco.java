package com.example.documentStorage.domains;
import java.util.Date;
import java.util.List;

import com.sun.istack.NotNull;
import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class Taco {
    private Long id;
    private Date createdAt;
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<String> ingredients;
}