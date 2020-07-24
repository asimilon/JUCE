/*
  ==============================================================================

   This file is part of the JUCE library.
   Copyright (c) 2020 - Raw Material Software Limited

   JUCE is an open source library subject to commercial or open-source
   licensing.

   By using JUCE, you agree to the terms of both the JUCE 5 End-User License
   Agreement and JUCE 5 Privacy Policy (both updated and effective as of the
   22nd April 2020).

   End User License Agreement: www.juce.com/juce-5-licence
   Privacy Policy: www.juce.com/juce-5-privacy-policy

   Or: You may also use this code under the terms of the GPL v3 (see
   www.gnu.org/licenses).

   JUCE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY, AND ALL WARRANTIES, WHETHER
   EXPRESSED OR IMPLIED, INCLUDING MERCHANTABILITY AND FITNESS FOR PURPOSE, ARE
   DISCLAIMED.

  ==============================================================================
*/

package com.rmsl.juce;

import android.database.ContentObserver;
import android.app.Activity;
import android.net.Uri;

//==============================================================================
public class SystemVolumeObserver extends ContentObserver
{
    private native void mediaSessionSystemVolumeChanged (long host);

    SystemVolumeObserver (Activity activityToUse, long hostToUse)
    {
        super (null);

        activity = activityToUse;
        host = hostToUse;
    }

    void setEnabled (boolean shouldBeEnabled)
    {
        if (shouldBeEnabled)
            activity.getApplicationContext ().getContentResolver ().registerContentObserver (android.provider.Settings.System.CONTENT_URI, true, this);
        else
            activity.getApplicationContext ().getContentResolver ().unregisterContentObserver (this);
    }

    @Override
    public void onChange (boolean selfChange, Uri uri)
    {
        if (uri.toString ().startsWith ("content://settings/system/volume_music"))
            mediaSessionSystemVolumeChanged (host);
    }

    private Activity activity;
    private long host;
}