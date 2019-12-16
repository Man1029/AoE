/*
 * Copyright 2019 The AoE Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taobao.android.mnn;

import android.graphics.Bitmap;
import android.util.Log;

public class MNNNetNative {
    // load libraries
    static {
        System.loadLibrary("MNN");
        try {
            System.loadLibrary("MNN_CL");
            System.loadLibrary("MNN_GL");
            System.loadLibrary("MNN_Vulkan");
        } catch (Throwable ce) {
            Log.w("MNN", "load MNN GPU so exception=%s", ce);
        }
        System.loadLibrary("mnncore");
    }

    //Net
    public static native long nativeCreateNetFromFile(String modelName);

    public static native long nativeCreateNetFromBuffer(byte[] bufs, int size);

    public static native long nativeReleaseNet(long netPtr);


    //Session
    public static native long nativeCreateSession(long netPtr, int forwardType, int numThread, String[] saveTensors, String[] outputTensors);

    public static native void nativeReleaseSession(long netPtr, long sessionPtr);

    public static native int nativeRunSession(long netPtr, long sessionPtr);

    public static native int nativeRunSessionWithCallback(long netPtr, long sessionPtr, String[] nameArray, long[] tensorAddr);

    public static native int nativeReshapeSession(long netPtr, long sessionPtr);

    public static native long nativeGetSessionInput(long netPtr, long sessionPtr, String name);

    public static native long nativeGetSessionOutput(long netPtr, long sessionPtr, String name);


    //Tensor
    public static native void nativeReshapeTensor(long netPtr, long tensorPtr, int[] dims);

    public static native int[] nativeTensorGetDimensions(long tensorPtr);

    public static native void nativeSetInputIntData(long netPtr, long tensorPtr, int[] data);

    public static native void nativeSetInputFloatData(long netPtr, long tensorPtr, float[] data);


    //If dest is null, return length
    public static native int nativeTensorGetData(long tensorPtr, float[] dest);

    public static native int nativeTensorGetIntData(long tensorPtr, int[] dest);

    public static native int nativeTensorGetUINT8Data(long tensorPtr, byte[] dest);


    //ImageProcess
    public static native boolean nativeConvertBitmapToTensor(Bitmap srcBitmap, long tensorPtr, int destFormat, int filterType, int wrap, float[] matrixValue, float[] mean, float[] normal);

    public static native boolean nativeConvertBufferToTensor(byte[] bufferData, int width, int height, long tensorPtr,
                                                             int srcFormat, int destFormat, int filterType, int wrap, float[] matrixValue, float[] mean, float[] normal);

}
