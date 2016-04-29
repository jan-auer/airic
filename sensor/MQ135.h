#ifndef MQ135_H
#define MQ135_H

class MQ135 {
public:
  MQ135(int pin);

  float readCO2(float temperature, float humidity);
private:
  int pin;
};

#endif /* MQ135_H */

