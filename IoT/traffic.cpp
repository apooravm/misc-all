int red = 6;
int green = 7;
int yellow = 8;

void trafficLight(int currentLED) {
	digitalWrite(red, LOW);
	digitalWrite(yellow, LOW);
	digitalWrite(green, LOW);
	digitalWrite(currentLED, HIGH);
}

void setup() {
	pinMode(red, OUTPUT);
	pinMode(yellow, OUTPUT);
	pinMode(green, OUTPUT);
}

void loop() {
	trafficLight(red);
	delay(1000);
	trafficLight(yellow);
	delay(1000);
	trafficLight(green);
	delay(2000);
}
