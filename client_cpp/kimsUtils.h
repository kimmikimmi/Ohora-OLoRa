#include <iostream>
#include <cstring>
#include <vector>
#include <sstream>

using namespace std;


/*
Description : This function make a whole JSON string from target string
@param : string
@return :string
*/


string strToJSON(string& temp, string& member, string& smoke, string& co, string& fire) {
	//string json_str = "{ \"address\" : {";
	//json_str += "\"부산동래구온천3동아림베르빌\" : {";
	string json_str = "{ \"101\" : {"; 
	json_str += "\"co\" : \"200\", ";	
	json_str += "\"fire\" : \"no\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"2\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},";   

    json_str += "\"102\" : {"; 
	json_str += "\"co\" : \"200\", ";	
	json_str += "\"fire\" : \"no\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"3\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},";   

	json_str += "\"201\" : {"; 
	json_str += "\"co\" : \"200\", ";	
	json_str += "\"fire\" : \"no\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"5\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},";   

    json_str += "\"202\" : {"; 
	json_str += "\"co\" : \"no\", ";	
	json_str += "\"fire\" : \"yes\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"3\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},";   
    

	json_str += "\"301\" : {"; 
	json_str += "\"co\" : \"200\", ";	
	json_str += "\"fire\" : \"no\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"4\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},";   

    json_str += "\"302\" : {"; 
	json_str += "\"co\" : \"200\", ";	
	json_str += "\"fire\" : \"no\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"4\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},"; 

    json_str += "\"401\" : {"; 
	json_str += "\"co\" : \"200\", ";	
	json_str += "\"fire\" : \"no\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"3\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},"; 

    json_str += "\"402\" : {"; 
	json_str += "\"co\" : \"200\", ";	
	json_str += "\"fire\" : \"no\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"4\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},"; 

    json_str += "\"501\" : {"; 
	json_str += "\"co\" : \"200\", ";	
	json_str += "\"fire\" : \"no\", ";	
	json_str += "\"image1\" : \"image1\",";
	json_str += "\"image2\" : \"image2\",";		
    json_str += "\"member\" : \"3\",";   	
    json_str += "\"smoke\" : \"20\",";    	
	json_str += "\"temperature\" : \"30\"";	        	
    json_str += "},"; 

    json_str += "\"502\" : {"; 
    json_str += "\"co\" : ";
    json_str += "\"" + co + "\","; 
    json_str += "\"fire\" : ";
    json_str += "\"" + fire + "\","; 
    json_str += "\"image1\" : \"image1\",";    
    json_str += "\"image2\" : \"image2\",";    
    json_str += "\"member\" : ";
	json_str += "\"" + member + "\","; 
    //json_str += "\"smoke\" : \"20\",";
    json_str += "\"smoke\" : ";
    json_str += "\"" + smoke + "\","; 
    json_str += "\"temperature\" : ";    
    json_str += "\"" + temp + "\""; 
    json_str += "}}";  

	return json_str;
} 


string get_value_of(string& s, int flag) {
	string new_str(s);
	copy(s.begin()+1, s.end()-1, new_str.begin());
	std::vector<string> fields;

	stringstream ss(new_str);
	string token;
	int i=0;
	while (getline(ss, token, '@'))
	{
		fields.push_back(token);
		i++;
	}	
		
	
	switch(flag) {
		case 0: // 0 means returning # of people
			return fields[0];
		case 1: // 1 means returning temparature
			return fields[1];
		case 2: // 2 means returning smoke.
			return fields[2];
		case 3:
			return fields[3];
			;

	}
	return "";
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

/* Description : This functction parses the smoke of the target string.
@param : string
@return : string */
string getSmoke(string& target) {
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


/*

Description : This functction parses the number of people indoor of the target string.
@param : string
@return : string

*/
string getNumber(string& target) {
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