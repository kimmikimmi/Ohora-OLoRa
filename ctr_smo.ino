
const int gasPin = A0; //가스센서 아웃을 아두이노 A0로 설정
 
 
void setup()
{
    Serial.begin(9600); //시리얼포트 설정
}
 
void loop()
{
    Serial.println(analogRead(gasPin)); //센서값을 시리얼모니터로 전송
    
    if (analogRead(gasPin) > 400)   // 가스 검출 시(자신의 센서 감도에 알맞게 조절필요)
    {                                     
        tone(12,2000,1000); // 피에조 ON (주파수 2000으로 1초간 울리기)  
    }  
 
    delay(1000);
}