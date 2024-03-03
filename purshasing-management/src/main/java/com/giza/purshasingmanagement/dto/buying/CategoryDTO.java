package com.giza.purshasingmanagement.dto.buying;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class CategoryDTO implements Serializable {
    private int id;
    private String name;
}
