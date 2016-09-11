// STL Headers
#include <iostream>
#include <arpa/inet.h>  
#include <assert.h>  
#include <errno.h>  
#include <netinet/in.h>  
#include <signal.h>  
#include <stdlib.h>  
#include <stdio.h>  
#include <string.h>  
#include <sys/types.h>  
#include <sys/socket.h>  
#include <sys/wait.h>  
#include <netdb.h>  
#include <unistd.h>  
 
// my headers
#include "serial2.h"
#include "kimsUtils.h"

using namespace std;

#define SA      struct sockaddr  
#define MAXLINE 4096 
#define MAXSUB  2000  
 
 
#define LISTENQ         1024  
  
#define CO_UPPER_BND 500

//Global variables
extern int h_errno;  
  
string my_buffer;
//----------------------

ssize_t process_http(int sockfd, char *host, char *page, char *poststr)  
{  
    char sendline[MAXLINE + 1], recvline[MAXLINE + 1];  
    ssize_t n;  
    snprintf(sendline, MAXSUB,  
         "POST %s HTTP/1.0\r\n"  
         "Host: %s\r\n"  
         "Content-type: application/x-www-form-urlencoded\r\n"  
         "Content-length: %d\r\n\r\n"  
         "%s", page, host, strlen(poststr), poststr);  
  
    write(sockfd, sendline, strlen(sendline));  
    cout << "here2.5" << endl;
    // while ((n = read(sockfd, recvline, MAXLINE)) > 0) {  
    //     recvline[n] = '\0';  
    //     printf("%s", recvline);  
    // }  
    cout << "here2" << endl;
    cout << "here2" << endl;
    return n;  
  
}  

int main(void)  
{  
    int sockfd;  
    struct sockaddr_in servaddr;  
  
    char **pptr;  
    //********** any change is possible.. Put any values here *******  
    char *hname = "localhost";  
    char *page = "/";  
    // char *poststr = "mode=login&user=cyh1704&password=cy34208263\r\n";  
    
    //char *poststr = "\r\n";  
    
    //char *poststr = "param1=jimin&param2=hyeonjun&param3=johan\r\n";  
    //*******************************************************  
  
    char str[200];  
    struct hostent *hptr;  
    if ((hptr = gethostbyname(hname)) == NULL) {  
        fprintf(stderr, " gethostbyname error for host: %s: %s",  
            hname, hstrerror(h_errno));  
        exit(1);  
    }  
    printf("hostname: %s\n", hptr->h_name);  
    if (hptr->h_addrtype == AF_INET  
        && (pptr = hptr->h_addr_list) != NULL) {  
        printf("address: %s\n",  
               inet_ntop(hptr->h_addrtype, *pptr, str,  
                 sizeof(str)));  
    } else {  
        fprintf(stderr, "Error call inet_ntop \n");  
    }  
  
    sockfd = socket(AF_INET, SOCK_STREAM, 0);  
    bzero(&servaddr, sizeof(servaddr));  
    servaddr.sin_family = AF_INET;  
    servaddr.sin_port = htons(80);  
    inet_pton(AF_INET, str, &servaddr.sin_addr);  
  
    connect(sockfd, (SA *) & servaddr, sizeof(servaddr));  
    
    // Connected to server..
    while(true) {

      SerialRead(my_buffer);

      //Print the content of Buffer as soon as data is passed by..
      cout << "------------------------------------" << endl;
      cout <<"Buffer : " << my_buffer << endl;
      cout << "------------------------------------" << endl << endl;

      // parsing process..
      string co = get_value_of(my_buffer, 0);
      string number = get_value_of(my_buffer, 1);
      string temp = get_value_of(my_buffer, 2);
      string smoke = get_value_of(my_buffer, 3);
      string fire = "no";

      /*Fire detection algorithm---------------------------------------------*/
      if(stoi(co) >= CO_UPPER_BND) fire = "yes";
      /*----------------------------------------------------------------------*/

      if(stoi(temp) > 30)
      cout << "------------------------------------" << endl;
      cout << "co : " << co << endl;
      cout << "# of people : " << number  << endl;
      cout << " temp : " << temp << endl;
      cout << " smoke : " << smoke << endl;
      cout << "------------------------------------" << endl << endl;


      //from std::string to JSON
      cout << strToJSON(temp, number, smoke, co, fire) << endl;
      string dest = strToJSON(temp, number, smoke, co, fire);
      dest += "\r\n"; // at the end of the message.

      char* poststr = new char[dest.size()+1];
      copy(dest.begin(), dest.end(), poststr);
      cout << poststr << endl;
      
      my_buffer.clear();

      process_http(sockfd, hname, page, poststr); 

      cout << "------------------------------------" << endl << endl;
    }
  
    close(sockfd);  
    exit(0);  
  
}  