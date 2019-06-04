#include <SoftwareSerial.h> //시리얼통신 라이브러리 호출
#include <Servo.h>

Servo myservo;

LiquidCrystal_I2C lcd(0x27, 20, 4);

int blueTx = 2; //Tx (보내는핀 설정)at
int blueRx = 3; //Rx (받는핀 설정)
char incomingByte;  // incoming data
int  LED = 8;      // LED pin

SoftwareSerial mySerial(blueTx, blueRx); //시리얼 통신을 위한 객체선언

int red = 5;
int green = 6;
int blue = 7;

int buzzerPin = 10;
int buttonPin = 4;

void setup() {
  Serial.begin(9600); //시리얼모니터
  mySerial.begin(9600); //블루투스 시리얼
  pinMode(LED, OUTPUT);
  myservo.attach(9);

  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(blue, OUTPUT);

  pinMode(buzzerPin, OUTPUT);
  pinMode(buttonPin, INPUT_PULLUP);

}

void loop() {

  if (digitalRead(buttonPin) == LOW) {
    tone(10, 72);
    delay(500);
    noTone(10);
  } else {
    digitalWrite(buzzerPin, LOW);
  }

  if (mySerial.available()) {
    Serial.write(mySerial.read()); //블루투스측 내용을 시리얼모니터에 출력
  }

  if (mySerial.available()) {  // if the data came
    incomingByte = mySerial.read(); // read byte
    if (incomingByte == '0') {
      digitalWrite(LED, LOW); 
      digitalWrite(red, LOW);
      digitalWrite(green, LOW);
      digitalWrite(blue, LOW);

    }
    if (incomingByte == '1') {
      digitalWrite(LED, HIGH);
    }

    if (incomingByte == '2') {
      myservo.write(0);
    }

    if (incomingByte == '3') {
      myservo.write(120);
    }

    if (incomingByte == '4') {
      digitalWrite(red, HIGH);
      digitalWrite(green, LOW);
      digitalWrite(blue, LOW);
    }

    if (incomingByte == '5') {
      digitalWrite(red, LOW);
      digitalWrite(green, HIGH);
      digitalWrite(blue, LOW);
    }

    if (incomingByte == '6') {
      digitalWrite(red, LOW);
      digitalWrite(green, LOW);
      digitalWrite(blue, HIGH);
    }
  }
}
