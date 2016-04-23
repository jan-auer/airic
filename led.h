#ifndef LED_H
#define LED_H

class Led {
public:
  Led(int pin, bool inverted = false);
 ~Led();

  void toggle();
  void set(bool state);
private:
  int pin;
  bool inverted;
  bool state;
};

#endif /* LED_H */

