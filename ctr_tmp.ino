#include <DHT11.h>    //라이브러리 불러옴

int pin=2;            //Signal 이 연결된 아두이노의 핀번호
DHT11 dht11(pin);   

char buffer[256];     
int cnt = 0;

float expect = 0;

void setup()
{   
    Serial.begin(9600); //통신속도 설정
    Serial.print("I'm Arduino! and I got ready!!!");
}
 
void loop()
{
  int err;
  float temp, humi;
  String buf_str = "#";
 
  // String buffer = "";
  if((err=dht11.read(humi, temp))==0) //온도, 습도 읽어와서 표시
  {
    //Serial.println(cnt);
    //Serial.println(temp);
    expect += temp;
    if(cnt == 10) {
        expect /= cnt;

        //Serial.print(expect);
        buf_str.concat(expect);
        buf_str.concat('@');
        buf_str.concat('#');
        Serial.print(buf_str); 
        cnt = 0;
        expect = 0;
    } 

    cnt++;
    //Serial.print(" humidity:");
    //Serial.print(humi);
    //Serial.println();
  }
  else                                //에러일 경우 처리
  {
    // Serial.println();
    // Serial.print("Error No :");
    // Serial.print(err);
    // Serial.println();    
  }
  delay(1000);                        //1초마다 측정
   // Serial.println(analogRead(gasPin)); //센서값을 시리얼모니터로 전송
    
    // if (analogRead(gasPin) > 400)   // 가스 검출 시(자신의 센서 감도에 알맞게 조절필요)
    // {                                     
    //     tone(12,2000,1000); // 피에조 ON (주파수 2000으로 1초간 울리기)  
    // }  
 
    // delay(1000);
        // send data only when you receive data:
    
    // Serial.write("Y");
    // delay(1000);
    //if (Serial.available() > 0) {

    // read the data
        //Serial.readBytes(buffer, 256);
                
    // say what you got:

        //Serial.print(buffer[0]);

    //}
}
 



