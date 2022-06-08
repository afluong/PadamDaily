package io.padam_exercise.padamdaily.services

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class TalkBackAccessibilityService: AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("Accessibility_event", "Accessibility_event_start !")
        event?.source.apply {
            //performAction()
        }
    }

    override fun onInterrupt() {
        Log.d("Accessibility_event", "Accessibility_event_end !")
    }
}