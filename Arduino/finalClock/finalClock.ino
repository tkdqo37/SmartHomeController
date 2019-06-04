#include <DS1302.h>
#include <Wire.h>
#include<LiquidCrystal_I2C.h>


#define CLK 13
#define DAT 12
#define RST 11


DS1302 rtc(RST, DAT, CLK);
LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup(){
  lcd.init();
  lcd.backlight();

  rtc.halt(false);
  rtc.writeProtect(false);
  rtc.setTime(10,33,00);
  rtc.setDate(03,07,2019);
}

void loop() {
  lcd.setCursor(0, 0);
  lcd.print("DATE:");
  lcd.setCursor(6, 0);
  lcd.print(rtc.getDateStr());
  lcd.setCursor(0, 1);
  lcd.print("TIME:");
  lcd.setCursor(6, 1);
  lcd.print(rtc.getTimeStr());
}
