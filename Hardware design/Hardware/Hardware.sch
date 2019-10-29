EESchema Schematic File Version 4
LIBS:Hardware-cache
EELAYER 29 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L RF_GSM:SIM900 U?
U 1 1 5D944796
P 9875 3175
F 0 "U?" H 9875 1486 50  0000 C CNN
F 1 "SIM900" H 9875 1395 50  0000 C CNN
F 2 "RF_GSM:SIMCom_SIM900" H 10375 1625 50  0001 C CNN
F 3 "http://simcom.ee/documents/SIM900/SIM900_Hardware%20Design_V2.05.pdf" H 7875 4475 50  0001 C CNN
	1    9875 3175
	1    0    0    -1  
$EndComp
$Comp
L 4xxx:4051 U?
U 1 1 5D9467F1
P 5425 2425
F 0 "U?" H 5969 2471 50  0000 L CNN
F 1 "4051" H 5969 2380 50  0000 L CNN
F 2 "" H 5425 2425 50  0001 C CNN
F 3 "http://www.intersil.com/content/dam/Intersil/documents/cd40/cd4051bms-52bms-53bms.pdf" H 5425 2425 50  0001 C CNN
	1    5425 2425
	1    0    0    -1  
$EndComp
$Comp
L MCU_Module:Adafruit_HUZZAH_ESP8266_breakout A?
U 1 1 5D95534D
P 7375 2275
F 0 "A?" H 7375 1386 50  0000 C CNN
F 1 "Adafruit_HUZZAH_ESP8266_breakout" H 7375 1295 50  0000 C CNN
F 2 "" H 7575 2875 50  0001 C CNN
F 3 "https://www.adafruit.com/product/2471" H 7675 2975 50  0001 C CNN
	1    7375 2275
	1    0    0    -1  
$EndComp
Wire Wire Line
	7975 2475 9175 2475
Wire Wire Line
	7975 2275 8975 2275
Wire Wire Line
	8975 2275 8975 2575
Wire Wire Line
	8975 2575 9175 2575
Wire Wire Line
	6775 2675 5925 2675
Wire Wire Line
	5925 2675 5925 1825
Wire Wire Line
	7975 2075 8325 2075
Wire Wire Line
	8325 2075 8325 1125
Wire Wire Line
	8325 1125 4625 1125
Wire Wire Line
	4625 1125 4625 2825
Wire Wire Line
	4625 2825 4925 2825
$Comp
L Pule_Sensor:SEN-11574 P?
U 1 1 5D946A52
P 3175 1925
F 0 "P?" H 3705 1971 50  0000 L CNN
F 1 "SEN-11574" H 3705 1880 50  0000 L CNN
F 2 "XDCR_SEN-11574" H 3175 1925 50  0001 L BNN
F 3 "" H 3175 1925 50  0001 L BNN
F 4 "SEN-11574" H 3175 1925 50  0001 L BNN "Field4"
F 5 "Unavailable" H 3175 1925 50  0001 L BNN "Field5"
F 6 "SparkFun" H 3175 1925 50  0001 L BNN "Field6"
F 7 "None" H 3175 1925 50  0001 L BNN "Field7"
F 8 "Pulse Sensor For Arduino" H 3175 1925 50  0001 L BNN "Field8"
	1    3175 1925
	-1   0    0    1   
$EndComp
Wire Wire Line
	3375 1825 4925 1825
Wire Wire Line
	4300 1925 4925 1925
$Comp
L Device:Battery BT?
U 1 1 5D947D4D
P 775 2500
F 0 "BT?" H 883 2546 50  0000 L CNN
F 1 "Battery" H 883 2455 50  0000 L CNN
F 2 "" V 775 2560 50  0001 C CNN
F 3 "~" V 775 2560 50  0001 C CNN
	1    775  2500
	1    0    0    -1  
$EndComp
Wire Wire Line
	7375 1475 7375 925 
Wire Wire Line
	7375 925  5425 925 
Wire Wire Line
	775  925  775  2300
Wire Wire Line
	3375 2025 3700 2025
Wire Wire Line
	3375 1925 3725 1925
Wire Wire Line
	3725 1925 3725 925 
Connection ~ 3725 925 
Wire Wire Line
	3725 925  775  925 
Wire Wire Line
	9875 5300 9875 4775
Wire Wire Line
	775  2700 775  5300
Wire Wire Line
	3700 2025 3700 5300
Connection ~ 3700 5300
Wire Wire Line
	7475 3075 7475 5300
Connection ~ 7475 5300
Wire Wire Line
	7475 5300 9875 5300
Wire Wire Line
	5525 3325 5525 5300
Connection ~ 5525 5300
Wire Wire Line
	5525 5300 7475 5300
Wire Wire Line
	3700 5300 4425 5300
Wire Wire Line
	4925 2725 4425 2725
Wire Wire Line
	4425 2725 4425 2925
Connection ~ 4425 5300
Wire Wire Line
	4425 5300 5425 5300
Wire Wire Line
	4925 2925 4425 2925
Connection ~ 4425 2925
Wire Wire Line
	4925 3025 4425 3025
Wire Wire Line
	4425 2925 4425 3025
Connection ~ 4425 3025
Wire Wire Line
	4425 3025 4425 5300
NoConn ~ 4925 2025
NoConn ~ 4925 2125
NoConn ~ 4925 2225
NoConn ~ 4925 2325
NoConn ~ 4925 2425
NoConn ~ 4925 2525
Wire Wire Line
	5425 3325 5425 5300
Connection ~ 5425 5300
Wire Wire Line
	5425 5300 5525 5300
Wire Wire Line
	5425 1525 5425 925 
Connection ~ 5425 925 
Wire Wire Line
	5425 925  4025 925 
NoConn ~ 7275 3075
NoConn ~ 6775 2475
NoConn ~ 6775 2375
NoConn ~ 6775 2175
NoConn ~ 6775 2075
NoConn ~ 6775 1975
NoConn ~ 7975 1875
NoConn ~ 7975 1975
NoConn ~ 7975 2175
NoConn ~ 7975 2375
NoConn ~ 7975 2575
NoConn ~ 7975 2675
NoConn ~ 7575 1475
NoConn ~ 7175 1475
$Comp
L Sensor_Temperature:TMP36xS U?
U 1 1 5D95ADE0
P 2775 3000
F 0 "U?" H 3319 3046 50  0000 L CNN
F 1 "TMP36xS" H 3319 2955 50  0000 L CNN
F 2 "Package_SO:SOIC-8_3.9x4.9mm_P1.27mm" H 2775 2550 50  0001 C CNN
F 3 "https://www.analog.com/media/en/technical-documentation/data-sheets/TMP35_36_37.pdf" H 2775 3000 50  0001 C CNN
	1    2775 3000
	1    0    0    -1  
$EndComp
Wire Wire Line
	775  5300 2775 5300
Wire Wire Line
	2775 2600 4025 2600
Wire Wire Line
	4025 2600 4025 925 
Connection ~ 4025 925 
Wire Wire Line
	4025 925  3725 925 
Wire Wire Line
	4300 1925 4300 3000
Wire Wire Line
	3275 3000 4300 3000
Wire Wire Line
	2775 3400 2775 5300
Connection ~ 2775 5300
Wire Wire Line
	2775 5300 3700 5300
NoConn ~ 2275 3000
Wire Wire Line
	7375 925  10075 925 
Wire Wire Line
	10075 925  10075 1575
Connection ~ 7375 925 
NoConn ~ 9675 1575
NoConn ~ 9875 1575
NoConn ~ 9175 1775
NoConn ~ 9175 1975
NoConn ~ 9175 2075
NoConn ~ 9175 2175
NoConn ~ 9175 2275
NoConn ~ 9175 2375
NoConn ~ 9175 2775
NoConn ~ 9175 2975
NoConn ~ 9175 3075
NoConn ~ 9175 3175
NoConn ~ 9175 3275
NoConn ~ 9175 3475
NoConn ~ 9175 3575
NoConn ~ 9175 3775
NoConn ~ 9175 3875
NoConn ~ 9175 4075
NoConn ~ 9175 4175
NoConn ~ 9175 4375
NoConn ~ 9175 4475
NoConn ~ 10575 1775
NoConn ~ 10575 1975
NoConn ~ 10575 2075
NoConn ~ 10575 2175
NoConn ~ 10575 2275
NoConn ~ 10575 2375
NoConn ~ 10575 2575
NoConn ~ 10575 2675
NoConn ~ 10575 2775
NoConn ~ 10575 2875
NoConn ~ 10575 2975
NoConn ~ 10575 3075
NoConn ~ 10575 3175
NoConn ~ 10575 3275
NoConn ~ 10575 3375
NoConn ~ 10575 3475
NoConn ~ 10575 3575
NoConn ~ 10575 3675
NoConn ~ 10575 3875
NoConn ~ 10575 4075
NoConn ~ 10575 4175
NoConn ~ 10575 4375
NoConn ~ 10575 4475
$EndSCHEMATC
