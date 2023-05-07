int val = 0;
int led = 12;
int PIR = 2;

void setup(){
	pinMode(led, OUTPUT);
	pinMode(PIR, INPUT);
	Serial.begin(9600);
}

void loop(){
	val = digitalRead(PIR);
	if(val)
	{
		digitalWrite(led, HIGH);
		delay(100);
		Serial.println("Motion detected");
	}
	else
	{
		digitalWrite(led, LOW);
		Serial.println("Motion not detected");
	}
}