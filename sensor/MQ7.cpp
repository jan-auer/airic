#include "MQ7.h"
#if ARDUINO >= 100
 #include "Arduino.h"
#else
 #include "WProgram.h"
#endif

MQ7::MQ7(int pin) {
  this->pin = pin;
}

float MQ7::readCO(float temperature, float humidity) {
  return analogRead(pin);
}

