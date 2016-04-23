#ifndef MQ7_H
#define MQ7_H

class MQ7 {
public:
  MQ7(int pin);

  float readCO(float temperature, float humidity);
private:
  int pin;
};

#endif /* MQ7_H */

