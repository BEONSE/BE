package com.beonse2.domain.branch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
public class BranchListDTO {

    private Long bid;

    private String email;

    private String name;

    private String address;

    private File image;

    private String introduction;

    @Builder
    public BranchListDTO(String email, String name, String address, File image, String introduction) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.image = image;
        this.introduction = introduction;
    }
}
