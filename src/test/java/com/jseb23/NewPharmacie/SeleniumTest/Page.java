package com.jseb23.NewPharmacie.SeleniumTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:63342/NewPharmacie/src/main/resources/static/index.html?_ijt=pi273c1ll7iincamuoslkro0st&_ij_reload=RELOAD_ON_SAVE#
public class Page {
    @FindBy(css = "button[id='valider']")
    public WebElement buttonValider;

    @FindBy(css = "html > body > section:nth-of-type(2) > form > a")
    public WebElement linkRetourOublie;

    public Page(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}