package org.positive.sms.extension

import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import org.positive.sms.presentation.base.BaseActivity

fun Context.intentFor(
    clazz: Class<*>,
    vararg pairs: Pair<String, Any?>,
    applyBlock: (Intent.() -> Unit)? = null
) = Intent(this, clazz).apply {
    putExtras(bundleOf(*pairs))
    applyBlock?.invoke(this)
}

inline fun <reified T : BaseActivity<*>> Context.startWith(
    vararg pairs: Pair<String, Any?>
) = startActivity(intentFor(T::class.java, *pairs))

inline fun <reified T : BaseActivity<*>> Context.startOnTop(vararg pairs: Pair<String, Any?>) {
    val flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intentFor(T::class.java, *pairs) { addFlags(flags) })
}

inline fun <reified T : BaseActivity<*>> Context.startOnHome(vararg pairs: Pair<String, Any?>) {
    TaskStackBuilder.create(this)
        .addNextIntentWithParentStack(intentFor(T::class.java, *pairs))
        .startActivities()
}