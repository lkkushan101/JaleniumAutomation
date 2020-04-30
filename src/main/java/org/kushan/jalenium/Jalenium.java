package org.kushan.jalenium;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public  class Jalenium {
	
	static WebDriver  drv;
	
	public Jalenium ()
	{
		WebDriverManager.chromedriver().setup();
	    drv = new ChromeDriver();	
	    drv.manage().window().maximize();
	}
	
	public Jalenium (String browser)
	{
		if (browser.contains("Firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
		    drv = new FirefoxDriver();	
		    drv.manage().window().maximize();
		}
		if (browser.contains("Firefox--headless"))
		{
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
		    drv = new FirefoxDriver(options);	
		    drv.manage().window().maximize();
		}
		
	}
		
	public Jalenium (boolean headless)
	{
		if (headless == true)
		{
			WebDriverManager.chromedriver().version("76").setup();
			ChromeOptions options = new ChromeOptions(); 
			options.addArguments("--headless");
		    drv = new ChromeDriver(options);
		}
		else 
		{
			WebDriverManager.chromedriver().version("76").setup();
		    drv = new ChromeDriver();	
		}
	    
	}
	
	public static void ClickPseudoElement(String locator)
	{
		WebElement element = drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]"));
		((JavascriptExecutor) drv).executeScript("arguments[0].click();", element);
				
	}
	
	public static String GetPseudoBeforeElementStyle(String locator, String syle_property)
	{
		WebElement switchLabel = drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]"));
		return ((JavascriptExecutor)drv)
		        .executeScript("return window.getComputedStyle(arguments[0], ':before').getPropertyValue('"+ syle_property + "');",switchLabel).toString();
	}
	
		public static void DragAndDrop(String sourceLocator, String destinationLocator) throws Exception 
		{
			WebElement sourceElement = drv.findElement(By.xpath("//*[contains(@value,'"+ sourceLocator +"') or contains(@name,'"+ sourceLocator +"') or contains(@id,'"+ sourceLocator +"') or contains(text(),'"+ sourceLocator +"') or contains(@for,'"+ sourceLocator +"') ]"));
			WebElement destinationElement = drv.findElement(By.xpath("//*[contains(@value,'"+ destinationLocator +"') or contains(@name,'"+ destinationLocator +"') or contains(@id,'"+ destinationLocator +"') or contains(text(),'"+ destinationLocator +"') or contains(@for,'"+ destinationLocator +"') ]"));
		    JavascriptExecutor js = (JavascriptExecutor) drv;
		    js.executeScript("function createEvent(typeOfEvent) {\n" +"var event =document.createEvent(\"CustomEvent\");\n" +"event.initCustomEvent(typeOfEvent,true, true, null);\n" +"event.dataTransfer = {\n" +"data: {},\n" +"setData: function (key, value) {\n" +"this.data[key] = value;\n" +"},\n" +"getData: function (key) {\n" +"return this.data[key];\n" +"}\n" +"};\n" +"return event;\n" +"}\n" +"\n" +"function dispatchEvent(element, event,transferData) {\n" +"if (transferData !== undefined) {\n" +"event.dataTransfer = transferData;\n" +"}\n" +"if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n" +"} else if (element.fireEvent) {\n" +"element.fireEvent(\"on\" + event.type, event);\n" +"}\n" +"}\n" +"\n" +"function simulateHTML5DragAndDrop(element, destination) {\n" +"var dragStartEvent =createEvent('dragstart');\n" +"dispatchEvent(element, dragStartEvent);\n" +"var dropEvent = createEvent('drop');\n" +"dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n" +"var dragEndEvent = createEvent('dragend');\n" +"dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" +"}\n" +"\n" +"var source = arguments[0];\n" +"var destination = arguments[1];\n" +"simulateHTML5DragAndDrop(source,destination);",sourceElement, destinationElement);
		    Thread.sleep(1500);

		}
	
	
	public static String GetPseudoAfterElementStyle(String locator, String syle_property)
	{
		WebElement switchLabel = drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]"));
		return ((JavascriptExecutor)drv)
		        .executeScript("return window.getComputedStyle(arguments[0], ':after').getPropertyValue('"+ syle_property + "');",switchLabel).toString();
	}
	
	public static void ScrollToPageEnd()
	{
		((JavascriptExecutor) drv)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public static void ScrollToPageTop()
	{
		((JavascriptExecutor) drv)
		.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public static void WaitForElementPresent(String locator)
	{
		WebElement element ;
		WebDriverWait wait=new WebDriverWait(drv, 30);
		element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]")));
	}
	
	public static void ScrollToElement(String locator)
	{
		WebElement element = drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]"));
		((JavascriptExecutor) drv).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public static void InteligentAssertElementPresent(String locator)
	{
		Assert.assertTrue(drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]")).isDisplayed());
	}
	
	public static void InteligentAssertElementText(String locator, String elementText)
	{
		Assert.assertEquals(drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]")).getText(), elementText);
	}
	
	public static void AssertElementPresent(String locator)
	{
		String selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("name"))
		{
			Assert.assertTrue(drv.findElement(By.name(StringUtils.substringAfter(locator, "="))).isDisplayed());
		}
		
	    selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("id"))
		{
			Assert.assertTrue(drv.findElement(By.id(StringUtils.substringAfter(locator, "="))).isDisplayed());
		}
		
		if (selector.contains("xpath"))
		{
			Assert.assertTrue(drv.findElement(By.xpath(StringUtils.substringAfter(locator, "="))).isDisplayed());
		}
		
		if (selector.contains("text"))
		{
			Assert.assertTrue(drv.findElement(By.xpath("//*[contains(text(),'"+ StringUtils.substringAfter(locator, "=") +"') ]")).isDisplayed());
		}
	}
	
	public static void AssertElementText(String locator, String elementText)
	{
		String selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("name"))
		{
			Assert.assertEquals(drv.findElement(By.name(StringUtils.substringAfter(locator, "="))).getText(),elementText);
		}
		
	    selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("id"))
		{
			Assert.assertEquals(drv.findElement(By.id(StringUtils.substringAfter(locator, "="))).getText(),elementText);
		}
		
		if (selector.contains("xpath"))
		{
			Assert.assertEquals(drv.findElement(By.xpath(StringUtils.substringAfter(locator, "="))).getText(),elementText);
		}
		
		if (selector.contains("text"))
		{
			Assert.assertEquals(drv.findElement(By.xpath("//*[contains(text(),'"+ StringUtils.substringAfter(locator, "=") +"') ]")).getText(),elementText);
		}
	}
	
	public static void InteligentClick (String locator)
	{
		drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]")).click();
		
	}
	

	public static void InteligentType (String locator, String textToType)
	{
		
		drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]")).sendKeys(textToType);
		
	}
	
	public static void PesudoEelementClick (String locator)
	{
		Actions build = new Actions(drv);
		build.moveToElement((drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]")))).click().build().perform();		
	}
	
	public static void PesudoEelementType (String locator, String text)
	{
		Actions build = new Actions(drv);
		build.moveToElement((drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]")))).sendKeys(text).build().perform();		
	}
	
	public static void Type (String locator, String text )
	{
		String selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("name"))
		{
			drv.findElement(By.name(StringUtils.substringAfter(locator, "="))).sendKeys(text);
		}
		
	    selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("id"))
		{
			drv.findElement(By.id(StringUtils.substringAfter(locator, "="))).sendKeys(text);
		}
		
		if (selector.contains("xpath"))
		{
			drv.findElement(By.xpath(StringUtils.substringAfter(locator, "="))).sendKeys(text);
		}
		
		if (selector.contains("text"))
		{
			drv.findElement(By.xpath("//*[contains(text(),'"+ StringUtils.substringAfter(locator, "=") +"') ]")).sendKeys(text);;
		}
	}
			
	public static void Type(By locator, String text)
	{
		drv.findElement(locator).sendKeys(text);
	}
	
	public static void Click( By locator)
	{
		drv.findElement(locator).click();
	}
	
	public static String GetText(By locator)
	{
		return drv.findElement(locator).getText();
	}
	
	public static void SelectDropDown(String locator, String value)
	{
		WebElement element = drv.findElement(By.xpath("//*[contains(@value,'"+ locator +"') or contains(@name,'"+ locator +"') or contains(@id,'"+ locator +"') or contains(text(),'"+ locator +"') or contains(@for,'"+ locator +"') ]"));
		Select selectMenu = new Select (element);
		selectMenu.selectByValue(value);
	}
	
	public static void Click (String locator )
	{
		String selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("name"))
		{
			drv.findElement(By.name(StringUtils.substringAfter(locator, "="))).click();
		}
		
	    selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("id"))
		{
			drv.findElement(By.id(StringUtils.substringAfter(locator, "="))).click();
		}
		
		if (selector.contains("xpath"))
		{
			drv.findElement(By.xpath(StringUtils.substringAfter(locator, "="))).click();
		}
		
		if (selector.contains("text"))
		{
			drv.findElement(By.xpath("//*[contains(text(),'"+ StringUtils.substringAfter(locator, "=") +"') ]")).click();
		}
	}
	
	public static void TakeScreenShot() throws IOException
	{
		 TakesScreenshot scrShot =((TakesScreenshot)drv);
		 File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
         File f = new File("./ScreenShots/");
         if (f.exists() && f.isDirectory()) {
        	 Date date = new Date() ;
        	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        	 File DestFile=new File("./ScreenShots/"+  dateFormat.format(date)+".jpg");
             FileUtils.copyFile(SrcFile, DestFile);
        }
         else
         {
        	 new File("./ScreenShots/").mkdir();
        	 Date date = new Date() ;
        	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        	 File DestFile=new File("./ScreenShots/"+ dateFormat.format(date)+".jpg");
             FileUtils.copyFile(SrcFile, DestFile);
         }
       
	}
	public static  void Go( String value)
	{
		drv.get(value);
	}
	
	public static void Back()
	{
		drv.navigate().back();
	}
	
	public static void Forward()
	{
		drv.navigate().forward();
	}
	
	public static void Refresh()
	{
		drv.navigate().refresh();
	}
	
	public static void CloseBrowser()
	{
		drv.close();
	}
}
