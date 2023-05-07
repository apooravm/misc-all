#include <Servo.h>
Servo servo;

void setup() {
	servo.attach(9);
}

void loop() {
	for (int pos = 10; pos < 170; pos += 10) {
		servo.write(pos);
		delay(100);
	}
	delay(500);
}
