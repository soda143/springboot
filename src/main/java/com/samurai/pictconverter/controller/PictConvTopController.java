package com.samurai.pictconverter.controller;

import java.io.File;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.samurai.pictconverter.Common;
import com.samurai.pictconverter.PictConvConst;
import com.samurai.pictconverter.form.PictConvFileDelForm;
import com.samurai.pictconverter.form.PictConvFileForm;
import com.samurai.pictconverter.form.PictConvReTopForm;
import com.samurai.pictconverter.service.PictConvTopServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class PictConvTopController {

    @Autowired
    private Common common;

    @Autowired
    private PictConvTopServiceImpl pictConvTopServiceImpl;

    @GetMapping("/top")
    public String pictConvTopView(Model model) {
        return "top";
    }

    @PostMapping("/upload")
    public Object upload(PictConvFileForm form, Model model) {

        /**
         * アップロードファイルをバイナリデータとして扱う
         * サンプルコードとして残す事とする
         */
        // File file = File.createTempFile(UUID.randomUUID().toString(), fileName);
        // mfile.transferTo(file.toPath());
        // byte[] byteImg = Files.readAllBytes(file.toPath());
        // String pictConvData = pictConvTopServiceImpl.pictConverter(byteImg);
        // model.addAttribute("pictConvData", "data:image/png;base64," + pictConvData);

        MultipartFile mfile = form.getUploadFile();
        boolean pictConvFlg = pictConvTopServiceImpl.pictConverterUpload(mfile);

        if (pictConvFlg) {
            String fileName = mfile.getOriginalFilename();

            try {
                // 変換後の同名ファイルを取得しイメージとして読み込む
                // ■対応理由
                // ローカルファイルへのアクセスがブラウザのセキュリティポリシーとして禁止されているため
                // ポリシーを変更することは出来るがブラウザ実行ファイルにオプションを指定して実行しなければならない等
                // 「掻いくぐる」ための手段を取らざるを得ないため。
                String renameAfterFileName = common.fileRename(fileName, "_fake");

                File file = new File(PictConvConst.afterDir + renameAfterFileName);
                byte[] byteImg = Files.readAllBytes(file.toPath());
                String pictConvData = pictConvTopServiceImpl.pictConverter(byteImg);

                model.addAttribute("pictConvData", "data:image/png;base64," + pictConvData);
                model.addAttribute("afterDir", PictConvConst.afterDir);
                model.addAttribute("fileName", fileName);
                model.addAttribute("dlflg", 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 画像変換出来なかったとみなし、エラーフラグを返す。
            model.addAttribute("conv_err", true);
            return "top";
        }
        return "result";
    }

    @PostMapping("/delete")
    public Object delete(PictConvFileDelForm form, Model model) {

        common.fileDelete(form);
        model.addAttribute("fileName", form.getFileName());
        model.addAttribute("afterDir", PictConvConst.afterDir);
        model.addAttribute("pictConvData", form.getConvImg());
        model.addAttribute("dlflg", 1);

        return "result";
    }

    @PostMapping("/reTopView")
    public String reTopView(PictConvReTopForm form, Model model) {
        PictConvFileDelForm pictConvFileDelForm = new PictConvFileDelForm();
        pictConvFileDelForm.setFileName(form.getFileName());
        pictConvFileDelForm.setAfterDir(form.getAfterDir());
        common.fileDelete(pictConvFileDelForm);
        return "top";
    }
}
