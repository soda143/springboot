package com.samurai.pictconverter.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samurai.pictconverter.Common;
import com.samurai.pictconverter.PictConvConst;

@Service
@PropertySource(value = "classpath:custom.properties")
public class PictConvTopServiceImpl implements PictConvTopService {

    @Value("${conv.pict.timer}")
    private long timer;

    @Value("${conv.pict.max.try.count}")
    private int maxCount;

    @Autowired
    private Common common;

    @Override
    public String pictConverter(byte[] pictData) {
        String base64Data = Base64.getEncoder().encodeToString(pictData);
        return base64Data;
    }

    @Override
    public boolean pictConverterUpload(MultipartFile mfile) {
       
        String scriptPath = "";
        String dataRoot = "";
        String modelName = "";
        String model = "test";

        String fileName = mfile.getOriginalFilename();
        boolean fileConvFlg = false;
        try {

            Path beforeFilePath = Paths.get(PictConvConst.beforeDir + fileName);

            String afterFileName = common.fileRename(fileName, "_fake");
            Path afterFilePath = Paths.get(PictConvConst.afterDir + afterFileName);

            int count = 0;

            // アップロードした場合のファイルを変換前用のフォルダに配置
            Files.copy(mfile.getInputStream(), beforeFilePath, StandardCopyOption.REPLACE_EXISTING);

            // 画像変換用のpythonファイルを呼び出し
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, "--dataroot", dataRoot, "--name", modelName, "--model", model, "--no_dropout");
            processBuilder.start();

            // 無限ループを実行
            while (true) {

                // 5000ミリ秒 = 5秒
                Thread.sleep(timer);

                // ファイルが存在していたらループを抜ける（フラグ ＝ true）
                if (Files.exists(afterFilePath)) {
                    fileConvFlg = true;
                    break;
                }

                // 変換待ち秒数×試行回数に到達したらループを抜ける（フラグ ＝ false）
                if (count == maxCount) {
                    break;
                }
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileConvFlg;
    }
}
