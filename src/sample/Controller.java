package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.crawl.main.MyCrawler;

public class Controller {

    public TextField url;

    public Label tips;

    public void init() {
        tips.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            }
        });
    }

    public void picDownload(ActionEvent actionEvent) {
        tips.setText("正在下载");
        threadTest(url.getText(), tips);
    }

    private static void threadTest(String url,Label tips) {
        new Thread(){
            @Override
            public void run() {
                MyCrawler crawler = new MyCrawler();
                if (url != null){
                    try {
                        crawler.crawling(new String[]{url});
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                tips.setText("下载完成");
                            }
                        });
                    }catch (IllegalArgumentException e){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                tips.setText("网址有误");
                            }
                        });
                    }
                }

            }
        }.start();
    }


}
