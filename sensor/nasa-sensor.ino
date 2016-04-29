#include "led.h"
#include "SHT1x.h"
#include "MQ7.h"
#include "MQ135.h"

#define SHT1x_DATA_PIN   2
#define SHT1x_CLK_PIN    3
#define LED_PIN         13
#define MQ7_PIN          7
#define MQ135_PIN        6

Led led(LED_PIN);
SHT1x sht1x(SHT1x_DATA_PIN, SHT1x_CLK_PIN);
MQ7 mq7(MQ7_PIN);
MQ135 mq135(MQ135_PIN);

struct {
  float temperature;
  float humidity;
  float co;
  float co2;
} data;

void readData() {
  data.temperature = sht1x.readTemperatureC();
  data.humidity = sht1x.readHumidity();
  data.co = mq7.readCO(data.temperature, data.humidity);
  data.co2 = mq135.readCO2(data.temperature, data.humidity);
}

void setup() {
   Serial.begin(38400);
   Serial.println("nasa-sensor");
}

void loop() {
  led.set(true);
  readData();
  led.set(false);

  Serial.print("[");
  Serial.print(data.temperature);
  Serial.print(",");
  Serial.print(data.humidity);
  Serial.print(",");
  Serial.print(data.co);
  Serial.print(",");
  Serial.print(data.co2);
  Serial.println("]");

  delay(1000);
}

