package com.emre.filedownloadmanagerconverter.LinkDm;

public class DownloadModel {
    long id;
    long downloadId;
    String title;
    String file_path;
    String progress;
    String status;
    String file_size;

    public void setId(long id) {
        this.id = id;
    }

    public void setDownloadId(long downloadId) {
        this.downloadId = downloadId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public void setIs_paused(boolean is_paused) {
        this.is_paused = is_paused;
    }

    boolean is_paused;

    public long getId() {
        return id;
    }

    public long getDownloadId() {
        return downloadId;
    }

    public String getTitle() {
        return title;
    }

    public String getFile_path() {
        return file_path;
    }

    public String getProgress() {
        return progress;
    }

    public String getStatus() {
        return status;
    }

    public String getFile_size() {
        return file_size;
    }

    public boolean isIs_paused() {
        return is_paused;
    }
}
