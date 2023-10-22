package com.samurai.pictconverter.form;

import lombok.Data;

@Data
public class PictConvFileDelForm {
    private String beforeDir;
    private String afterDir;
    private String fileName;
    private String convImg;
    private int downloadflg;
}
