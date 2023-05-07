import RPi.GPIO as GPIO
from time import sleep

led = 8

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(led, GPIO.OUT)

while True:
	GPIO.output(led, GPIO.HIGH)
	sleep(0.5)
	GPIO.output(led, GPIO.LOW)
	sleep(0.5)
