#include <iostream>
#include <cstring>

using namespace std;


/*
Description : This function make a whole JSON string from target string
@param : string
@return :string
*/


string strToJSON(string& temp) {
	//string json_str = "{ \"address\" : {";
	//json_str += "\"부산동래구온천3동아림베르빌\" : {";
	string json_str = "{ \"101\" : {"; 
	json_str += "\"fire\" : \"yes\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"7\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},";    	
    json_str += "\"102\" : {"; 
    json_str += "\"fire\" : \"no\",";
    json_str += "\"image1\" : \"image1\",";    
    json_str += "\"image2\" : \"image2\",";    
    json_str += "\"member\" : \"4\",";    
    json_str += "\"smoke\" : \"20\",";
    json_str += "\"temperature\" : ";    
    json_str += "\"" + temp + "\""; 
    json_str += "}}";  

	return json_str;
} 

/*

Description : This functction parses the temperature of the target string.
@param : string
@return : string

*/
string getTemp(string& target) {
	string temp_str("");
	
	int len = target.size();
	for(int i=1; i<len; i++) {
		if(target[i] == '#')
			continue; 
		else if(target[i] == '@')
			break;
		else
			temp_str += target[i];
	}

	return temp_str;
}
