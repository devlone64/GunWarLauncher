/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.client;

import dev.lone64.launcher.occupationalwar.frame.MainFrame;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import java.nio.file.Path;
import java.text.DecimalFormat;

/**
 *
 * @author lone64dev
 */
public class ClientRunnable implements IProgressCallback {
    private String progressText = "";
    private String percentageText = "";
    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

    @Override
    public void step(Step step) {
        //this.progressText = StepState.valueOf(step.name()).getDetails();
        //setProgressText(String.format("%s", this.progressText));
    }

    @Override
    public void update(DownloadList.DownloadInfo info) {
        this.percentageText = this.decimalFormat.format((int) (info.getDownloadedBytes() * 100 / info.getTotalToDownloadBytes()));
        setProgressValue((int) (info.getDownloadedBytes() * 100 / info.getTotalToDownloadBytes()));
        setProgressText("%s/%s".formatted(this.percentageText, "100"));
        //setProgressText(String.format("%s (%s)", this.progressText, this.percentageText));
    }

    @Override
    public void onFileDownloaded(Path path) {
        //var mcFolder = FileUtil.getDataFolder("Minecraft");
        //setProgressText("..." + path.toString().replace(mcFolder.getAbsolutePath(), ""));
    }
    
    private void setProgressValue(int value) {
        MainFrame.getInstance().getProgressBar().setValue(value);
    }
    
    private void setProgressText(String text) {
        MainFrame.getInstance().getProgressBar().setString(text);
    }
}