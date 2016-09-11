#include <iostream>
#include <cmath>
#include <algorithm>
#include <cstring>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

#define TOUCHPORT "/dev/tty.usbserial-AL0223TB" // waspmote
//#define TOUCHPORT "/dev/tty.usbmodem1431" // COM1
#define BAUDRATE B57600
#define BUFSIZE 64
#define TRUE 1
#define FALSE 0
#define U32 unsigned int

// Debug defines
#define Debug
#define SerialSet

// Functions 

void SerialRead(string& my_buffer)
{
    char buf[BUFSIZE]={0};
    int fd=0, r=0;                // file descript
    //int stat=0, cnt=0;
     int i = 0;    
    #ifdef Debug
        printf("SerialRead ---> Start !!\n");
        printf("TouchPort Open !! \n");
    #endif


    fd = open(TOUCHPORT,O_RDONLY);
    //fd = open(TOUCHPORT,O_RDONLY | O_NONBLOCK);

    if ( fd < 0 )
    {
        perror(TOUCHPORT);
        //close(fd);
        exit(-1);
    }
#ifdef SerialSet
    #ifdef Debug
        printf("SerialSet .. Start !!\n");
    #endif

    struct termios tio;
    tcgetattr(fd,&tio);
    tio.c_cflag = BAUDRATE | CS8 | CLOCAL | CREAD;
    tio.c_iflag = IGNPAR | IGNBRK | IXANY;
    tio.c_oflag = 0;
    tio.c_lflag &= (~ISIG);
    tio.c_lflag &= (~ICANON);
    tio.c_cc[VMIN] = 0;
    tio.c_cc[VTIME] = 0;
    tcsetattr(fd,TCSANOW,&tio);

    #ifdef Debug
    printf("SerialSet .. End !!\n");
    #endif
#endif  // SerialSet

#ifdef Debug
    printf("TouchPort descript ==> %d\n",fd);
    printf("Read Call !! \n");
#endif

    while(1)
    {
        //#ifdef Debug
        //    printf("Buf Read !! \n");
        //#endif

        r = read (fd, buf,sizeof(buf));

        //#ifdef Debug
        //    printf("read descript ===> %d\n",r);
        //#endif

        if( r <= 0)
        {
            //perror("read() fail !!");
            //close(r);
            //exit(-1);
            continue;
        }
        //printf("%d\n",r);
        for ( i = 0; i < r; i++ )
        {
            my_buffer += buf[i];
            //printf("buf[%d]= %c\n",i,buf[i]);
            
            if(my_buffer[0] != '#') my_buffer.clear();
            if(my_buffer.size()>2 && my_buffer[my_buffer.size()-1] == '#' ) {
              //  cout << my_buffer << endl;
                //my_buffer.clear();
                return;
            }
            // if ( buf[i] == 0xffffff80 )
            // {
            //     printf("x = %d , y = %d\n",(((2047-((((int)buf[i+3]) * 128) + ((int)buf[i+4]))) *800) /2047),
            //     (((2047-((((int)buf[i+1]) * 128) + ((int)buf[i+2]))) * 600)/2047) );
                        
            // }
        }

        memset(buf,0,sizeof(buf));
    } 

} 

