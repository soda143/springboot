package com.samurai.pictconverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.samurai.pictconverter.form.PictConvFileDelForm;

/**
 * 共通クラス
 */
@Component
public class Common {

    /**
     * ファイル名変更用メソッド
     * 
     * @param filename
     * @param addStr
     * @return 変更後ファイル名
     */
    public String fileRename(String filename, String addStr) {

        String separator = ".";
        int lastSeparatorIndex = filename.lastIndexOf(separator);
        String name = filename.substring(0, lastSeparatorIndex);
        String extension = filename.substring(lastSeparatorIndex);
        String renameFileName = name + addStr + ".png";

        return renameFileName;
    }

    /**
     * ファイル削除用メソッド
     * 
     */
    public void fileDelete(PictConvFileDelForm form) {
        
        String filepath = form.getAfterDir();
        String fileName = form.getFileName();

        Path beforeFilePath = Paths.get(PictConvConst.beforeDir + fileName);

        // 更新前ファイル名と更新後ファイル名を設定
        String renameBeforeFileName = fileRename(fileName, "_real");

        Path path_real = Paths.get(filepath + renameBeforeFileName);
        String renameAfterFileName = fileRename(fileName, "_fake");
        Path path_fake = Paths.get(filepath + renameAfterFileName);
        

        

        try {
            if (Files.exists(path_real)) {
                Files.delete(path_real);
            }
            if (Files.exists(path_fake)) {
                Files.delete(path_fake);
            }
            if (Files.exists(beforeFilePath)) {
                Files.delete(beforeFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

