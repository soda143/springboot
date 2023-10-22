package com.samurai.pictconverter.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PictConvFileForm {
    private MultipartFile uploadFile;
}
