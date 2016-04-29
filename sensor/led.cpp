#include "led.h"
#if (ARDUINO >= 100)
#include <Arduino.h>
#else
#include <WProgram.h>
#endif

Led::Led(int pin, bool inverted/*= false*/) {
  this->pin = pin;
  this->inverted = inverted;
  pinMode(pin, OUTPUT);
  set(false);
}

Led::~Led() {
  pinMode(this->pin, INPUT);
}

void Led::toggle() {
  set(!state);
}

void Led::set(bool state) {
  this->state = state;
  digitalWrite(pin, state ? HIGH : LOW);
}

