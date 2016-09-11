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
#include <netdb.h>
#include <unistd.h>

#define SA struct sockaddr
#define MAXLINE 4096
#define MAXSUB 200

extern int h_errno;

int process_http(int sockfd, char *host, char *page, char *poststr, char *response) {
    char sendline[MAXLINE + 1], recvline[MAXLINE + 1];
    ssize_t n;
    snprintf(sendline, MAXSUB,
				"POST %s HTTP/1.0\r\n"
				"Host: %s\r\n"
				"Content-type: application/x-www-form-urlencoded\r\n"
				"content-lenth: %d\r\n\r\n"
				"%s", page, host, (int)strlen(poststr), poststr);
    write(sockfd, sendline, strlen(sendline));

	int offset = 0;
	while((n = read(sockfd, recvline, MAXLINE)) > 0) {
		recvline[n] = '\0';
		//printf("%s", recvline);
		strcpy(response + offset, recvline);
		offset += n;
	}
	return offset;
}	
int send_post(char * params, char* response) {
	int sockfd;
	struct sockaddr_in servaddr;

	char **pptr;
	char *hname = "cyh1704.dothome.co.kr";
	char *page = "cyh1704.dothome.co.kr/lora/lora.php";

	char str[50];
	struct hostent *hptr;
	if((hptr = gethostbyname(hname)) == NULL) {
		fprintf(stderr, " gethostbyname err for host: %s: %s",
				hname, hstrerror(h_errno));
		exit(1);
	}
	if(hptr->h_addrtype == AF_INET && (pptr = hptr->h_addr_list) != NULL) {
		//
	} else {
		fprintf(stderr, "Error call inet_ntop \n");
	}
	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	bzero(&servaddr, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_port = htons(80);
	inet_pton(AF_INET, str, &servaddr.sin_addr);

	connect(sockfd, (SA *) & servaddr, sizeof(servaddr));
	process_http(sockfd, hname, page, params, response);
	close(sockfd);
}

int main(void) {
	char *poststr = "mode=login&user=cyh1704&password=cy34208263\r\n";
	char response[4096];

	int result = send_post(poststr, response);
	printf("#### RESPONSE ####\n%s\n", response);
	return result;
}











