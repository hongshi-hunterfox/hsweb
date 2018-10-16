package com.uclee.web.backend.vo;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by super13 on 6/3/17.
 */
public class FileVo {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
