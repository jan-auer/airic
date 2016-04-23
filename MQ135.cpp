#include "MQ135.h"
#if ARDUINO >= 100
 #include "Arduino.h"
#else
 #include "WProgram.h"
#endif

MQ135::MQ135(int pin) {
  this->pin = pin;
}

float MQ135::readCO2(float temperature, float humidity) {
  return analogRead(pin);
}

