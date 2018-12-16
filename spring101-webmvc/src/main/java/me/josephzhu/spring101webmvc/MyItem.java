package me.josephzhu.spring101webmvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyItem {
    @NotBlank
    @Size(min=2, max=30)
    private String name;
    @NotNull
    @Min(10)
    @Max(10000)
    private Integer price;
}
