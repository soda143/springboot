package com.samurai.pictconverter.service;

import org.springframework.web.multipart.MultipartFile;

public interface PictConvTopService {

    public String pictConverter(byte[] pictData);

    public boolean pictConverterUpload(MultipartFile mfile);

}
