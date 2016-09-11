//
//  main.cpp
//  FlameDetection

#include "common.h"
#include "VideoHandler.h"
#include "FlameDetector.h"

#ifdef TRAIN_MODE
bool trainComplete = false;
#endif

VideoHandler* videoHandler = NULL;

int main(int argc, const char* argv[])
{
//    VideoHandler handler("/Users/Jaden/Documents/Cpp/OpenCVTest/OpenCVTest/images/yohan2.gif");
    VideoHandler handler("/Users/Jaden/flame-detection-system/images/frame_hlow.mp4"); //시연 1
    //VideoHandler handler("/Users/Jaden/flame-detection-system/clips/6.avi"); //시연 2
    videoHandler = &handler;
    
    int ret = handler.handle();
    
    switch (ret) {
    case VideoHandler::STATUS_FLAME_DETECTED:
        cout << "Flame detected." << endl;
        break;
    case VideoHandler::STATUS_OPEN_CAP_FAILED:
        cout << "Open capture failed." << endl;
        break;
    case VideoHandler::STATUS_NO_FLAME_DETECTED:
        cout << "No flame detected." << endl;
        break;
    default:
        break;
    }

    return 0;
}
