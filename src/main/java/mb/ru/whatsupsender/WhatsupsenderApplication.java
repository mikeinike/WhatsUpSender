package mb.ru.whatsupsender;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@SpringBootApplication
public class WhatsupsenderApplication {

    public static void main(String[] args) throws InterruptedException, IOException {

   //     SpringApplication.run(WhatsupsenderApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(WhatsupsenderApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-blink-features=AutomationControlled");
//        options.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
//        options.addArguments("--lang=en-GB");
//        //options.setPageLoadStrategy(PageLoadStrategy.NONE);
//        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//        options.addArguments("--disable-infobars"); // disabling infobars
        ChromeDriver chromeDriver = new ChromeDriver(options);
        Actions actions = new Actions(chromeDriver);

        chromeDriver.get("https://web.whatsapp.com/");

        Thread.sleep(2000);

        WebElement ele = chromeDriver.findElementByXPath("//*[@id=\"app\"]/div[1]/div/div[2]/div[1]/div/div[2]/div/canvas");

        File screenshot = ((TakesScreenshot)chromeDriver).getScreenshotAs(OutputType.FILE);

        BufferedImage fullImg = ImageIO.read(screenshot);

        Point point = ele.getLocation();

        int eleWidth = ele.getSize().getWidth();
        int eleHeight = ele.getSize().getHeight();

        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);

//        ImageIO.write(eleScreenshot, "png", screenshot);
//
//        File screenshotLocation = new File("C:\\Users\\slava\\Desktop\\PomoikaV2\\MayaRobotics\\whatsupsender\\canvas.png");
//
//        FileUtils.copyFile(screenshot, screenshotLocation);

        CopyImagetoClipBoard copyImagetoClipBoard = new CopyImagetoClipBoard();
        copyImagetoClipBoard.copyImage(eleScreenshot);


        Thread.sleep(2000);

        ((JavascriptExecutor) chromeDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(chromeDriver.getWindowHandles());
        chromeDriver.switchTo().window(tabs.get(1));


       // chromeDriver.get("https://e.mail.ru/inbox/");
        chromeDriver.get("https://mail.google.com/mail/u/0/#inbox");

        Thread.sleep(500);
        //chromeDriver.findElementByXPath("//*[@id=\"root\"]/div/div[2]/div/div/div/form/div[2]/div[2]/div[1]/div/div/div/div/div/div[1]/div/input").sendKeys("checkmailtemp@mail.ru");
        //chromeDriver.findElementByXPath("//*[@id=\"root\"]/div/div[2]/div/div/div/form/div[2]/div[2]/div[3]/div/div[1]/button/span").click();

        chromeDriver.findElementByXPath("//*[@id=\"identifierId\"]").sendKeys("testovaya.pochta.mikeinike@gmail.com");
        chromeDriver.findElementByXPath("//*[@id=\"identifierNext\"]/div/button/span").click();

        Thread.sleep(1000);

    //    chromeDriver.findElementByXPath("//*[@id=\"root\"]/div/div[2]/div/div/div/form/div[2]/div/div[2]/div/div/div/div/div/input").sendKeys("qwe12345");
    //    chromeDriver.findElementByXPath("//*[@id=\"root\"]/div/div[2]/div/div/div/form/div[2]/div/div[3]/div/div[1]/div/button/span").click();

        chromeDriver.findElementByXPath("//*[@id=\"password\"]/div[1]/div/div[1]/input").sendKeys("your-password");
        chromeDriver.findElementByXPath("//*[@id=\"passwordNext\"]/div/button/span").click();

        Thread.sleep(6000);

        chromeDriver.findElementByCssSelector("div[class*='T-I-KE']").click();
        //chromeDriver.findElementByCssSelector("span[class*='compose-button__wrapper']").click();

        Thread.sleep(1500);
        chromeDriver.findElementByName("to").sendKeys("mikeinike1234567@gmail.com");
      //  chromeDriver.findElementByCssSelector("input[class*='container--H9L5q']").sendKeys("mikeinike1234567@gmail.com");

        chromeDriver.findElementByName("subjectbox").sendKeys("testTheme"); //для темы для отправки с gmail

        //chromeDriver.findElementByCssSelector("div[class*='cke_editable_inline']").click();
        chromeDriver.findElementByCssSelector("div[class*='LW-avf']").click();

        actions.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();

        Thread.sleep(1500);

       // chromeDriver.findElementByXPath("/html/body/div[15]/div[2]/div/div[2]/div[1]/span[1]/span").click();
        chromeDriver.findElementByCssSelector("div[class*='aoO']").click();

        Thread.sleep(1000);

       // chromeDriver.findElementByCssSelector("svg[class*='ico_16-close']").click(); //Доп кнопка для мгновенной отправки с помощью Mail.ru
       // Thread.sleep(500);

        chromeDriver.switchTo().window(tabs.get(0));

        Thread.sleep(30000);

        chromeDriver.findElementByXPath("//*[@id=\"side\"]/div[1]/div/label/div/div[2]").sendKeys("Даня Рудяк");
        Thread.sleep(500);

        chromeDriver.findElementByCssSelector("div[style*='z-index: 0; transition: none 0s ease 0s; height: 72px; transform: translateY(0px);']").click();

        Thread.sleep(200);
        chromeDriver.findElementByXPath("//*[@id=\"main\"]/footer/div[1]/div/div/div[2]/div[1]/div/div[2]").sendKeys("Тестовое сообщение с помощью веб ватсапа");

        chromeDriver.findElementByXPath("//*[@id=\"main\"]/footer/div[1]/div/div/div[2]/div[2]/button").click();

        //span[title*='ПАПА'] //title = имя, которое дали контакту

        //Для входа в гугл контакты
//        chromeDriver.get("https://contacts.google.com/");
//        chromeDriver.findElementByClassName("whsOnd zHQkBf").sendKeys("testovaya.pochta.mikeinike@gmail.com");
//
//        chromeDriver.findElementByClassName("whsOnd zHQkBf").sendKeys("lrSbgGWX");
//
//        chromeDriver.findElementByClassName("ZFr60d CeoRYc").click();

//        WebElement canvas = chromeDriver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/div[2]/div[1]/div/div[2]/div/canvas"));
//
//        String src = canvas.getAttribute("src");
//
//        URL url = new URL(src);
//
//        BufferedImage bufferedImage = ImageIO.read(url);
//
//        ImageIO.write(bufferedImage,"png",new File("Test.png"));
//
//        chromeDriver.close();
//
//        WebElement canvas = chromeDriver.findElementByXPath("//*[@id=\"app\"]/div[1]/div/div[2]/div[1]/div/div[2]/div/canvas");
//
//        String canvas64 = (String) chromeDriver.executeScript("return arguments[0].toDataURL('image/png').substring(21);", canvas);
//
//        byte[] canvasPng = Base64.decodeBase64(canvas64);
//
//       try (FileOutputStream stream = new FileOutputStream("C:\\Users\\slava\\Desktop\\PomoikaV2\\MayaRobotics\\whatsupsender\\canvas.png"))
//       {
//           stream.write(canvasPng);
//       }

    }

}
