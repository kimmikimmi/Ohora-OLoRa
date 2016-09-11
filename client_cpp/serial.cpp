#include <iostream>
#include <fstream>
#include <string>
using namespace std;
int main(int argc, char* argv[])
{
	//open arduino device file (linux)
 //        std::ofstream arduino;
	// arduino.open( "/dev/tty.usbmodem1431");

	// //write to it
 //    arduino << "Hello from C++!";
	// arduino.close();	
	
 std::ifstream ifs ("/dev/tty.usbmodem1431", std::ifstream::in);
  char c = ifs.get();
  cout << "kim" << endl;
  while (true) {
  	cout << "any" << endl;
    std::cout << c;
    c = ifs.get();
  }

  ifs.close();
	return 0;
}