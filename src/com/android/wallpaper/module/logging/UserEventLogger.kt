/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.wallpaper.module.logging

import android.content.Intent
import android.stats.style.StyleEnums
import androidx.annotation.IntDef

/** Interface for logging user events in the wallpaper picker. */
interface UserEventLogger {

    fun logAppLaunched(launchSource: Intent)

    fun logActionClicked(collectionId: String, actionLabelResId: Int)

    fun logIndividualWallpaperSelected(collectionId: String)

    fun logCategorySelected(collectionId: String)

    /** Log current existing snapshot data. */
    fun logSnapshot()

    /**
     * Logs the behavior when applying wallpaper.
     *
     * @param collectionId wallpaper category.
     * @param wallpaperId wallpaper id.
     * @param effects effects set with wallpaper.
     */
    fun logWallpaperSet(collectionId: String?, wallpaperId: String?, effects: String?)

    fun logWallpaperSetResult(@WallpaperSetResult result: Int)

    /**
     * Logs that a particular failure to set an individual wallpaper occurred for the given reason.
     */
    fun logWallpaperSetFailureReason(@WallpaperSetFailureReason reason: Int)

    /**
     * Logs the number of daily rotations that occurred in the last week if daily rotation has been
     * enabled for at least a week.
     */
    fun logNumDailyWallpaperRotationsInLastWeek()

    /**
     * Logs the number of daily rotations that occurred during the previous day (24 hour period
     * midnight to midnight) if daily rotation has been enabled at least since midnight yesterday.
     */
    fun logNumDailyWallpaperRotationsPreviousDay()

    /** Logs the last-known status of daily wallpapers on the device. */
    fun logDailyWallpaperRotationStatus(status: Int)

    /** Logs the result of an operation to update the daily wallpaper. */
    fun logDailyWallpaperSetNextWallpaperResult(@DailyWallpaperUpdateResult result: Int)

    /**
     * Logs that a particular crash occurred when trying to set the next wallpaper in a daily
     * rotation.
     */
    fun logDailyWallpaperSetNextWallpaperCrash(@DailyWallpaperUpdateCrash crash: Int)

    /**
     * Logs that the request for metadata for the next wallpaper in a daily rotation failed for the
     * given reason.
     */
    fun logDailyWallpaperMetadataRequestFailure(@DailyWallpaperMetadataFailureReason reason: Int)

    /** Logs that the "refresh daily wallpaper" button was clicked. */
    fun logRefreshDailyWallpaperButtonClicked()

    /** Logs the number of consecutive days that daily rotation was attempted but failed. */
    fun logNumDaysDailyRotationFailed(days: Int)

    /**
     * Logs the number of consecutive days that daily rotation was not attempted but should have
     * been attempted ("network conditions not met" doesn't count).
     */
    fun logNumDaysDailyRotationNotAttempted(days: Int)

    /** Logs that the StandalonePreviewActivity was launched. */
    fun logStandalonePreviewLaunched()

    /**
     * Logs whether the image URI passed to StandalonePreviewActivity came properly preconfigured
     * with read permissions.
     */
    fun logStandalonePreviewImageUriHasReadPermission(isReadPermissionGranted: Boolean)

    /**
     * Logs whether the user approved the runtime dialog to grant this app READ_MEDIA_IMAGES
     * permission in order to open an image URI.
     */
    fun logStandalonePreviewStorageDialogApproved(isApproved: Boolean)

    /** Logs the presentation mode of the current wallpaper. */
    fun logWallpaperPresentationMode()

    /** Logs that the app was restored from a backup set. */
    fun logRestored()

    /** Logs the action related to effect. */
    fun logEffectApply(
        effect: String,
        @EffectStatus status: Int,
        timeElapsedMillis: Long,
        resultCode: Int,
    )

    /** Logs the effect probe result. */
    fun logEffectProbe(effect: String, @EffectStatus status: Int)

    /** Logs the effect foreground download event. */
    fun logEffectForegroundDownload(
        effect: String,
        @EffectStatus status: Int,
        timeElapsedMillis: Long,
    )

    companion object {
        const val ROTATION_STATUS_NOT_ATTEMPTED = 0
        const val ROTATION_STATUS_FAILED = 5
        const val WALLPAPER_SET_RESULT_SUCCESS = 0
        const val WALLPAPER_SET_RESULT_FAILURE = 1
        const val DAILY_WALLPAPER_UPDATE_RESULT_SUCCESS = 0
        const val DAILY_WALLPAPER_UPDATE_RESULT_FAILURE_LOAD_METADATA = 1
        const val DAILY_WALLPAPER_UPDATE_RESULT_FAILURE_LOAD_BITMAP = 2
        const val DAILY_WALLPAPER_UPDATE_RESULT_FAILURE_SET_WALLPAPER = 3
        const val DAILY_WALLPAPER_UPDATE_RESULT_FAILURE_CRASH = 4
        const val WALLPAPER_SET_FAILURE_REASON_OTHER = 0
        const val WALLPAPER_SET_FAILURE_REASON_OOM = 1
        const val DAILY_WALLPAPER_UPDATE_CRASH_GENERIC = 0
        const val DAILY_WALLPAPER_UPDATE_CRASH_OOM = 1
        const val DAILY_WALLPAPER_METADATA_FAILURE_UNKNOWN = 0
        const val DAILY_WALLPAPER_METADATA_FAILURE_NO_CONNECTION = 1
        const val DAILY_WALLPAPER_METADATA_FAILURE_PARSE_ERROR = 2
        const val DAILY_WALLPAPER_METADATA_FAILURE_SERVER_ERROR = 3
        const val DAILY_WALLPAPER_METADATA_FAILURE_TIMEOUT = 4

        /** Possible results of a "set wallpaper" operation. */
        @IntDef(WALLPAPER_SET_RESULT_SUCCESS, WALLPAPER_SET_RESULT_FAILURE)
        @Retention(AnnotationRetention.SOURCE)
        annotation class WallpaperSetResult

        /** Possible results of an operation to set the next wallpaper in a daily rotation. */
        @IntDef(
            DAILY_WALLPAPER_UPDATE_RESULT_SUCCESS,
            DAILY_WALLPAPER_UPDATE_RESULT_FAILURE_LOAD_METADATA,
            DAILY_WALLPAPER_UPDATE_RESULT_FAILURE_LOAD_BITMAP,
            DAILY_WALLPAPER_UPDATE_RESULT_FAILURE_SET_WALLPAPER,
            DAILY_WALLPAPER_UPDATE_RESULT_FAILURE_CRASH
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class DailyWallpaperUpdateResult

        /** Possible reasons setting an individual wallpaper failed. */
        @IntDef(WALLPAPER_SET_FAILURE_REASON_OTHER, WALLPAPER_SET_FAILURE_REASON_OOM)
        @Retention(AnnotationRetention.SOURCE)
        annotation class WallpaperSetFailureReason

        /**
         * Possible crash types of a crashing failed "set next wallpaper" operation when daily
         * rotation is enabled and trying to set the next wallpaper.
         */
        @IntDef(DAILY_WALLPAPER_UPDATE_CRASH_GENERIC, DAILY_WALLPAPER_UPDATE_CRASH_OOM)
        @Retention(AnnotationRetention.SOURCE)
        annotation class DailyWallpaperUpdateCrash

        /**
         * Possible reasons for a request for "next wallpaper" metadata in a daily rotation to fail.
         */
        @IntDef(
            DAILY_WALLPAPER_METADATA_FAILURE_UNKNOWN,
            DAILY_WALLPAPER_METADATA_FAILURE_NO_CONNECTION,
            DAILY_WALLPAPER_METADATA_FAILURE_PARSE_ERROR,
            DAILY_WALLPAPER_METADATA_FAILURE_SERVER_ERROR,
            DAILY_WALLPAPER_METADATA_FAILURE_TIMEOUT
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class DailyWallpaperMetadataFailureReason

        /**
         * Possible actions for cinematic effect. These actions would be used for effect apply,
         * effect probe, effect download.
         */
        @IntDef(
            StyleEnums.EFFECT_PREFERENCE_UNSPECIFIED,
            StyleEnums.EFFECT_APPLIED_ON_SUCCESS,
            StyleEnums.EFFECT_APPLIED_ON_FAILED,
            StyleEnums.EFFECT_APPLIED_OFF,
            StyleEnums.EFFECT_APPLIED_ABORTED,
            StyleEnums.EFFECT_APPLIED_STARTED
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class EffectStatus
    }
}
