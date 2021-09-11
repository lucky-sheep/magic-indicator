package com.hunliji.ext_master.core

import android.app.Application
import com.hunliji.integration_mw.ConfigModule
import kotlin.properties.Delegates

/**
 * GlobalConfiguration
 *
 * @author wm
 * @date 19-8-19
 */
internal var app: Application by Delegates.notNull()

class GlobalConfiguration : ConfigModule {
    override fun injectApp(application: Application) {
        app = application
    }

    override fun terminateApp(application: Application) {

    }

    override fun getPriority() = 5
}
