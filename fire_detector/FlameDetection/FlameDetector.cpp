//
//  FlameDetector.cpp
//  FlameDetection


#include "FlameDetector.h"

FlameDetector::FlameDetector()
: mFrameCount(0)
, mFlameCount(0)
, mTrack(false)
{
}

bool FlameDetector::detect(const Mat& frame)
{
    mFrame = frame;
    
    clock_t start, finish;
    if(++mFrameCount > SKIP_FRAME_COUNT) {
        mTrack = true;
        start = clock();
    }
    
    mExtractor.extract(mFrame, mTargetMap, mTrack);
    if (mTrack) {
        mAnalyzer.analyze(mFrame, mTargetMap);
        bool result = mDecider.decide(mFrame, mTargetMap);
        cout << "result = " << result << endl;
        finish = clock();
        cout << "duration: " << 1.0 * (finish - start) / CLOCKS_PER_SEC << endl;
        if (result) {
            mFlameCount++;
        }
        cout << "frame: " << (mFrameCount - SKIP_FRAME_COUNT) << ", flame: " << mFlameCount << endl;
        cout << "detection rate: " << 1.0 * mFlameCount / (mFrameCount - SKIP_FRAME_COUNT) << endl;
        return result;
    }
    
    return false;
}
